package com.example.EstudoApiJava.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "empresa")
@NoArgsConstructor
@Getter @Setter
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "empresa")
    private List<Funcionario> funcionariosList;

    public Empresa(String nome,  String cnpj , String endereco , BigDecimal saldoAtual){
        super();
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.saldoAtual = saldoAtual;
    }
}
