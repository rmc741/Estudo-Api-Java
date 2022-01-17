package com.example.EstudoApiJava.resource;

import com.example.EstudoApiJava.dto.EmpresaDTO;
import com.example.EstudoApiJava.entities.Empresa;
import com.example.EstudoApiJava.service.EmpresaService;
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
@RequestMapping(value = "/empresa")
public class EmpresaResource {

    @Autowired
    EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> buscarTodos(){
        List<EmpresaDTO> empresaDtoList = empresaService.buscarTodos().stream()
                .map(obj -> new EmpresaDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(empresaDtoList);
    }

    @GetMapping("/{numSequencial}")
    public ResponseEntity<EmpresaDTO> buscar(@PathVariable final Long numSequencial){
        EmpresaDTO empresaDto = new EmpresaDTO(empresaService.buscarEmpresa(numSequencial));

        return ResponseEntity.ok().body(empresaDto);
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> inserir(@Valid @RequestBody final EmpresaDTO empresaDto){
        Empresa empresa = empresaService.inserir(empresaDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{numSequencial}").buildAndExpand(empresa.getNumSequencial()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{numSequencial}")
    public ResponseEntity<EmpresaDTO> atualizarDados(@PathVariable final Long numSequencial, @Valid @RequestBody EmpresaDTO empresaDto){
        EmpresaDTO novaEmpresa = new EmpresaDTO(empresaService.atualizarDados(numSequencial , empresaDto));
        return ResponseEntity.ok().body(novaEmpresa);
    }

    @DeleteMapping("/{numSequencial}")
    public ResponseEntity<Void> remover(@PathVariable final Long numSequencial){
        empresaService.remover(numSequencial);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/saldo/{numSequencial}")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable final Long numSequencial){
        EmpresaDTO empresaDto = new EmpresaDTO(empresaService.consultarSaldo(numSequencial));
        return ResponseEntity.ok().body(empresaDto.getSaldoAtual());
    }
}
