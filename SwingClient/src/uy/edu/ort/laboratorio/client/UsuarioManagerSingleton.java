/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.client;

import uy.edu.ort.laboratorio.travellers.cripto.DesEncrypter;

/**
 *
 * @author rodrigo
 */
public class UsuarioManagerSingleton {

    private static UsuarioManagerSingleton instance = new UsuarioManagerSingleton();
    private String login;
    private String pass;
    private Long idUser; 

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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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

    @Override
    public String toString() {
        return "UsuarioManagerSingleton{" + "login=" + login + ", pass=" + getPassword() + ", idUser=" + idUser + '}';
    }
    
}
