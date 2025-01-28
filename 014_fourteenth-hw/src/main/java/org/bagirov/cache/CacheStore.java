package org.bagirov.cache;

import java.util.Optional;

public interface CacheStore {
    void save(String key, String value);
    Optional<String> get(String key);
    void clear();
}
