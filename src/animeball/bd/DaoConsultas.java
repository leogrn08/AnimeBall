/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.bd;

import animeball.javafx.TabelaAnimeCategoriaQuantidade;
import animeball.javafx.TabelaAnimeUsuario;
import animeball.javafx.TabelaUsuarioAnimeQuantidade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 05200213
 */
public class DaoConsultas {

    public ObservableList<TabelaAnimeUsuario> pesquisaAnimeUsuario() {
        ObservableList<TabelaAnimeUsuario> tabelaAnimeUsuarios = FXCollections.observableArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesConsultas.ANIMEUSUARIO);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    TabelaAnimeUsuario tabelaAnimeUsuario = new TabelaAnimeUsuario(rs.getInt("id"), rs.getString("nome"), rs.getString("username"));
                    tabelaAnimeUsuarios.add(tabelaAnimeUsuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabelaAnimeUsuarios;
    }

    public ObservableList<TabelaUsuarioAnimeQuantidade> pesquisaUsuarioAnimeQuantidade() {
        ObservableList<TabelaUsuarioAnimeQuantidade> tabelaUsuarioAnimeQuantidades = FXCollections.observableArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesConsultas.USUARIOQUANTIDADEANIME);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TabelaUsuarioAnimeQuantidade tabelaUsuarioAnimeQuantidade = new TabelaUsuarioAnimeQuantidade(rs.getString("U.username"), rs.getInt("count(A.userID)"));
                    tabelaUsuarioAnimeQuantidades.add(tabelaUsuarioAnimeQuantidade);
                }
            }
        } catch (SQLException e) {
            System.out.println("b");
        }
        return tabelaUsuarioAnimeQuantidades;
    }

    public ObservableList<TabelaAnimeCategoriaQuantidade> pesquisaAnimeCategoriaQuantidade() {
        ObservableList<TabelaAnimeCategoriaQuantidade> tabelaAnimeCategoriaQuantidades = FXCollections.observableArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(SQLConstantesConsultas.ANIMEQUANTIDADECATEGORIA);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TabelaAnimeCategoriaQuantidade tabelaAnimeCategoriaQuantidade = new TabelaAnimeCategoriaQuantidade(rs.getString("A.nome"), rs.getInt("count(ATC.idAnime)"));
                    tabelaAnimeCategoriaQuantidades.add(tabelaAnimeCategoriaQuantidade);
                }
            }
        } catch (SQLException e) {
            System.out.println("c");
        }
        return tabelaAnimeCategoriaQuantidades;
    }
}
