package JavaFX;

import NetWork.Client.Client;
import NetWork.Message.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalDate;

public class AdminAfterLoginController {

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

    @FXML
    private Button button8;

    @FXML
    private Button button9;


    @FXML
    public void handleExitButtonAction(javafx.event.ActionEvent event) throws IOException {
        //Torna all'interfaccia iniziale
        Client.setNotAdmin();
        FXMLLoader loaderInitial = new FXMLLoader(getClass().getResource("BeforeLoginScene.fxml"));
        Parent rootInitial = loaderInitial.load();
        Scene newScene = new Scene(rootInitial);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();

    }

    public void handleInsertExpenseAdmin(ActionEvent eventClick) {
        // Crea la finestra di dialogo
        Stage dialog = new Stage();
        dialog.setTitle("Inserisci dati");

        // Crea i controlli per l'input
        Label importoLabel = new Label("Importo:");
        TextField importoField = new TextField();
        Label dataLabel = new Label("Data:");
        DatePicker dataPicker = new DatePicker();
        Label descrizioneLabel = new Label("Descrizione:");
        TextField descrizioneField = new TextField();

        // Crea il pulsante di conferma
        Button confermaButton = new Button("Conferma");
        confermaButton.setOnAction(event -> {
            // Legge i valori inseriti dall'utente
            double importo = Double.parseDouble(importoField.getText());
            LocalDate data = dataPicker.getValue();
            String descrizione = descrizioneField.getText();

            // Stampa i valori inseriti dall'utente
            System.out.println("Importo: " + importo);
            System.out.println("Data: " + data);
            System.out.println("Descrizione: " + descrizione);
            //TODO: inviare il messaggio
            try {
                //invia un messaggio al Server
                Client.commandHandler.expenseMessage(Client.getIdClient(),importo,data.getDayOfMonth(),data.getMonthValue(),data.getYear(),descrizione);

                //riceve un messaggio dal Server
                Message replyFromServer = Client.messageHandler.receive();
                Client.messageHandler.handle(replyFromServer);

                //notifica con il messaggio ricevuto
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Messaggio: ");
                alert.setHeaderText("Inserimento della spesa: ");
                alert.setContentText(Client.getMessage());
                //messaggio di default
                Client.setMessage("");

                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Chiude la finestra di dialogo
            dialog.close();
        });

        // Crea il layout della finestra di dialogo
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.addRow(0, importoLabel, importoField);
        layout.addRow(1, dataLabel, dataPicker);
        layout.addRow(2, descrizioneLabel, descrizioneField);
        layout.add(confermaButton, 0, 3, 2, 1);
        layout.setAlignment(Pos.CENTER);

        // Crea la scena e imposta il layout
        Scene scene = new Scene(layout, 300, 200);

        // Imposta la scena sulla finestra di dialogo e mostra la finestra
        dialog.setScene(scene);
        dialog.show();
    }

    public void handleViewAllMembersAdmin(ActionEvent event) {
    }

    public void handlePaymentAdmin(ActionEvent event) {
    }
}
