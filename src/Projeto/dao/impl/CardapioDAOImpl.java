

package Projeto.dao.impl;

import Projeto.dao.CardapioDAO;
import Projeto.exception.ProdutoNaoEncontradoException;
import Projeto.factory.ConnectionFactory;
import Projeto.model.Bebida;
import Projeto.model.Lanche;
import Projeto.model.Produto;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CardapioDAOImpl implements CardapioDAO {
    public CardapioDAOImpl() {
    }

    public void adicionar(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (nome, preco, tipo, is_vegano, is_alcoolica) VALUES (?, ?, ?, ?, ?)";

        try {
            try (
                    Connection conn = ConnectionFactory.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql, 1);
            ) {
                pstmt.setString(1, produto.getNome());
                pstmt.setDouble(2, produto.getPreco());
                pstmt.setString(3, produto.getTipo());
                int isVegano = 0;
                int isAlcoolica = 0;
                if (produto instanceof Lanche) {
                    if (((Lanche)produto).isVegano()) {
                        isVegano = 1;
                    }
                } else if (produto instanceof Bebida && ((Bebida)produto).isAlcoolica()) {
                    isAlcoolica = 1;
                }

                pstmt.setInt(4, isVegano);
                pstmt.setInt(5, isAlcoolica);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            produto.setId(generatedKeys.getInt(1));
                        }
                    }

                    PrintStream var10000 = System.out;
                    String var10001 = produto.getNome();
                    var10000.println(var10001 + " adicionado ao cardápio (banco de dados) com ID: " + produto.getId());
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar produto ao banco: " + e.getMessage());
            throw e;
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try {
            try (
                    Connection conn = ConnectionFactory.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql);
            ) {
                pstmt.setInt(1, id);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Produto com ID " + id + " removido do cardápio.");
                } else {
                    System.out.println("Nenhum produto encontrado com ID " + id + " para remover.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao remover produto com ID " + id + " do banco: " + e.getMessage());
            throw e;
        }
    }

    public void atualizar(Produto produtoAtualizado) throws ProdutoNaoEncontradoException, SQLException {
        String sql = "UPDATE produtos SET preco = ?, tipo = ?, is_vegano = ?, is_alcoolica = ? WHERE id = ?";

        try {
            try (
                    Connection conn = ConnectionFactory.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql);
            ) {
                pstmt.setDouble(1, produtoAtualizado.getPreco());
                pstmt.setString(2, produtoAtualizado.getTipo());
                Integer isVegano = null;
                Integer isAlcoolica = null;
                if (produtoAtualizado instanceof Lanche) {
                    isVegano = ((Lanche)produtoAtualizado).isVegano() ? 1 : 0;
                } else if (produtoAtualizado instanceof Bebida) {
                    isAlcoolica = ((Bebida)produtoAtualizado).isAlcoolica() ? 1 : 0;
                }

                pstmt.setObject(3, isVegano);
                pstmt.setObject(4, isAlcoolica);
                pstmt.setInt(5, produtoAtualizado.getId());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows <= 0) {
                    throw new ProdutoNaoEncontradoException("Produto com ID '" + produtoAtualizado.getId() + "' não encontrado no cardápio.");
                }

                PrintStream var10000 = System.out;
                String var10001 = produtoAtualizado.getNome();
                var10000.println(var10001 + " (ID: " + produtoAtualizado.getId() + ") atualizado no cardápio (banco de dados).");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto no banco: " + e.getMessage());
            throw e;
        }
    }

    public Produto buscar(String nome) throws ProdutoNaoEncontradoException, SQLException {
        String sql = "SELECT id, nome, preco, tipo, is_vegano, is_alcoolica FROM produtos WHERE nome = ?";

        try {
            Produto var6;
            try (
                    Connection conn = ConnectionFactory.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql);
            ) {
                pstmt.setString(1, nome);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    throw new ProdutoNaoEncontradoException("Produto '" + nome + "' não encontrado no cardápio.");
                }

                var6 = this.criarProdutoDoResultSet(rs);
            }

            return var6;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto no banco: " + e.getMessage());
            throw e;
        }
    }

    public List<Produto> listar() throws SQLException {
        List<Produto> produtos = new ArrayList();
        String sql = "SELECT id, nome, preco, tipo, is_vegano, is_alcoolica FROM produtos";

        try {
            try (
                    Connection conn = ConnectionFactory.getConnection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
            ) {
                while(rs.next()) {
                    produtos.add(this.criarProdutoDoResultSet(rs));
                }
            }

            return produtos;
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos do banco: " + e.getMessage());
            throw e;
        }
    }

    private Produto criarProdutoDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        double preco = rs.getDouble("preco");
        String tipo = rs.getString("tipo");
        boolean isVegano = rs.getInt("is_vegano") == 1;
        boolean isAlcoolica = rs.getInt("is_alcoolica") == 1;
        if (!tipo.equals("Projeto.model.Lanche") && !tipo.equals("Lanche")) {
            return (Produto)(!tipo.equals("Projeto.model.Bebida") && !tipo.equals("Bebida") ? new Produto(id, nome, preco, tipo) : new Bebida(id, nome, preco, isAlcoolica));
        } else {
            return new Lanche(id, nome, preco, isVegano);
        }
    }
}
