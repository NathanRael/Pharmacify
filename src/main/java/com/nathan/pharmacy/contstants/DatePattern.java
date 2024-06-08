package com.nathan.pharmacy.contstants;

import java.time.format.DateTimeFormatter;

public interface DatePattern {
    public static DateTimeFormatter dateFormatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
