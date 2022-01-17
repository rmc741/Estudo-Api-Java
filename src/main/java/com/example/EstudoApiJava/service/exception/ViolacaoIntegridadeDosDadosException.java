package com.example.EstudoApiJava.service.exception;

public class ViolacaoIntegridadeDosDadosException extends RuntimeException{

    public ViolacaoIntegridadeDosDadosException(String message , Throwable cause){ super(message, cause);}

    public ViolacaoIntegridadeDosDadosException(String message) {super(message); }
}
