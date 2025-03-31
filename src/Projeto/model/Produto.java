
package Projeto.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Produto {
    private IntegerProperty id;
    private StringProperty nome;
    private DoubleProperty preco;
    private String tipo;

    public Produto(int id, String nome, double preco, String tipo) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.preco = new SimpleDoubleProperty(preco);
        this.tipo = tipo;
    }

    public Produto(String nome, double preco) {
        this.id = new SimpleIntegerProperty(0);
        this.nome = new SimpleStringProperty(nome);
        this.preco = new SimpleDoubleProperty(preco);
        this.tipo = "Outro";
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public StringProperty nomeProperty() {
        return this.nome;
    }

    public DoubleProperty precoProperty() {
        return this.preco;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return this.id.get();
    }

    public String getNome() {
        return (String)this.nome.get();
    }

    public double getPreco() {
        return this.preco.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public void setPreco(double preco) {
        this.preco.set(preco);
    }

    public String toString() {

        // "ID: " + this.getId() +
        return " Nome: " + this.getNome() + ", Preço: " + this.getPreco() + ", Tipo: " + this.tipo;
    }
}
