/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.javafx;

import animeball.bd.DaoAnime;
import animeball.bd.DaoConsultas;
import animeball.bd.DaoUsuario;
import animeball.bd.Usuario;
import static animeball.javafx.Javafx.mostraAlert;
import static animeball.javafx.Javafx.playSound;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 05200214
 */
public class GerenciamentoController implements Initializable {

    private Usuario usuario;
    private DaoUsuario daoUsuario = new DaoUsuario();
    private DaoAnime daoAnime = new DaoAnime();
    private DaoConsultas daoConsultas = new DaoConsultas();
    @FXML
    private Button voltarButton;
    @FXML
    private TableView userTableView, animeTableView, userAnimeQuantidadeTableView,
            animeCategoriaQuantidadeTableView;
    @FXML
    private TableColumn idUsuarioColumn, idAnimeColumn, usuarioAnimeColumn,
            userAnimeQuantidadeColumn, animeQuantidadeColumn, animeCategoriaQuantidadeColumn,
            categoriaQuantidadeColumn, nomeUsuarioColumn, senhaUsuarioColumn,
            cargoUsuarioColumn, nomeAnimeColumn;
    private ObservableList dadosUsuario = FXCollections.observableArrayList();
    private ObservableList dadosAnime = FXCollections.observableArrayList();
    private ObservableList dadosTabelaAnimeUsuario = FXCollections.observableArrayList();
    private ObservableList dadosTabelaUsuarioAnimeQuantidade = FXCollections.observableArrayList();
    private ObservableList dadosTabelaAnimeCategoriaQuantidade = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idUsuarioColumn.setCellValueFactory(new PropertyValueFactory("id"));
        nomeUsuarioColumn.setCellValueFactory(new PropertyValueFactory("username"));
        senhaUsuarioColumn.setCellValueFactory(new PropertyValueFactory("senha"));
        cargoUsuarioColumn.setCellValueFactory(new PropertyValueFactory("cargo"));
        dadosUsuario = daoUsuario.pesquisaTodos();
        userTableView.setItems(dadosUsuario);

        idAnimeColumn.setCellValueFactory(new PropertyValueFactory("id"));
        nomeAnimeColumn.setCellValueFactory(new PropertyValueFactory("anime"));
        usuarioAnimeColumn.setCellValueFactory(new PropertyValueFactory("usuario"));
        dadosTabelaAnimeUsuario = daoConsultas.pesquisaAnimeUsuario();
        animeTableView.setItems(dadosTabelaAnimeUsuario);

        userAnimeQuantidadeColumn.setCellValueFactory(new PropertyValueFactory("usuario"));
        animeQuantidadeColumn.setCellValueFactory(new PropertyValueFactory("quantidadeAnime"));
        dadosTabelaUsuarioAnimeQuantidade = daoConsultas.pesquisaUsuarioAnimeQuantidade();
        userAnimeQuantidadeTableView.setItems(dadosTabelaUsuarioAnimeQuantidade);

        animeCategoriaQuantidadeColumn.setCellValueFactory(new PropertyValueFactory("anime"));
        categoriaQuantidadeColumn.setCellValueFactory(new PropertyValueFactory("categoriaQuantidade"));
        dadosTabelaAnimeCategoriaQuantidade = daoConsultas.pesquisaAnimeCategoriaQuantidade();
        animeCategoriaQuantidadeTableView.setItems(dadosTabelaAnimeCategoriaQuantidade);
    }

    @FXML
    private void voltarButton(ActionEvent event) {
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
        display.passaUsuario(usuario);
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(new Scene(p));
        stage.show();
        stage = (Stage) voltarButton.getScene().getWindow();
        stage.close();
    }

    public void passaUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
