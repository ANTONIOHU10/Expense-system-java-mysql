package JavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Controller {
    @FXML
    private Button button;


    @FXML
    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Salve!");
    }


}
