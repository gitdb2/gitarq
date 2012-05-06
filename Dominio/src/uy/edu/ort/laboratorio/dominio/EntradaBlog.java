/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.dominio;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tanquista
 */
public class EntradaBlog extends Contenido {
    
    private String titulo;
    private String nombreAutor;
    private String texto;
    private List<String> tags = new ArrayList<String>();

    public EntradaBlog() {
    }
    
    public EntradaBlog(Date fechaPublicacion) {
        super(fechaPublicacion);
    }
    
    public EntradaBlog(String titulo, String nombreAutor, String texto, List<String> tags, Date fechaPublicacion) {
        super(fechaPublicacion);
        this.titulo = titulo;
        this.nombreAutor = nombreAutor;
        this.texto = texto;
        this.tags = tags;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    @Override
    protected String obtenerNombreCarpeta() {
        return "entrada_blog";
    }

    @Override
    public String obtenerRutaArchivo() {
        return obtenerNombreCarpeta() + File.separator + 
               nombreAutor + File.separator + oid + 
               "_" + this.getFechaPublicacionFormateada() + "_" + titulo;
    }
    
}
