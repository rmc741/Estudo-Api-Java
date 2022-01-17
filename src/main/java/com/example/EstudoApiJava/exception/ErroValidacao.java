package com.example.EstudoApiJava.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class ErroValidacao extends ErroPadrao {

    private static final long serialVersionUID = 1L;

    private List<FieldMessage> erros = new ArrayList<>();

    public ErroValidacao(Long timestamp, Integer status, String erro) {
        super(timestamp, status, erro);
    }

    public void addErro(String fieldName, String message) {
        this.erros.add(new FieldMessage(fieldName, message));
    }

}