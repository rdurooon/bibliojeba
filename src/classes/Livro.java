package classes;

public class Livro {
    private int id;
    private String titulo;
    private Genero genero;
    private Autor autor;
    private Editora editora;
    
    public Livro(String titulo, Genero genero, Autor autor, Editora editora) {
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.editora = editora;
    }

    public Livro(int id, String titulo, Genero genero, Autor autor, Editora editora) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.editora = editora;
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
}
