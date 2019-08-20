/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.javafx;

/**
 *
 * @author WINDOWS10
 */
public class TabelaUsuarioAnimeQuantidade {
    private String usuario;
    private int quantidadeAnime;

    public TabelaUsuarioAnimeQuantidade(String usuario, int quantidadeAnime) {
        this.usuario = usuario;
        this.quantidadeAnime = quantidadeAnime;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getQuantidadeAnime() {
        return quantidadeAnime;
    }

    public void setQuantidadeAnime(int quantidadeAnime) {
        this.quantidadeAnime = quantidadeAnime;
    }
    
    
}
