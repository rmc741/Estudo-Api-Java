package com.example.EstudoApiJava.service;

import com.example.EstudoApiJava.dto.EmpresaDTO;
import com.example.EstudoApiJava.entities.Empresa;
import com.example.EstudoApiJava.entities.Funcionario;
import com.example.EstudoApiJava.repository.EmpresaRepository;
import com.example.EstudoApiJava.repository.FuncionarioRepository;
import com.example.EstudoApiJava.resource.request.TransacaoRequest;
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
public class EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    TransacaoService transacaoService;

    public List<Empresa> buscarTodos() { return empresaRepository.findAll(); }

    public Empresa buscarEmpresa(final Long numSequencial){
        Optional<Empresa> empresa = empresaRepository.findById(numSequencial);

        return empresa.orElseThrow(() -> new ObjetoNaoEncontradoException("Empresa não foi localizada."));
    }

    public Empresa inserir(final EmpresaDTO empresaDto){
        if(findByCnpj(empresaDto) != null){
            throw new ViolacaoIntegridadeDosDadosException("Empresa ja cadastrada");
        }
        return empresaRepository.save(new Empresa(empresaDto.getNome(), empresaDto.getCnpj(),
                empresaDto.getEndereco(),empresaDto.getSaldoAtual()));
    }

    private Empresa findByCnpj(final EmpresaDTO empresaDto) {
        Empresa empresa = empresaRepository.findByCnpj(empresaDto.getCnpj());
        if(empresa != null){
            return empresa;
        }
        return null;
    }

    public Empresa atualizarDados(final Long numSequencial , @Valid final EmpresaDTO empresaDto){
        Empresa empresa = buscarEmpresa(numSequencial);

        if(findByCnpj(empresaDto) != null && findByCnpj(empresaDto).getNumSequencial() != numSequencial){
            throw new ViolacaoIntegridadeDosDadosException("CNPJ ja cadastrado.");
        }

        empresa.setNome(empresaDto.getNome());
        empresa.setCnpj(empresaDto.getCnpj());
        empresa.setEndereco(empresaDto.getEndereco());
        empresa.setSaldoAtual(empresaDto.getSaldoAtual());
        return empresaRepository.save(empresa);
    }

    public void remover(final Long numSequencial){
        Empresa empresa = buscarEmpresa(numSequencial);
        if(empresa.getFuncionariosList().size() > 0){
            throw new ViolacaoIntegridadeDosDadosException("A Empresa possui um ou mais funcionarios vinculados a ela");
        }
        empresaRepository.delete(empresa);
    }

    public Empresa consultarSaldo(final Long numSequencial){
        Optional<Empresa> empresaOpt = empresaRepository.findById(numSequencial);
        if(empresaOpt.isPresent()){ return empresaOpt.get();}
        throw new ObjetoNaoEncontradoException("Empresa não foi encontrada.");
    }

    public void transferir(final TransacaoRequest request){
        Optional<Empresa> empresaOpt = empresaRepository.findById(request.getEmpresaNumSequencial());
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.getByCpf(request.getCpfFuncionario());

        if(!empresaOpt.isPresent()){ throw new ObjetoNaoEncontradoException("Empresa não encontrada."); }
        if(!funcionarioOpt.isPresent()){ throw new ObjetoNaoEncontradoException("Funcionario não encontrado."); }

        Empresa empresa = empresaOpt.get();
        Funcionario funcionario = funcionarioOpt.get();

        if(request.getValorTransacao().compareTo(empresa.getSaldoAtual()) == 1){
            throw new ViolacaoIntegridadeDosDadosException("Saldo insuficiente.");
        }

        transacaoService.sacar(empresa.getNumSequencial() , request.getValorTransacao());
        transacaoService.depositar(funcionario.getNumSequencial() , request.getValorTransacao());

        empresaRepository.save(empresa);
        funcionarioRepository.save(funcionario);
    }

}
