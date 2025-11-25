package projeto.lp3.dao;

import projeto.lp3.model.Usuario;
import projeto.lp3.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;

public class UsuarioDAO {

    public boolean salvar(Usuario u) {
        String sql = "INSERT INTO usuarios (nome, usuario, email, senha, nascimento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getUsuario());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getSenha());
            stmt.setDate(5, Date.valueOf(u.getNascimento()));
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;

        } catch (Exception e) {
        }

        return false;
    }

    public boolean usuarioExiste(String usuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;

        } catch (Exception e) {
        }

        return false;
    }

    public Usuario buscarPorUsuario(String usuario) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setUsuario(rs.getString("usuario"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));

                Date d = rs.getDate("nascimento");
                if (d != null)
                    u.setNascimento(d.toLocalDate());

                u.setFoto(rs.getString("foto_perfil_url"));

                return u;
            }

        } catch (Exception e) {
        }

        return null;
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setUsuario(rs.getString("usuario"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setFoto(rs.getString("foto_perfil_url"));

                Date d = rs.getDate("nascimento");
                if (d != null)
                    u.setNascimento(d.toLocalDate());

                return u;
            }

        } catch (Exception e) {
        }

        return null;
    }

    public int contarSeguidores(int id) {
        String sql = "SELECT COUNT(*) FROM seguidores WHERE segue_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
        }
        return 0;
    }

    public int contarSeguindo(int id) {
        String sql = "SELECT COUNT(*) FROM seguidores WHERE usuario_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
        }
        return 0;
    }

    public void atualizarPerfil(int id, String nome, String usuario, String email, LocalDate nasc, String foto) {
        String sql = "UPDATE usuarios SET nome=?, usuario=?, email=?, nascimento=?, foto_perfil_url=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, usuario);
            stmt.setString(3, email);

            if (nasc != null) {
                stmt.setDate(4, Date.valueOf(nasc));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }

            stmt.setString(5, foto);
            stmt.setInt(6, id);

            stmt.executeUpdate();

        } catch (Exception e) {
        }
    }
}