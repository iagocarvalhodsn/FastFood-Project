package Projeto.dao;

import Projeto.model.ItemPedido;
import java.sql.SQLException;
import java.util.List;

public interface ItemPedidoDAO {
    boolean adicionarItemPedido(ItemPedido var1) throws SQLException;

    List<ItemPedido> buscarItensPedidoPorPedido(int var1) throws SQLException;

    boolean removerItemPedido(ItemPedido var1) throws SQLException;
}