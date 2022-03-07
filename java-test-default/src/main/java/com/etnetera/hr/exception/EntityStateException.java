package com.etnetera.hr.exception;

public class EntityStateException extends RuntimeException{

    public <E> EntityStateException(E e){
        super(e+" already exists with this properties");
    }

    public EntityStateException(){

    }
}
