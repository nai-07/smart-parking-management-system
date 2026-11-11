package frontend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class LiveParkingView {

    private final ApiClient api;
    private final Map<String, Integer> venues = new HashMap<>();
    private final GridPane grid = new GridPane();
    private ComboBox<String> venue;
    private Timeline timer;

    public LiveParkingView(ApiClient api) {
        this.api = api;
    }

    public VBox create(Runnable back) {

        Label title = UiHelper.title("Live Parking");

        venue = new ComboBox<>();
        venue.setPromptText("Select Venue");

        VenueHelper.load(api, venue, venues);

        venue.setOnAction(e -> load());

        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        Button refresh =
                UiHelper.actionButton("Refresh");

        refresh.setOnAction(e -> load());

        Button backBtn =
                UiHelper.backButton();

        backBtn.setOnAction(e -> {
            stopTimer();
            back.run();
        });

        VBox box = new VBox(
                15,
                title,
                venue,
                grid,
                refresh,
                backBtn
        );

        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(20));

        timer = new Timeline(
                new KeyFrame(
                        Duration.seconds(2),
                        e -> load()
                )
        );

        timer.setCycleCount(
                Timeline.INDEFINITE
        );

        timer.play();

        return box;
    }

    private void load() {

        Integer venueId =
                venues.get(venue.getValue());

        if (venueId == null)
            return;

        try {

            String response =
                    api.get("/api/slots");

            if (ApiClient.statusCode(response) != 200)
                return;

            grid.getChildren().clear();

            int column = 0;
            int row = 0;

            for (String item :
                    JsonHelper.objects(
                            ApiClient.body(response))) {

                String slotId =
                        JsonHelper.number(
                                item,
                                "slotId"
                        );

                String slotNumber =
                        JsonHelper.value(
                                item,
                                "slotNumber"
                        );

                String status =
                        JsonHelper.value(
                                item,
                                "status"
                        );

                String floorVenueId =
                        JsonHelper.nestedFloorVenueId(
                                item
                        );

                if (slotId == null ||
                        slotNumber == null ||
                        status == null ||
                        !String.valueOf(venueId)
                                .equals(floorVenueId)) {

                    continue;
                }

                Label cell =
                        new Label(
                                slotNumber +
                                "\n" +
                                status
                        );

                cell.setPrefSize(120, 70);
                cell.setAlignment(Pos.CENTER);
                cell.setFont(
                        javafx.scene.text.Font.font(
                                "Arial",
                                javafx.scene.text.FontWeight.BOLD,
                                14
                        )
                );

                cell.setTextFill(Color.WHITE);

                String background;

                switch (status) {
                    case "FREE" ->
                            background = "#16a34a";

                    case "OCCUPIED" ->
                            background = "#dc2626";

                    case "RESERVED" ->
                            background = "#d97706";

                    case "BOOKED" ->
                            background = "#7c3aed";

                    default ->
                            background = "#64748b";
                }

                cell.setStyle(
                        "-fx-background-color:" +
                        background +
                        ";" +
                        "-fx-background-radius:10;"
                );

                grid.add(
                        cell,
                        column,
                        row
                );

                column++;

                if (column == 4) {
                    column = 0;
                    row++;
                }
            }

        } catch (Exception ignored) {
        }
    }

    private void stopTimer() {

        if (timer != null)
            timer.stop();
    }
}