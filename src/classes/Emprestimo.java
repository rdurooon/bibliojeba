package classes;

public class Emprestimo {
    private int id;
    private Usuario usuario;
    private Livro livro;
    private int notificacao;
    
    public Emprestimo(int id, Usuario usuario, Livro livro, int notificacao) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
        this.notificacao = notificacao;
    }

    public int getId() {
        return id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getNotificacao(){
        return notificacao;
    }

    public void setNotificacao(int notificacao){
        this.notificacao = notificacao;
    }
}
