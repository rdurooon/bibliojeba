package dbconnect;

import java.sql.*;

import classes.Usuario;

public class Dao {
    public boolean joinService(String login, String senha){
        String query = "SELECT * FROM usuario WHERE username = ? AND senha = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, login);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public int getUserType(String login){
        String query = "SELECT u.id_tipo_usuario FROM usuario u WHERE u.username = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("id_tipo_usuario");
            } else {
                return 3;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return 1;
        }   
    }

    public boolean existAccount(String email){
        String query = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean createAccount(Usuario usuario){
        String query = "INSERT INTO `usuario` (`username`, `email`, `senha`, `numero_cel`, `endereco`, `bairro`, `cidade`, `estado`, `cep`, `id_tipo_usuario`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getNumeroCel());
            ps.setString(5, usuario.getEndereco());
            ps.setString(6, usuario.getBairro());
            ps.setString(7, usuario.getCidade());
            ps.setString(8, usuario.getEstado());
            ps.setString(9, usuario.getCep());
            ps.setInt(10, usuario.getIdTipoUsuario());

            int sucess = ps.executeUpdate();

            return sucess > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
