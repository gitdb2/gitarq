/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.dominio;

import java.io.File;
import java.util.Date;

/**
 * Clase que representa las paginas web.
 * Se le agregaron contstructor por defecto, getters y setters para
 * poder persistirla.
 * @author tanquista
 */
public class PaginaWeb extends Contenido {
    
    private String nombre;
    private byte[] html;

    public PaginaWeb() {
    }

    public PaginaWeb(Date fechaPublicacion) {
        super(fechaPublicacion);
    }

    public PaginaWeb(String nombre, byte[] html, Date fechaPublicacion) {
        super(fechaPublicacion);
        this.nombre = nombre;
        this.html = html;
    }
    
    public byte[] getHtml() {
        return html;
    }

    public void setHtml(byte[] html) {
        this.html = html;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    protected String obtenerNombreCarpeta() {
        return "pagina_web";
    }
    
    @Override
    public String obtenerRutaArchivo() {
        return obtenerNombreCarpeta() + 
               File.separator + oid + "_" + 
               this.getFechaPublicacionFormateada() + "_" + nombre;
    }
    
}
