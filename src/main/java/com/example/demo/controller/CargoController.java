package com.example.demo.controller;

import com.example.demo.model.Cargo;
import com.example.demo.service.CargoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cargos", cargoService.listarTodos());
        return "cargo-list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cargo", new Cargo());
        return "cargo-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("cargo") Cargo cargo,
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cargo-form";
        }
        
        try {
            cargoService.salvar(cargo);
            redirectAttributes.addFlashAttribute("mensagem", "Cargo salvo com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "error");
            return "cargo-form";
        }
        
        return "redirect:/cargos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            Cargo cargo = cargoService.buscarPorId(id);
            model.addAttribute("cargo", cargo);
            return "cargo-form";
        } catch (RuntimeException e) {
            return "redirect:/cargos";
        }
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("cargo") Cargo cargo,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            return "cargo-form";
        }
        try {
            cargo.setId(id); // Garante que o ID está correto
            cargoService.salvar(cargo);
            redirectAttributes.addFlashAttribute("mensagem", "Cargo atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "error");
            return "cargo-form";
        }
        return "redirect:/cargos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cargoService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Cargo excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "error");
        }
        return "redirect:/cargos";
    }
} 