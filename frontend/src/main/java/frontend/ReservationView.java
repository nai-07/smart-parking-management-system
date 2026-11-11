package frontend;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ReservationView {

    private final ApiClient api;
    private final Map<String, Integer> venues = new HashMap<>();
    private final Map<String, Integer> slots = new HashMap<>();

    public ReservationView(ApiClient api) {
        this.api = api;
    }

    public VBox create(Runnable back) {

        Label title = UiHelper.title("Reservations");

        ComboBox<String> venue = new ComboBox<>();
        venue.setPromptText("Select Venue");

        TextField vehicle = new TextField();
        vehicle.setPromptText("Vehicle Number");

        ComboBox<String> slot = new ComboBox<>();
        slot.setPromptText("Select Free Slot");

        TextField start = new TextField();
        start.setPromptText("2026-11-10T16:00");

        TextField end = new TextField();
        end.setPromptText("2026-11-10T18:00");

        Button create = UiHelper.actionButton("Create Reservation");
        Button search = UiHelper.actionButton("Check Reservation");

        Label result = new Label();
        result.setWrapText(true);

        Button backBtn = UiHelper.backButton();

        VenueHelper.load(api, venue, venues);

        venue.setOnAction(e ->
                loadSlots(slot, venues.get(venue.getValue()))
        );

        create.setOnAction(e ->
                createReservation(
                        venue, vehicle, slot,
                        start, end, result
                )
        );

        search.setOnAction(e ->
                search(vehicle, result)
        );

        backBtn.setOnAction(e -> back.run());

        VBox form = UiHelper.formBox();

        form.getChildren().addAll(
                UiHelper.fieldLabel("Venue"),
                venue,
                UiHelper.fieldLabel("Vehicle Number"),
                vehicle,
                UiHelper.fieldLabel("Free Slot"),
                slot,
                UiHelper.fieldLabel("Start"),
                start,
                UiHelper.fieldLabel("End"),
                end,
                create,
                search,
                result,
                backBtn
        );

        return new VBox(title, form);
    }

    private void loadSlots(
            ComboBox<String> slot,
            Integer venueId
    ) {

        if (venueId == null) return;

        try {

            String response = api.get("/api/slots");

            if (ApiClient.statusCode(response) != 200)
                return;

            slot.getItems().clear();
            slots.clear();

            for (String item :
                    JsonHelper.objects(ApiClient.body(response))) {

                String id = JsonHelper.number(item, "slotId");
                String number = JsonHelper.value(item, "slotNumber");
                String status = JsonHelper.value(item, "status");
                String floorVenueId =
                        JsonHelper.nestedFloorVenueId(item);

                if ("FREE".equals(status)
                        && id != null
                        && number != null
                        && String.valueOf(venueId)
                        .equals(floorVenueId)) {

                    slots.put(
                            number,
                            Integer.parseInt(id)
                    );

                    slot.getItems().add(number);
                }
            }

        } catch (Exception ignored) {
        }
    }

    private void createReservation(
            ComboBox<String> venue,
            TextField vehicle,
            ComboBox<String> slot,
            TextField start,
            TextField end,
            Label result
    ) {

        try {

            if (venue.getValue() == null ||
                    slot.getValue() == null ||
                    vehicle.getText().trim().isEmpty()) {

                UiHelper.error(result, "Fill all required details.");
                return;
            }

            LocalDateTime startTime =
                    LocalDateTime.parse(start.getText().trim());

            LocalDateTime endTime =
                    LocalDateTime.parse(end.getText().trim());

            if (!endTime.isAfter(startTime)) {
                UiHelper.error(
                        result,
                        "End time must be after start time."
                );
                return;
            }

            String json =
                    "{\"venue\":{\"venueId\":" +
                    venues.get(venue.getValue()) +
                    "},\"slot\":{\"slotId\":" +
                    slots.get(slot.getValue()) +
                    "},\"vehicleNumber\":\"" +
                    vehicle.getText().trim().toUpperCase() +
                    "\",\"reservationStart\":\"" +
                    startTime +
                    "\",\"reservationEnd\":\"" +
                    endTime +
                    "\",\"status\":\"ACTIVE\"}";

            String response =
                    api.postJson("/api/reservations", json);

            int code = ApiClient.statusCode(response);

            if (code >= 200 && code < 300) {

                UiHelper.success(
                        result,
                        "Reservation created successfully!"
                );

                slot.getItems().remove(slot.getValue());
                slot.setValue(null);

            } else {

                UiHelper.error(
                        result,
                        "Could not create reservation."
                );
            }

        } catch (Exception e) {

            UiHelper.error(
                    result,
                    "Use date/time format: 2026-11-10T16:00"
            );
        }
    }

    private void search(
            TextField vehicle,
            Label result
    ) {

        String number =
                vehicle.getText().trim().toUpperCase();

        if (number.isEmpty()) {
            UiHelper.error(
                    result,
                    "Enter a vehicle number."
            );
            return;
        }

        try {

            String response =
                    api.get(
                            "/api/reservations/vehicle/" + number
                    );

            if (ApiClient.statusCode(response) == 200) {

                UiHelper.success(
                        result,
                        "Active reservation found."
                );

            } else {

                UiHelper.error(
                        result,
                        "No active reservation found."
                );
            }

        } catch (Exception e) {

            UiHelper.error(
                    result,
                    "Backend connection failed."
            );
        }
    }
}