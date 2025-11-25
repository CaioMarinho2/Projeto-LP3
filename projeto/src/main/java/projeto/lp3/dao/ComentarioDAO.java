package projeto.lp3.dao;

import projeto.lp3.model.Comentario;
import projeto.lp3.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    public boolean salvar(Comentario c) {
        String sql = "INSERT INTO comentarios (post_id, usuario_id, conteudo) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getPostId());
            stmt.setInt(2, c.getUsuarioId());
            stmt.setString(3, c.getConteudo());
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<Comentario> listarPorPost(int postId) {
        List<Comentario> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.post_id, c.usuario_id, u.usuario, c.conteudo, c.data_criacao " +
                "FROM comentarios c " +
                "JOIN usuarios u ON c.usuario_id = u.id " +
                "WHERE c.post_id = ? " +
                "ORDER BY c.data_criacao ASC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Comentario c = new Comentario();
                c.setId(rs.getInt("id"));
                c.setPostId(rs.getInt("post_id"));
                c.setUsuarioId(rs.getInt("usuario_id"));
                c.setUsuarioNome(rs.getString("usuario"));
                c.setConteudo(rs.getString("conteudo"));
                Timestamp ts = rs.getTimestamp("data_criacao");
                if (ts != null) {
                    c.setDataCriacao(ts.toLocalDateTime());
                }
                lista.add(c);
            }

        } catch (Exception e) {
        }

        return lista;
    }
}