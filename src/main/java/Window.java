import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import helpers.Routes;

/**
 * main window that also loads fxml file
 */
public class Window extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Routes.viewsRoute("WorkingView.fxml"));
        stage.setTitle("TSP");
        stage.setScene(new Scene(root, 1000, 700));
        stage.show();
    }

    public void launchApp() {
        launch();
    }
}
