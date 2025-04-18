package dbconnect;

import java.sql.*;

import classes.Usuario;
import gui.Login;

public class UserDao {
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

    public Usuario getUser(String login){
        Usuario usuario = null;
        String query = "SELECT u.id_usuario, p.id_pessoa, p.nome, p.email, p.cpf, p.numero_cel, u.username, u.senha, u.endereco, u.bairro, u.cidade, u.estado, u.cep, u.id_tipo_usuario FROM usuario u JOIN pessoa p ON u.id_pessoa = p.id_pessoa WHERE u.username = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, login);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int idPessoa = rs.getInt("id_pessoa");
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");
                    String cpf = rs.getString("cpf");
                    String numCel = rs.getString("numero_cel");

                    int idUsuario = rs.getInt("id_usuario");
                    String username = rs.getString("username");
                    String senha = rs.getString("senha");
                    String endereco = rs.getString("endereco");
                    String bairro = rs.getString("bairro");
                    String cidade = rs.getString("cidade");
                    String estado = rs.getString("estado");
                    String cep = rs.getString("cep");
                    int idTipoUsuario = rs.getInt("id_tipo_usuario");

                    usuario = new Usuario(idPessoa, nome, email, cpf, numCel, idUsuario, username, senha, endereco, bairro, cidade , estado, cep, idTipoUsuario);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return usuario;
    }

    public int getUserId(String login){
        int userId = -1;
        String query = "SELECT id_usuario FROM usuario WHERE username = ?";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                userId = rs.getInt("id_usuario");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return userId;
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
        String query = "SELECT * FROM pessoa WHERE email = ?";

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
        String queryInsertPessoa = "INSERT INTO `pessoa`(`nome`,`email`,`cpf`,`numero_cel`) VALUES (?,?,?,?)";
        String queryInsertUser = "INSERT INTO `usuario`(`id_pessoa`,`username`,`senha`,`endereco`,`bairro`,`cidade`,`estado`,`cep`,`id_tipo_usuario`) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection conn = dbConnection.getConnection();){
            conn.setAutoCommit(false);

            try(PreparedStatement psPessoa = conn.prepareStatement(queryInsertPessoa, Statement.RETURN_GENERATED_KEYS)){
                psPessoa.setString(1, usuario.getNome());
                psPessoa.setString(2, usuario.getEmail());
                psPessoa.setString(3, usuario.getCpf());
                psPessoa.setString(4, usuario.getNum_cel());

                int pessoaInsertSucess = psPessoa.executeUpdate();

                if(pessoaInsertSucess == 0){
                    conn.rollback();
                    return false;
                }

                ResultSet rs = psPessoa.getGeneratedKeys();
                if(!rs.next()){
                    conn.rollback();
                    return false;
                }
                int idPessoa = rs.getInt(1);
                try(PreparedStatement psUser = conn.prepareStatement(queryInsertUser)){
                    psUser.setInt(1, idPessoa);
                    psUser.setString(2, usuario.getUsername());
                    psUser.setString(3, usuario.getPassword());
                    psUser.setString(4, usuario.getEndereco());
                    psUser.setString(5, usuario.getBairro());
                    psUser.setString(6, usuario.getCidade());
                    psUser.setString(7, usuario.getEstado());
                    psUser.setString(8, usuario.getCep());
                    psUser.setInt(9, usuario.getIdTipoUsuario());

                    int userInsertSucess = psUser.executeUpdate();

                    if(userInsertSucess > 0){
                        conn.commit();
                        return true;
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            } catch (SQLException e){
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(int idPessoa){
        String query = "DELETE FROM pessoa WHERE id_pessoa = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, idPessoa);

            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePassword(String username, String password, String newPassword){
        String query = "UPDATE usuario SET senha = ? WHERE username = ? AND senha = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.setString(3, password);

            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeData(Usuario usuario){
        int idUsuario = getUserId(Login.userName);
        String queryPessoa = "UPDATE pessoa SET nome = ?, cpf = ?, numero_cel = ? WHERE id_pessoa = (SELECT id_pessoa FROM usuario WHERE username = ?)";
        String queryUser = "UPDATE usuario SET endereco = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE username = ?";

        try(Connection conn = dbConnection.getConnection();){
            conn.setAutoCommit(false);

            try(PreparedStatement psPessoa = conn.prepareStatement(queryPessoa); PreparedStatement psUser = conn.prepareStatement(queryUser)){
                psPessoa.setString(1, usuario.getNome());
                psPessoa.setString(2, usuario.getCpf());
                psPessoa.setString(3, usuario.getNum_cel());
                psPessoa.setString(4, Login.userName);
                psPessoa.executeUpdate();
                
                psUser.setString(1, usuario.getEndereco());
                psUser.setString(2, usuario.getBairro());
                psUser.setString(3, usuario.getCidade());
                psUser.setString(4, usuario.getEstado());
                psUser.setString(5, usuario.getCep());
                psUser.setString(6, Login.userName);
                psUser.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e){
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
