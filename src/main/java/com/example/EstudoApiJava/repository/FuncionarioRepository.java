package com.example.EstudoApiJava.repository;

import com.example.EstudoApiJava.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    boolean existsByCpf(String cpf);

    @Query("SELECT obj FROM Funcionario obj WHERE obj.cpf =:cpf")
    Funcionario findBycpf(@Param("cpf") String cpf);

    Optional<Funcionario> getByCpf(String cpf);
}
