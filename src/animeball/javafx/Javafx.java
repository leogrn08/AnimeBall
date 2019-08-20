/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.javafx;

import animeball.bd.Anime;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author 05200214
 */
public class Javafx {

    public static void mostraAlert(Alert.AlertType tipo, String mensagem, String titulo) {
        Alert d = new Alert(tipo);

        d.setContentText(mensagem);
        d.setTitle(titulo);
        d.showAndWait();
    }

    public static void playSound(String url) {
        Media sound = new Media(new File(url).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

}
