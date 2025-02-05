package org.bagirov.cahce;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для кэширования результатов вызова метода.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Memoize {
    /**
     * Выбор стратегии кэширования.
     * По умолчанию используется кэширование в памяти.
     */
    CacheStrategy strategy() default CacheStrategy.MEMORY;

    /**
     * Флаг сжатия данных при сохранении.
     */
    boolean compress() default false;

    /**
     * Префикс для имени файла, если используется файловое кэширование.
     */
    String prefix() default "";

    /**
     * Массив классов, по полям которых определяется уникальность параметров.
     */
    Class<?>[] keyFields() default {};

    /**
     * Ограничение на размер списка кэша.
     */
    int maxElements() default Integer.MAX_VALUE;
}