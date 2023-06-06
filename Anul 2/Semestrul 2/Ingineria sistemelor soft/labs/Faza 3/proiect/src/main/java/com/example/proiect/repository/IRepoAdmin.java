package com.example.proiect.repository;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.Admin;

public interface IRepoAdmin extends IRepository<Admin,Integer>{
    Object authenticateAdmin(String username, String password) throws MyException;
}
