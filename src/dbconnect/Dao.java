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

    public String getUserType(String login){
        String query = "SELECT t.nome FROM usuario u JOIN tipo_usuario t ON u.id_tipo_usuario = t.id_tipo_usuario WHERE u.username = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getString("nome");
            } else {
                return "Cliente";
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
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
        String query = "INSERT INTO usuario (email, senha, username, id_tipo_usuario) VALUES (?,?,?,?)";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getUsername());
            ps.setInt(4, 3);

            int sucess = ps.executeUpdate();

            return sucess > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
