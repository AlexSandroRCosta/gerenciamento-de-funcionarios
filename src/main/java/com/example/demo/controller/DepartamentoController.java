package com.example.demo.controller;

import com.example.demo.model.Departamento;
import com.example.demo.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("departamentos", departamentoService.listarTodos());
        return "departamento-list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("departamento", new Departamento());
        return "departamento-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("departamento") Departamento departamento,
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "departamento-form";
        }
        
        try {
            departamentoService.salvar(departamento);
            redirectAttributes.addFlashAttribute("mensagem", "Departamento salvo com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "error");
            return "departamento-form";
        }
        
        return "redirect:/departamentos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            Departamento departamento = departamentoService.buscarPorId(id);
            model.addAttribute("departamento", departamento);
            return "departamento-form";
        } catch (RuntimeException e) {
            return "redirect:/departamentos";
        }
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("departamento") Departamento departamento,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            return "departamento-form";
        }
        try {
            departamento.setId(id); // Garante que o ID está correto
            departamentoService.salvar(departamento);
            redirectAttributes.addFlashAttribute("mensagem", "Departamento atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "error");
            return "departamento-form";
        }
        return "redirect:/departamentos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            departamentoService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Departamento excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "error");
        }
        return "redirect:/departamentos";
    }
} 