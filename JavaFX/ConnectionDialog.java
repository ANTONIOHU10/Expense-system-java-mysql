package JavaFX;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * a panel that inform the user, connection is down
 */
public class ConnectionDialog extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Client");

        Label label = new Label("Il server è offline, riprovare più tardi");
        label.setFont(new Font("Arial", 16));

        Button yesButton = new Button("OK");
        yesButton.setPrefWidth(80);
        yesButton.setOnAction(event -> stage.close());

        //icone
        ImageView icon = new ImageView(new Image("Resource/iconCoffee2.jpg"));

        VBox vbox = new VBox(20,icon , label, yesButton);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 300, 220);

        stage.getIcons().add(new Image("Resource/icon.png"));
        stage.setScene(scene);
        stage.show();
    }


}