package com.example.HavenBook.exceptions;

/**
 * Exceção lançada quando uma data inválida é encontrada ou processada.
 */
public class InvalidDateException extends RuntimeException {

    /**
     * Construtor para criar uma nova exceção {@code InvalidDateException} com a mensagem fornecida.
     *
     * @param message Mensagem que descreve o motivo da exceção.
     */
    public InvalidDateException(String message) {
        super(message);
    }
}
