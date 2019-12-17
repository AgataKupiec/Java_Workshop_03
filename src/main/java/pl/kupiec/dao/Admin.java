package pl.kupiec.dao;

import org.mindrot.jbcrypt.BCrypt;

public class Admin {
    private int id;
    private String login;
    private String password;
    
    public Admin(String login, String password) {
        this.login = login;
        this.hashPassword(password);
    }
    
    public Admin() {
    }
    
    private void hashPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.hashPassword(password);
    }
    
    public void setPasswordAlreadyHashed(String hashedPassword) {
        this.password = hashedPassword;
    }
}
