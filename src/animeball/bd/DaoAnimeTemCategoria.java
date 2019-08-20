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
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 05200214
 */
public class DaoAnimeTemCategoria implements Dao<AnimeTemCategoria> {

    @Override
    public boolean adiciona(AnimeTemCategoria relacionamento) {
        String sql = SQLConstantesAnimeTemCategoria.INSERT;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, relacionamento.getIdAnime());
                stmt.setInt(2, relacionamento.getIdCategoria());
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir dados na tabela de relacionamento entre Anime e Categoria!");
            return false;
        }
        return true;
    }

    @Override
    public boolean altera(AnimeTemCategoria relacionamento) {
        String sql = SQLConstantesAnime.UPDATE;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, relacionamento.getIdAnime());
                stmt.setInt(2, relacionamento.getIdCategoria());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao alterar dados do anime de id " + relacionamento.getIdAnime());
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(AnimeTemCategoria relacionamento) {
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesAnimeTemCategoria.REMOVE)) {
                stmt.setInt(1, relacionamento.getIdAnime());
                stmt.setInt(2, relacionamento.getIdCategoria());
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao remover relacionamento do anime de id " + relacionamento.getIdAnime());
            return false;
        }
        return true;
    }

    @Override
    public boolean pesquisa(AnimeTemCategoria relacionamento) {
        ObservableList<AnimeTemCategoria> allRelacionamentos = pesquisaTodos();

        for (AnimeTemCategoria r : allRelacionamentos) {
            if (r.equals(relacionamento)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ObservableList<AnimeTemCategoria> pesquisaTodos() {
        ObservableList<AnimeTemCategoria> relacionamentos = FXCollections.observableArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesAnimeTemCategoria.SEARCH);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AnimeTemCategoria relacionamento = new AnimeTemCategoria();
                    relacionamento.setIdAnime(rs.getInt("idAnime"));
                    relacionamento.setIdCategoria(rs.getInt("idCategoria"));
                    relacionamentos.add(relacionamento);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar por contatos no banco de dados!");
        }
        return relacionamentos;
    }

    public ObservableList<AnimeTemCategoria> pesquisaRelacionamentoIdAnime(int id) {
        ObservableList<AnimeTemCategoria> all = new DaoAnimeTemCategoria().pesquisaTodos();
        ObservableList<AnimeTemCategoria> selected = FXCollections.observableArrayList();
        for (AnimeTemCategoria relacionamento : all) {
            if (relacionamento.getIdAnime() == id) {
                selected.add(relacionamento);
            }
        }
        return selected;
    }

}
