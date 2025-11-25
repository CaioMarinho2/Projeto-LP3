package projeto.lp3.dao;

import projeto.lp3.model.Post;
import projeto.lp3.util.DatabaseConnection;

import java.sql.*;
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

        String sql =
            "SELECT p.id AS id, p.conteudo, u.usuario, p.data_criacao, " +
            "       p.usuario_id AS owner_id, " +
            "       NULL AS compartilhado_por, 0 AS compartilhado, " +
            "       (SELECT COUNT(*) FROM curtidas c WHERE c.post_id = p.id) AS curtidas " +
            "FROM posts p " +
            "JOIN usuarios u ON p.usuario_id = u.id " +
            "UNION ALL " +
            "SELECT p.id AS id, p.conteudo, u.usuario, c.data_compartilhamento AS data_criacao, " +
            "       c.usuario_id AS owner_id, u2.usuario AS compartilhado_por, 1 AS compartilhado, " +
            "       (SELECT COUNT(*) FROM curtidas c2 WHERE c2.post_id = p.id) AS curtidas " +
            "FROM compartilhamentos c " +
            "JOIN posts p ON c.post_id = p.id " +
            "JOIN usuarios u ON p.usuario_id = u.id " +
            "JOIN usuarios u2 ON c.usuario_id = u2.id " +
            "ORDER BY data_criacao DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setConteudo(rs.getString("conteudo"));
                p.setUsuarioNome(rs.getString("usuario"));
                Timestamp ts = rs.getTimestamp("data_criacao");
                if (ts != null) {
                    p.setDataCriacao(ts.toLocalDateTime());
                }
                p.setUsuarioId(rs.getInt("owner_id"));
                p.setCurtidas(rs.getInt("curtidas"));

                int comp = rs.getInt("compartilhado");
                p.setCompartilhado(comp == 1);
                String compartilhadoPor = rs.getString("compartilhado_por");
                if (compartilhadoPor != null) {
                    p.setCompartilhadoPor(compartilhadoPor);
                }

                lista.add(p);
            }

        } catch (Exception e) {
        }

        return lista;
    }

    public boolean usuarioCurtiu(int usuarioId, int postId) {
        String sql = "SELECT COUNT(*) FROM curtidas WHERE usuario_id = ? AND post_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, postId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getInt(1) > 0;

        } catch (Exception e) {
        }

        return false;
    }

    public void curtir(int usuarioId, int postId) {
        String sql = "INSERT INTO curtidas (usuario_id, post_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, postId);
            stmt.executeUpdate();

        } catch (Exception e) {
        }
    }

    public void removerCurtida(int usuarioId, int postId) {
        String sql = "DELETE FROM curtidas WHERE usuario_id = ? AND post_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, postId);
            stmt.executeUpdate();

        } catch (Exception e) {
        }
    }

    public int contarCurtidas(int postId) {
        String sql = "SELECT COUNT(*) FROM curtidas WHERE post_id = ?";
        int total = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
        }

        return total;
    }

    public void compartilhar(int usuarioId, int postId) {
        String sql = "INSERT INTO compartilhamentos (usuario_id, post_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, postId);
            stmt.executeUpdate();

        } catch (Exception e) {
        }
    }

    public void atualizarConteudo(int postId, int usuarioId, String novoConteudo) {
        String sql = "UPDATE posts SET conteudo = ? WHERE id = ? AND usuario_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoConteudo);
            stmt.setInt(2, postId);
            stmt.setInt(3, usuarioId);
            stmt.executeUpdate();

        } catch (Exception e) {
        }
    }

    public void excluirPost(int postId, int usuarioId) {
        String sql = "DELETE FROM posts WHERE id = ? AND usuario_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postId);
            stmt.setInt(2, usuarioId);
            stmt.executeUpdate();

        } catch (Exception e) {
        }
    }

    public void excluirCompartilhamento(int postId, int usuarioId) {
        String sql = "DELETE FROM compartilhamentos WHERE post_id = ? AND usuario_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postId);
            stmt.setInt(2, usuarioId);
            stmt.executeUpdate();

        } catch (Exception e) {
        }
    }
}