
package Projeto.model;

import javafx.beans.property.SimpleBooleanProperty;

public class Lanche extends Produto {
    private SimpleBooleanProperty vegano = new SimpleBooleanProperty(false);

    public Lanche(int id, String nome, double preco, boolean vegano) {
        super(id, nome, preco, (String)null);
        this.vegano.set(vegano);
    }

    public Lanche(String nome, double preco, boolean vegano) {
        super(nome, preco);
        this.vegano.set(vegano);
    }

    public boolean isVegano() {
        return this.vegano.get();
    }

    public SimpleBooleanProperty veganoProperty() {
        return this.vegano;
    }

    public void setVegano(boolean vegano) {
        this.vegano.set(vegano);
    }

    public void setId(int id) {
        super.setId(id);
    }

    public String toString() {
        String var10000 = super.toString();
        return var10000 + ", Vegano: " + (this.isVegano() ? "Sim" : "Não");
    }
}
