package com.example.demo.service;

import com.example.demo.model.Funcionario;
import com.example.demo.model.Departamento;
import com.example.demo.model.Cargo;
import com.example.demo.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
    }

    public Funcionario buscarPorEmail(String email) {
        return funcionarioRepository.findByEmail(email);
    }

    public Funcionario buscarPorCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf);
    }

    public Funcionario salvar(Funcionario funcionario) {
        // Verifica se já existe um funcionário com o mesmo email
        if (funcionario.getId() == null && funcionarioRepository.existsByEmail(funcionario.getEmail())) {
            throw new RuntimeException("Já existe um funcionário com o email: " + funcionario.getEmail());
        }
        
        // Verifica se já existe um funcionário com o mesmo CPF
        if (funcionario.getId() == null && funcionarioRepository.existsByCpf(funcionario.getCpf())) {
            throw new RuntimeException("Já existe um funcionário com o CPF: " + funcionario.getCpf());
        }
        
        return funcionarioRepository.save(funcionario);
    }

    public void excluir(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado com ID: " + id);
        }
        funcionarioRepository.deleteById(id);
    }

    public List<Funcionario> buscarPorNomeContendo(String nome) {
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Funcionario> buscarPorDepartamento(Departamento departamento) {
        return funcionarioRepository.findByDepartamento(departamento);
    }

    public List<Funcionario> buscarPorCargo(Cargo cargo) {
        return funcionarioRepository.findByCargo(cargo);
    }

    public List<Funcionario> buscarPorDepartamentoECargo(Departamento departamento, Cargo cargo) {
        return funcionarioRepository.findByDepartamentoAndCargo(departamento, cargo);
    }

    public List<Funcionario> buscarContratadosApos(LocalDate data) {
        return funcionarioRepository.findByDataContratacaoAfter(data);
    }

    public List<Funcionario> buscarPorSalarioMaiorQue(BigDecimal salario) {
        return funcionarioRepository.findBySalarioGreaterThan(salario);
    }

    public List<Object[]> calcularSalarioMedioPorDepartamento() {
        return funcionarioRepository.calcularSalarioMedioPorDepartamento();
    }

    public boolean existePorEmail(String email) {
        return funcionarioRepository.existsByEmail(email);
    }

    public boolean existePorCpf(String cpf) {
        return funcionarioRepository.existsByCpf(cpf);
    }
} 