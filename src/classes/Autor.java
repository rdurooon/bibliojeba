package classes;

public class Autor {
    private int id;
    private String nome;
    
    public Autor(String nome) {
        this.nome = nome;
    }

    public Autor(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
