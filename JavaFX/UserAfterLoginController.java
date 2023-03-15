package JavaFX;

import Model.Balance;
import Model.Expense;
import NetWork.Client.Client;
import NetWork.Message.Message;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * controller for the scene of user (not admin) after login
 */
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

    @FXML
    private CheckBox ifUserIdOnUser;

    @FXML
    private Label userIdUser;

    /**
     *
     * @param event the click action
     * @throws IOException error of action input
     */
    @FXML
    public void handleExitButtonActionUser(ActionEvent event) throws IOException {
        //Torna all'interfaccia iniziale
        Client.setNotAdmin();
        //Notificare il server che ha effettuato il log out
        Client.commandHandler.logOutRequest();

        FXMLLoader loaderInitial = new FXMLLoader(getClass().getResource("BeforeLoginScene.fxml"));
        Parent rootInitial = loaderInitial.load();
        Scene newScene = new Scene(rootInitial);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();

    }

    /**
     *
     * @param event the click action
     */
    @FXML
    public void handleInsertExpenseUser(ActionEvent event) {
        // Crea la finestra di dialogo
        Stage dialog = new Stage();
        dialog.setTitle("Inserisci dati");

        //set icon
        Image icon = new Image("Resource/icon.png");
        dialog.getIcons().add(icon);

        // Crea i controlli per l'input
        Label importoLabel = new Label("Importo:");
        TextField importoField = new TextField();
        Label dataLabel = new Label("Data:");
        DatePicker dataPicker = new DatePicker();
        Label descrizioneLabel = new Label("Descrizione:");
        TextField descrizioneField = new TextField();

        // Crea il pulsante di conferma
        Button confermaButton = new Button("Conferma");
        confermaButton.setOnAction(eventOn -> {
            // Legge i valori inseriti dall'utente
            double importo = Double.parseDouble(importoField.getText());
            LocalDate data = dataPicker.getValue();
            String descrizione = descrizioneField.getText();

            // Stampa i valori inseriti dall'utente
            System.out.println("Importo: " + importo);
            System.out.println("Data: " + data);
            System.out.println("Descrizione: " + descrizione);
            //inviare il messaggio
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
            } catch (IOException | ClassNotFoundException e) {
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

    /**
     *
     * @param event action click
     * @throws IOException error of action input
     * @throws ClassNotFoundException error of class used
     */
    @FXML
    public void handleViewAllMembersUser(ActionEvent event) throws IOException, ClassNotFoundException {
        //invia un messaggio al Server
        Client.commandHandler.viewRoomates();

        //riceve un messaggio dal Server
        Message replyFromServer = Client.messageHandler.receive();
        Client.messageHandler.handle(replyFromServer);


        // Creiamo una tabella
        TableView<Map.Entry<Integer, String>> table = new TableView<>();

        // Creiamo due colonne, una per l'id e una per lo username
        TableColumn<Map.Entry<Integer, String>, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getKey()).asObject());

        TableColumn<Map.Entry<Integer, String>, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));

        // Aggiungiamo le colonne alla tabella
        table.getColumns().add(idColumn);
        table.getColumns().add(usernameColumn);

        // Creiamo una lista di entry (ovvero chiavi-valori) a partire dalla mappa
        List<Map.Entry<Integer, String>> entries = new ArrayList<>(Client.messageHandler.getIdAndUsernames().entrySet());

        // Aggiungiamo gli entry alla tabella
        table.getItems().addAll(entries);

        // Creiamo una finestra di dialogo per mostrare la tabella all'utente
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Tabella utenti");
        stage.setWidth(300);
        stage.setHeight(500);

        //set icon
        Image icon = new Image("Resource/icon.png");
        stage.getIcons().add(icon);

        ((Group) scene.getRoot()).getChildren().add(table);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param event click action event
     */
    @FXML
    public void handlePaymentUser(ActionEvent event) {
        int code;

        // Crea un TextInputDialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Inserisci il codice della spesa");
        dialog.setHeaderText(null);
        dialog.setContentText("Codice:");
        dialog.setGraphic(new ImageView("Resource/iconEagle.jpg"));
        //icone
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("Resource/icon.png"));

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
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                // L'utente ha premuto cancel
                break;
            }
        }
    }

    /**
     *
     * @param event click action event
     * @throws IOException error of action input
     * @throws ClassNotFoundException error of class used
     */
    @FXML
    public void handleConsultExpensesToBePaidUser(ActionEvent event) throws IOException, ClassNotFoundException {
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

        TableColumn<Expense, Double> paidCol = new TableColumn<>("Da pagare");
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

        //set icon
        Image icon = new Image("Resource/icon.png");
        stage.getIcons().add(icon);

        Client.setExpensesList(null);
        stage.show();
    }

    /**
     *
     * @param event click action event
     * @throws IOException error of action input
     * @throws ClassNotFoundException error of the class used
     */
    @FXML
    public void handleConsultExpensesPaidUser(ActionEvent event) throws IOException, ClassNotFoundException {
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

        TableColumn<Expense, Double> paidCol = new TableColumn<>("Da pagare");
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

        //set icon
        Image icon = new Image("Resource/icon.png");
        stage.getIcons().add(icon);

        Client.setExpensesList(null);
        stage.show();
    }

    /**
     *
     * @param event click action event
     * @throws IOException error of action input
     * @throws ClassNotFoundException error of the class used
     */
    @FXML
    public void handleConsultAllExpensesUser(ActionEvent event) throws IOException, ClassNotFoundException {
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

        TableColumn<Expense, Double> paidCol = new TableColumn<>("Da pagare");
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

        //set icon
        Image icon = new Image("Resource/icon.png");
        stage.getIcons().add(icon);

        Client.setExpensesList(null);
        stage.show();
    }

    /**
     *
     * @param event click action
     * @throws IOException error of action input
     * @throws ClassNotFoundException error of the class used
     */
    @FXML
    public void handleConsultBalanceUser(ActionEvent event) throws IOException, ClassNotFoundException {
        //invia un messsaggio al Server
        Client.commandHandler.consultationAllBalance();

        //riceve un messaggio dal Server
        Message replyFromServer = Client.messageHandler.receive();
        Client.messageHandler.handle(replyFromServer);

        TableView<Balance> table = new TableView<>();

        // Definizione delle colonne della tabella
        TableColumn<Balance, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Balance, Double> owedCol = new TableColumn<>("Importo dovuto");
        owedCol.setCellValueFactory(new PropertyValueFactory<>("amount_owed"));

        TableColumn<Balance, Double> paidCol = new TableColumn<>("Importo pagato");
        paidCol.setCellValueFactory(new PropertyValueFactory<>("amount_paid"));

        TableColumn<Balance, Double> balanceCol = new TableColumn<>("Bilancio");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));

        table.getColumns().addAll(idCol, owedCol, paidCol, balanceCol);
        table.getItems().addAll(Client.getBalanceList());

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Tabella del bilancio");

        //set icon
        Image icon = new Image("Resource/icon.png");
        stage.getIcons().add(icon);

        stage.show();
    }


    public void handleUserIdCheckUser() {

        //ricavare l'id dell'utente
        String id = String.valueOf(Client.getIdClient());
        userIdUser.setText(id);

        //se la prima volta che clicco
        userIdUser.setVisible(ifUserIdOnUser.isSelected());

        // if checkbox is selected -> let label visible
        ifUserIdOnUser.setOnAction(eventOn -> userIdUser.setVisible(ifUserIdOnUser.isSelected()));

    }
}
