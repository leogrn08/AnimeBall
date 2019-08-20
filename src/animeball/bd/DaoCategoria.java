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
public class DaoCategoria implements Dao<Categoria> {

    @Override
    public boolean adiciona(Categoria categoria) {
        String sql = SQLConstantesCategoria.INSERT;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, null);
                stmt.setString(2, categoria.getNome());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados na tabela contato!");
            return false;
        }
        return true;
    }

    @Override
    public boolean altera(Categoria m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Categoria m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean pesquisa(Categoria m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Categoria> pesquisaTodos() {
        ObservableList<Categoria> categorias = FXCollections.observableArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesCategoria.SEARCH);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setNome(rs.getString("nome"));
                    categorias.add(categoria);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar por contatos no banco de dados!");
        }
        return categorias;
    }

    public int buscaId(Categoria categoria) throws SQLException, ClassNotFoundException {
        List<Categoria> categorias = new DaoCategoria().pesquisaTodos();
        for (Categoria mod : categorias) {
            if (categoria.getNome().equals(mod.getNome())) {
                return mod.getId();
            }
        }
        return -1;
    }

    public ObservableList<String> buscaNomes() {
        ObservableList<Categoria> categorias = new DaoCategoria().pesquisaTodos();
        ObservableList<String> nomes = FXCollections.observableArrayList();
        for (Categoria categoria : categorias) {
            nomes.add(categoria.getNome());
        }
        return nomes;
    }

}
