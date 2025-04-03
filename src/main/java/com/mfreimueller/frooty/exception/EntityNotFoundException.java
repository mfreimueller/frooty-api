package com.mfreimueller.frooty.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Integer id) {
        super("Could not find meal " + id);
    }

}
