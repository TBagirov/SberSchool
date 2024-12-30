package org.bagirov.model.task1;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.zip.*;

public class CacheHandler implements InvocationHandler {
    private final Object targetService;
    private final String cacheDirectory;
    private final Map<String, Object> memoryCache = new HashMap<>();

    public CacheHandler(Object targetService, String cacheDirectory) {
        this.targetService = targetService;
        this.cacheDirectory = cacheDirectory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
        Cache cacheAnnotation = method.getAnnotation(Cache.class);

        if (cacheAnnotation == null) {
            return method.invoke(targetService, parameters);
        }

        String key = generateCacheKey(method, parameters, cacheAnnotation.identityBy());
        if (cacheAnnotation.cacheType() == CacheType.IN_MEMORY) {
            return processInMemoryCache(key, method, parameters, cacheAnnotation);
        } else {
            return processFileCache(key, method, parameters, cacheAnnotation);
        }
    }

    private Object processInMemoryCache(String key, Method method, Object[] parameters, Cache cacheAnnotation) throws Throwable {
        if (memoryCache.containsKey(key)) {
            System.out.println("Fetching from in-memory cache: " + key);
            return memoryCache.get(key);
        }

        Object result = method.invoke(targetService, parameters);
        Object limitedResult = limitListSizeIfNeeded(result, cacheAnnotation.listLimit());
        memoryCache.put(key, limitedResult);
        return limitedResult;
    }

    private Object processFileCache(String key, Method method, Object[] parameters, Cache cacheAnnotation) throws Throwable {
        String filePath = cacheDirectory + File.separator +
                (cacheAnnotation.fileNamePrefix().isEmpty() ? method.getName() : cacheAnnotation.fileNamePrefix()) + "_" + key + ".cache";
        File cacheFile = new File(filePath);

        if (cacheFile.exists()) {
            System.out.println("Fetching from file cache: " + filePath);
            return readFromFile(cacheFile, cacheAnnotation.zip());
        }

        Object result = method.invoke(targetService, parameters);
        Object limitedResult = limitListSizeIfNeeded(result, cacheAnnotation.listLimit());
        writeToFile(limitedResult, cacheFile, cacheAnnotation.zip());
        return limitedResult;
    }

    private String generateCacheKey(Method method, Object[] parameters, Class<?>[] identityClasses) {
        StringBuilder keyBuilder = new StringBuilder(method.getName());
        if (parameters != null) {
            for (Object parameter : parameters) {
                if (identityClasses.length == 0 || Arrays.asList(identityClasses).contains(parameter.getClass())) {
                    keyBuilder.append("_").append(parameter);
                }
            }
        }
        return keyBuilder.toString();
    }

    private Object limitListSizeIfNeeded(Object result, int limit) {
        if (result instanceof List<?> list && list.size() > limit) {
            return list.subList(0, limit);
        }
        return result;
    }

    private void writeToFile(Object result, File file, boolean shouldZip) throws IOException {
        if (!(result instanceof Serializable)) {
            throw new IllegalArgumentException("Result must implement Serializable interface.");
        }

        if (shouldZip) {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file))) {
                zipOutputStream.putNextEntry(new ZipEntry("cache"));

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(zipOutputStream);
                objectOutputStream.writeObject(result);
                objectOutputStream.flush();

                zipOutputStream.closeEntry();
                System.out.println("Successfully written ZIP file: " + file.getAbsolutePath());
            }
        } else {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
                objectOutputStream.writeObject(result);
                System.out.println("Successfully written file: " + file.getAbsolutePath());
            }
        }
    }

    private Object readFromFile(File file, boolean shouldZip) throws IOException, ClassNotFoundException {
        if (shouldZip) {
            try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
                ZipEntry entry = zipInputStream.getNextEntry();
                if (entry == null) {
                    throw new EOFException("No entries found in the ZIP file: " + file.getName());
                }
                try (ObjectInputStream objectInputStream = new ObjectInputStream(zipInputStream)) {
                    Object result = objectInputStream.readObject();
                    System.out.println("Successfully read from ZIP file: " + file.getAbsolutePath());
                    return result;
                }
            }
        } else {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                return objectInputStream.readObject();
            }
        }
    }
}