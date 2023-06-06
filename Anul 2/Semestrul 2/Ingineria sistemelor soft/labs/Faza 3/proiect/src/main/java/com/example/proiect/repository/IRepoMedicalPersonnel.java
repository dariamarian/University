package com.example.proiect.repository;

import com.example.proiect.exceptions.MyException;
import com.example.proiect.model.MedicalPersonnel;

public interface IRepoMedicalPersonnel extends IRepository<MedicalPersonnel,Integer>{
    Object authenticateMedicalPersonnel(String username, String password) throws MyException;
}
