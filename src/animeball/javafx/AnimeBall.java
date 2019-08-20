/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author 05200214
 */
public class AnimeBall extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("TelaInicial.fxml"));
        
        Scene scene = new Scene(root);
        
        
        stage.setScene(scene);
        stage.setTitle("Anime Ball");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
