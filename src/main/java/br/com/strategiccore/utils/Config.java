package br.com.strategiccore.utils;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author Eduardo Folly
 */
public class Config {
    public static final LocalDateTime NOT_DELETED =
            LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0);

    public static final int MAX_GET_PER_PAGE = 100;

    public static final int MAX_SYNC_PER_PAGE = 10000;
}
