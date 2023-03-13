package NetWork.Client;

import NetWork.Message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageHandler {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    private Client client;

    //costruttore
    public MessageHandler(Socket socket) throws IOException {
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        this.socket = socket;
    }

    //invio del messaggio
    public void send(Message message) throws IOException {
        output.writeObject(message);
    }

    //riceve un messaggio
    public Message receive() throws IOException, ClassNotFoundException {
        return (Message) input.readObject();
    }

    //elabora il messaggio ricevuto
    public void handle(Message message) {
        MessageType type = message.getType();
        System.out.println(">>>>il messaggio ricevuto è tipo "+type);
        switch (type) {

            case LOGIN_RESPONSE:
                handleLoginResponse((LoginResponseMessage) message);
                break;
            case REGISTER_RESPONSE:
                handleRegisterResponse((RegisterResponseMessage) message);
                break;
            case EXPENSE_RESPONSE:
                handleExpenseResponse((ExpenseMessageResponse) message);
                break;
            case VIEW_USERNAMES_RESPONSE:
                handlerViewUsernamesResponse((ViewUsernamesResponse) message);
                break;
            case PAYMENT_EXPENSE_RESPONSE:
                handlerPaymentResponse((PaymentResponseMessage) message);
                break;
            case CONSULTATION_EXPENSES_TO_BE_PAID_RESPONSE:
                handlerExpenseToBePaidResponse((ConsultExpensesToBePaidResponse) message);
                break;
            case CONSULTATION_EXPENSES_PAID_RESPONSE:
                handlerExpensePaidResponse((ConsultExpensesPaidResponse) message);
                break;
            case CONSULTATION_ALL_EXPENSES_INFORMATION_RESPONSE:
                handlerAllExpensesConsultationResponse((ConsultAllExpensesResponse) message);
                break;
            case CONSULTATION_BALANCE_INFORMATION_RESPONSE:
                handlerAllBalanceConsultationResponse((ConsultBalanceResponse) message);
                break;
            // handle other message types here
        }
    }

    private void handleLoginResponse(LoginResponseMessage message) {
        System.out.println("Hai ricevuto un messaggio:  >>>   "+ message.getMessage());
        // handle data reply message
        //se login ha avuto successo
        if(message.getIfSuccess()){
            Client.setLoggedIn(true);
            Client.setId_client(message.getClientId());
            //se è un admin
            if(message.getIfAdmin()==1){
                Client.setAdmin();
            }
        }
        else {
            Client.setLoggedIn(false);
        }
    }
    private void handleRegisterResponse(RegisterResponseMessage message){
        //System.out.println("Hai ricevuto un messaggio: >>>    "+ message.getMessage());
        Client.setMessage(message.getMessage());
        //System.out.println("Sei un: "+message.getIfAdmin() +     "1= Admin, 0= User");
    }

    private void handleExpenseResponse(ExpenseMessageResponse message){
        Client.setMessage(message.getMessage());
    }
    private void handlerViewUsernamesResponse(ViewUsernamesResponse message){
        System.out.println("Questi sono i tuoi coinquilini:");
        for(int i=0; i<message.getUsernames().size();i++){
            System.out.println(message.getUsernames().get(i));
        }
    }

    private void handlerPaymentResponse(PaymentResponseMessage message){
        System.out.println("Messaggio relativo al pagamento effettuato");
        System.out.println(message.getMessage());
    }

    private void handlerExpenseToBePaidResponse(ConsultExpensesToBePaidResponse message){
        System.out.println("Elenco delle spese da pagare:    "+"ci sono "+message.getListOfExpense().size()+" spese");
        for(int i=0; i<message.getListOfExpense().size();i++){
            System.out.println("Codice spesa: "+ message.getListOfExpense().get(i).getExpense_id()
                    + " Codice pagante: "+ message.getListOfExpense().get(i).getId_payer()
                    + " Totale: "+message.getListOfExpense().get(i).getPayer_amount()
                    + " Da pagare: "+message.getListOfExpense().get(i).getPayee_amount()
                    + " Giorno: "+message.getListOfExpense().get(i).getDay()
                    + " Mese: "+message.getListOfExpense().get(i).getMonth()
                    + " Anno: "+message.getListOfExpense().get(i).getYear()
                    + " Descrizione: "+message.getListOfExpense().get(i).getDescription()
            );
        }
    }

    private void handlerExpensePaidResponse(ConsultExpensesPaidResponse message){
        System.out.println("Elenco delle spese pagate:    "+"ci sono "+message.getListOfExpense().size()+" spese");
        for(int i=0; i<message.getListOfExpense().size();i++){
            System.out.println("Codice spesa: "+ message.getListOfExpense().get(i).getExpense_id()
                    + " Codice pagante: "+ message.getListOfExpense().get(i).getId_payer()
                    + " Totale: "+message.getListOfExpense().get(i).getPayer_amount()
                    + " Pagata: "+message.getListOfExpense().get(i).getPayee_amount()
                    + " Giorno: "+message.getListOfExpense().get(i).getDay()
                    + " Mese: "+message.getListOfExpense().get(i).getMonth()
                    + " Anno: "+message.getListOfExpense().get(i).getYear()
                    + " Descrizione: "+message.getListOfExpense().get(i).getDescription()
            );
        }
    }

    private void handlerAllExpensesConsultationResponse(ConsultAllExpensesResponse message){
        System.out.println("Tutte le informazioni: ");
        System.out.println("Elenco delle spese :    "+"ci sono "+message.getListOfExpense().size()+" spese");
        for(int i=0; i<message.getListOfExpense().size();i++){
            System.out.println("Codice spesa: "+ message.getListOfExpense().get(i).getExpense_id()
                    + " Codice pagante: "+ message.getListOfExpense().get(i).getId_payer()
                    + " Totale: "+message.getListOfExpense().get(i).getPayer_amount()
                    + " Pagata: "+message.getListOfExpense().get(i).getPayee_amount()
                    + " Giorno: "+message.getListOfExpense().get(i).getDay()
                    + " Mese: "+message.getListOfExpense().get(i).getMonth()
                    + " Anno: "+message.getListOfExpense().get(i).getYear()
                    + " Descrizione: "+message.getListOfExpense().get(i).getDescription()
                    + " Se pagato: "+message.getListOfExpense().get(i).getIfPaid()
            );
        }
    }

    private void handlerAllBalanceConsultationResponse(ConsultBalanceResponse message){
        System.out.println("Tabella Balance: ");
        for(int i=0; i<message.getListOfBalance().size();i++){
            System.out.println("Id: "+ message.getListOfBalance().get(i).getId()
                    + " importo da pagare: "+ message.getListOfBalance().get(i).getAmount_owed()
                    + " importo pagato: "+ message.getListOfBalance().get(i).getAmount_paid()
                    + " bilancio: "+ message.getListOfBalance().get(i).getBalance()
            );
        }
    }
    private void handlerDeleteAllInfoResponse(DeleteAllInformationResponse message){
        System.out.println(message.getMessage());
    }
    private void handlerDeleteExpenseBalance(DeleteExpensesBalanceResponse message){
        System.out.println(message.getMessage());
    }
    public ObjectInputStream getInput() {
        return input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public Socket getSocket() {
        return socket;
    }
    // add other handler methods here
}
