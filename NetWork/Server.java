package NetWork;

import Model.Expense;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Expense expense;
    private int porta;

    public Server(Expense model, int porta) {
        this.expense = model;
        this.porta = porta;
    }

    public void start() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(porta);
                while (true) {
                    Socket socket = serverSocket.accept();
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    Expense expense = (Expense) inputStream.readObject();
                    aggiungiSpesa(expense);
                    inputStream.close();
                    socket.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void aggiungiSpesa(Expense spesa) {
        model.aggiungiSpesa(spesa);
        inviaAggiornamentoBilanci();
    }

    private void inviaAggiornamentoBilanci() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", porta);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(model.getBilanci());
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
