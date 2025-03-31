
package Projeto.model;

public class Cardapio {
    private int id;
    private String nome;
    private double preco;
    private String tipo;

    public Cardapio() {
    }

    public Cardapio(int id, String nome, double preco, String tipo) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public double getPreco() {
        return this.preco;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String toString() {
        return "Cardapio{id=" + this.id + ", nome='" + this.nome + "', preco=" + this.preco + ", tipo='" + this.tipo + "'}";
    }
}
