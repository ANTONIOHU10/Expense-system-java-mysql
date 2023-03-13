package JavaFX;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class Controller {
    @FXML
    private Button LoginButton;

    @FXML
    private Button RegisterButton;

    @FXML
    private Button ExitButton;
    @FXML
    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        //创建一个对话框
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please enter your login information.");

        //创建登录按钮
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        //创建表单
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        //添加表单控件
        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        //禁用登录按钮，除非用户名和密码都不为空
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || password.getText().isEmpty());
        });
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || username.getText().isEmpty());
        });

        //将表单添加到对话框中
        dialog.getDialogPane().setContent(grid);

        //聚焦用户名字段
        Platform.runLater(() -> username.requestFocus());

        //当用户单击登录按钮时，将用户名和密码传递回主应用程序
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return username.getText() + ":" + password.getText();
            }
            return null;
        });

        //显示对话框，等待用户输入
        Optional<String> result = dialog.showAndWait();

        //如果用户单击登录按钮，则打印用户名和密码
        result.ifPresent(usernamePassword -> System.out.println("Username and password: " + usernamePassword));
    }


    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        //crea un nuovo ChoiceBox per selezionare Admin o User
        ChoiceBox<String> userTypeChoiceBox = new ChoiceBox<>();
        userTypeChoiceBox.getItems().addAll("Admin", "User");
        userTypeChoiceBox.setValue("User");

        //crea un nuovo dialog per chiedere l'utente e la password
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Register Dialog");
        dialog.setHeaderText("Please enter your register information.");

        //crea il bottone di login
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
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
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || password.getText().isEmpty());
        });
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || username.getText().isEmpty());
        });

        //aggiunge il form al dialog
        dialog.getDialogPane().setContent(grid);

        //fa focus sulla casella di testo dell'utente
        Platform.runLater(() -> username.requestFocus());

        //quando l'utente clicca il bottone di login, ritorna l'username e la password
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return userTypeChoiceBox.getValue() + ":" + username.getText() + ":" + password.getText();
            }
            return null;
        });

        //mostra il dialog e aspetta la risposta
        Optional<String> result = dialog.showAndWait();

        //se l'utente ha cliccato il bottone di login, stampa l'username e la password
        result.ifPresent(usernamePassword -> System.out.println("User Type, username, and password: " + usernamePassword));
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }


}
