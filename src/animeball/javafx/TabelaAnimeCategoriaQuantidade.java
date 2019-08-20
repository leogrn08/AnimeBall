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
public class TabelaAnimeCategoriaQuantidade {
    private String anime;
    private int categoriaQuantidade;

    public TabelaAnimeCategoriaQuantidade(String anime, int categoriaQuantidade) {
        this.anime = anime;
        this.categoriaQuantidade = categoriaQuantidade;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public int getCategoriaQuantidade() {
        return categoriaQuantidade;
    }

    public void setCategoriaQuantidade(int categoriaQuantidade) {
        this.categoriaQuantidade = categoriaQuantidade;
    }
    
    
}
