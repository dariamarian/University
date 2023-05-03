package com.example.proiect.model.validators;

import com.example.proiect.exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}