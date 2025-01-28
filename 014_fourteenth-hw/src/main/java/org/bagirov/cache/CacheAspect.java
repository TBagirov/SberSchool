package org.bagirov.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bagirov.db.H2DB;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class CacheAspect {
    private final CacheStore cacheStore = new H2DB();
    private final ObjectMapper objectMapper = new ObjectMapper(); // Для сериализации объектов

    public Object handleCachable(Method method, Object[] args, Object target) throws Exception {
        Cachable cachable = method.getAnnotation(Cachable.class);
        if (cachable != null) {
            String key = generateKey(method, args);
            Optional<String> cachedResult = cacheStore.get(key);

            if (cachedResult.isPresent()) {
                return objectMapper.readValue(cachedResult.get(), method.getReturnType());
            }

            Object result = method.invoke(target, args); // Вызываем оригинальный метод
            cacheStore.save(key, objectMapper.writeValueAsString(result));
            return result;
        }
        return method.invoke(target, args);
    }

    private String generateKey(Method method, Object[] args) {
        return method.getName() + "(" + String.join(",",
                Arrays.stream(args).map(String::valueOf).toArray(String[]::new)) + ")";
    }
}

