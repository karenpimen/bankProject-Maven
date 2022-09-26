package jmp.service.api;

public class OptionalException extends RuntimeException{
    String message;
    public OptionalException(){}
    public OptionalException(String mess){
        this.message = mess;
    }
}
