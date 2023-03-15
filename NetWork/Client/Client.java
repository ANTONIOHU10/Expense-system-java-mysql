package NetWork.Client;

import JavaFX.ClientApplication;
import JavaFX.ConnectionDialog;
import Model.Balance;
import Model.Expense;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Client + MessageHandler(riceve messaggio) + CommandHandler(invia messaggi) = part Client
 */
public class Client {
    //Variabili per identificazione
    private static boolean isLoggedIn = false;
    private static int id_client;
    private static boolean isAdmin = false;
    private static String message;
    private static List<Expense> expensesList = new ArrayList<>();
    private static List<Balance> balanceList = new ArrayList<>();
    public static MessageHandler messageHandler;
    public static CommandHandler commandHandler;
    //GUI


    public static void main(String[] args) {
        String host = "localhost"; // IP o nome del server
        int port = 8080; // porta su cui il server ascolta le connessioni


            try (
                    Socket socket = new Socket(host, port)
            ) {
                //istanziare handler di message + command  -> set up
                messageHandler = new MessageHandler(socket);
                commandHandler = new CommandHandler(messageHandler);
                System.out.println("Benvenuto nel sistema di gestione delle spese.");

                ClientApplication.launch(ClientApplication.class,args);

            } catch (IOException e) {
                // Se la connessione fallisce, chiedi all'utente se vuole riprovare
                ConnectionDialog.launch(ConnectionDialog.class);

            }


    }

    /**
     *
     * @return true = logged, false = not logged
     */
    public static boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    /**
     *
     * @return true = Admin, false = User
     */
    public static boolean getIsAdmin(){
        return isAdmin;
    }

    /**
     *
     * @return unique id of the user
     */
    public static int getIdClient(){
        return id_client;
    }

    /**
     *
     * @param loggedIn true = logged, false = not logged
     */
    public static void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    /**
     *
     * @param id_client the unique id of user
     */
    public static void setId_client(int id_client) {
        Client.id_client = id_client;
    }

    /**
     *
     * @param expensesList list of expense
     */
    public static void setExpensesList(List<Expense> expensesList){
        Client.expensesList = expensesList;
    }

    /**
     *  let the user becomes Admin
     */
    public static void setAdmin(){
        Client.isAdmin = true;
    }

    /**
     *  let the user becomes User
     */
    public static void setNotAdmin(){Client.isAdmin = false;}

    /**
     *
     * @param message the message to be load
     */
    public static void setMessage(String message) {
        Client.message = message;
    }

    /**
     *
     * @param balanceList the list of balance to be shown
     */
    public static void setBalanceList(List<Balance> balanceList) {
        Client.balanceList = balanceList;
    }

    /**
     *
     * @return get the message to be shown
     */
    public static String getMessage() {
        return message;
    }

    /**
     *
     * @return list of the expenses
     */
    public static List<Expense> getExpensesList() {
        return expensesList;
    }

    /**
     *
     * @return list of the balance
     */
    public static List<Balance> getBalanceList() {
        return balanceList;
    }
}
