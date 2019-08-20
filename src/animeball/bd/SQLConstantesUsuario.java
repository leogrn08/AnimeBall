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
public class SQLConstantesUsuario {
    public static final String INSERT = "insert into "
            + "Usuario (id, username, senha, cargo) "
            + "values (?,?,?,?)";

    public static final String UPDATE = "update Usuario set "
            + "username=?, senha=?, cargo=? where id=?";

    public static final String REMOVE = "delete from Usuario where id=?";

    public static final String SEARCH = "select * from Usuario";
}
