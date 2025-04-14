package dbconnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import classes.Autor;
import classes.Livro;
import classes.Editora;
import classes.Genero;

public class BookDao {
    public boolean insertBook(Livro livro){
        String query = "INSERT INTO `livro` (`titulo_livro`,`id_genero`,`id_autor`,`id_editora`) VALUES (?,?,?,?)";

        int id_autor = selectAutor(livro.getAutor());
        int id_editora = selectEditora(livro.getEditora());
        int id_genero = selectGenero(livro.getGenero());

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, id_genero);
            ps.setInt(3, id_autor);
            ps.setInt(4, id_editora);

            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterBook(Livro livro){
        String query = "UPDATE `livro` SET `titulo_livro` = ?, `id_genero` = ?, `id_autor` = ?, `id_editora` = ? WHERE `id_livro` = ?";

        int id_autor = selectAutor(livro.getAutor());
        int id_editora = selectEditora(livro.getEditora());
        int id_genero = selectGenero(livro.getGenero());

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, id_genero);
            ps.setInt(3, id_autor);
            ps.setInt(4, id_editora);
            ps.setInt(5, livro.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(Livro livro){
        String query = "DELETE FROM livro WHERE `livro`.`id_livro` = ?";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, livro.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Livro selectBook(String titulo){
        String query = "SELECT l.id_livro, l.titulo_livro, g.id_genero, g.nome_genero, a.id_autor, a.nome_autor, e.id_editora, e.nome_editora FROM livro l JOIN autor a ON l.id_autor = a.id_autor JOIN genero g ON l.id_genero = g.id_genero JOIN editora e ON l.id_editora = e.id_editora WHERE l.titulo_livro = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, titulo);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Autor autor = new Autor(rs.getInt("id_autor"), rs.getString("nome_autor"));
                Editora editora = new Editora(rs.getInt("id_editora"), rs.getString("nome_editora"));
                Genero genero = new Genero(rs.getInt("id_genero"), rs.getString("nome_genero"));

                return new Livro(rs.getInt("id_livro"),rs.getString("titulo_livro"),genero,autor,editora);
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

    public List<Livro> selectAllBooks(){
        List<Livro> livros = new ArrayList<>();
        String query = "SELECT l.id_livro, l.titulo_livro, g.id_genero, g.nome_genero, a.id_autor, a.nome_autor, e.id_editora, e.nome_editora FROM livro l JOIN autor a ON l.id_autor = a.id_autor JOIN genero g ON l.id_genero = g.id_genero JOIN editora e ON l.id_editora = e.id_editora";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                int idLivro = rs.getInt("id_livro");
                String titulo = rs.getString("titulo_livro");
                int idGenero = rs.getInt("id_genero");
                String genero = rs.getString("nome_genero");
                int idAutor = rs.getInt("id_autor");
                String autor = rs.getString("nome_autor");
                int idEditora = rs.getInt("id_editora");
                String editora = rs.getString("nome_editora");

                Livro livro = new Livro(idLivro, titulo, new Genero(idGenero, genero), new Autor(idAutor, autor), new Editora(idEditora, editora));
                livros.add(livro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public List<Livro> selectAllAvailableBooks(){
        List<Livro> livros = new ArrayList<>();
        String query = "SELECT l.id_livro, l.titulo_livro, g.id_genero, g.nome_genero, a.id_autor, a.nome_autor, e.id_editora, e.nome_editora FROM livro l JOIN autor a ON l.id_autor = a.id_autor JOIN genero g ON l.id_genero = g.id_genero JOIN editora e ON l.id_editora = e.id_editora WHERE l.disponibilidade = TRUE";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Livro livro = new Livro(rs.getInt("id_livro"), rs.getString("titulo_livro"), new Genero(rs.getInt("id_genero"),rs.getString("nome_genero")),new Autor(rs.getInt("id_autor"),rs.getString("nome_autor")),new Editora(rs.getInt("id_editora"),rs.getString("nome_editora")));

                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
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

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
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
    public int selectGenero(Genero genero){
        String query = "SELECT id_genero FROM genero WHERE nome_genero = ?";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, genero.getNome());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("id_genero");
            } else {
                return insertGenero(genero);
            }

        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public int insertGenero(Genero genero){
        String query = "INSERT INTO genero (`nome_genero`) VALUES (?)";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, genero.getNome());

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