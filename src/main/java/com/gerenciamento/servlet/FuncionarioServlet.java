package com.gerenciamento.servlet;

import com.gerenciamento.dao.CargoDAO;
import com.gerenciamento.dao.FuncionarioDAO;
import com.gerenciamento.model.Funcionario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/funcionarios/*")
public class FuncionarioServlet extends HttpServlet {
    
    private FuncionarioDAO funcionarioDAO;
    private CargoDAO cargoDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public void init() {
        funcionarioDAO = new FuncionarioDAO();
        cargoDAO = new CargoDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getPathInfo();
        
        try {
            if (action == null || action.equals("/")) {
                listarFuncionarios(request, response);
            } else if (action.equals("/novo")) {
                mostrarFormularioNovo(request, response);
            } else if (action.equals("/editar")) {
                mostrarFormularioEditar(request, response);
            } else if (action.equals("/departamento")) {
                listarPorDepartamento(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao processar requisição", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getPathInfo();
        
        try {
            if (action == null || action.equals("/")) {
                String deleteId = request.getParameter("deleteId");
                if (deleteId != null && !deleteId.isEmpty()) {
                    excluirFuncionario(Integer.parseInt(deleteId), request, response);
                } else {
                    salvarFuncionario(request, response);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException | ParseException e) {
            throw new ServletException("Erro ao processar requisição", e);
        }
    }
    
    private void listarFuncionarios(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<Funcionario> funcionarios = funcionarioDAO.listar();
        request.setAttribute("funcionarios", funcionarios);
        request.getRequestDispatcher("/WEB-INF/views/funcionario/listar.jsp")
               .forward(request, response);
    }
    
    private void listarPorDepartamento(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        int departamentoId = Integer.parseInt(request.getParameter("id"));
        List<Funcionario> funcionarios = funcionarioDAO.buscarPorDepartamento(departamentoId);
        request.setAttribute("funcionarios", funcionarios);
        request.getRequestDispatcher("/WEB-INF/views/funcionario/listar.jsp")
               .forward(request, response);
    }
    
    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        request.setAttribute("cargos", cargoDAO.listar());
        request.getRequestDispatcher("/WEB-INF/views/funcionario/form.jsp")
               .forward(request, response);
    }
    
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Funcionario funcionario = funcionarioDAO.buscarPorId(id);
        
        if (funcionario != null) {
            request.setAttribute("funcionario", funcionario);
            request.setAttribute("cargos", cargoDAO.listar());
            request.getRequestDispatcher("/WEB-INF/views/funcionario/form.jsp")
                   .forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void salvarFuncionario(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ParseException {
        
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String dataNascimento = request.getParameter("dataNascimento");
        String dataContratacao = request.getParameter("dataContratacao");
        String cargoId = request.getParameter("cargoId");
        String salario = request.getParameter("salario");
        String status = request.getParameter("status");
        
        Funcionario funcionario = new Funcionario();
        if (id != null && !id.isEmpty()) {
            funcionario.setId(Integer.parseInt(id));
        }
        
        funcionario.setNome(nome);
        funcionario.setEmail(email);
        funcionario.setCpf(cpf);
        funcionario.setDataNascimento(dateFormat.parse(dataNascimento));
        funcionario.setDataContratacao(dateFormat.parse(dataContratacao));
        funcionario.setCargoId(Integer.parseInt(cargoId));
        funcionario.setSalario(Double.parseDouble(salario));
        funcionario.setStatus(status);
        
        if (funcionario.getId() == 0) {
            funcionarioDAO.inserir(funcionario);
        } else {
            funcionarioDAO.atualizar(funcionario);
        }
        
        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }
    
    private void excluirFuncionario(int id, HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        funcionarioDAO.excluir(id);
        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }
} 