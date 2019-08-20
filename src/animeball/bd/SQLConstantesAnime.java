/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.bd;

/**
 *
 * @author coelho
 */
public class SQLConstantesAnime {

    public static final String INSERT = "insert into "
            + "Anime (id, nome, numEp, numEpAssistidos, ano, link, imagem, userID) "
            + "values (?,?,?,?,?,?,?,?)";

    public static final String UPDATE = "update Anime set "
            + "nome=?, numEp=?, ano=?, link=?, imagem=? where id=?";
    
    public static final String UPDATE_NUMEP = "update Anime set "
            + "numEpAssistidos=? where id=?";

    public static final String REMOVE = "delete from Anime where id=?";

    public static final String SEARCH = "select * from Anime";
}
