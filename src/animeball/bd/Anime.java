/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.bd;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author 05200214
 */
public class Anime implements Serializable {

    private String nome, link, imagem;
    private int id, numEp, numEpAssistidos, ano, userID;

    public Anime() {;
    }

    public Anime(int id, String nome, int numEp, int ano, String link, String imagem) {
        this.id = id;
        this.nome = nome;
        this.numEp = numEp;
        this.numEpAssistidos = 0;
        this.ano = ano;
        this.link = link;
        this.imagem = imagem;
    }
    
    public Anime(int id, String nome, int numEp, int ano, String link, String imagem, int userID) {
        this.id = id;
        this.nome = nome;
        this.numEp = numEp;
        this.numEpAssistidos = 0;
        this.ano = ano;
        this.link = link;
        this.imagem = imagem;
        this.userID = userID;
    }

    public Anime(int id, String nome, int numEp, int numEpAssistidos, int ano, String link, String imagem) {
        this.nome = nome;
        this.link = link;
        this.imagem = imagem;
        this.id = id;
        this.numEp = numEp;
        this.numEpAssistidos = numEpAssistidos;
        this.ano = ano;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getNumEp() {
        return numEp;
    }

    public void setNumEp(int numEp) {
        this.numEp = numEp;
    }

    public int getNumEpAssistidos() {
        return numEpAssistidos;
    }

    public void setNumEpAssistidos(int numEpAssistidos) {
        this.numEpAssistidos = numEpAssistidos;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Anime{" + "nome=" + nome + ", link=" + link + ", imagem=" + imagem + ", id=" + id + ", numEp=" + numEp + ", numEpAssistidos=" + numEpAssistidos + ", ano=" + ano + ", userID=" + userID + '}';
    }

    

}
