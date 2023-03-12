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
            // handle other message types here
        }
    }

    private void handleLoginResponse(LoginResponseMessage message) {
        System.out.println("Hai ricevuto un messaggio:  >>>   "+ message.getMessage());
        // handle data reply message
        if(message.getIfSuccess()){
            Client.setLoggedIn(true);
            Client.setId_client(message.getClientId());
        }
        else {
            Client.setLoggedIn(false);
        }
    }
    private void handleRegisterResponse(RegisterResponseMessage message){
        System.out.println("Hai ricevuto un messaggio: >>>    "+ message.getMessage());
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
                    + "Codice pagante: "+ message.getListOfExpense().get(i).getId_payer()
                    + "Totale: "+message.getListOfExpense().get(i).getPayer_amount()
                    + "Da pagare: "+message.getListOfExpense().get(i).getPayee_amount()
                    + "Giorno: "+message.getListOfExpense().get(i).getDay()
                    + "Mese: "+message.getListOfExpense().get(i).getMonth()
                    + "Anno: "+message.getListOfExpense().get(i).getYear()
                    + "Descrizione: "+message.getListOfExpense().get(i).getDescription()
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
