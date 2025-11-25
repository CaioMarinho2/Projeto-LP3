package projeto.lp3.model;

import java.time.LocalDate;

public class Usuario {

    private int id;
    private String nome;
    private String usuario;
    private String email;
    private String senha;
    private LocalDate nascimento;
    private String foto;

    public Usuario() {
    }

    public Usuario(String nome, String usuario, String email, String senha, LocalDate nascimento) {
        this.nome = nome;
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
        this.nascimento = nascimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}