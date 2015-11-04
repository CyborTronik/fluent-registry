package com.github.cybortronik.registry.repository;

/**
 * Created by stanislav on 10/28/15.
 */
public interface Repository<T, ID> {

    T findById(ID id);

    void disable(T entity);

    void disableById(ID id);
    
    void deleteAll();

}
