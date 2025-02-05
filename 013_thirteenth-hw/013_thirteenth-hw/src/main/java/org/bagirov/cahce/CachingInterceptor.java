package org.bagirov.cahce;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Перехватчик вызовов методов для реализации механизма кэширования.
 */
public class CachingInterceptor implements InvocationHandler {
    private final Object targetObject;
    private final String baseDirectory;
    private final Map<String, Object> cacheStorage = new ConcurrentHashMap<>();
    private final Map<String, ReentrantLock> keyLocks = new ConcurrentHashMap<>();

    public CachingInterceptor(Object targetObject, String baseDirectory) {
        this.targetObject = targetObject;
        this.baseDirectory = baseDirectory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
        Memoize cachingConfig = method.getAnnotation(Memoize.class);
        if (cachingConfig == null) {
            return method.invoke(targetObject, parameters);
        }

        String cacheId = generateCacheId(method, parameters, cachingConfig.keyFields());
        ReentrantLock lock = keyLocks.computeIfAbsent(cacheId, id -> new ReentrantLock());

        if (cachingConfig.strategy() == CacheStrategy.MEMORY) {
            return processMemoryCache(cacheId, method, parameters, cachingConfig, lock);
        } else {
            return processFileCache(cacheId, method, parameters, cachingConfig, lock);
        }
    }

    private Object processMemoryCache(String cacheId, Method method, Object[] parameters, Memoize cachingConfig, ReentrantLock lock)
            throws Throwable {
        lock.lock();
        try {
            if (cacheStorage.containsKey(cacheId)) {
                System.out.println("Возвращаю результат из кэша в памяти: " + cacheId);
                return cacheStorage.get(cacheId);
            }
            Object outcome = method.invoke(targetObject, parameters);
            Object limitedOutcome = limitListSize(outcome, cachingConfig.maxElements());
            cacheStorage.put(cacheId, limitedOutcome);
            return limitedOutcome;
        } finally {
            lock.unlock();
        }
    }

    private Object processFileCache(String cacheId, Method method, Object[] parameters, Memoize cachingConfig, ReentrantLock lock)
            throws Throwable {
        String prefix = cachingConfig.prefix().isEmpty() ? method.getName() : cachingConfig.prefix();
        String filePath = baseDirectory + File.separator + prefix + "_" + cacheId + ".cache";
        File storageFile = new File(filePath);

        lock.lock();
        try {
            if (storageFile.exists()) {
                System.out.println("Возвращаю результат из файла: " + filePath);
                return readDataFromFile(storageFile, cachingConfig.compress());
            }
            Object outcome = method.invoke(targetObject, parameters);
            Object limitedOutcome = limitListSize(outcome, cachingConfig.maxElements());
            writeDataToFile(limitedOutcome, storageFile, cachingConfig.compress());
            return limitedOutcome;
        } finally {
            lock.unlock();
        }
    }

    private String generateCacheId(Method method, Object[] parameters, Class<?>[] identityCriteria) {
        StringBuilder identifier = new StringBuilder(method.getName());
        if (parameters != null) {
            List<Class<?>> criteria = Arrays.asList(identityCriteria);
            for (Object param : parameters) {
                if (identityCriteria.length == 0 || criteria.contains(param.getClass())) {
                    identifier.append("_").append(param);
                }
            }
        }
        return identifier.toString();
    }

    private Object limitListSize(Object result, int maxSize) {
        if (result instanceof List<?> list && list.size() > maxSize) {
            return list.subList(0, maxSize);
        }
        return result;
    }

    private void writeDataToFile(Object data, File storageFile, boolean compress) throws IOException {
        if (!(data instanceof Serializable)) {
            throw new IllegalArgumentException("Данные должны реализовывать Serializable.");
        }

        File parentDir = storageFile.getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Не удалось создать директории: " + parentDir.getAbsolutePath());
        }

        if (compress) {
            // Используем try-with-resources для управления ZipOutputStream, но ObjectOutputStream создаём вручную
            try (FileOutputStream fos = new FileOutputStream(storageFile);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                zos.putNextEntry(new ZipEntry("cache_entry"));

                // Создаём ObjectOutputStream без отдельного try-with-resources, чтобы не закрывать zos раньше времени
                ObjectOutputStream oos = new ObjectOutputStream(zos);
                oos.writeObject(data);
                oos.flush(); // принудительно сбрасываем данные в ZipOutputStream

                // Закрываем запись в текущем элементе архива
                zos.closeEntry();
            }
        } else {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
                oos.writeObject(data);
                System.out.println("Успешно сохранён файл: " + storageFile.getAbsolutePath());
            }
        }
    }


    private Object readDataFromFile(File storageFile, boolean compress) throws IOException, ClassNotFoundException {
        if (compress) {
            try (ZipInputStream zipStream = new ZipInputStream(new FileInputStream(storageFile))) {
                ZipEntry entry = zipStream.getNextEntry();
                if (entry == null) {
                    throw new EOFException("В сжатом файле отсутствуют записи: " + storageFile.getName());
                }
                try (ObjectInputStream objectStream = new ObjectInputStream(zipStream)) {
                    Object data = objectStream.readObject();
                    System.out.println("Успешно прочитан сжатый файл: " + storageFile.getAbsolutePath());
                    return data;
                }
            }
        } else {
            try (ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(storageFile))) {
                return objectStream.readObject();
            }
        }
    }
}
