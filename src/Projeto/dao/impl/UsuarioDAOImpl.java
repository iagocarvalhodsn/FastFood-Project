

package Projeto.dao.impl;

import Projeto.dao.UsuarioDAO;
import Projeto.factory.ConnectionFactory;
import Projeto.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAOImpl implements UsuarioDAO {
    public UsuarioDAOImpl() {
    }

    public boolean cadastrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";

        boolean var16;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, 1);
        ) {
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getSenha());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId(generatedKeys.getInt(1));
                    }
                }

                var16 = true;
                return var16;
            }

            var16 = false;
        }

        return var16;
    }

    public Usuario buscarUsuarioPorEmail(String email) throws SQLException {
        String sql = "SELECT id, nome, senha, data_cadastro FROM usuarios WHERE email = ?";

        Usuario usuario;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(email);
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setDataCadastro(rs.getString("data_cadastro"));
                    Usuario var7 = usuario;
                    return var7;
                }

                usuario = null;
            }
        }

        return usuario;
    }

    public boolean atualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, senha = ? WHERE email = ?";

        boolean var6;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, usuario.getEmail());
            int affectedRows = pstmt.executeUpdate();
            var6 = affectedRows > 0;
        }

        return var6;
    }

    public boolean emailJaCadastrado(String email) throws SQLException {
        String sql = "SELECT COUNT(id) FROM usuarios WHERE email = ?";

        boolean var6;
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    var6 = rs.getInt(1) > 0;
                    return var6;
                }

                var6 = false;
            }
        }

        return var6;
    }
}
