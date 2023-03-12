package NetWork.Client;

import Model.Expense;
import NetWork.Message.*;

import java.io.IOException;

public class CommandHandler {
    private MessageHandler messageHandler;

    //costruttore
    public CommandHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    //opearazioni da fare

    //operazione login ->
    public void loginRequest(String username, String password) throws IOException {
        LoginRequestMessage message = new LoginRequestMessage(username, password);
        messageHandler.send(message);
    }

    //operazione register ->
    public void registerRequest(String username, String password,int adminOrNot) throws IOException {
        RegisterRequestMessage message = new RegisterRequestMessage(username, password,adminOrNot);
        messageHandler.send(message);
    }

    //operazione loggout
    public void logoutMessage() throws IOException {
        LogoutMessage message = new LogoutMessage();
        messageHandler.send(message);
    }

    //operazione inserimento spesa
    public void expenseMessage(int id_payer, double amount, int day, int month , int year, String description) throws IOException {
        ExpenseMessage message = new ExpenseMessage(new Expense(0,id_payer,amount ,0, day, month, year, description,0));
        messageHandler.send(message);
    }

    //operazione visualizzazione coinquilini
    public void viewRoomates() throws IOException {
        ViewUsernamesRequest message = new ViewUsernamesRequest();
        messageHandler.send(message);
    }

    //richiesta di pagamento di una spesa
    public void paymentRequset(int expense_id) throws IOException {
        PaymentRequestMessage message = new PaymentRequestMessage(expense_id);
        messageHandler.send(message);
    }

    //richeista di consultare le spese da pagare
    public void consultationExpensesToBePaidRequest() throws IOException {
        ConsultExpensesToBePaidRequest message = new ConsultExpensesToBePaidRequest();
        messageHandler.send(message);
    }

    //richietsa di consultare le spese pagate
    public void consultationExpensesPaidRequest() throws IOException {
        ConsultExpensesPaidRequest message = new ConsultExpensesPaidRequest();
        messageHandler.send(message);
    }

    public void consultationAllExpenses() throws IOException {
        ConsultAllExpensesRequest message = new ConsultAllExpensesRequest();
        messageHandler.send(message);
    }

    public void consultationAllBalance() throws IOException {
        ConsultBalanceRequest message = new ConsultBalanceRequest();
        messageHandler.send(message);
    }

    public void deleteAllInfoRequest() throws IOException {
        DeleteAllInformationRequest message = new DeleteAllInformationRequest();
        messageHandler.send(message);
    }
    public void deleteExpensesBalanceRequest() throws IOException {
        DeleteExpensesBalanceRequest message = new DeleteExpensesBalanceRequest();
        messageHandler.send(message);
    }


    // add other command methods here

    public void closeClient() throws IOException {
        messageHandler.getOutput().close();
        if(messageHandler.getInput() != null){
            messageHandler.getInput().close();
        }
        messageHandler.getSocket().close();
    }
}
