package NetWork.Client;

import JavaFX.ClientApplication;
import Model.Expense;
import NetWork.Message.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public static MessageHandler messageHandler;
    public static CommandHandler commandHandler;
    //GUI


    public static void main(String[] args) {
        Client client = new Client();
        String host = "localhost"; // IP o nome del server
        int port = 8080; // porta su cui il server ascolta le connessioni
        try (
             Socket socket = new Socket(host, port);

             //legge un carattere dalla tastiera -> è necessario
             Scanner scanner = new Scanner(System.in)
        ) {
            //istanziare handler di message + command  -> set up
            messageHandler = new MessageHandler(socket);
            commandHandler = new CommandHandler(messageHandler);
            System.out.println("Benvenuto nel sistema di gestione delle spese.");

            ClientApplication.launch(ClientApplication.class,args);
            //fixme---------------------le seguenti sono da togliere
            /*
            while(true){
                if(!Client.isLoggedIn){
                    //TODO  ------------------------ prima login -----------------------------
                    System.out.println("Cosa vuoi fare?");
                    System.out.println("1. Login");
                    System.out.println("2. Registrazione");
                    System.out.println("3. Uscire");

                    //dichiaro int scelta da usare
                    int scelta;
                    while(true){
                        System.out.println("Inserisci un intero:");
                        if(scanner.hasNextInt()){
                            scelta = scanner.nextInt();
                            System.out.println("Hai inserito: "+ scelta);
                            break;
                        } else {
                            System.out.println("Input non valido, riprova,");
                            scanner.next();
                        }
                    }

                    switch (scelta) {
                        case 1:
                            //TODO quando effettua login, dovrebbe riceve una risposta del suo ruolo
                            // e inizializza variabile role= admin/ user
                            System.out.println("Please insert your username:");
                            Scanner usernameScannerLogin = new Scanner(System.in);
                            String usernameLogin = usernameScannerLogin.nextLine();

                            System.out.println("Please insert your password");
                            Scanner passwordScannerLogin = new Scanner(System.in);
                            String passwordLogin = passwordScannerLogin.nextLine();

                            commandHandler.loginRequest(usernameLogin, passwordLogin);
                            break;
                        case 2:
                            //TODO completare il caso di registrazione
                            //TODO chiede se è un admin o user
                            int adminOrNot;
                            while(true){
                                System.out.println("Sei un admin o user: 1 = admin, 0 = user");
                                if(scanner.hasNextInt()){
                                    adminOrNot = scanner.nextInt();
                                    System.out.println("Hai inserito: "+ adminOrNot);
                                    break;
                                } else {
                                    System.out.println("Input non valido, riprova,");
                                    scanner.next();
                                }
                            }
                            System.out.println("Scegli il nome utente");
                            Scanner usernameScannerRegister = new Scanner(System.in);
                            String usernameRegister = usernameScannerRegister.nextLine();

                            System.out.println("Scegli il password");
                            Scanner passwordScannerRegister = new Scanner(System.in);
                            String passwordRegister = passwordScannerRegister.nextLine();

                            commandHandler.registerRequest(usernameRegister, passwordRegister,adminOrNot);
                            break;


                        case 3:
                            System.out.println("Grazie per aver usato il sistema di gestione delle spese. Arrivederci!");
                            commandHandler.closeClient();
                            return;
                        default:
                            System.out.println("Scelta non valida.");
                            break;
                    }

                    //elabora il messaggio ricevuto dal Server

                    Message replyFromServer = messageHandler.receive();
                    messageHandler.handle(replyFromServer);
                } else {

                    //  ------------------------ dopo login -------------------------
                    //TODO: role = if admin  else user
                    // esegui il login e salva le credenziali in username e password
                    Scanner scannerAfterLogin = new Scanner(System.in);
                    int scelta = -1;


                    //se l'utente è un amministratore
                    if(!isAdmin){
                        //utente è un amministratore
                        while (scelta != 0) {
                            System.out.println("                  ");
                            System.out.println("Scegli un'opzione:");
                            System.out.println("1. Inserisci una spesa");
                            System.out.println("2. Visualizzare tutti i coinquilini");
                            System.out.println("3. Pagare una spesa");
                            System.out.println("4. Consultazione lista delle spese da pagare");
                            System.out.println("5. Consultazione lista delle spese pagate");
                            System.out.println("6. Consultazione tutta la cronologia");
                            System.out.println("7. Consultazione la tabella bilancio");
                            System.out.println("0. Esci");

                            scelta = scannerAfterLogin.nextInt();
                            //scanner.nextLine(); // consuma il carattere newline rimanente dopo nextInt()

                            switch (scelta) {
                                case 1:
                                    // inserisci una spesa

                                    //-----------------------------...importo...------------------------------
                                    //legge un double -> l'importo
                                    double amountExpense;
                                    while(true){
                                        System.out.println("Inserisci l'importo della spesa:");
                                        if(scanner.hasNextDouble()){
                                            amountExpense = scanner.nextDouble();
                                            System.out.println("Hai inserito: "+ amountExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }
                                    //----------------------------...giorno...-------------------------------
                                    int dayExpense;
                                    while(true){
                                        System.out.println("Inserisci il giorno:");
                                        if(scanner.hasNextInt()){
                                            dayExpense = scanner.nextInt();
                                            System.out.println("Hai inserito: "+ dayExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }
                                    //----------------------------...month...--------------------------------
                                    int monthExpense;
                                    while(true){
                                        System.out.println("Inserisci il mese:");
                                        if(scanner.hasNextInt()){
                                            monthExpense = scanner.nextInt();
                                            System.out.println("Hai inserito: "+ monthExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }
                                    //----------------------------...anno...-------------------------------
                                    int yearExpense;
                                    while(true){
                                        System.out.println("Inserisci l'anno:");
                                        if(scanner.hasNextInt()){
                                            yearExpense = scanner.nextInt();
                                            System.out.println("Hai inserito: "+ yearExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }

                                    //----------------------------..descrizione...------------------------------
                                    String descriptionExpense;
                                    while(true){
                                        System.out.println("Inserisci la descrizione:");
                                        if(scanner.hasNextLine()){
                                            //consuma un carattere del precedente input
                                            scanner.nextLine();
                                            descriptionExpense = scanner.nextLine();
                                            System.out.println("Hai inserito: "+ descriptionExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }
                                    //-------------------------...preparazione messaggio...------------------------
                                    commandHandler.expenseMessage(id_client,amountExpense,dayExpense,monthExpense,yearExpense,descriptionExpense);

                                    break;
                                case 2:
                                    // visualizzare i coinquilini
                                    commandHandler.viewRoomates();

                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServer = messageHandler.receive();
                                    messageHandler.handle(replyFromServer);

                                    break;
                                case 3:
                                    // pagamento di una spesa
                                    //TODO inserisce id di una spesa-> lo stato della spesa => pagata
                                    //quando uno paga la spesa ( importo payee_amount )-> nella tabella balance ha amount_owed - quello pagato?
                                    int idExpense;
                                    while(true){
                                        System.out.println("Inserisci il codice della spesa:");
                                        if(scanner.hasNextInt()){
                                            idExpense = scanner.nextInt();
                                            System.out.println("Hai inserito: "+ idExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }
                                    commandHandler.paymentRequset(idExpense);
                                    Message paymentReplyFromServer = messageHandler.receive();
                                    messageHandler.handle(paymentReplyFromServer);
                                    break;
                                case 4:
                                    commandHandler.consultationExpensesToBePaidRequest();
                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServerForExpenseToBePaid = messageHandler.receive();
                                    messageHandler.handle(replyFromServerForExpenseToBePaid);

                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServerAdminToPay = messageHandler.receive();
                                    messageHandler.handle(replyFromServerAdminToPay);
                                    break;
                                case 5:
                                    commandHandler.consultationExpensesPaidRequest();
                                    Message replyFromServerForExpensePaid = messageHandler.receive();
                                    messageHandler.handle(replyFromServerForExpensePaid);

                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServerAdminPaid = messageHandler.receive();
                                    messageHandler.handle(replyFromServerAdminPaid);
                                    break;
                                case 6:
                                    commandHandler.consultationAllExpenses();
                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServerAdminAllExpenses = messageHandler.receive();
                                    messageHandler.handle(replyFromServerAdminAllExpenses);
                                    break;
                                case 7:
                                    commandHandler.consultationAllBalance();
                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServerAdminAllBalance = messageHandler.receive();
                                    messageHandler.handle(replyFromServerAdminAllBalance);
                                    break;
                                case 0:
                                    Client.setLoggedIn(false);
                                    commandHandler.logoutMessage();
                                    // esci dalla condizione logged -> quindi torna all'interfaccia iniziale
                                    Client.isAdmin = false;
                                    break;
                                default:
                                    System.out.println("Opzione non valida. Riprova.");
                                    break;
                            }
                        }
                    } else {
                    //l'utente non è un amministratore
                        while (scelta != 0) {
                            System.out.println("                  ");
                            System.out.println("Scegli un'opzione:");
                            System.out.println("1. Inserisci una spesa");
                            System.out.println("2. Visualizzare tutti i coinquilini");
                            System.out.println("3. Pagare una spesa");
                            System.out.println("4. Consultazione lista delle spese da pagare");
                            System.out.println("5. Consultazione lista delle spese pagate");
                            System.out.println("6. Consultazione tutta la cronologia");
                            System.out.println("7. Consultazione la tabella bilancio");
                            System.out.println("8. Cancellare tutte le informazioni");
                            System.out.println("9. Cancellare le spese e i conti");
                            System.out.println("0. Esci");

                            scelta = scannerAfterLogin.nextInt();
                            //scanner.nextLine(); // consuma il carattere newline rimanente dopo nextInt()

                            switch (scelta) {
                                case 1:
                                    // inserisci una spesa

                                    //-----------------------------...importo...------------------------------
                                    //legge un double -> l'importo
                                    double amountExpense;
                                    while(true){
                                        System.out.println("Inserisci l'importo della spesa:");
                                        if(scanner.hasNextDouble()){
                                            amountExpense = scanner.nextDouble();
                                            System.out.println("Hai inserito: "+ amountExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }
                                    //----------------------------...giorno...-------------------------------
                                    int dayExpense;
                                    while(true){
                                        System.out.println("Inserisci il giorno:");
                                        if(scanner.hasNextInt()){
                                            dayExpense = scanner.nextInt();
                                            System.out.println("Hai inserito: "+ dayExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }
                                    //----------------------------...month...--------------------------------
                                    int monthExpense;
                                    while(true){
                                        System.out.println("Inserisci il mese:");
                                        if(scanner.hasNextInt()){
                                            monthExpense = scanner.nextInt();
                                            System.out.println("Hai inserito: "+ monthExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }
                                    //----------------------------...anno...-------------------------------
                                    int yearExpense;
                                    while(true){
                                        System.out.println("Inserisci l'anno:");
                                        if(scanner.hasNextInt()){
                                            yearExpense = scanner.nextInt();
                                            System.out.println("Hai inserito: "+ yearExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }

                                    //----------------------------..descrizione...------------------------------
                                    String descriptionExpense;
                                    while(true){
                                        System.out.println("Inserisci la descrizione:");
                                        if(scanner.hasNextLine()){
                                            //consuma un carattere del precedente input
                                            scanner.nextLine();
                                            descriptionExpense = scanner.nextLine();
                                            System.out.println("Hai inserito: "+ descriptionExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }
                                    //-------------------------...preparazione messaggio...------------------------
                                    commandHandler.expenseMessage(id_client,amountExpense,dayExpense,monthExpense,yearExpense,descriptionExpense);

                                    break;
                                case 2:
                                    // visualizzare i coinquilini
                                    commandHandler.viewRoomates();

                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServer = messageHandler.receive();
                                    messageHandler.handle(replyFromServer);

                                    break;
                                case 3:
                                    // pagamento di una spesa
                                    //TODO inserisce id di una spesa-> lo stato della spesa => pagata
                                    //quando uno paga la spesa ( importo payee_amount )-> nella tabella balance ha amount_owed - quello pagato?
                                    int idExpense;
                                    while(true){
                                        System.out.println("Inserisci il codice della spesa:");
                                        if(scanner.hasNextInt()){
                                            idExpense = scanner.nextInt();
                                            System.out.println("Hai inserito: "+ idExpense);
                                            break;
                                        } else {
                                            System.out.println("Input non valido, riprova,");
                                            scanner.next();
                                        }
                                    }
                                    commandHandler.paymentRequset(idExpense);
                                    Message paymentReplyFromServer = messageHandler.receive();
                                    messageHandler.handle(paymentReplyFromServer);
                                    break;
                                case 4:
                                    commandHandler.consultationExpensesToBePaidRequest();
                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServerForExpenseToBePaid = messageHandler.receive();
                                    messageHandler.handle(replyFromServerForExpenseToBePaid);

                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServerUserToPay = messageHandler.receive();
                                    messageHandler.handle(replyFromServerUserToPay);
                                    break;
                                case 5:
                                    commandHandler.consultationExpensesPaidRequest();
                                    Message replyFromServerForExpensePaid = messageHandler.receive();
                                    messageHandler.handle(replyFromServerForExpensePaid);

                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServerUserPaid = messageHandler.receive();
                                    messageHandler.handle(replyFromServerUserPaid);
                                    break;
                                case 6:
                                    commandHandler.consultationAllExpenses();
                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServerUserAllExpenses = messageHandler.receive();
                                    messageHandler.handle(replyFromServerUserAllExpenses);
                                    break;
                                case 7:
                                    commandHandler.consultationAllBalance();

                                    //elabora il messaggio ricevuto dal Server
                                    Message replyFromServerAdminAllBalance = messageHandler.receive();
                                    messageHandler.handle(replyFromServerAdminAllBalance);
                                    break;
                                case 8:
                                    commandHandler.deleteAllInfoRequest();
                                    break;
                                case 9:
                                    commandHandler.deleteExpensesBalanceRequest();
                                    break;
                                case 0:
                                    Client.setLoggedIn(false);
                                    Client.isAdmin = false;
                                    commandHandler.logoutMessage();
                                    // esci dalla condizione logged -> quindi torna all'interfaccia iniziale
                                    break;
                                default:
                                    System.out.println("Opzione non valida. Riprova.");
                                    break;
                            }
                        }
                    }


                }

            }

             */




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getIsLoggedIn() {
        return isLoggedIn;
    }
    public static boolean getIsAdmin(){
        return isAdmin;
    }

    public static int getIdClient(){
        return id_client;
    }
    public static void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public static void setId_client(int id_client) {
        Client.id_client = id_client;
    }
    public static void setExpensesList(List<Expense> expensesList){
        Client.expensesList = expensesList;
    }
    public static void setAdmin(){
        Client.isAdmin = true;
    }

    public static void setNotAdmin(){Client.isAdmin = false;}

    public static void setMessage(String message) {
        Client.message = message;
    }

    public static String getMessage() {
        return message;
    }

    public static List<Expense> getExpensesList() {
        return expensesList;
    }

    private static void inserisciSpesa(Scanner scanner, ObjectOutputStream  output, ObjectInputStream input) throws IOException {
        System.out.println("Inserisci l'importo della spesa:");
        double importo = scanner.nextDouble();

        System.out.println("Inserisci la descrizione della spesa:");
        scanner.nextLine(); // Consuma il newline lasciato dal nextDouble()
        String descrizione = scanner.nextLine();

        System.out.println("Inserisci il numero di coinquilini che partecipano alla spesa:");
        int numCoinquilini = scanner.nextInt();
        //TODO il messaggio che devo inviare deve essere di tipo oggetto e non stringa
        /*
        output.println("SPESA " + importo + " " + descrizione + " " + numCoinquilini);

        String response = input.readLine();
        if (response.equals("OK")) {
            System.out.println("Spesa inserita correttamente.");
        } else {
            System.out.println("Errore durante l'inserimento della spesa.");
        }
        */
    }

    private static void visualizzaSaldo(ObjectOutputStream output, ObjectInputStream input) throws IOException {
        /*
        * invio un oggetto con domanda -> visualizza saldo e mi deve rispondere il saldo
        * */

        /*
        output.println("SALDO");

        String response = input.readLine();
        System.out.println("Saldo corrente: " + response + " EUR");

         */
    }

}
