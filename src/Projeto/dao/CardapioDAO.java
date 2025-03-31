

package Projeto.dao;

import Projeto.exception.ProdutoNaoEncontradoException;
import Projeto.model.Produto;
import java.sql.SQLException;
import java.util.List;

public interface CardapioDAO {
    void adicionar(Produto var1) throws SQLException;

    void remover(int var1) throws SQLException;

    void atualizar(Produto var1) throws ProdutoNaoEncontradoException, SQLException;

    Produto buscar(String var1) throws ProdutoNaoEncontradoException, SQLException;

    List<Produto> listar() throws SQLException;
}
