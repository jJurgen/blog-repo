package com.jurgen.blog.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
    
    public PK create(T object);

    public T get(PK id);

    public void update(T object);

    public void delete(T object);
    
    public List<T> getAll();

}
