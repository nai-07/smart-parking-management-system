package frontend;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.HashMap;
import java.util.Map;

public class EntryView {

    private final ApiClient api;
    private final Map<String, Integer> venueIds = new HashMap<>();

    public EntryView(ApiClient api) {
        this.api = api;
    }

    public VBox create(Runnable back) {

        Label title = UiHelper.title("Vehicle Entry");

        ComboBox<String> venue = new ComboBox<>();
        venue.setPromptText("Select Venue");

        TextField plate = new TextField();
        plate.setPromptText("Example: DL01AB1234");

        ComboBox<String> type = new ComboBox<>();
        type.getItems().addAll("CAR", "BIKE", "EV");
        type.setPromptText("Vehicle Type");

        Button enter = UiHelper.actionButton("Enter Vehicle");
        Label result = new Label();
        Button backBtn = UiHelper.backButton();

        VenueHelper.load(api, venue, venueIds);

        enter.setOnAction(e ->
                enter(venue, plate, type, result));

        backBtn.setOnAction(e -> back.run());

        VBox form = UiHelper.formBox();

        form.getChildren().addAll(
                UiHelper.fieldLabel("Venue"),
                venue,
                UiHelper.fieldLabel("Vehicle Number"),
                plate,
                UiHelper.fieldLabel("Vehicle Type"),
                type,
                enter,
                result,
                backBtn
        );

        return new VBox(title, form);
    }

    private void enter(
            ComboBox<String> venue,
            TextField plate,
            ComboBox<String> type,
            Label result
    ) {

        String number = plate.getText().trim().toUpperCase();

        if (venue.getValue() == null ||
                type.getValue() == null ||
                !number.matches("^[A-Z]{2}[0-9]{1,2}[A-Z]{1,3}[0-9]{4}$")) {

            UiHelper.error(result, "Please enter valid vehicle details.");
            return;
        }

        try {
            String url = "/api/parking/entry?venueId=" +
                    venueIds.get(venue.getValue()) +
                    "&plateNumber=" + number +
                    "&vehicleType=" + type.getValue();

            String response = api.post(url);
            int code = ApiClient.statusCode(response);

            if (code >= 200 && code < 300) {

                String body = ApiClient.body(response);

                UiHelper.success(
                        result,
                        "Entry successful!\nTicket: " +
                        JsonHelper.number(body, "ticketId") +
                        "\nSlot: " +
                        JsonHelper.value(body, "slotNumber")
                );

                plate.clear();
                type.setValue(null);

            } else {
                UiHelper.error(
                        result,
                        code == 409
                                ? "Vehicle already parked or parking is full."
                                : "Vehicle entry failed."
                );
            }

        } catch (Exception e) {
            UiHelper.error(result, "Backend connection failed.");
        }
    }
}