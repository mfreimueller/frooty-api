package com.mfreimueller.frooty.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Integer id) {
        super("Could not find entity " + id);
    }

    public EntityNotFoundException(Integer id, String entity) {
        super("Could not find " + entity + " " + id);
    }

}
