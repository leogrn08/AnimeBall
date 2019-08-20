/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 05200214
 */
public class DaoAnime implements Dao<Anime> {

    @Override
    public boolean adiciona(Anime anime) {
        String sql = SQLConstantesAnime.INSERT;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, null);
                stmt.setString(2, anime.getNome());
                stmt.setInt(3, anime.getNumEp());
                stmt.setInt(4, anime.getNumEpAssistidos());
                stmt.setInt(5, anime.getAno());
                stmt.setString(6, anime.getLink());
                stmt.setString(7, anime.getImagem());
                stmt.setInt(8, anime.getUserID());
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir dados na tabela Anime!");
            return false;
        }
        return true;
    }

    @Override
    public boolean altera(Anime anime) {
        String sql = SQLConstantesAnime.UPDATE;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, anime.getNome());
                stmt.setInt(2, anime.getNumEp());
                stmt.setInt(3, anime.getAno());
                stmt.setString(4, anime.getLink());
                stmt.setString(5, anime.getImagem());
                stmt.setInt(6, anime.getId());
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao alterar dados do anime " + anime.getNome());
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(Anime anime) {
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesAnime.REMOVE)) {
                stmt.setInt(1, anime.getId());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover contato " + anime.getNome());
            return false;
        }
        return true;
    }

    @Override
    public boolean pesquisa(Anime anime) {
        ObservableList<Anime> allAnime = pesquisaTodos();

        for (Anime a : allAnime) {
            if (a.equals(anime)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ObservableList<Anime> pesquisaTodos() {
        ObservableList<Anime> animes = FXCollections.observableArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesAnime.SEARCH);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Anime anime = new Anime();
                    anime.setId(rs.getInt("id"));
                    anime.setNome(rs.getString("nome"));
                    anime.setNumEp(rs.getInt("numEp"));
                    anime.setNumEpAssistidos(rs.getInt("numEpAssistidos"));
                    anime.setAno(rs.getInt("ano"));
                    anime.setLink(rs.getString("link"));
                    anime.setImagem(rs.getString("imagem"));
                    anime.setUserID(rs.getInt("userID"));
                    animes.add(anime);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar por contatos no banco de dados!");
        }
        return animes;
    }

    public ObservableList<Anime> pesquisaUser(int idUser) {
        ObservableList<Anime> animes = FXCollections.observableArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesAnime.SEARCH);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Anime anime = new Anime();
                    anime.setId(rs.getInt("id"));
                    anime.setNome(rs.getString("nome"));
                    anime.setNumEp(rs.getInt("numEp"));
                    anime.setNumEpAssistidos(rs.getInt("numEpAssistidos"));
                    anime.setAno(rs.getInt("ano"));
                    anime.setLink(rs.getString("link"));
                    anime.setImagem(rs.getString("imagem"));
                    anime.setUserID(rs.getInt("userID"));
                    if (anime.getUserID() == idUser) {
                        animes.add(anime);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar por contatos no banco de dados!");
        }
        return animes;
    }

    public Anime pesquisaAnime(int id) {
        ObservableList<Anime> animes = new DaoAnime().pesquisaTodos();
        Anime selectedAnime = new Anime();
        for (Anime anime : animes) {
            if (anime.getId() == id) {
                selectedAnime = anime;
            }
        }
        return selectedAnime;
    }

    public int buscaId(Anime anime) throws SQLException, ClassNotFoundException {
        ObservableList<Anime> animes = new DaoAnime().pesquisaTodos();
        for (Anime mod : animes) {
            if (anime.getNome().equals(mod.getNome())) {
                return mod.getId();
            }
        }
        return -1;
    }

    public ObservableList<Integer> buscaAnos(Usuario usuario) {
        ObservableList<Anime> animes = new DaoAnime().pesquisaTodos();
        ObservableList<Integer> anos = FXCollections.observableArrayList();
        for (Anime anime : animes) {
            anos.add(anime.getAno());
        }
        return anos;
    }

    public ObservableList<Integer> buscaNumEp(Usuario usuario) {
        ObservableList<Anime> animes = new DaoAnime().pesquisaTodos();
        ObservableList<Integer> numEps = FXCollections.observableArrayList();
        for (Anime anime : animes) {
            numEps.add(anime.getNumEp());
        }
        return numEps;
    }

    public boolean updateNumEp(Anime anime) {
        String sql = SQLConstantesAnime.UPDATE_NUMEP;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, anime.getNumEpAssistidos());
                stmt.setInt(2, anime.getId());
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao alterar dados do anime " + anime.getNome());
            return false;
        }
        return true;
    }
}
