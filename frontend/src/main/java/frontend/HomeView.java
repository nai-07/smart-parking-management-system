package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HomeView {

    public VBox create(
            Runnable entry,
            Runnable exit,
            Runnable live,
            Runnable validation,
            Runnable reservation,
            Runnable admin
    ) {

        Label title = new Label(
                "SMART PARKING"
        );

        title.setFont(
                Font.font(
                        "Segoe UI",
                        FontWeight.BOLD,
                        32
                )
        );

        title.setTextFill(
                Color.web("#172033")
        );

        Label subtitle = new Label(
                "Management System"
        );

        subtitle.setFont(
                Font.font(
                        "Segoe UI",
                        FontWeight.NORMAL,
                        18
                )
        );

        subtitle.setTextFill(
                Color.web("#64748b")
        );

        Label welcome = new Label(
                "Manage parking, reservations and payments"
        );

        welcome.setFont(
                Font.font(
                        "Segoe UI",
                        FontWeight.NORMAL,
                        14
                )
        );

        welcome.setTextFill(
                Color.web("#64748b")
        );

        GridPane grid = new GridPane();

        grid.setHgap(18);
        grid.setVgap(18);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 0, 20, 0));

        Button entryBtn =
                dashboardButton(
                        "VEHICLE ENTRY",
                        "Register a new parking entry"
                );

        Button exitBtn =
                dashboardButton(
                        "VEHICLE EXIT",
                        "Process vehicle exit and payment"
                );

        Button liveBtn =
                dashboardButton(
                        "LIVE PARKING",
                        "View real-time slot availability"
                );

        Button validationBtn =
                dashboardButton(
                        "RESTAURANT",
                        "Validate restaurant parking"
                );

        Button reservationBtn =
                dashboardButton(
                        "RESERVATIONS",
                        "Reserve a parking slot"
                );

        Button adminBtn =
                dashboardButton(
                        "ADMIN DASHBOARD",
                        "View parking statistics"
                );

        entryBtn.setOnAction(e -> entry.run());
        exitBtn.setOnAction(e -> exit.run());
        liveBtn.setOnAction(e -> live.run());
        validationBtn.setOnAction(e -> validation.run());
        reservationBtn.setOnAction(e -> reservation.run());
        adminBtn.setOnAction(e -> admin.run());

        grid.add(entryBtn, 0, 0);
        grid.add(exitBtn, 1, 0);
        grid.add(liveBtn, 0, 1);
        grid.add(validationBtn, 1, 1);
        grid.add(reservationBtn, 0, 2);
        grid.add(adminBtn, 1, 2);

        Label footer = new Label(
                "Smart • Fast • Organized Parking"
        );

        footer.setTextFill(
                Color.web("#94a3b8")
        );

        footer.setFont(
                Font.font(
                        "Segoe UI",
                        FontWeight.NORMAL,
                        13
                )
        );

        VBox root = new VBox(
                8,
                title,
                subtitle,
                welcome,
                grid,
                footer
        );

        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(
                new Insets(55, 40, 30, 40)
        );


        return root;
    }

    private Button dashboardButton(
            String title,
            String description
    ) {

        Button button = new Button();

        Label heading = new Label(title);

        heading.setFont(
                Font.font(
                        "Segoe UI",
                        FontWeight.BOLD,
                        16
                )
        );

        heading.setTextFill(
                Color.WHITE
        );

        Label text = new Label(description);

        text.setFont(
                Font.font(
                        "Segoe UI",
                        FontWeight.NORMAL,
                        12
                )
        );

        text.setTextFill(
                Color.web("#dbeafe")
        );

        VBox content = new VBox(
                7,
                heading,
                text
        );

        content.setAlignment(Pos.CENTER_LEFT);

        button.setGraphic(content);

        button.setPrefSize(300, 105);

        button.setStyle(
                "-fx-background-color: #1e40af;" +
                "-fx-background-radius: 14;" +
                "-fx-padding: 18;" +
                "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #2563eb;" +
                        "-fx-background-radius: 14;" +
                        "-fx-padding: 18;" +
                        "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: #1e40af;" +
                        "-fx-background-radius: 14;" +
                        "-fx-padding: 18;" +
                        "-fx-cursor: hand;"
                )
        );

        return button;
    }
}
