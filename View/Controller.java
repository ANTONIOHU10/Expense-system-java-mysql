package View;

import NetWork.Client.Client;
import NetWork.Message.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * controller for the primary stage
 */
public class Controller {
    @FXML
    private Button ExitButton;

    @FXML
    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     *
     * @param event the click action
     */
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        //caricare icone
        Image icon = new Image("Resource/icon.png");

        //create a dialog pane
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please enter your login information.");

        //icon per dialog
        dialog.setGraphic(new ImageView(new Image("Resource/iconCoffee2.jpg")));

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        //create the login button
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        //create a grid pane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        //create grid controller field
        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        //disable the login button unless other filed are filled up
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);
        username.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty() || password.getText().isEmpty()));
        password.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty() || username.getText().isEmpty()));

        //add the dialog into the pane
        dialog.getDialogPane().setContent(grid);

        //focus the username field
        Platform.runLater(username::requestFocus);

        //when the client clicked the login button, return the information
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                //Inviare il messaggio al Server
                try {
                    Client.commandHandler.loginRequest(username.getText(),password.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Ricevere un messaggio dal Server
                Message replyFromServer = null;
                try {
                    replyFromServer = Client.messageHandler.receive();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                assert replyFromServer != null;
                Client.messageHandler.handle(replyFromServer);


                //Verifica della condizione
                if(Client.getIsLoggedIn()){
                    FXMLLoader loader;
                    if(Client.getIsAdmin()){

                        loader = new FXMLLoader(getClass().getResource("AdminAfterLoginScene.fxml"));
                    } else {
                        loader = new FXMLLoader(getClass().getResource("UserAfterLoginScene.fxml"));
                    }
                    //System.out.println("Logged in !!!!!");

                    //Apertura nuova scena
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene newScene = new Scene(root);
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(newScene);
                    currentStage.show();
                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login fallito");
                    alert.setHeaderText("Username o password errati");
                    alert.setContentText("Si prega di riprovare.");

                    alert.showAndWait();
                    System.out.println("Fallito!");
                }
                return username.getText() + ":" + password.getText();
            }
            return null;
        });

        //display the dialog pane
        dialog.showAndWait();
    }


    @FXML
    private void handleRegisterButtonAction() {
        //caricare icone
        Image icon = new Image("Resource/icon.png");

        //crea un nuovo ChoiceBox per selezionare Admin o User
        ChoiceBox<String> userTypeChoiceBox = new ChoiceBox<>();
        userTypeChoiceBox.getItems().addAll("Admin", "User");
        userTypeChoiceBox.setValue("User");

        //crea un nuovo dialog per chiedere l'utente e la password
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Register Dialog");
        dialog.setHeaderText("Please enter your register information.");

        //icon
        dialog.setGraphic(new ImageView(new Image("Resource/iconCoffee1.jpg")));
        //set icon
        Stage stage =(Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        //crea il bottone di login
        ButtonType loginButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        //crea il form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        //aggiunge i controlli al form
        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        grid.add(new Label("User Type:"), 0, 0);
        grid.add(userTypeChoiceBox, 1, 0);
        grid.add(new Label("Username:"), 0, 1);
        grid.add(username, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(password, 1, 2);

        //disabilita il bottone di login finché l'utente e la password non sono validi
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);
        username.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty() || password.getText().isEmpty()));
        password.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty() || username.getText().isEmpty()));

        //aggiunge il form al dialog
        dialog.getDialogPane().setContent(grid);

        //fa focus sulla casella di testo dell'utente
        Platform.runLater(username::requestFocus);

        //quando l'utente clicca il bottone di login, ritorna l'username e la password
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                //Inviare il messaggio al Server
                if(userTypeChoiceBox.getValue().equals("Admin")){
                    try {
                        Client.commandHandler.registerRequest(username.getText(),password.getText(),1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Client.commandHandler.registerRequest(username.getText(),password.getText(),0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //Ricevere un messaggio dal Server
                Message replyFromServer = null;
                try {
                    replyFromServer = Client.messageHandler.receive();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                assert replyFromServer != null;
                Client.messageHandler.handle(replyFromServer);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hai ricevuto un messaggio");
                alert.setHeaderText("Message");
                alert.setContentText(Client.getMessage());

                alert.showAndWait();
                //set message a default
                return userTypeChoiceBox.getValue() + ":" + username.getText() + ":" + password.getText();
            }
            return null;
        });

        //mostra il dialog e aspetta la risposta
        Optional<String> result = dialog.showAndWait();

        //se l'utente ha cliccato il bottone di login, stampa l'username e la password
        result.ifPresent(usernamePassword -> System.out.println("User Type, username, and password: " + usernamePassword));


        Client.setMessage("");

    }

    @FXML
    private void handleExitButton() {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }


}
