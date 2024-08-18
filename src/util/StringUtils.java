package util;

import java.util.List;

public class StringUtils {

    public static String toTitleCase(String spaceSeparated, String joiner) {
        var split = spaceSeparated.split(" ");
        for (int i = 0; i < split.length; i++) {
            String word = split[i];
            String first = word.substring(0, 1).toUpperCase();
            word = word.substring(1).toLowerCase();
            split[i] = first + word;
        }
        return String.join(joiner, List.of(split));
    }

}
