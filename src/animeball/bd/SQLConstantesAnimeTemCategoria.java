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
public class SQLConstantesAnimeTemCategoria {

    public static final String INSERT = "insert into "
            + "AnimeTemCategoria (idAnime, idCategoria) "
            + "values (?,?)";

    public static final String REMOVE = "delete from AnimeTemCategoria where idAnime=? and idCategoria=?";

    public static final String UPDATE = "update AnimeTemCategoria set "
            + "idAnime=?, idCategoria=? where idAnime=?";

    public static final String SEARCH_CATEGORIA = "select idCategoria from AnimeTemCategoria where idAnime=?";

    public static final String SEARCH = "select * from AnimeTemCategoria";
}
