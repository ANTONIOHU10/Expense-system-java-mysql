package JavaFX;

import Model.Expense;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

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

    public void handleViewAllMembersAdmin(ActionEvent event) throws IOException, ClassNotFoundException {
        //invia un messaggio al Server
        Client.commandHandler.viewRoomates();

        //riceve un messaggio dal Server
        Message replyFromServer = Client.messageHandler.receive();
        Client.messageHandler.handle(replyFromServer);

        //ottiene la lista dei nomi
        //Client.messageHandler.getUsernames()

        // Creazione del componente TextArea che conterrà gli elementi della lista
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setText(String.join("\n", Client.messageHandler.getUsernames()));

        // Creazione del componente ScrollPane per permettere la visualizzazione della lista completa
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);

        // Creazione del componente di dialogo Alert e impostazione del contenuto della finestra
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lista dei coinquilini");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(scrollPane);

        // Impostazione del comportamento della finestra di dialogo
        alert.showAndWait();
    }

    public void handlePaymentAdmin(ActionEvent event) {
        int code = 0;

        // Crea un TextInputDialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Inserisci il codice della spesa");
        dialog.setHeaderText(null);
        dialog.setContentText("Codice:");

        // Loop finché l'utente non inserisce un valore valido
        while (true) {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    code = Integer.parseInt(result.get());

                    //invia un messaggio
                    Client.commandHandler.paymentRequset(code);
                    //System.out.println("->>>>>>>>>>>"+code);

                    //riceve un messaggio dal server
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

                    break;
                } catch (NumberFormatException e) {
                    // Mostra un messaggio di errore se l'utente non ha inserito un intero
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText(null);
                    alert.setContentText("Inserisci un valore numerico intero!");
                    alert.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                // L'utente ha premuto cancel
                break;
            }
        }
    }

    public void handleConsultExpensesToBePaidAction(ActionEvent event) throws IOException, ClassNotFoundException {
        //invia un messsaggio al Server
        Client.commandHandler.consultationExpensesToBePaidRequest();

        //riceve un messaggio dal Server
        Message replyFromServer = Client.messageHandler.receive();
        Client.messageHandler.handle(replyFromServer);

        TableView<Expense> table = new TableView<>();

        // Definizione delle colonne della tabella
        TableColumn<Expense, Integer> codeCol = new TableColumn<>("Codice spesa");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("expense_id"));

        TableColumn<Expense, Integer> payerCol = new TableColumn<>("Codice pagante");
        payerCol.setCellValueFactory(new PropertyValueFactory<>("id_payer"));

        TableColumn<Expense, Double> totalCol = new TableColumn<>("Totale");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("payer_amount"));

        TableColumn<Expense, Double> paidCol = new TableColumn<>("Pagata");
        paidCol.setCellValueFactory(new PropertyValueFactory<>("payee_amount"));

        TableColumn<Expense, Integer> dayCol = new TableColumn<>("Giorno");
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));

        TableColumn<Expense, Integer> monthCol = new TableColumn<>("Mese");
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn<Expense, Integer> yearCol = new TableColumn<>("Anno");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Expense, String> descCol = new TableColumn<>("Descrizione");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Expense, Integer> paidStatusCol = new TableColumn<>("Se pagata(1 = sì /0 = no)");
        paidStatusCol.setCellValueFactory(new PropertyValueFactory<>("ifPaid"));

        table.getColumns().addAll(codeCol, payerCol, totalCol, paidCol, dayCol, monthCol, yearCol, descCol, paidStatusCol);
        table.getItems().addAll(Client.getExpensesList());

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Tabella delle spese");

        Client.setExpensesList(null);
        stage.show();

    }

    public void handleConsultExpensesPaidAction(ActionEvent event) throws IOException, ClassNotFoundException {
        //invia un messsaggio al Server
        Client.commandHandler.consultationExpensesPaidRequest();

        //riceve un messaggio dal Server
        Message replyFromServer = Client.messageHandler.receive();
        Client.messageHandler.handle(replyFromServer);

        TableView<Expense> table = new TableView<>();

        // Definizione delle colonne della tabella
        TableColumn<Expense, Integer> codeCol = new TableColumn<>("Codice spesa");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("expense_id"));

        TableColumn<Expense, Integer> payerCol = new TableColumn<>("Codice pagante");
        payerCol.setCellValueFactory(new PropertyValueFactory<>("id_payer"));

        TableColumn<Expense, Double> totalCol = new TableColumn<>("Totale");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("payer_amount"));

        TableColumn<Expense, Double> paidCol = new TableColumn<>("Pagata");
        paidCol.setCellValueFactory(new PropertyValueFactory<>("payee_amount"));

        TableColumn<Expense, Integer> dayCol = new TableColumn<>("Giorno");
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));

        TableColumn<Expense, Integer> monthCol = new TableColumn<>("Mese");
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn<Expense, Integer> yearCol = new TableColumn<>("Anno");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Expense, String> descCol = new TableColumn<>("Descrizione");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Expense, Integer> paidStatusCol = new TableColumn<>("Se pagata(1 = sì /0 = no)");
        paidStatusCol.setCellValueFactory(new PropertyValueFactory<>("ifPaid"));

        table.getColumns().addAll(codeCol, payerCol, totalCol, paidCol, dayCol, monthCol, yearCol, descCol, paidStatusCol);
        table.getItems().addAll(Client.getExpensesList());

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Tabella delle spese");

        Client.setExpensesList(null);
        stage.show();

    }

    public void handleConsultAllExpensesAction(ActionEvent event) throws IOException, ClassNotFoundException {
        //invia un messsaggio al Server
        Client.commandHandler.consultationAllExpenses();

        //riceve un messaggio dal Server
        Message replyFromServer = Client.messageHandler.receive();
        Client.messageHandler.handle(replyFromServer);

        TableView<Expense> table = new TableView<>();

        // Definizione delle colonne della tabella
        TableColumn<Expense, Integer> codeCol = new TableColumn<>("Codice spesa");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("expense_id"));

        TableColumn<Expense, Integer> payerCol = new TableColumn<>("Codice pagante");
        payerCol.setCellValueFactory(new PropertyValueFactory<>("id_payer"));

        TableColumn<Expense, Double> totalCol = new TableColumn<>("Totale");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("payer_amount"));

        TableColumn<Expense, Double> paidCol = new TableColumn<>("Pagata");
        paidCol.setCellValueFactory(new PropertyValueFactory<>("payee_amount"));

        TableColumn<Expense, Integer> dayCol = new TableColumn<>("Giorno");
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));

        TableColumn<Expense, Integer> monthCol = new TableColumn<>("Mese");
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn<Expense, Integer> yearCol = new TableColumn<>("Anno");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Expense, String> descCol = new TableColumn<>("Descrizione");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Expense, Integer> paidStatusCol = new TableColumn<>("Se pagata(1 = sì /0 = no)");
        paidStatusCol.setCellValueFactory(new PropertyValueFactory<>("ifPaid"));

        table.getColumns().addAll(codeCol, payerCol, totalCol, paidCol, dayCol, monthCol, yearCol, descCol, paidStatusCol);
        table.getItems().addAll(Client.getExpensesList());

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Tabella delle spese");

        Client.setExpensesList(null);
        stage.show();
    }

    public void handleConsultBalanceAction(ActionEvent event) {
    }
}
