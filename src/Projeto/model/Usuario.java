
package Projeto.model;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String dataCadastro;

    public Usuario() {
    }

    public Usuario(int id, String nome, String email, String senha, String dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String toString() {
        return "Projeto.model.Usuario{id=" + this.id + ", nome='" + this.nome + "', email='" + this.email + "', senha='********', dataCadastro='" + this.dataCadastro + "'}";
    }
}
