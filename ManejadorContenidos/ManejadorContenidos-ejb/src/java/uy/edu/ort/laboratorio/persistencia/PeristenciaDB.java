/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.persistencia;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.configuracion.LectorDeConfiguracion;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;

/**
 *
 * @author rodrigo
 */
@Stateless
public class PeristenciaDB implements PeristenciaLocal {

    @PersistenceContext
    private EntityManager manejadorPersistenciaDB;

    @Override
    public void persist(Object entity) {
        manejadorPersistenciaDB.persist(entity);
    }

    @Override
    public <T> T merge(T entity) {
        return manejadorPersistenciaDB.merge(entity);
    }

    @Override
    public void remove(Object entity) {
        manejadorPersistenciaDB.remove(entity);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return manejadorPersistenciaDB.find(entityClass, primaryKey);
    }

    @Override
    public List<PaginaWeb> findAllPaginasWb() {
        return manejadorPersistenciaDB.createNamedQuery("PaginaWeb.findAll", PaginaWeb.class).getResultList();
    }

    @Override
    public List<EntradaBlog> findAllBlogs() {
        return manejadorPersistenciaDB.createNamedQuery("EntradaBlog.findAll", EntradaBlog.class).getResultList();
    }

    @Override
    public List<PaginaWeb> listarPaginasWebFiltrando(String nombre, Date fechaPublicacion) throws ArquitecturaException {
        CriteriaQuery query = obtenerCriteriaQueryPaginaWeb(nombre, fechaPublicacion);
        return manejadorPersistenciaDB.createQuery(query).getResultList();
    }

    @Override
    public List<EntradaBlog> listarEntradaBlogFiltrando(String titulo, Date fechaPublicacion, String contenido, String autor, String tag) throws ArquitecturaException {
        CriteriaQuery query = obtenerCriteriaQueryEntradaBlog(titulo, fechaPublicacion, contenido, autor, tag);
        return manejadorPersistenciaDB.createQuery(query).getResultList();
    }

    private boolean stringEsVacio(String parametro) {
        return parametro == null || parametro.trim().isEmpty();
    }

    private CriteriaQuery obtenerCriteriaQueryPaginaWeb(String nombre, Date fechaPublicacion) throws ArquitecturaException {
        if (stringEsVacio(nombre) && fechaPublicacion == null) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.obtenerCriteriaQueryPaginaWeb.sinFiltros"));
        } else {
            CriteriaBuilder qb = manejadorPersistenciaDB.getCriteriaBuilder();
            CriteriaQuery<PaginaWeb> query = qb.createQuery(PaginaWeb.class);
            Root<PaginaWeb> paginaWeb = query.from(PaginaWeb.class);

            Predicate filtros = qb.conjunction();

            if (!stringEsVacio(nombre)) {
                filtros = qb.and(filtros, qb.like(paginaWeb.get("nombre").as(String.class), "%" + nombre.replaceAll("%", "") + "%"));
                //qb.equal(paginaWeb.get("nombre"), nombre));
            }

            if (fechaPublicacion != null) {
                filtros = qb.and(filtros, qb.equal(paginaWeb.get("fechaPublicacion"), fechaPublicacion));
            }

            return query.where(filtros);
        }
    }

    private CriteriaQuery obtenerCriteriaQueryEntradaBlog(String titulo, Date fechaPublicacion,
            String contenido, String autor, String tag) throws ArquitecturaException {
        if (stringEsVacio(titulo)
                && fechaPublicacion == null
                && stringEsVacio(contenido)
                && stringEsVacio(autor)
                && stringEsVacio(tag)) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.obtenerCriteriaQueryPaginaWeb.sinFiltros"));
        } else {
            CriteriaBuilder qb = manejadorPersistenciaDB.getCriteriaBuilder();
            CriteriaQuery<EntradaBlog> query = qb.createQuery(EntradaBlog.class);
            Root<EntradaBlog> entradaBlog = query.from(EntradaBlog.class);

            Predicate filtros = qb.conjunction();

            if (!stringEsVacio(titulo)) {
                filtros = qb.and(filtros, qb.like(entradaBlog.get("titulo").as(String.class), "%" + titulo.replaceAll("%", "") + "%"));
                //qb.equal(entradaBlog.get("titulo"), titulo));
            }

            if (fechaPublicacion != null) {
                filtros = qb.and(filtros, qb.equal(entradaBlog.get("fechaPublicacion"), fechaPublicacion));
            }

            if (!stringEsVacio(autor)) {
                filtros = qb.and(filtros, qb.like(entradaBlog.get("nombreAutor").as(String.class), "%" + autor.replaceAll("%", "") + "%"));
//                                          qb.equal(entradaBlog.get("nombreAutor"), autor));
            }

            if (!stringEsVacio(contenido)) {
                filtros = qb.and(filtros, qb.like(entradaBlog.get("texto").as(String.class), "%" + contenido.replaceAll("%", "") + "%"));
            }

            if (!stringEsVacio(tag)) {
                filtros = qb.and(filtros, qb.like(entradaBlog.get("tags").as(String.class), "%" + tag.replaceAll("%", "") + "%"));
                //qb.equal(entradaBlog.get("tags"), tag));
            }

            return query.where(filtros);
        }
    }
}
