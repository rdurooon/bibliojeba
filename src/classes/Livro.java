package classes;

public class Livro {
    private int id;
    private String titulo;
    private Genero genero;
    private Autor autor;
    private Editora editora;
    private boolean disponibilidade;
    
    public Livro(int id, String titulo){
        this.id = id;
        this.titulo = titulo;
    }
    
    public Livro(String titulo, Genero genero, Autor autor, Editora editora) {
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.editora = editora;
        this.disponibilidade = true;
    }
    
    public Livro(int id, String titulo, Genero genero, Autor autor, Editora editora) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.editora = editora;
        this.disponibilidade = true;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Genero getGenero(){
        return genero;
    }

    public void setGenero(Genero genero){
        this.genero = genero;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}
