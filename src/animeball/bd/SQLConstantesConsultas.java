/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animeball.bd;

/**
 *
 * @author 05200213
 */
public class SQLConstantesConsultas {

    public static final String ANIMEUSUARIO = "select A.id, A.nome, U.username "
            + "from Anime A, Usuario U "
            + "where A.userID = U.id";

    public static final String USUARIOQUANTIDADEANIME = "select U.username, count(A.userID) "
            + "from Anime A, Usuario U "
            + "where A.userID = U.id "
            + "group by U.id";

    public static final String ANIMEQUANTIDADECATEGORIA = "select A.nome, count(ATC.idAnime) "
            + "from Anime A, AnimeTemCategoria ATC "
            + "where A.id = ATC.idAnime "
            + "group by A.id";
}
