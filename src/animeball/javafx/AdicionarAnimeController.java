/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.javafx;

import animeball.bd.DaoAnime;
import animeball.bd.Anime;
import animeball.bd.AnimeTemCategoria;
import animeball.bd.Categoria;
import animeball.bd.DaoAnimeTemCategoria;
import animeball.bd.DaoCategoria;
import animeball.bd.Usuario;
import static animeball.javafx.Javafx.mostraAlert;
import static animeball.javafx.Javafx.playSound;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 05200214
 */
public class AdicionarAnimeController implements Initializable {

    private Usuario usuario;
    private DaoCategoria daoCategoria = new DaoCategoria();
    private DaoAnime daoAnime = new DaoAnime();
    private DaoAnimeTemCategoria daoAnimeTemCategoria = new DaoAnimeTemCategoria();
    private FileChooser fileChooser = new FileChooser();
    @FXML
    private Button concluirButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Button addImageButton;
    @FXML
    private Button addCategoriaButton;
    @FXML
    private Button removeCategoriaButton;
    @FXML
    private TextField animeName;
    @FXML
    private TextField numEp;
    @FXML
    private TextField ano;
    @FXML
    private TextField link;
    @FXML
    private ListView<Categoria> allCategoriasListView;
    private ObservableList<Categoria> allCategorias = daoCategoria.pesquisaTodos();
    @FXML
    private ListView<Categoria> selectedCategoriasListView;
    private ObservableList<Categoria> selectedCategorias = FXCollections.observableArrayList();
    @FXML
    private ImageView image;
    private Image imagem;
    private String caminhoImagem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numEp.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                numEp.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        ano.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                ano.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        allCategoriasListView.setItems(allCategorias);
        allCategoriasListView.setCellFactory((list) -> {
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
        selectedCategoriasListView.setItems(selectedCategorias);
        selectedCategoriasListView.setCellFactory((list) -> {
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

    @FXML
    private void concluirButton(ActionEvent event) {

        if (verificadorTextField()) {
            if (!selectedCategorias.isEmpty()) {
                if (imagem != null) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Tudo certo?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> bt = a.showAndWait();
                    try {
                        if (bt.get() == ButtonType.YES) {
                            playSound("pikachu.mp3");
                            Anime anime = new Anime();
                            anime.setNome(animeName.getText());
                            anime.setNumEp(Integer.parseInt(numEp.getText()));
                            anime.setAno(Integer.parseInt(ano.getText()));
                            anime.setLink(link.getText());
                            anime.setImagem(caminhoImagem);
                            anime.setUserID(usuario.getId());
                            boolean ok = daoAnime.adiciona(anime);
                            if (ok) {
                                for (Categoria categoria : selectedCategorias) {
                                    AnimeTemCategoria relacionamento = new AnimeTemCategoria(daoAnime.buscaId(anime), categoria.getId());
                                    daoAnimeTemCategoria.adiciona(relacionamento);
                                }
                            } else {
                                mostraAlert(Alert.AlertType.ERROR,
                                        "Erro ao adicionar Anime.",
                                        "ERRO");
                            }
                        }
                    } catch (SQLException ex) {
                        mostraAlert(Alert.AlertType.ERROR,
                                "Problema no SQL durante o cadastro.",
                                "ERRO");
                    } catch (ClassNotFoundException ex) {
                        mostraAlert(Alert.AlertType.ERROR,
                                "Identificador (ID) do anime no BD não foi encontrado.",
                                "ERRO");
                    }
                    /*Stage stage;
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
                    stage = (Stage) concluirButton.getScene().getWindow();
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
                    display.passaUsuario(usuario);
                    Parent p = Loader.getRoot();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
                    stage.setScene(new Scene(p));
                    stage.show();
                    stage = (Stage) concluirButton.getScene().getWindow();
                    stage.close();
                } else {
                    mostraAlert(Alert.AlertType.ERROR,
                            "Você deve selecionar uma imagem!",
                            "ERRO");
                }
            } else {
                mostraAlert(Alert.AlertType.ERROR,
                        "Pelo menos uma categoria ser selecionada!",
                        "ERRO");
            }
        } else {
            mostraAlert(Alert.AlertType.ERROR,
                    "Todos os campos devem estar preenchidos!",
                    "ERRO");
        }

    }

    @FXML
    private void cancelarButton(ActionEvent event) {
        /*playSound("pikachu.mp3");
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
        stage = (Stage) cancelarButton.getScene().getWindow();
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
        display.passaUsuario(usuario);
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(new Scene(p));
        stage.show();
        stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addImageButton(ActionEvent event
    ) {
        Stage stage = new Stage();
        stage.setTitle("File Chooser Sample");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            imagem = new Image(file.toURI().toString());
            caminhoImagem = file.toURI().toString();
            image.setImage(imagem);
        }
    }

    @FXML
    private void addCategoriaButton(ActionEvent event
    ) {
        if (allCategoriasListView.getSelectionModel().getSelectedItem() != null) {
            selectedCategorias.add(allCategoriasListView.getSelectionModel().getSelectedItem());
            allCategorias.remove(allCategoriasListView.getSelectionModel().getSelectedIndex());
            allCategoriasListView.setItems(allCategorias);
            selectedCategoriasListView.setItems(selectedCategorias);
        }
    }

    @FXML
    private void removeCategoriaButton(ActionEvent event
    ) {
        if (selectedCategoriasListView.getSelectionModel().getSelectedItem() != null) {
            allCategorias.add(selectedCategoriasListView.getSelectionModel().getSelectedItem());
            selectedCategorias.remove(selectedCategoriasListView.getSelectionModel().getSelectedIndex());
            selectedCategoriasListView.setItems(selectedCategorias);
            allCategoriasListView.setItems(allCategorias);
        }
    }

    private boolean verificadorTextField() {
        if (animeName.getText().isEmpty()) {
            return false;
        }
        if (numEp.getText().isEmpty()) {
            return false;
        }
        if (ano.getText().isEmpty()) {
            return false;
        }
        return !link.getText().isEmpty();
    }

    public void passaUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
