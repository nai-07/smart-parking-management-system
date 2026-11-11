package frontend;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    private JsonHelper() {}

    public static String value(String json, String key) {

        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);

        if (start == -1) return null;

        start += search.length();

        int end = json.indexOf("\"", start);

        if (end == -1) return null;

        return json.substring(start, end);
    }

    public static String number(String json, String key) {

        String search = "\"" + key + "\":";
        int start = json.indexOf(search);

        if (start == -1) return null;

        start += search.length();

        while (start < json.length()
                && Character.isWhitespace(json.charAt(start))) {
            start++;
        }

        int end = start;

        while (end < json.length()
                && (Character.isDigit(json.charAt(end))
                || json.charAt(end) == '.'
                || json.charAt(end) == '-')) {
            end++;
        }

        if (start == end) return null;

        return json.substring(start, end);
    }

    public static String nestedVenueId(String json) {

        int start = json.indexOf("\"venue\":{");

        if (start == -1) return null;

        return number(
                json.substring(start),
                "venueId"
        );
    }

    public static String nestedFloorVenueId(String json) {

        int start = json.indexOf("\"floor\":{");

        if (start == -1) return null;

        return number(
                json.substring(start),
                "venueId"
        );
    }

    public static List<String> objects(String json) {

        List<String> result = new ArrayList<>();

        if (json == null || json.isBlank())
            return result;

        String data = json.trim();

        if (data.startsWith("[")) {
            data = data.substring(
                    1,
                    data.length() - 1
            );
        }

        int depth = 0;
        int start = -1;

        boolean string = false;
        boolean escaped = false;

        for (int i = 0; i < data.length(); i++) {

            char c = data.charAt(i);

            if (escaped) {
                escaped = false;
                continue;
            }

            if (c == '\\' && string) {
                escaped = true;
                continue;
            }

            if (c == '"') {
                string = !string;
                continue;
            }

            if (string)
                continue;

            if (c == '{') {

                if (depth == 0)
                    start = i;

                depth++;

            } else if (c == '}') {

                depth--;

                if (depth == 0 && start >= 0) {

                    result.add(
                            data.substring(
                                    start,
                                    i + 1
                            )
                    );

                    start = -1;
                }
            }
        }

        return result;
    }
}
