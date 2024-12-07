package org.bagirov.model.task6;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MetricInvocationHandler implements InvocationHandler {
    private Object delegate;

    public MetricInvocationHandler(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Metric.class)) return invoke(method, args);
        long startTime = System.nanoTime();
        Object result = method.invoke(delegate, args);
        long endTime = System.nanoTime();
        System.out.printf("Время выполнения метода %s - %d", method.getName(), endTime - startTime);
        return result;
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegate, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Ошибка вызова", e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}