import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import helpers.Routes;

public class Window extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Routes.viewsRoute("MainView.fxml"));
        stage.setTitle("TSP");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void launchApp() {
        launch();
    }
}
