package dbconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import classes.Livro;

public class EmprestimoDao {
    public boolean fazerEmprestimo(List<Livro> livros, int idUsuario){
        String queryEmprestimo = "INSERT INTO `emprestimo`(`id_usuario`,`id_livro`) VALUES (?,?)";
        String queryAtualizarLivro = "UPDATE livro SET disponibilidade = FALSE where id_livro = ?";

        try(Connection conn = dbConnection.getConnection()){
            conn.setAutoCommit(false);

            try(PreparedStatement psEmprestimo = conn.prepareStatement(queryEmprestimo); PreparedStatement psLivro = conn.prepareStatement(queryAtualizarLivro)){
                for(Livro livro : livros){
                    psEmprestimo.setInt(1, idUsuario);
                    psEmprestimo.setInt(2, livro.getId());
                    psEmprestimo.addBatch();

                    psLivro.setInt(1, livro.getId());
                    psLivro.addBatch();
                }

                psEmprestimo.executeBatch();
                psLivro.executeBatch();

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

    public boolean devolverLivros(List<Livro> livros, int idUsuario){
        String queryEmprestimo = "DELETE FROM emprestimo WHERE id_usuario = ? AND id_livro = ?";
        String queryAtualizarLivro = "UPDATE livro SET disponibilidade = TRUE WHERE id_livro = ?";

        try(Connection conn = dbConnection.getConnection()){
            conn.setAutoCommit(false);

            try(PreparedStatement psEmprestimo = conn.prepareStatement(queryEmprestimo); PreparedStatement psLivro = conn.prepareStatement(queryAtualizarLivro)){
                for(Livro livro : livros){
                    psEmprestimo.setInt(1, idUsuario);
                    psEmprestimo.setInt(2, livro.getId());
                    psEmprestimo.addBatch();

                    psLivro.setInt(1, livro.getId());
                    psLivro.addBatch();
                }

                psEmprestimo.executeBatch();
                psLivro.executeBatch();

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

    public int emprestimosUserCount(int idUsuario){
        String query = "SELECT COUNT(*) FROM emprestimo WHERE id_usuario = ?";
        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public List<Livro> buscarLivrosEmprestados(int idUsuario){
        List<Livro> livros = new ArrayList<>();
        String query = "SELECT l.* FROM emprestimo e INNER JOIN livro l ON e.id_livro = l.id_livro WHERE e.id_usuario = ?";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Livro livro = new Livro(rs.getInt("id_livro"),rs.getString("titulo_livro"));
                livros.add(livro);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return livros;
    }
} 