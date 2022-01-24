package com.example.EstudoApiJava.service;

import com.example.EstudoApiJava.dto.FuncionarioDTO;
import com.example.EstudoApiJava.entities.Empresa;
import com.example.EstudoApiJava.entities.Funcionario;
import com.example.EstudoApiJava.repository.EmpresaRepository;
import com.example.EstudoApiJava.repository.FuncionarioRepository;
import com.example.EstudoApiJava.service.exception.ObjetoNaoEncontradoException;
import com.example.EstudoApiJava.service.exception.ViolacaoIntegridadeDosDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public Funcionario buscarFuncionario(final Long numSequencial) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(numSequencial);
        return funcionario.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado. "));
    }

    public List<Funcionario> buscarTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario inserir(final FuncionarioDTO funcionarioDto) {
        if (findByCpf(funcionarioDto) != null) {
            throw new ViolacaoIntegridadeDosDadosException("Funcionário já cadastrado. ");
        }
        Optional<Empresa> empresa = empresaRepository.findById(funcionarioDto.getEmpresaNumSequencial());
        return funcionarioRepository.save(new Funcionario(funcionarioDto.getNome(), funcionarioDto.getCpf(),
                funcionarioDto.getEndereco(), funcionarioDto.getSaldoAtual(), empresa.get()));
    }

    public Funcionario atualizarDados(final Long numSequencial , @Valid FuncionarioDTO funcionarioDto){
        Funcionario funcionario = buscarFuncionario(numSequencial);

        if(findByCpf(funcionarioDto) != null && findByCpf(funcionarioDto).getNumSequencial() != numSequencial){
            throw new ViolacaoIntegridadeDosDadosException("CPF já cadastrado.");
        }
        Optional<Empresa> empresa = empresaRepository.findById(funcionarioDto.getEmpresaNumSequencial());

        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setCpf(funcionarioDto.getCpf());
        funcionario.setEndereco(funcionarioDto.getEndereco());
        funcionario.setSaldoAtual(funcionarioDto.getSaldoAtual());
        funcionario.setEmpresa(empresa.get());
        return funcionarioRepository.save(funcionario);
    }

    public void remover(final Long numSequencial){
        Funcionario funcionario = buscarFuncionario(numSequencial);
        funcionarioRepository.delete(funcionario);
    }

    public Funcionario consultarSaldo(final Long numSequencial){
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(numSequencial);
        if(funcionarioOpt.isPresent()) { return funcionarioOpt.get(); }
        throw new ObjetoNaoEncontradoException("Funcionario não encontrado");
    }

    private Funcionario findByCpf(final FuncionarioDTO funcionarioDto) {
        Funcionario funcionario = funcionarioRepository.findByCpf(funcionarioDto.getCpf());
        if(funcionario != null){ return funcionario;}
        return null;
    }
}
