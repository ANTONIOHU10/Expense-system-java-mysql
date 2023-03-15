package NetWork.Server;

import Controller.ExpenseController;
import Controller.UsersController;
import NetWork.Message.*;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

/**
 * a single thread that contain a connection with the Client socket
 */
//per Server -> gestisce tutte le connessioni
public class ClientHandler extends Thread {
    private final Socket clientSocket;

    private UsersController usersController;
    private ExpenseController expenseController;
    //per identificare l'utente nella database e dagli altri
    private int idUser;
    //Serve l'oggetto server per fare delle operazioni sulla lista
    private final Server server;

    /**
     *
     * @param clientSocket socket for this client
     * @param server the server to be used
     */
    public ClientHandler(Socket clientSocket,Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    public void run() {
        try {
            //inizializzo il handler per manipolare i messaggi
            ServerMessageHandler serverMessageHandler = new ServerMessageHandler(clientSocket);

            //connessione al database
            try {
                //dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","825310894");
                connectToDatabase();
                expenseController = new ExpenseController("jdbc:mysql://localhost:3306/test","root","825310894");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Connessione al database fallita");
            }

            // Riceve i dati dal client e li elabora
            while (true) {
                try{
                    Message request = serverMessageHandler.receive();


                    //######### ... elabora il messaggio ricevuto...##########

                    System.out.println("ricevuto un messaggio di tipo >>>>"+ request.getType());
                    switch (request.getType()) {
                        case LOGIN_REQUEST -> {
                            System.out.println("esegue operazione del caso LOGIN");
                            //casting il message base -> loginRequestMessage
                            LoginRequestMessage loginMessage = (LoginRequestMessage) request;
                            String usernameLogin = loginMessage.getUsername();
                            String passwordLogin = loginMessage.getPassword();

                            //verifico se l'account esiste
                            boolean loginSuccess = usersController.login(usernameLogin, passwordLogin);

                            //invio della risposta la client
                            if (loginSuccess) {
                                //se esiste
                                System.out.println("Account e password sono corretti");
                                idUser = usersController.getUserId(usernameLogin);
                                server.activeClientHandlers.put(this, idUser);
                                System.out.println("####numero di utenti attivi:" + server.activeClientHandlers.size() + "" +
                                        "            ID di questo client = " + idUser);
                                //messaggio inviato al client
                                if (usersController.getUserRoleById(idUser).equals("Admin")) {
                                    serverMessageHandler.send(new LoginResponseMessage(true, idUser, "Login successful!", 1));
                                } else {
                                    serverMessageHandler.send(new LoginResponseMessage(true, idUser, "Login successful!", 0));
                                }


                            } else {
                                System.out.println("Username o password incorretto");
                                serverMessageHandler.send(new LoginResponseMessage(false, idUser, "invalid username or password", 0));
                            }
                        }
                        case REGISTER_REQUEST -> {
                            System.out.println("esegue operazione del caso REGISTER_REQUEST");
                            //casting il message base -> loginRequestMessage
                            RegisterRequestMessage registerMessage = (RegisterRequestMessage) request;
                            String usernameRegister = registerMessage.getUsername();
                            String passwordRegister = registerMessage.getPassword();
                            int ifAdmin = registerMessage.getAdminOrNot();
                            //verifico se l'account esiste
                            boolean registerSuccess = usersController.usernameExists(usernameRegister);
                            System.out.println("Server: risultato della verifica è " + registerSuccess);
                            //invio della risposta la client
                            if (registerSuccess) {
                                //se esiste il nome cercato-> non va bene
                                serverMessageHandler.send(new RegisterResponseMessage(false, "Username already taken", ifAdmin));
                            } else {
                                //se non esiste il nome cercato-> va bene
                                usersController.registerAccount(usernameRegister, passwordRegister, ifAdmin);
                                serverMessageHandler.send(new RegisterResponseMessage(true, "Register successful", 0));
                            }
                        }
                        case LOGIN_RESPONSE -> System.out.println("un client ha effettuato il log in");
                        case EXPENSE -> {
                            System.out.println("esegue operazione del caso EXPENSE");
                            ExpenseMessage expenseMessage = (ExpenseMessage) request;
                            double amount = expenseMessage.getExpense().getPayer_amount();
                            int day = expenseMessage.getExpense().getDay();
                            int month = expenseMessage.getExpense().getMonth();
                            int year = expenseMessage.getExpense().getYear();
                            String description = expenseMessage.getExpense().getDescription();
                            expenseController.insertExpense(idUser, amount, day, month, year, description);
                            //messaggio di risposta
                            serverMessageHandler.send(new ExpenseMessageResponse("Spesa inserita con successo!"));
                        }
                        case VIEW_USERNAMES_REQUEST -> {
                            System.out.println("preparando i nomi dei coinquilini da inviare.....");
                            serverMessageHandler.send(new ViewUsernamesResponse(usersController.getUsersIdAndUsername()));
                        }
                        case LOGOUT -> {
                            System.out.println("un client ha effettuato il logout");
                            server.activeClientHandlers.remove(idUser);
                        }
                        case PAYMENT_EXPENSE_REQUEST -> {
                            PaymentRequestMessage paymentRequestMessage = (PaymentRequestMessage) request;
                            System.out.println(">>>ricevuto una richiesta di pagamento della spesa:     " + paymentRequestMessage.getExpenseId());
                            boolean ifPaid = expenseController.payExpense(paymentRequestMessage.getExpenseId(), idUser);
                            boolean ifExists = expenseController.consultIfExistsExpense(paymentRequestMessage.getExpenseId());
                            if (ifExists) {
                                if (ifPaid) {
                                    //invio l'avvenuta successa del pagamento
                                    serverMessageHandler.send(new PaymentResponseMessage("Pagamento avvenuto con successo!"));
                                } else {
                                    //invio messaggio che non può pagare di nuovo
                                    serverMessageHandler.send(new PaymentResponseMessage("Non puoi pagare di nuovo!"));
                                }
                            } else {
                                //invio messaggio che la spesa non esiste
                                serverMessageHandler.send(new PaymentResponseMessage("Non esiste la spesa inserita!"));
                            }
                        }
                        case CONSULTATION_EXPENSES_PAID_REQUEST -> {
                            System.out.println(">>>ricevuto una richiesta di consultazione delle spese pagate");
                            //option 1= paid
                            ConsultExpensesPaidResponse consultExpensesPaidResponse = new ConsultExpensesPaidResponse(expenseController.consultListExpense(idUser, 1));
                            serverMessageHandler.send(consultExpensesPaidResponse);
                        }
                        case CONSULTATION_EXPENSES_TO_BE_PAID_REQUEST -> {
                            System.out.println(">>>ricevuto una richiesta di consultazione delle spese da pagare");
                            //option 0= to be paid
                            ConsultExpensesToBePaidResponse consultExpensesToBePaidResponse = new ConsultExpensesToBePaidResponse(expenseController.consultListExpense(idUser, 0));
                            serverMessageHandler.send(consultExpensesToBePaidResponse);
                        }
                        case CONSULTATION_ALL_EXPENSES_INFORMATION_REQUEST -> {
                            System.out.println(">>>ricevuto una richiesta di consultazione tutte le spese");
                            ConsultAllExpensesResponse consultAllExpensesResponse = new ConsultAllExpensesResponse(expenseController.consultAllExpenses());
                            serverMessageHandler.send(consultAllExpensesResponse);
                        }
                        case CONSULTATION_BALANCE_INFORMATION_REQUEST -> {
                            System.out.println(">>>ricevuto una richiesta di consultazione tabella balance");
                            ConsultBalanceResponse consultBalanceResponse = new ConsultBalanceResponse(expenseController.consultAllBalances());
                            serverMessageHandler.send(consultBalanceResponse);
                        }
                        case DELETE_ALL_INFORMATION_REQUEST -> {
                            usersController.deleteAllData();
                            DeleteAllInformationResponse deleteAllInformationResponse = new DeleteAllInformationResponse("Tutti i dati sono cancellati");
                            serverMessageHandler.send(deleteAllInformationResponse);
                        }
                        case DELTE_EXPENSES_BALANCE_INFORMATION_REQUEST -> {
                            usersController.deleteExpensesBalance();
                            DeleteExpensesBalanceResponse deleteExpensesBalanceResponse = new DeleteExpensesBalanceResponse("Tutte le spese e il conto sono cancellati");
                            serverMessageHandler.send(deleteExpensesBalanceResponse);
                        }
                    }

                } catch (SocketException e) {
                    // Eccezione sollevata quando il client si disconnette, quando chiudo il client direttamente
                    System.out.println("Il client "+ clientSocket.getInetAddress()+"si è disconnesso");
                    server.removeClientHandler(this);

                    server.activeClientHandlers.remove(idUser);
                    serverMessageHandler.closeSocket();
                    break;
                    //per evitare quando termino il client il Server va in shout down
                } catch (EOFException e) {
                    System.out.println("Il client"+ clientSocket.getInetAddress()+ " si è disconnesso(terminato)");

                    server.activeClientHandlers.remove(idUser);
                    serverMessageHandler.closeSocket();
                    break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws SQLException error of connection to database
     */
    private void connectToDatabase() throws SQLException {
        usersController= new UsersController("jdbc:mysql://localhost:3306/test", "root", "825310894");
    }

}

