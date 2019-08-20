/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.bd;

import java.util.Objects;

/**
 *
 * @author 05200214
 */
public class Usuario {
    
    private int id;
    private String username, senha, cargo;

    public Usuario() {
    }

    public Usuario(String username, String senha) {
        this.username = username;
        this.senha = senha;
    }
    
    

    public Usuario(String username, String senha, String cargo) {
        this.username = username;
        this.senha = senha;
        this.cargo = cargo;
    }

    public Usuario(int id, String username, String senha, String cargo) {
        this.id = id;
        this.username = username;
        this.senha = senha;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", username=" + username + ", senha=" + senha + ", cargo=" + cargo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.senha);
        hash = 97 * hash + Objects.hashCode(this.cargo);
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
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.senha, other.senha)) {
            return false;
        }
        if (!Objects.equals(this.cargo, other.cargo)) {
            return false;
        }
        return true;
    }
    
    
    
}
