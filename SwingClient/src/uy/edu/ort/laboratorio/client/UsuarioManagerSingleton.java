/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.client;

/**
 *
 * @author rodrigo
 */
public class UsuarioManagerSingleton {
    private static UsuarioManagerSingleton instance = new UsuarioManagerSingleton();
    
    private String login;
    private String pass;
    
    private UsuarioManagerSingleton(){}
            
    public static UsuarioManagerSingleton getInstance(){
        return instance;
    }
    
    public void login(String user, String pass){
        this.login = user;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }
    
    
}
