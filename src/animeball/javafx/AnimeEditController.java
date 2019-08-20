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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 05200214
 */
public class AnimeEditController implements Initializable {

    private Usuario usuario;
    DaoAnime daoAnime = new DaoAnime();
    DaoAnimeTemCategoria daoAnimeTemCategoria = new DaoAnimeTemCategoria();
    DaoCategoria daoCategoria = new DaoCategoria();
    private int id;
    private Anime anime;
    private Anime temp;
    private String nome;
    private int nep, a;
    private FileChooser fileChooser = new FileChooser();
    @FXML
    private Button concluirButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Button excluirButton;
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
    private ObservableList<Categoria> allCategoriasLista = FXCollections.observableArrayList();
    @FXML
    private ListView<Categoria> selectedCategoriasListView;
    private ObservableList<Categoria> selectedCategoriasLista = FXCollections.observableArrayList();
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
    }

    @FXML
    private void concluirButton(ActionEvent event) {

        if (verificadorTextField()) {
            if (verificadorListView()) {
                if (verificadorImagem()) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Confirma cadastro?", ButtonType.YES, ButtonType.NO);
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
                            anime.setId(this.id);
                            temp = anime;
                            boolean ok = daoAnime.altera(anime);
                            if (ok) {
                                ObservableList<AnimeTemCategoria> relacionamentos = FXCollections.observableArrayList();
                                for (Categoria categoria : selectedCategoriasLista) {
                                    AnimeTemCategoria relacionamento = new AnimeTemCategoria(daoAnime.buscaId(anime), categoria.getId());
                                    relacionamentos.add(relacionamento);
                                    if (!daoAnimeTemCategoria.pesquisaTodos().contains(relacionamento)) {
                                        daoAnimeTemCategoria.adiciona(relacionamento);
                                    }
                                }
                                ObservableList<AnimeTemCategoria> lista = daoAnimeTemCategoria.pesquisaRelacionamentoIdAnime(this.anime.getId());
                                lista.stream().filter((relacionamento) -> (!relacionamentos.contains(relacionamento))).forEachOrdered((relacionamento) -> {
                                    daoAnimeTemCategoria.remove(relacionamento);
                                });
                            } else {
                                mostraAlert(Alert.AlertType.ERROR,
                                        "Erro ao editar Anime.",
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
                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("AnimeSelect.fxml"));
                    try {
                        Loader.load();
                    } catch (IOException ex) {
                        mostraAlert(Alert.AlertType.ERROR,
                                "Erro ao trocar de janela.",
                                "ERRO");
                    }
                    AnimeSelectController display = Loader.getController();
                    display.passaDadosAnime(temp);
                    display.passaUsuario(usuario);
                    Parent p = Loader.getRoot();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
                    stage.setScene(new Scene(p));
                    stage.show();
                    stage = (Stage) cancelarButton.getScene().getWindow();
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
        playSound("pikachu.mp3");
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("AnimeSelect.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            mostraAlert(Alert.AlertType.ERROR,
                    "Erro ao trocar de janela.",
                    "ERRO");
        }
        AnimeSelectController display = Loader.getController();
        display.passaDadosAnime(this.anime);
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
    private void excluirButton(ActionEvent event
    ) {
        daoAnime.remove(this.anime);
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

    private void mostraDadosAnime(Anime anime) {
        this.animeName.setText(anime.getNome());
        this.numEp.setText(Integer.toString(anime.getNumEp()));
        this.ano.setText(Integer.toString(anime.getAno()));
        this.link.setText(anime.getLink());
        imagem = new Image(caminhoImagem);
        image.setImage(imagem);
        selectedCategoriasListView.setItems(selectedCategoriasLista);
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
        for (Categoria categoria : allCategoriasLista) {
            if (selectedCategoriasLista.contains(categoria)) {
                allCategoriasLista.remove(categoria);
            }
        }
        allCategoriasListView.setItems(allCategoriasLista);
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
    }

    public void passaDadosAnime(Anime anime) {
        this.id = anime.getId();
        this.anime = daoAnime.pesquisaAnime(id);
        selectedCategoriasLista = selectedCategorias();
        for (Categoria categoria : daoCategoria.pesquisaTodos()) {
            if (!selectedCategoriasLista.contains(categoria)) {
                allCategoriasLista.add(categoria);
            }
        }
        caminhoImagem = anime.getImagem();
        mostraDadosAnime(anime);
    }

    private ObservableList<Categoria> selectedCategorias() {
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
    private void addImageButton(ActionEvent event) {
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
    private void addCategoriaButton(ActionEvent event) {
        if (allCategoriasListView.getSelectionModel().getSelectedItem() != null) {
            selectedCategoriasLista.add(allCategoriasListView.getSelectionModel().getSelectedItem());
            allCategoriasLista.remove(allCategoriasListView.getSelectionModel().getSelectedIndex());
            allCategoriasListView.setItems(allCategoriasLista);
            selectedCategoriasListView.setItems(selectedCategoriasLista);
        }
    }

    @FXML
    private void removeCategoriaButton(ActionEvent event) {
        if (selectedCategoriasListView.getSelectionModel().getSelectedItem() != null) {
            allCategoriasLista.add(selectedCategoriasListView.getSelectionModel().getSelectedItem());
            selectedCategoriasLista.remove(selectedCategoriasListView.getSelectionModel().getSelectedIndex());
            selectedCategoriasListView.setItems(selectedCategoriasLista);
            allCategoriasListView.setItems(allCategoriasLista);
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

    private boolean verificadorListView() {
        return !selectedCategoriasLista.isEmpty();
    }

    private boolean verificadorImagem() {
        return !(imagem.getHeight() == 0 && imagem.getWidth() == 0);
    }

    public void passaUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
