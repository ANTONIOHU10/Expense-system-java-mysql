package NetWork.Client;

import NetWork.Message.Message;

import java.io.*;
import java.net.Socket;
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

                int scelta = scanner.nextInt();

                switch (scelta) {
                    case 1:
                        System.out.println("Please insert your username:");
                        Scanner usernameScannerLogin = new Scanner(System.in);
                        String usernameLogin = usernameScannerLogin.nextLine();

                        System.out.println("Please insert your password");
                        Scanner passwordScannerLogin = new Scanner(System.in);
                        String passwordLogin = passwordScannerLogin.nextLine();

                        commandHandler.loginRequest(usernameLogin,passwordLogin);
                        break;
                    case 2:
                        //TODO completare il caso di registrazione
                        System.out.println("Please choose an username");
                        Scanner usernameScannerRegister = new Scanner(System.in);
                        String usernameRegister = usernameScannerRegister.nextLine();

                        System.out.println("Please choose a password");
                        Scanner passwordScannerRegister = new Scanner(System.in);
                        String passwordRegister = passwordScannerRegister.nextLine();

                        commandHandler.registerRequest(usernameRegister,passwordRegister);
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
            }


        } catch (IOException | ClassNotFoundException e) {
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
