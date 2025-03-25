package dbconnect;

import java.sql.*;

import classes.Autor;
import classes.Livro;
import classes.Editora;

public class BookDao {
    public boolean insertBook(Livro livro){
        String query = "INSERT INTO `livro` (`titulo_livro`,`id_autor`,`id_editora`) VALUES (?,?,?)";

        int id_autor = selectAutor(livro.getAutor());
        int id_editora = selectEditora(livro.getEditora());

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, id_autor);
            ps.setInt(3, id_editora);

            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public Livro selectBook(String titulo){
        String query = "SELECT l.id_livro, l.titulo_livro, a.id_autor, a.nome_autor, e.id_editora, e.nome_editora FROM livro l JOIN autor a ON l.id_autor = a.id_autor JOIN editora e ON l.id_editora = e.id_editora WHERE l.titulo_livro = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, titulo);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Autor autor = new Autor(rs.getInt("id_autor"), rs.getString("nome_autor"));
                Editora editora = new Editora(rs.getInt("id_editora"), rs.getString("nome_editora"));

                return new Livro(rs.getString("titulo_livro"),autor,editora);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean existBook(String titulo){
        String query = "SELECT * FROM livro WHERE titulo_livro = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, titulo);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int selectEditora(Editora editora){
        String query = "SELECT id_editora FROM editora WHERE nome_editora = ?";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, editora.getNome());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("id_editora");
            } else {
                return insertEditora(editora);
            }

        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public int insertEditora(Editora editora){
        String query = "INSERT INTO editora (`nome_editora`) VALUES (?)";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, editora.getNome());

            int sucess = ps.executeUpdate();

            if(sucess > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int selectAutor(Autor autor){
        String query = "SELECT id_autor FROM autor WHERE nome_autor = ?";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, autor.getNome());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("id_autor");
            } else {
                return insertAutor(autor);
            }

        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public int insertAutor(Autor autor){
        String query = "INSERT INTO autor (`nome_autor`) VALUES (?)";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, autor.getNome());

            int sucess = ps.executeUpdate();

            if(sucess > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}