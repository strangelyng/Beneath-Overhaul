package net.strangelyng.beneathoverhaul.util;

import java.util.Locale;
import java.util.stream.Stream;

/*
 * Special thanks to Gourmandd, much of this utility function library is based on their work for On-Ancient-Ground-Core
 * https://github.com/Gourmandd/On-Ancient-Ground-Core/blob/main/src/main/java/net/gourmand/core/util/TextUtil.java
 */

public class TextUtils {

    public static String getName(String string){
        final String[] new_string = {""};
        final int[] count = {0};

        Stream.of(string.toLowerCase(Locale.ROOT).split("_")).forEach(str -> {
            if (count[0] == 0){
                new_string[0] = str.substring(0, 1).toUpperCase() + str.substring(1);
            } else {
                new_string[0] = new_string[0] + " " + str.substring(0, 1).toUpperCase() + str.substring(1);
            }
            count[0] = count[0] + 1;
        });

        return new_string[0];
    }
}