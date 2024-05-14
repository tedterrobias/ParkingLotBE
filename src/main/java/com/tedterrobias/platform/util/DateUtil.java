package com.tedterrobias.platform.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String formatDate(LocalDateTime localDateTime)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
        return localDateTime.format(formatter);
    }
}
