package org.bagirov.cahce;

import java.util.Date;
import java.util.List;

public interface DataProcessor {
    @Memoize(strategy = CacheStrategy.FILE, prefix = "data", compress = true, keyFields = {String.class})
    List<String> executeTask(String key, double threshold, Date timestamp) throws InterruptedException;
}