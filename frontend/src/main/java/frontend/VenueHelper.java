package frontend;

import javafx.scene.control.ComboBox;
import java.util.Map;

public class VenueHelper {

    private VenueHelper() {}

    public static void load(
            ApiClient api,
            ComboBox<String> box,
            Map<String, Integer> ids
    ) {
        try {
            String response = api.get("/api/venues");

            if (ApiClient.statusCode(response) != 200) return;

            box.getItems().clear();
            ids.clear();

            for (String item : JsonHelper.objects(ApiClient.body(response))) {
                String id = JsonHelper.number(item, "venueId");
                String name = JsonHelper.value(item, "name");

                if (id != null && name != null) {
                    ids.put(name, Integer.parseInt(id));
                    box.getItems().add(name);
                }
            }
        } catch (Exception ignored) {}
    }
}