package com.spp.model.dataaccess.idao;

import com.spp.model.domain.Practitioner;

import java.util.List;

public interface IUserDAO<T> {
    List<T> getAllUsers();
    T getUserByUsername(String studentEnrollment);
    boolean addUser(T user);
    boolean deleteUser(T user);
}
