package frontend;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ValidationView {

    private final ApiClient api;

    public ValidationView(ApiClient api) {
        this.api = api;
    }

    public VBox create(Runnable back) {

        Label title = UiHelper.title("Restaurant Validation");

        TextField code = new TextField();
        code.setPromptText("Validation Code");

        Button check =
                UiHelper.actionButton("Validate Parking");

        Label result = new Label();
        Button backBtn = UiHelper.backButton();

        check.setOnAction(e -> validate(code, result));
        backBtn.setOnAction(e -> back.run());

        VBox form = UiHelper.formBox();

        form.getChildren().addAll(
                UiHelper.fieldLabel("Validation Code"),
                code,
                check,
                result,
                backBtn
        );

        return new VBox(title, form);
    }

    private void validate(
            TextField code,
            Label result
    ) {

        String value = code.getText().trim();

        if (value.isEmpty()) {
            UiHelper.error(result, "Enter a validation code.");
            return;
        }

        try {

            String response =
                    api.get("/api/validations/code/" + value);

            if (ApiClient.statusCode(response) == 200) {

                UiHelper.success(
                        result,
                        "Validation successful."
                );

            } else {
                UiHelper.error(
                        result,
                        "Invalid validation code."
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