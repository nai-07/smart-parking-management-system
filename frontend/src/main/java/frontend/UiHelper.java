package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UiHelper {

    private UiHelper() {
    }

    public static Label title(String text) {

        Label label = new Label(text);

        label.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        28
                )
        );

        label.setTextFill(
                Color.web("#172033")
        );

        label.setPadding(
                new Insets(25, 0, 10, 0)
        );

        return label;
    }

    public static Label fieldLabel(String text) {

        Label label = new Label(text);

        label.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        14
                )
        );

        label.setTextFill(
                Color.web("#334155")
        );

        return label;
    }

    public static Button actionButton(String text) {

        Button button = new Button(text);

        button.setPrefWidth(350);
        button.setPrefHeight(45);

        button.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        14
                )
        );

        button.setStyle(
                "-fx-background-color: #2563eb;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 8;"
        );

        return button;
    }

    public static Button backButton() {

        Button button =
                new Button("Back to Dashboard");

        button.setPrefWidth(220);
        button.setPrefHeight(40);

        return button;
    }

    public static void success(
            Label label,
            String message
    ) {

        label.setText(message);

        label.setTextFill(
                Color.web("#166534")
        );

        label.setStyle(
                "-fx-background-color: #dcfce7;" +
                "-fx-padding: 15;" +
                "-fx-background-radius: 8;"
        );
    }

    public static void error(
            Label label,
            String message
    ) {

        label.setText(message);

        label.setTextFill(
                Color.web("#991b1b")
        );

        label.setStyle(
                "-fx-background-color: #fee2e2;" +
                "-fx-padding: 15;" +
                "-fx-background-radius: 8;"
        );
    }

    public static VBox formBox() {

        VBox box = new VBox(12);

        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));

        return box;
    }

    public static Label stat(String text) {

        Label label = new Label(text);

        label.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        17
                )
        );

        label.setPadding(
                new Insets(15)
        );

        label.setMaxWidth(
                Double.MAX_VALUE
        );

        label.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 10;"
        );

        return label;
    }
}