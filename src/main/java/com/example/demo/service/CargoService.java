package com.example.demo.service;

import com.example.demo.model.Cargo;
import com.example.demo.repository.CargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<Cargo> listarTodos() {
        return cargoRepository.findAll();
    }

    public Cargo buscarPorId(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo não encontrado com ID: " + id));
    }

    public Cargo buscarPorNome(String nome) {
        List<Cargo> cargos = cargoRepository.findByNomeContainingIgnoreCase(nome);
        if (cargos.isEmpty()) {
            throw new RuntimeException("Nenhum cargo encontrado com o nome: " + nome);
        }
        return cargos.get(0);
    }

    public Cargo salvar(Cargo cargo) {
        // Verifica se já existe um cargo com o mesmo nome
        if (cargo.getId() == null && cargoRepository.existsByNome(cargo.getNome())) {
            throw new RuntimeException("Já existe um cargo com o nome: " + cargo.getNome());
        }
        return cargoRepository.save(cargo);
    }

    public void excluir(Long id) {
        if (!cargoRepository.existsById(id)) {
            throw new RuntimeException("Cargo não encontrado com ID: " + id);
        }
        cargoRepository.deleteById(id);
    }

    public List<Cargo> buscarPorNomeContendo(String nome) {
        return cargoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Cargo> buscarPorSalarioBaseMaiorQue(BigDecimal salarioBase) {
        return cargoRepository.findBySalarioBaseGreaterThan(salarioBase);
    }

    public boolean existePorNome(String nome) {
        return cargoRepository.existsByNome(nome);
    }
} 