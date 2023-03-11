package NetWork.Client;

import NetWork.Message.Message;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Client + MessageHandler(riceve messaggio) + CommandHandler(invia messaggi) = part Client
 */
public class Client {
    private static boolean isLoggedIn = false;
    private static int id_client;
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
            MessageHandler messageHandler = new MessageHandler(socket);
            CommandHandler commandHandler = new CommandHandler(messageHandler);
            System.out.println("Benvenuto nel sistema di gestione delle spese.");


            while(true){
                if(!Client.isLoggedIn){
                    //TODO  ------------------------ prima login -----------------------------
                    // Loop principale del client
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
                            System.out.println("Please choose an username");
                            Scanner usernameScannerRegister = new Scanner(System.in);
                            String usernameRegister = usernameScannerRegister.nextLine();

                            System.out.println("Please choose a password");
                            Scanner passwordScannerRegister = new Scanner(System.in);
                            String passwordRegister = passwordScannerRegister.nextLine();

                            commandHandler.registerRequest(usernameRegister, passwordRegister);
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

                    //TODO  ------------------------ dopo login -------------------------
                    // esegui il login e salva le credenziali in username e password
                    Scanner scannerAfterLogin = new Scanner(System.in);
                    int scelta = -1;

                    while (scelta != 0) {
                        System.out.println("                  ");
                        System.out.println("Scegli un'opzione:");
                        System.out.println("1. Inserisci una spesa");
                        System.out.println("2. Da fare");
                        System.out.println("3. Da fare");
                        System.out.println("0. Esci");

                        scelta = scannerAfterLogin.nextInt();
                        scanner.nextLine(); // consuma il carattere newline rimanente dopo nextInt()

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
                                commandHandler.espenseMessage(id_client,amountExpense,dayExpense,monthExpense,yearExpense,descriptionExpense);

                                break;
                            case 2:
                                // aggiungi prodotto al carrello
                                break;
                            case 3:
                                // visualizza carrello
                                break;
                            case 0:
                                Client.setLoggedIn(false);
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




        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public static void setId_client(int id_client) {
        Client.id_client = id_client;
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
