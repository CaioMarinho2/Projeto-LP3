package projeto.lp3.service;

import projeto.lp3.dao.UsuarioDAO;
import projeto.lp3.model.Usuario;

import java.time.LocalDate;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public String validarCadastro(String nome, String usuario, String email, String senha, LocalDate nascimento) {

        if (usuarioDAO.emailExiste(email)) {
            return "Email já está cadastrado.";
        }

        if (usuarioDAO.usuarioExiste(usuario)) {
            return "Nome de usuário já está em uso.";
        }

        return "OK";
    }

    public boolean cadastrarUsuario(String nome, String usuario, String email, String senha, LocalDate nascimento) {
        Usuario u = new Usuario(nome, usuario, email, senha, nascimento);
        return usuarioDAO.salvar(u);
    }

    public Usuario autenticar(String usuario, String senha) {
        Usuario u = usuarioDAO.buscarPorUsuario(usuario);
        if (u != null && u.getSenha().equals(senha)) {
            return u;
        }
        return null;
    }
}