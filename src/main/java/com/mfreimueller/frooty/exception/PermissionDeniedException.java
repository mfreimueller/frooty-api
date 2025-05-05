package com.mfreimueller.frooty.exception;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException() {
        super("Permission to this resource was denied.");
    }

}
