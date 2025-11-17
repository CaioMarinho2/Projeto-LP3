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
}
