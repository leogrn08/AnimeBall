/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.javafx;

import animeball.bd.Anime;
import animeball.bd.AnimeTemCategoria;
import animeball.bd.Categoria;
import animeball.bd.DaoAnime;
import animeball.bd.DaoAnimeTemCategoria;
import animeball.bd.DaoCategoria;
import animeball.bd.Usuario;
import static animeball.javafx.Javafx.mostraAlert;
import static animeball.javafx.Javafx.playSound;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.HostServices;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 05200214
 */
public class AnimeSelectController implements Initializable {

    private Usuario usuario;
    DaoAnime daoAnime = new DaoAnime();
    DaoCategoria daoCategoria = new DaoCategoria();
    DaoAnimeTemCategoria daoAnimeTemCategoria = new DaoAnimeTemCategoria();
    private int id;
    private Anime anime;
    @FXML
    private Button voltarButton;
    @FXML
    private Button editarButton;
    @FXML
    private Label animeName;
    @FXML
    private Label numEp;
    @FXML
    private Label ano;
    @FXML
    private ListView<Categoria> categoriaListView;
    private ObservableList<Categoria> categoriaLista = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> numEpCBox;
    private ObservableList<String> numEpLista = FXCollections.observableArrayList();
    @FXML
    private Hyperlink link;
    @FXML
    private ImageView image;
    private Image imagem;
    private HostServices host;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numEpCBox.setOnAction((event) -> {
            String numEpAssistidos = numEpCBox.getSelectionModel().getSelectedItem();
            anime.setNumEpAssistidos(Integer.parseInt(numEpAssistidos));
            daoAnime.updateNumEp(anime);
        });
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

    @FXML
    private void editarButton(ActionEvent event) {
        playSound("pikachu.mp3");
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("AnimeEdit.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            mostraAlert(Alert.AlertType.ERROR,
                    "Erro ao trocar de janela.",
                    "ERRO");
        }
        AnimeEditController display = Loader.getController();
        display.passaDadosAnime(this.anime);
        display.passaUsuario(usuario);
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(new Scene(p));
        stage.show();
        stage = (Stage) editarButton.getScene().getWindow();
        stage.close();
    }

    public void mostraDadosAnime(Anime anime) {
        animeName.setText(anime.getNome());
        numEp.setText(Integer.toString(anime.getNumEp()));
        ano.setText(Integer.toString(anime.getAno()));
        link.setText(anime.getLink());
        for (int i = 0; i <= Integer.parseInt(numEp.getText()); i++) {
            numEpLista.add(Integer.toString(i));
        }
        numEpCBox.setPrefWidth(numEpLista.get(numEpLista.size() - 1).length() * 5 + 50);
        numEpCBox.setItems(numEpLista);
        numEpCBox.getSelectionModel().select(anime.getNumEpAssistidos());
        try {
            imagem = new Image(anime.getImagem());
        } catch (NullPointerException ex) {

        }
        image.setImage(imagem);
    }

    public void passaDadosAnime(Anime anime) {
        this.id = anime.getId();
        this.anime = daoAnime.pesquisaAnime(id);
        mostraDadosAnime(anime);
        categoriaLista = selectedCategorias();
        categoriaListView.setItems(categoriaLista);
        categoriaListView.setCellFactory((list) -> {
            return new ListCell<Categoria>() {
                @Override
                protected void updateItem(Categoria item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getNome());
                    }
                }
            };
        });
    }

    public ObservableList<Categoria> selectedCategorias() {
        ObservableList<Categoria> all = daoCategoria.pesquisaTodos();
        ObservableList<AnimeTemCategoria> relacionamentos = daoAnimeTemCategoria.pesquisaRelacionamentoIdAnime(anime.getId());
        ObservableList<Categoria> selected = FXCollections.observableArrayList();
        all.forEach((categoria) -> {
            relacionamentos.stream().filter((relacionamento) -> (relacionamento.getIdCategoria() == categoria.getId())).forEachOrdered((_item) -> {
                selected.add(categoria);
            });
        });
        return selected;
    }

    @FXML
    private void abreLink(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(link.getText()));
        } catch (IOException e1) {
            mostraAlert(Alert.AlertType.ERROR,
                    "O link não funciona.",
                    "ERRO");
        } catch (URISyntaxException e1) {
            System.out.println("O link não funciona");
        }
    }

    public void passaUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
