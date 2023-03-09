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
    public static void main(String[] args) {
        String host = "localhost"; // IP o nome del server
        int port = 8080; // porta su cui il server ascolta le connessioni

        try (
                Socket socket = new Socket(host, port);

             /*
             //comunicazione basata su oggetti, non uso più queste due
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
              */

             //legge un carattere dalla tastiera -> è necessario
             Scanner scanner = new Scanner(System.in)
        ) {
            //istanziare handler di message + command
            MessageHandler messageHandler = new MessageHandler(socket);
            CommandHandler commandHandler = new CommandHandler(messageHandler);
            System.out.println("Benvenuto nel sistema di gestione delle spese.");

            // Loop principale del client

            while (true) {
                System.out.println("Cosa vuoi fare?");
                System.out.println("1. Login");
                System.out.println("2. Registrazione");
                System.out.println("3. Uscire");

                //TODO :  ----------------------------- prima di login --------------------------
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
                /*
                Message replyFromServer = messageHandler.receive();
                messageHandler.handle(replyFromServer);

                 */


            }

            //TODO  ------------------------ dopo login -------------------------
            // esegui il login e salva le credenziali in username e password
            /*
            Scanner scannerAfterLogin = new Scanner(System.in);
            int scelta = -1;

            while (scelta != 0) {
                System.out.println("Scegli un'opzione:");
                System.out.println("1. Visualizza elenco prodotti");
                System.out.println("2. Aggiungi prodotto al carrello");
                System.out.println("3. Visualizza carrello");
                System.out.println("0. Esci");

                scelta = scannerAfterLogin.nextInt();
                scanner.nextLine(); // consuma il carattere newline rimanente dopo nextInt()

                switch (scelta) {
                    case 1:
                        // visualizza elenco prodotti
                        break;
                    case 2:
                        // aggiungi prodotto al carrello
                        break;
                    case 3:
                        // visualizza carrello
                        break;
                    case 0:
                        // esci dal loop e chiudi la connessione al server
                        break;
                    default:
                        System.out.println("Opzione non valida. Riprova.");
                        break;
                }
            }
            */


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void inserisciSpesa(Scanner scanner,ObjectOutputStream  output, ObjectInputStream input) throws IOException {
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
