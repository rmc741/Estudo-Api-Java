package com.example.EstudoApiJava.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "funcionario")
@NoArgsConstructor
@Getter
@Setter
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_sequencial")
    private Long numSequencial;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "saldo_Atual")
    private BigDecimal saldoAtual;

    @ManyToOne
    @JoinColumn(name = "empresa" , referencedColumnName = "num_sequencial")
    private Empresa empresa;

    public Funcionario(String nome, String cpf, String endereco, BigDecimal saldoAtual , Empresa empresa){
        super();
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.saldoAtual = saldoAtual;
        this.empresa = empresa;
    }

}
