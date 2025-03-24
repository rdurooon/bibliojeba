package classes;

public class Usuario {
    private int id;
    private String email;
    private String password;
    private String username;
    private TipoUsuario typeUser;

    public Usuario(){}

    public Usuario(int id, String email, String password, String username, TipoUsuario typeUser) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.typeUser = typeUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TipoUsuario getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TipoUsuario typeUser) {
        this.typeUser = typeUser;
    }
}
