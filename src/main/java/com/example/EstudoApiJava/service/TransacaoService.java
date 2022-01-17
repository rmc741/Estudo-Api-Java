package com.example.EstudoApiJava.service;

import com.example.EstudoApiJava.entities.Empresa;
import com.example.EstudoApiJava.entities.Funcionario;
import com.example.EstudoApiJava.repository.EmpresaRepository;
import com.example.EstudoApiJava.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class TransacaoService {

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public Empresa sacar(final Long numSequencial , final BigDecimal valor){
        Optional<Empresa> empresaOpt = empresaRepository.findById(numSequencial);
        Empresa empresa = empresaOpt.get();
        empresa.setSaldoAtual(empresaOpt.get().getSaldoAtual());
        if(valor.compareTo(BigDecimal.ZERO) == 1 && valor.compareTo(empresa.getSaldoAtual()) <= 0){
            empresa.setSaldoAtual(empresa.getSaldoAtual().subtract(valor));
            return empresa;
        }
        return null;
    }

    public Funcionario depositar(final Long numSequencial , final BigDecimal valor){
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(numSequencial);
        Funcionario funcionario = funcionarioOpt.get();
        funcionario.setSaldoAtual(funcionarioOpt.get().getSaldoAtual());
        if(valor.compareTo(BigDecimal.ZERO) == 1){
            funcionario.setSaldoAtual(funcionario.getSaldoAtual().add(valor));
            return funcionario;
        }
        return null;
    }
}
