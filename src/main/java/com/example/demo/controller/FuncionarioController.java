package com.example.demo.controller;

import com.example.demo.model.Funcionario;
import com.example.demo.service.FuncionarioService;
import com.example.demo.service.DepartamentoService;
import com.example.demo.service.CargoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final DepartamentoService departamentoService;
    private final CargoService cargoService;

    public FuncionarioController(FuncionarioService funcionarioService,
                                DepartamentoService departamentoService,
                                CargoService cargoService) {
        this.funcionarioService = funcionarioService;
        this.departamentoService = departamentoService;
        this.cargoService = cargoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("funcionarios", funcionarioService.listarTodos());
        return "funcionario-list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("departamentos", departamentoService.listarTodos());
        model.addAttribute("cargos", cargoService.listarTodos());
        return "funcionario-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("funcionario") Funcionario funcionario,
                         BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departamentos", departamentoService.listarTodos());
            model.addAttribute("cargos", cargoService.listarTodos());
            return "funcionario-form";
        }
        
        try {
            funcionarioService.salvar(funcionario);
            redirectAttributes.addFlashAttribute("mensagem", "Funcionário salvo com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "error");
            model.addAttribute("departamentos", departamentoService.listarTodos());
            model.addAttribute("cargos", cargoService.listarTodos());
            return "funcionario-form";
        }
        
        return "redirect:/funcionarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            Funcionario funcionario = funcionarioService.buscarPorId(id);
            model.addAttribute("funcionario", funcionario);
            model.addAttribute("departamentos", departamentoService.listarTodos());
            model.addAttribute("cargos", cargoService.listarTodos());
            return "funcionario-form";
        } catch (RuntimeException e) {
            return "redirect:/funcionarios";
        }
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("funcionario") Funcionario funcionario,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departamentos", departamentoService.listarTodos());
            model.addAttribute("cargos", cargoService.listarTodos());
            return "funcionario-form";
        }
        try {
            funcionario.setId(id); // Garante que o ID está correto
            funcionarioService.salvar(funcionario);
            redirectAttributes.addFlashAttribute("mensagem", "Funcionário atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "error");
            model.addAttribute("departamentos", departamentoService.listarTodos());
            model.addAttribute("cargos", cargoService.listarTodos());
            return "funcionario-form";
        }
        return "redirect:/funcionarios";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            funcionarioService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Funcionário excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "error");
        }
        return "redirect:/funcionarios";
    }
} 