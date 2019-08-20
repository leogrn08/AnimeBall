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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 05200214
 */
public class DaoUsuario implements Dao<Usuario> {

    @Override
    public boolean adiciona(Usuario usuario) {
        String sql = SQLConstantesUsuario.INSERT;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, null);
                stmt.setString(2, usuario.getUsername());
                stmt.setString(3, usuario.getSenha());
                stmt.setString(4, usuario.getCargo());
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir dados na tabela Usuario!");
            return false;
        }
        return true;
    }

    @Override
    public boolean altera(Usuario usuario) {
        String sql = SQLConstantesUsuario.UPDATE;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, usuario.getUsername());
                stmt.setString(2, usuario.getSenha());
                stmt.setString(3, usuario.getCargo());
                stmt.setInt(4, usuario.getId());
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao alterar dados do usuário " + usuario.getUsername());
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(Usuario usuario) {
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesUsuario.REMOVE)) {
                stmt.setInt(1, usuario.getId());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover usuário " + usuario.getUsername());
            return false;
        }
        return true;
    }

    @Override
    public boolean pesquisa(Usuario usuario) {
        ObservableList<Usuario> allUsers = pesquisaTodos();

        for (Usuario u : allUsers) {
            if (u.equals(usuario)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ObservableList<Usuario> pesquisaTodos() {
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesUsuario.SEARCH);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("username"), rs.getString("senha"), rs.getString("cargo"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar por usuarios no banco de dados!");
        }
        return usuarios;
    }

    public Usuario pesquisaUsuario(int id) {
        ObservableList<Usuario> usuarios = new DaoUsuario().pesquisaTodos();
        Usuario selectedUsuario = new Usuario();
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                selectedUsuario = usuario;
            }
        }
        return selectedUsuario;
    }

    public int buscaId(Usuario usuario) throws SQLException, ClassNotFoundException {
        ObservableList<Usuario> usuarios = new DaoUsuario().pesquisaTodos();
        for (Usuario mod : usuarios) {
            if (usuario.getUsername().equals(mod.getUsername())) {
                return mod.getId();
            }
        }
        return -1;
    }

    public boolean usuarioExiste(Usuario usuario) {
        ObservableList<Usuario> usuarios = new DaoUsuario().pesquisaTodos();
        for (Usuario u : usuarios) {
            if(u.getUsername().equals(usuario.getUsername()))
                return true;
        }
        return false;
    }
    
    public boolean entrar(Usuario usuario) {
        ObservableList<Usuario> usuarios = new DaoUsuario().pesquisaTodos();
        for (Usuario u : usuarios) {
            if(u.getUsername().equals(usuario.getUsername()) && u.getSenha().equals(usuario.getSenha()))
                return true;
        }
        return false;
    }
    
    public Usuario getUsuario(String username) {
        ObservableList<Usuario> usuarios = new DaoUsuario().pesquisaTodos();
        Usuario usuario = new Usuario();
        for (Usuario u : usuarios) {
            if(u.getUsername().equals(username))
                usuario = u;
        }
        return usuario;
    }
}
