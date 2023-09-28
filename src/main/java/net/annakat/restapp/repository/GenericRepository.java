package net.annakat.restapp.repository;

import java.util.List;

public interface GenericRepository <T, I>{
    T create(T object);
    T get(I id);
    T update(T object);
    void delete(I id);
    List<T> getAll();
}
