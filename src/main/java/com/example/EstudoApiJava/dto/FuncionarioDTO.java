package com.example.EstudoApiJava.dto;

import com.example.EstudoApiJava.entities.Funcionario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class FuncionarioDTO {

    private Long numSequencial;
    private String nome;
    private String cpf;
    private String endereco;
    private Long empresaNumSequencial;
    private BigDecimal saldoAtual;

    public FuncionarioDTO(final Funcionario funcionario){
        this.numSequencial = funcionario.getNumSequencial();
        this.nome = funcionario.getNome();
        this.cpf = funcionario.getCpf();
        this.endereco = funcionario.getEndereco();
        this.empresaNumSequencial = funcionario.getEmpresa().getNumSequencial();
        this.saldoAtual = funcionario.getSaldoAtual();
    }

}
