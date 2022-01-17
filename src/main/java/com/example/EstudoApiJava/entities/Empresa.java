package com.example.EstudoApiJava.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "empresa")
@NoArgsConstructor
@Getter @Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_sequencial")
    private Long numSequencial;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "saldo_Atual")
    private BigDecimal saldoAtual;

    @JsonIgnore
    @OneToMany
    private List<Funcionario> funcionariosList;

    public Empresa(String nome,  String cnpj , String endereco , BigDecimal saldoAtual){
        super();
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.saldoAtual = saldoAtual;
    }
}
