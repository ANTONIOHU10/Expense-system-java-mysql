package JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Optional;

public class ClientApplication extends Application {
    @Override
    //Questo metodo ha un parametro Stage primaryStage
    // che rappresenta la finestra principale dell'applicazione.
    public void start(Stage primaryStage) throws Exception{
        //viene creato un oggetto FXMLLoader che viene utilizzato per caricare il file FXML
        // Controller.fxml che contiene la struttura della GUI dell'applicazione.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BeforeLoginScene.fxml"));

        //otenere l'icone
        Image icon = new Image("Resource/icon.png");

        //carica il file FXML e lo converte in un oggetto Parent
        // che rappresenta la radice della scena principale.
        Parent root = loader.load();

        //Questa riga imposta il titolo della finestra principale.
        primaryStage.setTitle("Payment system");

        primaryStage.setResizable(false); //finestra modificabile
        primaryStage.setMaximized(false);

        primaryStage.getIcons().add(icon);

        primaryStage.setOnCloseRequest(e -> { //bottone di chiusura
            e.consume(); //per evitare che si chiude subito
            closeProgram(primaryStage);
        });

        // viene creata una nuova scena con la radice root e le dimensioni 600x400 pixel,
        // e viene impostata come scena principale della finestra.
        primaryStage.setScene(new Scene(root, 892, 600));

        //Questa riga fa apparire la finestra principale sullo schermo.
        primaryStage.show();
        //viene recuperato l'oggetto Controller che gestisce il file FXML caricato
        Controller controller = loader.getController();

        //viene impostata la scena della finestra principale come scena del controller.
        controller.setScene(primaryStage.getScene());
    }

    private void closeProgram(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma chiusura");
        alert.setHeaderText("Sei sicuro di voler chiudere la finestra?");
        Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("Resource/icon.png"));

        alert.setContentText("Tutti i dati non salvati andranno persi.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // esegui le operazioni di chiusura
            primaryStage.close();
            // ...
            primaryStage.close();
        } else {
            // l'utente ha cliccato "Cancel" o ha chiuso la finestra di dialogo
        }
    }
    /*
    public static void main(String[] args) {
        launch(args);
    }

     */
}
