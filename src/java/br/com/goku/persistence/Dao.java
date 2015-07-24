/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.goku.persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ronaldo
 * @version 1.1
 * @param <T>
 */
public class Dao<T> implements GenericDAO<T> {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final EntityTransaction tx;
    private final String unitName = "gedPU";

    private final Class<T> entityClass;

    public Dao(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.emf = Persistence.createEntityManagerFactory(unitName);
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @Override
    public void save(T entity) {
        beginTransaction();
        em.persist(entity);
        tx.commit();
    }

    @Override
    public T edit(T entity) {
        beginTransaction();
        T editado = em.merge(entity);
        tx.commit();
        return editado;
    }

    @Override
    public void remove(T entity) {
        beginTransaction();
        em.remove(entity);
        tx.commit();
    }

    @Override
    public List<T> findAll() {
        beginTransaction();
        List<T> lista = em.createNamedQuery(entityClass.getSimpleName() + ".findAll").getResultList();
        tx.commit();
        return lista;
    }

    @Override
    public T findById(Object id) {
        beginTransaction();
        T busca = em.find(entityClass, id);
        tx.commit();
        return busca;
    }

    public void beginTransaction() {
        if (!tx.isActive()) {
            tx.begin();
        }
    }

    public T executeSingleResult(String queryName, QueryParameter... values) {
        Query nq = em.createNamedQuery(queryName);
        for (QueryParameter value : values) {
            nq.setParameter(value.getField(), value.getValue());
        }
        try {
            return (T) nq.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<T> executeResultList(String queryName, QueryParameter... values) {
        Query nq = em.createNamedQuery(queryName);
        for (QueryParameter value : values) {
            if (!value.estaVazio()) {
                nq.setParameter(value.getField(), value.getValue());
            }
        }
        try {
            return nq.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    public void saveCategoria(T entity) {
        beginTransaction();
        T merged = em.merge(entity);
        em.flush();        
        em.clear();
        tx.commit();
    }
    
    public void removeCategoria(T entity, Object id) {
        beginTransaction();
        entity = this.findById(id);        
        em.remove(entity);
        em.flush();
        em.clear();
        tx.commit();
    }
    
}
