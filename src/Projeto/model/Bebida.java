
package Projeto.model;

import javafx.beans.property.SimpleBooleanProperty;

public class Bebida extends Produto {
    private SimpleBooleanProperty alcoolica = new SimpleBooleanProperty(false);

    public Bebida(int id, String nome, double preco, boolean alcoolica) {
        super(id, nome, preco, (String)null);
        this.alcoolica.set(alcoolica);
    }

    public Bebida(String nome, double preco, boolean alcoolica) {
        super(nome, preco);
        this.alcoolica.set(alcoolica);
    }

    public boolean isAlcoolica() {
        return this.alcoolica.get();
    }

    public SimpleBooleanProperty alcoolicaProperty() {
        return this.alcoolica;
    }

    public void setAlcoolica(boolean alcoolica) {
        this.alcoolica.set(alcoolica);
    }

    public void setId(int id) {
        super.setId(id);
    }

    public String toString() {
        String var10000 = super.toString();
        return var10000 + ", Alcoólica: " + (this.isAlcoolica() ? "Sim" : "Não");
    }
}
