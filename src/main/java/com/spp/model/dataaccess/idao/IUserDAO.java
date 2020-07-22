package com.spp.model.dataaccess.idao;

import java.util.List;

public interface IUserDAO<T> {
    List<T> getAllUsers();
    T getUserByUsername(String studentEnrollment);
    boolean addUser(T user);
    boolean deleteUser(T user);
    boolean existUser(String studentEnrollment);
}
