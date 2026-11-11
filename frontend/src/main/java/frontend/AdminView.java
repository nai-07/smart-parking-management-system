package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AdminView {

    private final ApiClient api;

    public AdminView(ApiClient api) {
        this.api = api;
    }

    public VBox create(Runnable back) {

        Label title = UiHelper.title("Admin Dashboard");

        Label subtitle = new Label(
                "Parking system overview"
        );

        subtitle.setTextFill(Color.web("#64748b"));

        Label venues = card("VENUES", "—");
        Label total = card("TOTAL SLOTS", "—");
        Label free = card("FREE", "—");
        Label occupied = card("OCCUPIED", "—");
        Label reserved = card("RESERVED", "—");
        Label revenue = card("REVENUE", "₹0");

        GridPane cards = new GridPane();
        cards.setHgap(15);
        cards.setVgap(15);
        cards.setAlignment(Pos.CENTER);

        cards.add(venues, 0, 0);
        cards.add(total, 1, 0);
        cards.add(free, 2, 0);
        cards.add(occupied, 0, 1);
        cards.add(reserved, 1, 1);
        cards.add(revenue, 2, 1);

        Button refresh =
                UiHelper.actionButton("Refresh Dashboard");

        Button backBtn =
                UiHelper.backButton();

        Label status = new Label(
                "Last updated: —"
        );

        status.setTextFill(
                Color.web("#64748b")
        );

        refresh.setOnAction(e ->
                load(
                        venues,
                        total,
                        free,
                        occupied,
                        reserved,
                        revenue,
                        status
                )
        );

        backBtn.setOnAction(
                e -> back.run()
        );

        load(
                venues,
                total,
                free,
                occupied,
                reserved,
                revenue,
                status
        );

        VBox box = new VBox(
                10,
                title,
                subtitle,
                cards,
                refresh,
                status,
                backBtn
        );

        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(25));

        return box;
    }

    private Label card(
            String name,
            String value
    ) {

        Label label = new Label(
                name + "\n" + value
        );

        label.setPrefSize(220, 105);
        label.setAlignment(Pos.CENTER);
        label.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        18
                )
        );

        label.setTextFill(
                Color.web("#172033")
        );

        label.setStyle(
                "-fx-background-color:white;" +
                "-fx-background-radius:14;" +
                "-fx-border-color:#e2e8f0;" +
                "-fx-border-radius:14;" +
                "-fx-border-width:1;"
        );

        return label;
    }

    private void load(
            Label venues,
            Label total,
            Label free,
            Label occupied,
            Label reserved,
            Label revenue,
            Label status
    ) {

        try {

            String venueResponse =
                    api.get("/api/venues");

            String slotResponse =
                    api.get("/api/slots");

            String paymentResponse =
                    api.get("/api/payments");

            if (ApiClient.statusCode(venueResponse) != 200 ||
                    ApiClient.statusCode(slotResponse) != 200 ||
                    ApiClient.statusCode(paymentResponse) != 200) {

                status.setText(
                        "Unable to load dashboard data."
                );
                return;
            }

            int venueCount =
                    JsonHelper.objects(
                            ApiClient.body(venueResponse)
                    ).size();

            int totalCount = 0;
            int freeCount = 0;
            int occupiedCount = 0;
            int reservedCount = 0;

            for (String item :
                    JsonHelper.objects(
                            ApiClient.body(slotResponse))) {

                totalCount++;

                String state =
                        JsonHelper.value(
                                item,
                                "status"
                        );

                if ("FREE".equals(state))
                    freeCount++;

                if ("OCCUPIED".equals(state))
                    occupiedCount++;

                if ("RESERVED".equals(state))
                    reservedCount++;
            }

            double totalRevenue = 0;

            for (String item :
                    JsonHelper.objects(
                            ApiClient.body(paymentResponse))) {

                String amount =
                        JsonHelper.number(
                                item,
                                "amount"
                        );

                if (amount != null)
                    totalRevenue +=
                            Double.parseDouble(amount);
            }

            venues.setText(
                    "VENUES\n" + venueCount
            );

            total.setText(
                    "TOTAL SLOTS\n" + totalCount
            );

            free.setText(
                    "FREE\n" + freeCount
            );

            occupied.setText(
                    "OCCUPIED\n" + occupiedCount
            );

            reserved.setText(
                    "RESERVED\n" + reservedCount
            );

            revenue.setText(
                    "REVENUE\n₹" +
                    String.format(
                            "%.2f",
                            totalRevenue
                    )
            );

            status.setText(
                    "Dashboard updated successfully"
            );

        } catch (Exception e) {

            status.setText(
                    "Backend connection failed."
            );
        }
    }
}