package com.jurgen.blog.dao.impl;

import com.jurgen.blog.dao.GenericDao;
import java.io.Serializable;
import java.util.List;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected final Class<T> type;

    private static Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        this.type = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public PK create(T object) {
        PK pk = null;
        try {
            pk = (PK) getCurrentSession().save(object);
        } catch (DataIntegrityViolationException ex) {
            logger.warn("Can't create entity:" + type.getSimpleName() + ". Message:" + ex.getMessage());
        }
        logger.debug("Type of entity: " + type.getSimpleName() + ". Object with primary key: " + pk + " created");
        return pk;
    }

    @Override
    public T get(PK id) {
        T result = (T) getCurrentSession().createCriteria(type).add(Restrictions.idEq(id)).uniqueResult();
        if (result != null) {
            logger.debug(String.format("Type of entity: %s obtained from db. Primary key: %s", type.getSimpleName(), id));
        }
        return result;
    }

    @Override
    public void update(T object) {
        getCurrentSession().update(object);
    }

    @Override
    public void delete(T object) {
        if (object != null) {
            getCurrentSession().delete(object);
            logger.debug(String.format("Type of entity: %s removed from db", type.getSimpleName()));
        }
    }

    @Override
    public List<T> getAll() {
        List<T> results = getCurrentSession().createCriteria(type).list();
        if(results != null) logger.debug(String.format("Type of entity: %s. Obtained all from db. Count: %s", type.getSimpleName(), results.size()));
        else logger.debug(String.format("Type of entity: %s. Obtained null instead of all elements", type.getSimpleName()));
        return results;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
