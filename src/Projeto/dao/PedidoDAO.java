package Projeto.dao;

import Projeto.model.Pedido;
import java.sql.SQLException;
import java.util.List;

public interface PedidoDAO {
    int criarPedido(Pedido var1) throws SQLException;

    Pedido buscarPedidoPorId(int var1) throws SQLException;

    List<Pedido> buscarPedidosPorUsuario(int var1) throws SQLException;

    boolean atualizarStatusPedido(Pedido var1) throws SQLException;

    boolean atualizarValorTotalPedido(int var1, double var2) throws SQLException;
}