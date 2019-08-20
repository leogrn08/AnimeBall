/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.bd;

/**
 *
 * @author 05200214
 */
public class AnimeTemCategoria {
    private int idAnime;
    private int idCategoria;

    public AnimeTemCategoria(){
        
    }
    
    public AnimeTemCategoria(int idAnime, int idCategoria) {
        this.idAnime = idAnime;
        this.idCategoria = idCategoria;
    }

    public int getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(int idAnime) {
        this.idAnime = idAnime;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "AnimeTemCategoria{" + "idAnime=" + idAnime + ", idCategoria=" + idCategoria + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.idAnime;
        hash = 79 * hash + this.idCategoria;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimeTemCategoria other = (AnimeTemCategoria) obj;
        if (this.idAnime != other.idAnime) {
            return false;
        }
        if (this.idCategoria != other.idCategoria) {
            return false;
        }
        return true;
    }
    
}
