import javafx.application.Application; 
import javafx.scene.Scene; 
import javafx.scene.control.Label; 
import javafx.stage.Stage; 
import javafx.scene.control.Button;  
public class Main extends Application { 
    @Override 
    public void start(Stage stage) { 
        Label label = new Label("Hello, JavaFX be patient!"); 
        Scene scene = new Scene(label, 300, 200); 
        stage.setScene(scene); 
        stage.setTitle("First JavaFX App"); 
        stage.show(); 
            
    } 
 
    public static void main(String[] args) { 
        launch(args); 
    } 
} 