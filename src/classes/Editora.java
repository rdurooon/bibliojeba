package classes;

public class Editora {
    private int id;
    private String nome;
    
    public Editora(String nome) {
        this.nome = nome;
    }

    public Editora(int id, String nome) {
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
