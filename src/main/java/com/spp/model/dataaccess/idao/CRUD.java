package com.spp.model.dataaccess.idao;

import java.util.List;

public interface CRUD<T> {
    List<T> getListed();
    T getByID(int id);
    boolean addElement(T t);
    boolean deleteElement(int id);
}
