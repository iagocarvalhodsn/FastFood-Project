
package Projeto.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private int usuarioId;
    private LocalDateTime dataPedido;
    private String status;
    private double valorTotal;
    private List<ItemPedido> itensPedido = new ArrayList();

    public Pedido() {
        this.dataPedido = LocalDateTime.now();
        this.status = "pendente";
        this.valorTotal = (double)0.0F;
    }

    public Pedido(int id, int usuarioId, LocalDateTime dataPedido, String status, double valorTotal) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.dataPedido = dataPedido;
        this.status = status;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getDataPedido() {
        return this.dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ItemPedido> getItensPedido() {
        return this.itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public void adicionarItem(ItemPedido item) {
        this.itensPedido.add(item);
        this.valorTotal += item.getPrecoUnitario() * (double)item.getQuantidade();
    }

    public String toString() {
        int var10000 = this.id;
        return "Projeto.model.Pedido{id=" + var10000 + ", usuarioId=" + this.usuarioId + ", dataPedido=" + String.valueOf(this.dataPedido) + ", status='" + this.status + "', valorTotal=" + this.valorTotal + ", itensPedido=" + String.valueOf(this.itensPedido) + "}";
    }
}
