package frontend;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ExitView {

    private final ApiClient api;

    public ExitView(ApiClient api) {
        this.api = api;
    }

    public VBox create(Runnable back) {

        Label title = UiHelper.title("Vehicle Exit");

        TextField ticketId = new TextField();
        ticketId.setPromptText("Ticket ID");

        TextField plate = new TextField();
        plate.setPromptText("Vehicle Number");

        TextField validation = new TextField();
        validation.setPromptText(
                "Restaurant validation code (optional)"
        );

        Button ticketExit =
                UiHelper.actionButton("Exit by Ticket ID");

        Button plateExit =
                UiHelper.actionButton("Exit by Vehicle Number");

        Label result = new Label();
        result.setWrapText(true);

        Button backBtn =
                UiHelper.backButton();

        ticketExit.setOnAction(e ->
                exitByTicket(
                        ticketId,
                        validation,
                        result
                )
        );

        plateExit.setOnAction(e ->
                exitByPlate(
                        plate,
                        result
                )
        );

        backBtn.setOnAction(e -> back.run());

        VBox form = UiHelper.formBox();

        form.getChildren().addAll(
                UiHelper.fieldLabel("Ticket ID"),
                ticketId,
                ticketExit,

                UiHelper.fieldLabel("Vehicle Number"),
                plate,
                plateExit,

                UiHelper.fieldLabel(
                        "Validation Code (Restaurant)"
                ),
                validation,

                result,
                backBtn
        );

        return new VBox(title, form);
    }

    private void exitByTicket(
            TextField ticketId,
            TextField validation,
            Label result
    ) {

        try {

            int id = Integer.parseInt(
                    ticketId.getText().trim()
            );

            String endpoint =
                    "/api/parking/exit/ticket/" + id;

            String code =
                    validation.getText().trim();

            if (!code.isEmpty()) {
                endpoint += "?validationCode=" + code;
            }

            String response =
                    api.post(endpoint);

            showResult(response, result);

        } catch (Exception e) {

            UiHelper.error(
                    result,
                    "Enter a valid ticket ID."
            );
        }
    }

    private void exitByPlate(
            TextField plate,
            Label result
    ) {

        String number =
                plate.getText().trim().toUpperCase();

        if (number.isEmpty()) {

            UiHelper.error(
                    result,
                    "Enter a vehicle number."
            );
            return;
        }

        try {

            String response =
                    api.post(
                            "/api/parking/exit/plate/"
                                    + number
                    );

            showResult(response, result);

        } catch (Exception e) {

            UiHelper.error(
                    result,
                    "Backend connection failed."
            );
        }
    }

    private void showResult(
            String response,
            Label result
    ) {

        int status =
                ApiClient.statusCode(response);

        if (status >= 200 && status < 300) {

            UiHelper.success(
                    result,
                    "Exit successful!\n" +
                    "Final Parking Fee: ₹" +
                    ApiClient.body(response)
            );

        } else {

            UiHelper.error(
                    result,
                    "Could not process exit."
            );
        }
    }
}