

package Projeto.dao.impl;

import Projeto.dao.PedidoDAO;
import Projeto.factory.ConnectionFactory;
import Projeto.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PedidoDAOImpl() {
    }

    public int criarPedido(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (usuario_id, data_pedido, status, valor_total) VALUES (?, ?, ?, ?)";

        byte var15;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, 1);
        ) {
            pstmt.setInt(1, pedido.getUsuarioId());
            pstmt.setString(2, pedido.getDataPedido().format(formatter));
            pstmt.setString(3, pedido.getStatus());
            pstmt.setDouble(4, pedido.getValorTotal());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int var7 = generatedKeys.getInt(1);
                        return var7;
                    }
                }
            }

            var15 = -1;
        }

        return var15;
    }

    public Pedido buscarPedidoPorId(int id) throws SQLException {
        String sql = "SELECT usuario_id, data_pedido, status, valor_total FROM pedidos WHERE id = ?";

        Object var14;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(id);
                    pedido.setUsuarioId(rs.getInt("usuario_id"));
                    pedido.setDataPedido(LocalDateTime.parse(rs.getString("data_pedido"), formatter));
                    pedido.setStatus(rs.getString("status"));
                    pedido.setValorTotal(rs.getDouble("valor_total"));
                    Pedido var7 = pedido;
                    return var7;
                }
            }

            var14 = null;
        }

        return (Pedido)var14;
    }

    public List<Pedido> buscarPedidosPorUsuario(int usuarioId) throws SQLException {
        List<Pedido> pedidos = new ArrayList();
        String sql = "SELECT id, data_pedido, status, valor_total FROM pedidos WHERE usuario_id = ?";

        Object var15;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, usuarioId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getInt("id"));
                    pedido.setUsuarioId(usuarioId);
                    pedido.setDataPedido(LocalDateTime.parse(rs.getString("data_pedido"), formatter));
                    pedido.setStatus(rs.getString("status"));
                    pedido.setValorTotal(rs.getDouble("valor_total"));
                    pedidos.add(pedido);
                }
            }

            var15 = pedidos;
        }

        return (List<Pedido>)var15;
    }

    public boolean atualizarStatusPedido(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedidos SET status = ? WHERE id = ?";

        boolean var6;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, pedido.getStatus());
            pstmt.setInt(2, pedido.getId());
            int affectedRows = pstmt.executeUpdate();
            var6 = affectedRows > 0;
        }

        return var6;
    }

    public boolean atualizarValorTotalPedido(int pedidoId, double valorTotal) throws SQLException {
        String sql = "UPDATE pedidos SET valor_total = ? WHERE id = ?";

        boolean var8;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setDouble(1, valorTotal);
            pstmt.setInt(2, pedidoId);
            int affectedRows = pstmt.executeUpdate();
            var8 = affectedRows > 0;
        }

        return var8;
    }
}
