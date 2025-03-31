
package Projeto.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ItemPedido {
    private int id;
    private int pedidoId;
    private int produtoId;
    private SimpleIntegerProperty quantidade = new SimpleIntegerProperty(1);
    private SimpleDoubleProperty precoUnitario;
    private Produto produto;

    public ItemPedido() {
        this.precoUnitario = new SimpleDoubleProperty((double)0.0F);
    }

    public ItemPedido(int id, int pedidoId, int produtoId, int quantidade, double precoUnitario) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.quantidade.set(quantidade);
        this.precoUnitario = new SimpleDoubleProperty(precoUnitario);
    }

    public ItemPedido(int produtoId, int quantidade, double precoUnitario) {
        this.produtoId = produtoId;
        this.quantidade.set(quantidade);
        this.precoUnitario = new SimpleDoubleProperty(precoUnitario);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedidoId() {
        return this.pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getProdutoId() {
        return this.produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public SimpleIntegerProperty quantidadeProperty() {
        return this.quantidade;
    }

    public int getQuantidade() {
        return this.quantidade.get();
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }

    public SimpleDoubleProperty precoUnitarioProperty() {
        return this.precoUnitario;
    }

    public double getPrecoUnitario() {
        return this.precoUnitario.get();
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario.set(precoUnitario);
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        if (produto != null) {
            this.produtoId = produto.getId();
            this.precoUnitario.set(produto.getPreco());
        }

    }

    public String toString() {
        int var10000 = this.id;
        return "Projeto.model.ItemPedido{id=" + var10000 + ", pedidoId=" + this.pedidoId + ", produtoId=" + this.produtoId + ", quantidade=" + this.quantidade.get() + ", precoUnitario=" + this.precoUnitario.get() + "}";
    }
}
