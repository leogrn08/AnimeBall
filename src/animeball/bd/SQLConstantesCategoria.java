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
public class SQLConstantesCategoria {

    public static final String INSERT = "insert into "
            + "Categoria (id, nome)"
            + "values (?,?)";
    
    public static final String SEARCH = "select * from Categoria";
}
