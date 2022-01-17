package com.example.EstudoApiJava.resource;

import com.example.EstudoApiJava.dto.FuncionarioDTO;
import com.example.EstudoApiJava.entities.Funcionario;
import com.example.EstudoApiJava.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioResource {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> buscarTodos(){
        List<FuncionarioDTO> funcionarioDto = funcionarioService.buscarTodos().stream()
                .map(obj -> new FuncionarioDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(funcionarioDto);
    }

    @GetMapping("/{numSequencial}")
    public ResponseEntity<FuncionarioDTO> buscar(@PathVariable final Long numSequencial){
        FuncionarioDTO funcionarioDto = new FuncionarioDTO(funcionarioService.buscarFuncionario(numSequencial));

        return ResponseEntity.ok().body(funcionarioDto);
    }

    @PostMapping
    public ResponseEntity<FuncionarioDTO> inserir(@Valid final FuncionarioDTO funcionarioDto){
        Funcionario funcionario = funcionarioService.inserir(funcionarioDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{numSequencial}")
                .buildAndExpand(funcionario.getNumSequencial()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{numSequencial}")
    public ResponseEntity<FuncionarioDTO> atualizarDados(@PathVariable final Long numSequencial, @Valid @RequestBody FuncionarioDTO funcionarioDto){
        FuncionarioDTO novoFuncionario = new FuncionarioDTO(funcionarioService.atualizarDados(numSequencial , funcionarioDto));

        return ResponseEntity.ok().body(novoFuncionario);
    }

    @DeleteMapping("/{numSequencial}")
    public ResponseEntity<Void> remover(@PathVariable final Long numSequencial){
        funcionarioService.remover(numSequencial);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/saldo/{numSequencial}")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable final Long numSequencial){
        FuncionarioDTO funcionarioDto = new FuncionarioDTO(funcionarioService.consultarSaldo(numSequencial));

        return ResponseEntity.ok().body(funcionarioDto.getSaldoAtual());
    }
}
