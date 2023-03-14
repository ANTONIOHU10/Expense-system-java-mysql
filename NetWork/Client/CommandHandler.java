package NetWork.Client;

import Model.Expense;
import NetWork.Message.*;
import java.io.IOException;

public record CommandHandler(MessageHandler messageHandler) {
    //costruttore

    //opearazioni da fare

    /**
     *
     * @param username username used for login
     * @param password password used for login
     * @throws IOException error of the message I/O
     */
    //operazione login ->
    public void loginRequest(String username, String password) throws IOException {
        LoginRequestMessage message = new LoginRequestMessage(username, password);
        messageHandler.send(message);
    }

    /**
     *
     * @param username username used for register
     * @param password password used for register
     * @param adminOrNot 0= User, 1 = Admin
     * @throws IOException error of the message I/O
     */
    //operazione register ->
    public void registerRequest(String username, String password, int adminOrNot) throws IOException {
        RegisterRequestMessage message = new RegisterRequestMessage(username, password, adminOrNot);
        messageHandler.send(message);
    }

    /**
     *
     * @param id_payer the unique id of the uploader
     * @param amount total value of the expense
     * @param day information time
     * @param month information time
     * @param year information time
     * @param description information of the detail
     * @throws IOException error of the message I/O
     */
    //operazione inserimento spesa
    public void expenseMessage(int id_payer, double amount, int day, int month, int year, String description) throws IOException {
        ExpenseMessage message = new ExpenseMessage(new Expense(0, id_payer, amount, 0, day, month, year, description, 0));
        messageHandler.send(message);
    }

    /**
     *
     * @throws IOException error of the message I/O
     */
    //operazione visualizzazione coinquilini
    public void viewRoomates() throws IOException {
        ViewUsernamesRequest message = new ViewUsernamesRequest();
        messageHandler.send(message);
    }

    /**
     *
     * @param expense_id the unique id of the expense
     * @throws IOException error of the message I/O
     */
    //richiesta di pagamento di una spesa
    public void paymentRequset(int expense_id) throws IOException {
        PaymentRequestMessage message = new PaymentRequestMessage(expense_id);
        messageHandler.send(message);
    }

    /**
     *
     * @throws IOException error of the message I/O
     */
    //richeista di consultare le spese da pagare
    public void consultationExpensesToBePaidRequest() throws IOException {
        ConsultExpensesToBePaidRequest message = new ConsultExpensesToBePaidRequest();
        messageHandler.send(message);
    }

    /**
     *
     * @throws IOException error of the message I/O
     */
    //richietsa di consultare le spese pagate
    public void consultationExpensesPaidRequest() throws IOException {
        ConsultExpensesPaidRequest message = new ConsultExpensesPaidRequest();
        messageHandler.send(message);
    }

    /**
     *
     * @throws IOException error of the message I/O
     */
    public void consultationAllExpenses() throws IOException {
        ConsultAllExpensesRequest message = new ConsultAllExpensesRequest();
        messageHandler.send(message);
    }

    /**
     *
     * @throws IOException error of the message I/O
     */
    public void consultationAllBalance() throws IOException {
        ConsultBalanceRequest message = new ConsultBalanceRequest();
        messageHandler.send(message);
    }

    /**
     *
     * @throws IOException error of the message I/O
     */
    public void deleteAllInfoRequest() throws IOException {
        DeleteAllInformationRequest message = new DeleteAllInformationRequest();
        messageHandler.send(message);
    }

    /**
     *
     * @throws IOException error of the message I/O
     */
    public void deleteExpensesBalanceRequest() throws IOException {
        DeleteExpensesBalanceRequest message = new DeleteExpensesBalanceRequest();
        messageHandler.send(message);
    }

    /**
     *
     * @throws IOException error of the message I/O
     */
    public void logOutRequest() throws IOException {
        LogoutMessage message = new LogoutMessage();
        messageHandler.send(message);
    }

    // add other command methods here

}
