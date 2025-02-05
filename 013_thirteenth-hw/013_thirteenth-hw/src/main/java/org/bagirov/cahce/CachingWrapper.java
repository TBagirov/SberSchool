package org.bagirov.cahce;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Обёртка для динамического проксирования объектов с кэшированием.
 */
public class CachingWrapper {
    private final String basePath;

    public CachingWrapper(String basePath) {
        this.basePath = basePath;
    }

    /**
     * Возвращает объект-прокси, который перехватывает вызовы методов для реализации кэширования.
     *
     * @param <T>      тип исходного сервиса
     * @param delegate исходный объект, чьи методы необходимо кэшировать
     * @return проксированный объект с поддержкой кэширования
     */
    @SuppressWarnings("unchecked")
    public <T> T wrap(T delegate) {
        ClassLoader loader = delegate.getClass().getClassLoader();
        Class<?>[] interfaces = delegate.getClass().getInterfaces();
        InvocationHandler handler = new CachingInterceptor (delegate, basePath);

        return (T) Proxy.newProxyInstance(loader, interfaces, handler);
    }
}