package javafxapplication5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXApplication5 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                JavaFXApplication5.class.getResource("/javafxapplication5/FXMLDocument.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setTitle("Scientific Calculator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
