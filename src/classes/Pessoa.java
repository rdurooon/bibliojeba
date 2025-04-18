package classes;

import java.util.Objects;

public class Pessoa {
    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String num_cel;
    
    public Pessoa(String nome, String cpf, String num_cel){
        this.nome = nome;
        this.cpf = cpf;
        this.num_cel = num_cel;
    }

    public Pessoa(String nome, String email, String cpf, String num_cel) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.num_cel = num_cel;
    }

    public Pessoa(int id, String nome, String email, String cpf, String num_cel) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.num_cel = num_cel;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNum_cel() {
        return num_cel;
    }

    public void setNum_cel(String num_cel) {
        this.num_cel = num_cel;
    }

}
