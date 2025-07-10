package com.example.demo.service;

import com.example.demo.model.Departamento;
import com.example.demo.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public List<Departamento> listarTodos() {
        return departamentoRepository.findAll();
    }

    public Departamento buscarPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado com ID: " + id));
    }

    public Departamento buscarPorNome(String nome) {
        List<Departamento> departamentos = departamentoRepository.findByNomeContainingIgnoreCase(nome);
        if (departamentos.isEmpty()) {
            throw new RuntimeException("Nenhum departamento encontrado com o nome: " + nome);
        }
        return departamentos.get(0);
    }

    public Departamento salvar(Departamento departamento) {
        // Verifica se já existe um departamento com o mesmo nome
        if (departamento.getId() == null && departamentoRepository.existsByNome(departamento.getNome())) {
            throw new RuntimeException("Já existe um departamento com o nome: " + departamento.getNome());
        }
        return departamentoRepository.save(departamento);
    }

    public void excluir(Long id) {
        if (!departamentoRepository.existsById(id)) {
            throw new RuntimeException("Departamento não encontrado com ID: " + id);
        }
        departamentoRepository.deleteById(id);
    }

    public List<Departamento> buscarPorNomeContendo(String nome) {
        return departamentoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public boolean existePorNome(String nome) {
        return departamentoRepository.existsByNome(nome);
    }
} 