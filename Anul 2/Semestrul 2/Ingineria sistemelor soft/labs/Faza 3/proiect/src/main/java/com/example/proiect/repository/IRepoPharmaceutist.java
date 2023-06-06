package com.example.proiect.repository;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.MedicalPersonnel;
import com.example.proiect.model.Pharmaceutist;

public interface IRepoPharmaceutist extends IRepository<Pharmaceutist,Integer>{
    Object authenticatePharmaceutist(String username, String password) throws MyException;
}
