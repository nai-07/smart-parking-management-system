package frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ParkingApp extends Application {

    private final ApiClient api = new ApiClient();
    private Stage stage;

    @Override
    public void start(Stage stage) {

        this.stage = stage;

        stage.setTitle(
                "Smart Parking Management System"
        );

        showHome();

        stage.show();
    }

    private void showHome() {

        HomeView view = new HomeView();

        setScene(
                view.create(
                        this::showEntry,
                        this::showExit,
                        this::showLive,
                        this::showValidation,
                        this::showReservation,
                        this::showAdmin
                )
        );
    }

    private void showEntry() {
        setScene(
                new EntryView(api)
                        .create(this::showHome)
        );
    }

    private void showExit() {
        setScene(
                new ExitView(api)
                        .create(this::showHome)
        );
    }

    private void showLive() {
        setScene(
                new LiveParkingView(api)
                        .create(this::showHome)
        );
    }

    private void showValidation() {
        setScene(
                new ValidationView(api)
                        .create(this::showHome)
        );
    }

    private void showReservation() {
        setScene(
                new ReservationView(api)
                        .create(this::showHome)
        );
    }

    private void showAdmin() {
        setScene(
                new AdminView(api)
                        .create(this::showHome)
        );
    }

    private void setScene(VBox root) {

        Scene scene =
                new Scene(root, 1050, 700);

        scene.getStylesheets().add(
                getClass()
                        .getResource("/style.css")
                        .toExternalForm()
        );

        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}