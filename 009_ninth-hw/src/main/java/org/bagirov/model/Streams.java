package org.bagirov.model;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Streams<T> {

    private final List<T> elements;

    // Конструктор для инициализации списка элементов
    private Streams(List<T> elements) {
        this.elements = new ArrayList<>(elements);
    }

    // Статический метод для создания экземпляра Streams
    public static <T> Streams<? extends T> of(List<? extends T> list) {
        Objects.requireNonNull(list, "Список не может быть пуст");
        return new Streams<>(list);
    }

    // Метод для фильтрации элементов по заданному предикату
    public Streams<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "Предикат не может быть null");
        List<T> filteredElements = elements.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        return new Streams<>(filteredElements);
    }

    // Метод для преобразования элементов с использованием функции
    public <R> Streams<R> transform(Function<? super T, ? extends R> transformer) {
        Objects.requireNonNull(transformer, "Преобразование не может быть не задано");
        List<R> transformedElements = elements.stream()
                .map(transformer)
                .collect(Collectors.toList());
        return new Streams<>(transformedElements);
    }

    // Метод для преобразования элементов в карту
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends V> valueMapper) {
        Objects.requireNonNull(keyMapper, "Key не может быть null");
        Objects.requireNonNull(valueMapper, "Value не может быть null");
        return elements.stream()
                .collect(Collectors.toMap(keyMapper, valueMapper));
    }

    // Метод для получения списка элементов
    public List<T> toList() {
        return new ArrayList<>(elements);
    }

    // Метод для получения множества элементов
    public Set<T> toSet() {
        return new HashSet<>(elements);
    }
}