package projeto.lp3.service;

import projeto.lp3.dao.PostDAO;
import projeto.lp3.model.Post;

import java.util.List;

public class PostService {

    private PostDAO postDAO = new PostDAO();

    public boolean postar(int usuarioId, String conteudo) {
        Post p = new Post(usuarioId, conteudo);
        return postDAO.salvar(p);
    }

    public List<Post> listarPosts() {
        return postDAO.listarTodos();
    }

    public void alternarCurtida(int usuarioId, int postId) {
        if (postDAO.usuarioCurtiu(usuarioId, postId)) {
            postDAO.removerCurtida(usuarioId, postId);
        } else {
            postDAO.curtir(usuarioId, postId);
        }
    }

    public int contarCurtidas(int postId) {
        return postDAO.contarCurtidas(postId);
    }

    public void compartilhar(int usuarioId, int postId) {
        postDAO.compartilhar(usuarioId, postId);
    }

    public void editarPost(int usuarioId, int postId, String novoConteudo) {
        postDAO.atualizarConteudo(postId, usuarioId, novoConteudo);
    }

    public void excluirPost(int usuarioId, int postId) {
        postDAO.excluirPost(postId, usuarioId);
    }

    public void excluirCompartilhamento(int usuarioId, int postId) {
        postDAO.excluirCompartilhamento(postId, usuarioId);
    }
}