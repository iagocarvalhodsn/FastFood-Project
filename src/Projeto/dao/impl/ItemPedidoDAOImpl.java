

package Projeto.dao.impl;

import Projeto.dao.ItemPedidoDAO;
import Projeto.factory.ConnectionFactory;
import Projeto.model.ItemPedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAOImpl implements ItemPedidoDAO {
    public ItemPedidoDAOImpl() {
    }

    public boolean adicionarItemPedido(ItemPedido itemPedido) throws SQLException {
        String sql = "INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";

        boolean var6;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, itemPedido.getPedidoId());
            pstmt.setInt(2, itemPedido.getProdutoId());
            pstmt.setInt(3, itemPedido.getQuantidade());
            pstmt.setDouble(4, itemPedido.getPrecoUnitario());
            int affectedRows = pstmt.executeUpdate();
            var6 = affectedRows > 0;
        }

        return var6;
    }

    public List<ItemPedido> buscarItensPedidoPorPedido(int pedidoId) throws SQLException {
        List<ItemPedido> itens = new ArrayList();
        String sql = "SELECT id, produto_id, quantidade, preco_unitario FROM itens_pedido WHERE pedido_id = ?";

        Object var15;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, pedidoId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    ItemPedido item = new ItemPedido();
                    item.setId(rs.getInt("id"));
                    item.setPedidoId(pedidoId);
                    item.setProdutoId(rs.getInt("produto_id"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setPrecoUnitario(rs.getDouble("preco_unitario"));
                    itens.add(item);
                }
            }

            var15 = itens;
        }

        return (List<ItemPedido>)var15;
    }

    public boolean removerItemPedido(ItemPedido itemPedido) throws SQLException {
        String sql = "DELETE FROM itens_pedido WHERE pedido_id = ? AND produto_id = ?";

        boolean var6;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, itemPedido.getPedidoId());
            pstmt.setInt(2, itemPedido.getProdutoId());
            int affectedRows = pstmt.executeUpdate();
            var6 = affectedRows > 0;
        }

        return var6;
    }
}
