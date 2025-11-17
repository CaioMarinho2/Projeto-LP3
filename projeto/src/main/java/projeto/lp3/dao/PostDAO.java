package projeto.lp3.dao;

import projeto.lp3.model.Post;
import projeto.lp3.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public boolean salvar(Post p) {
        String sql = "INSERT INTO posts (usuario_id, conteudo) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getUsuarioId());
            stmt.setString(2, p.getConteudo());
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<Post> listarTodos() {
        List<Post> lista = new ArrayList<>();
        String sql = "SELECT p.conteudo, u.usuario, p.data_criacao FROM posts p JOIN usuarios u ON p.usuario_id = u.id ORDER BY p.data_criacao DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post p = new Post();
                p.setConteudo(rs.getString("conteudo"));
                p.setUsuarioNome(rs.getString("usuario"));
                Timestamp ts = rs.getTimestamp("data_criacao");
                if (ts != null)
                    p.setDataCriacao(ts.toLocalDateTime());
                lista.add(p);
            }

        } catch (Exception e) {
        }

        return lista;
    }
}