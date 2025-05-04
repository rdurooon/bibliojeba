package classes;

public class Usuario extends Pessoa{
    private int id;
    private String username;
    private String password;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private int idTipoUsuario;
    private Pessoa pessoa;

    public Usuario(){
        super();
    }
    
    public Usuario(String nome, String cpf, String num_cel, String endereco, String bairro, String cidade, String estado, String cep){
        super(nome, cpf, num_cel);
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
    
    public Usuario(String nome, String email, String cpf, String num_cel, String username, String password, String endereco, String bairro, String cidade, String estado, String cep, int idTipoUsuario) {
        super(nome, email, cpf, num_cel);
        this.username = username;
        this.password = password;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.idTipoUsuario = idTipoUsuario;
    }
    
    public Usuario(int id, String nome, String email, String cpf, String num_cel, int id2, String username, String password, String endereco, String bairro, String cidade, String estado, String cep, int idTipoUsuario) {
        super(id, nome, email, cpf, num_cel);
        this.id = id2;
        this.username = username;
        this.password = password;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.idTipoUsuario = idTipoUsuario;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public void setPessoa(Pessoa pessoa){
        this.pessoa = pessoa;
    }

    public Pessoa getPessoa(){
        return pessoa;
    }
}
