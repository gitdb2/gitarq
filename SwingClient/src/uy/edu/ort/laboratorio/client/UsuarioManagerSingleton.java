/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.client;

import uy.edu.ort.laboratorio.ejb.cripto.DesEncrypter;

/**
 *
 * @author rodrigo
 */
public class UsuarioManagerSingleton {

    private static UsuarioManagerSingleton instance = new UsuarioManagerSingleton();
    private String login;
    private String pass;

    private UsuarioManagerSingleton() {
    }

    public static UsuarioManagerSingleton getInstance() {
        return instance;
    }

    public void login(String user, String pass) {
        this.login = user;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    private String getPass() {
        return pass;
    }

    public String getMD5Key() {
        return DesEncrypter.MD5(getPass());
    }

    public String getPassword() {
        DesEncrypter enc = new DesEncrypter(getMD5Key());
        return enc.encrypt(getMD5Key());
    }
}
