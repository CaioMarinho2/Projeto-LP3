package projeto.lp3.service; 

import projeto.lp3.dao.ComentarioDAO;
import projeto.lp3.model.Comentario;

import java.util.List;

public class ComentarioService {

    private ComentarioDAO comentarioDAO = new ComentarioDAO();

    public boolean comentar(int usuarioId, int postId, String conteudo) {
        Comentario c = new Comentario();
        c.setPostId(postId);
        c.setUsuarioId(usuarioId);
        c.setConteudo(conteudo);
        return comentarioDAO.salvar(c);
    }

    public List<Comentario> listarPorPost(int postId) {
        return comentarioDAO.listarPorPost(postId);
    }
}