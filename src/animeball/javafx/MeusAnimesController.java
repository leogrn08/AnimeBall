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
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Iterator;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author 05200214
 */
public class MeusAnimesController implements Initializable {

    private Usuario usuario;
    DaoAnime daoAnime = new DaoAnime();
    DaoCategoria daoCategoria = new DaoCategoria();
    DaoAnimeTemCategoria daoAnimeTemCategoria = new DaoAnimeTemCategoria();
    @FXML
    private TextField pesquisarAnime;
    @FXML
    private Button clearNumEpButton, clearAnoButton, clearCategoriaButton;
    @FXML
    private Button voltarButton;
    @FXML
    private Button filtrarButton;
    @FXML
    private Button addAnimeButton;
    @FXML
    private Button gerenciarButton;
    @FXML
    private ComboBox<Categoria> categoriaCBox;
    private ObservableList<Categoria> categoriasLista = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Integer> numEpCBox;
    private ObservableList<Integer> numEpLista = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Integer> anoCBox;
    private ObservableList<Integer> anoLista = FXCollections.observableArrayList();
    @FXML
    private ListView<Anime> animeListView;
    private ObservableList<Anime> animeLista = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        animeListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Anime> observable, Anime oldValue, Anime newValue) -> {
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
            display.passaDadosAnime(newValue);
            display.passaUsuario(usuario);
            Parent p = Loader.getRoot();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
            stage.setScene(new Scene(p));
            stage.show();
            stage = (Stage) animeListView.getScene().getWindow();
            stage.close();
        });
    }

    @FXML
    private void voltarButton(ActionEvent event) {
        playSound("pikachu.mp3");
        Stage stage;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("TelaInicial.fxml"));
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            mostraAlert(Alert.AlertType.ERROR,
                    "Erro ao trocar de janela.",
                    "ERRO");
        }
        stage = (Stage) voltarButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addAnimeButton(ActionEvent event) {
        /*playSound("pikachu.mp3");
        Stage stage;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("AdicionarAnime.fxml"));
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
        stage = (Stage) addAnimeButton.getScene().getWindow();
        stage.close();*/
        playSound("pikachu.mp3");
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("AdicionarAnime.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            mostraAlert(Alert.AlertType.ERROR,
                    "Erro ao trocar de janela.",
                    "ERRO");
        }
        AdicionarAnimeController display = Loader.getController();
        display.passaUsuario(usuario);
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(new Scene(p));
        stage.show();
        stage = (Stage) addAnimeButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void gerenciarButton(ActionEvent event) {
        playSound("pikachu.mp3");
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Gerenciamento.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            mostraAlert(Alert.AlertType.ERROR,
                    "Erro ao trocar de janela.",
                    "ERRO");
        }
        GerenciamentoController display = Loader.getController();
        display.passaUsuario(usuario);
        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(new Scene(p));
        stage.show();
        stage = (Stage) addAnimeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clearNumEpButton(ActionEvent event) {
        numEpCBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void clearAnoButton(ActionEvent event) {
        anoCBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void clearCategoriaButton(ActionEvent event) {
        categoriaCBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void clearTextField(MouseEvent event) {
        if (pesquisarAnime.getText().equals("Nome do anime...")) {
            pesquisarAnime.clear();
        }
    }

    @FXML
    private void filtrarButton(ActionEvent event) {
        ObservableList<Anime> temp = FXCollections.observableArrayList(animeLista);
        if (pesquisarAnime.getText().equals("")) {
            pesquisarAnime.setText("Nome do anime...");
        }
        animeLista.stream().map((anime) -> {
            if (pesquisarAnime.getText() != null && !pesquisarAnime.getText().equals("Nome do anime...")) {
                if (!anime.getNome().toLowerCase().contains(pesquisarAnime.getText().toLowerCase())) {
                    temp.remove(anime);
                }
            }
            return anime;
        }).map((anime) -> {
            if (!numEpCBox.getSelectionModel().isEmpty()) {
                if (anime.getNumEp() != numEpCBox.getSelectionModel().getSelectedItem()) {
                    temp.remove(anime);
                }
            }
            return anime;
        }).map((anime) -> {
            if (!anoCBox.getSelectionModel().isEmpty()) {
                if (anime.getAno() != anoCBox.getSelectionModel().getSelectedItem()) {
                    temp.remove(anime);
                }
            }
            return anime;
        }).filter((anime) -> (!categoriaCBox.getSelectionModel().isEmpty())).forEachOrdered((anime) -> {
            ObservableList<Categoria> all = daoCategoria.pesquisaTodos();
            ObservableList<AnimeTemCategoria> relacionamentos = daoAnimeTemCategoria.pesquisaRelacionamentoIdAnime(anime.getId());
            ObservableList<Categoria> selected = FXCollections.observableArrayList();
            all.forEach((categoria) -> {
                relacionamentos.stream().filter((relacionamento) -> (relacionamento.getIdCategoria() == categoria.getId())).forEachOrdered((_item) -> {
                    selected.add(categoria);
                });
            });
            if (!selected.contains(categoriaCBox.getSelectionModel().getSelectedItem())) {
                temp.remove(anime);
            }
        });
        ObservableList<String> animeNames = FXCollections.observableArrayList();
        ObservableList<Anime> animeSortedLista = FXCollections.observableArrayList();
        temp.forEach((anime) -> {
            animeNames.add(anime.getNome());
        });
        animeNames.sort(Comparator.naturalOrder());
        animeNames.forEach((nome) -> {
            temp.stream().filter((anime) -> (anime.getNome().equals(nome))).forEachOrdered((anime) -> {
                animeSortedLista.add(anime);
            });
        });
        animeListView.setItems(animeSortedLista);
        animeListView.setCellFactory((list) -> {
            return new ListCell<Anime>() {
                @Override
                protected void updateItem(Anime item, boolean empty) {
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

    private void loadData() {
        try {
            numEpLista = daoAnime.buscaNumEp(usuario);
            anoLista = daoAnime.buscaAnos(usuario);
            categoriasLista = daoCategoria.pesquisaTodos();
            animeLista = daoAnime.pesquisaTodos();
            for (Iterator<Anime> iter = animeLista.listIterator(); iter.hasNext();) {
                Anime a = iter.next();
                if (a.getUserID() != usuario.getId()) {
                    iter.remove();
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("erro");
        }
    }

    private void allSetItems() {
        categoriasLista = ordenaCategorias();
        categoriasLista.add(new Categoria(14, "Qualquer uma"));
        categoriaCBox.setItems(categoriasLista);
        numEpLista.sort(Comparator.naturalOrder());
        numEpCBox.setItems(numEpLista);
        anoLista.sort(Comparator.naturalOrder());
        anoCBox.setItems(anoLista);
        animeListView.setItems(ordenaAnimes());
    }

    private ObservableList<Categoria> ordenaCategorias() {
        ObservableList<String> categoriaNames = FXCollections.observableArrayList();
        ObservableList<Categoria> categoriaSortedLista = FXCollections.observableArrayList();
        categoriasLista.forEach((categoria) -> {
            categoriaNames.add(categoria.getNome());
        });
        categoriaNames.sort(Comparator.naturalOrder());
        categoriaNames.forEach((nome) -> {
            categoriasLista.stream().filter((categoria) -> (categoria.getNome().equals(nome))).forEachOrdered((categoria) -> {
                categoriaSortedLista.add(categoria);
            });
        });
        return categoriaSortedLista;
    }

    private ObservableList<Anime> ordenaAnimes() {
        ObservableList<String> animeNames = FXCollections.observableArrayList();
        ObservableList<Anime> animeSortedLista = FXCollections.observableArrayList();
        animeLista.forEach((anime) -> {
            animeNames.add(anime.getNome());
        });
        animeNames.sort(Comparator.naturalOrder());
        animeNames.forEach((nome) -> {
            animeLista.stream().filter((anime) -> (anime.getNome().equals(nome))).forEachOrdered((anime) -> {
                animeSortedLista.add(anime);

            });
        });
        return animeSortedLista;
    }

    private void allCellFactories() {
        categoriaCBox.setCellFactory((list) -> {
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
        animeListView.setCellFactory((list) -> {
            return new ListCell<Anime>() {
                @Override
                protected void updateItem(Anime item, boolean empty) {
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

    public void passaUsuario(Usuario usuario) {
        this.usuario = usuario;
        loadData();
        ordenaAnimes();
        allSetItems();
        allCellFactories();
    }
}
