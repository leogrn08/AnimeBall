/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.javafx;

import animeball.bd.DaoUsuario;
import animeball.bd.Usuario;
import static animeball.javafx.Javafx.mostraAlert;
import static animeball.javafx.Javafx.playSound;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 05200214
 */
public class TelaInicialController implements Initializable {

    private DaoUsuario daoUsuario = new DaoUsuario();
    private Usuario usuarioLogado;
    private MediaPlayer mediaPlayer;
    @FXML
    private Button myAnimeButton;
    @FXML
    private Button sairButton;
    @FXML
    private Button entrarButton;
    @FXML
    private Button criarContaButton;
    @FXML
    private TextField usuarioTextField;
    @FXML
    private PasswordField senhaPasswordField;
    @FXML
    private Label usuarioLabel, senhaLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Media sound = new Media(new File("backgroundmusic.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        myAnimeButton.setVisible(false);
    }

    @FXML
    private void myAnimeButton(ActionEvent event) {
        /*mediaPlayer.stop();
        playSound("pikachu.mp3");
        Stage stage;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("MeusAnimes.fxml"));
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            mostraAlert(Alert.AlertType.ERROR,
                    "Erro ao trocar de janela.",
                    "ERRO");
        }
        stage = (Stage) myAnimeButton.getScene().getWindow();
        stage.close();*/

        playSound("pikachu.mp3");
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MeusAnimes.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            mostraAlert(Alert.AlertType.ERROR,
                    "Erro ao trocar de janela.",
                    "ERRO");
        }
        MeusAnimesController display = Loader.getController();
        display.passaUsuario(usuarioLogado);
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(new Scene(p));
        stage.show();
        stage = (Stage) myAnimeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void sairButton(ActionEvent event) {
        Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoExe.setTitle("Sair");
        dialogoExe.setHeaderText("Você deseja sair?");
        Optional<ButtonType> result = dialogoExe.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    @FXML
    private void entrarButton(ActionEvent event) {
        Usuario usuario = new Usuario(usuarioTextField.getText(), senhaPasswordField.getText());
        if (!usuarioTextField.getText().isEmpty() && !senhaPasswordField.getText().isEmpty()) {
            if (daoUsuario.usuarioExiste(usuario)) {
                if (daoUsuario.entrar(usuario)) {
                    playSound("pikachu.mp3");
                    usuarioLogado = daoUsuario.getUsuario(usuario.getUsername());
                    afterLogIn();
                } else {
                    mostraAlert(Alert.AlertType.ERROR,
                            "Nome de usuário ou senha incorreto.",
                            "ERRO");
                }
            } else {
                mostraAlert(Alert.AlertType.ERROR,
                        "O usuário não existe.",
                        "ERRO");
            }
        } else {
            mostraAlert(Alert.AlertType.ERROR,
                    "Preencha todos os campos e tente novamente.",
                    "ERRO");
        }
    }

    @FXML
    private void criarContaButton(ActionEvent event
    ) {
        Usuario usuario = new Usuario(usuarioTextField.getText(), senhaPasswordField.getText(), "Membro");
        if (!usuarioTextField.getText().isEmpty() && !senhaPasswordField.getText().isEmpty()) {
            if (!daoUsuario.usuarioExiste(usuario)) {
                playSound("pikachu.mp3");
                daoUsuario.adiciona(usuario);
            } else {
                mostraAlert(Alert.AlertType.ERROR,
                        "O username já foi utilizado.",
                        "ERRO");
            }
        } else {
            mostraAlert(Alert.AlertType.ERROR,
                    "Preencha todos os campos e tente novamente.",
                    "ERRO");
        }
    }

    private void afterLogIn() {
        usuarioTextField.setVisible(false);
        senhaPasswordField.setVisible(false);
        entrarButton.setVisible(false);
        criarContaButton.setVisible(false);
        usuarioLabel.setVisible(false);
        senhaLabel.setVisible(false);
        myAnimeButton.setVisible(true);
    }
}
