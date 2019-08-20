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
public class TabelaAnimeUsuario {
    private int id;
    private String anime, usuario;

    public TabelaAnimeUsuario(int id, String anime, String usuario) {
        this.id = id;
        this.anime = anime;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
}
