package domain.validators;

import exceptions.ValidationException;

public interface Validator<T> {
    /**
     * valideaza entitatea data
     *
     * @param entity - utilizatorul/prietenia care trebuie validata
     * @throws ValidationException - in caz ca entitatea nu e valida
     */
    void validate(T entity) throws ValidationException;
}