package JavaFX;

import NetWork.Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UserAfterLoginController {
    @FXML
    private Button button0;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;


    public void handleExitButtonActionUser(ActionEvent event) throws IOException {
        //Torna all'interfaccia iniziale
        Client.setNotAdmin();
        FXMLLoader loaderInitial = new FXMLLoader(getClass().getResource("BeforeLoginScene.fxml"));
        Parent rootInitial = loaderInitial.load();
        Scene newScene = new Scene(rootInitial);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();

    }
}
