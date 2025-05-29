package com.gerenciamento.servlet;

import com.gerenciamento.dao.DepartamentoDAO;
import com.gerenciamento.model.Departamento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/departamentos/*")
public class DepartamentoServlet extends HttpServlet {
    
    private DepartamentoDAO departamentoDAO;
    
    @Override
    public void init() {
        departamentoDAO = new DepartamentoDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getPathInfo();
        
        try {
            if (action == null || action.equals("/")) {
                listarDepartamentos(request, response);
            } else if (action.equals("/novo")) {
                mostrarFormularioNovo(request, response);
            } else if (action.equals("/editar")) {
                mostrarFormularioEditar(request, response);
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
                    excluirDepartamento(Integer.parseInt(deleteId), request, response);
                } else {
                    salvarDepartamento(request, response);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao processar requisição", e);
        }
    }
    
    private void listarDepartamentos(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<Departamento> departamentos = departamentoDAO.listar();
        request.setAttribute("departamentos", departamentos);
        request.getRequestDispatcher("/WEB-INF/views/departamento/listar.jsp")
               .forward(request, response);
    }
    
    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/views/departamento/form.jsp")
               .forward(request, response);
    }
    
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Departamento departamento = departamentoDAO.buscarPorId(id);
        
        if (departamento != null) {
            request.setAttribute("departamento", departamento);
            request.getRequestDispatcher("/WEB-INF/views/departamento/form.jsp")
                   .forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void salvarDepartamento(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        
        Departamento departamento = new Departamento();
        if (id != null && !id.isEmpty()) {
            departamento.setId(Integer.parseInt(id));
        }
        departamento.setNome(nome);
        departamento.setDescricao(descricao);
        
        if (departamento.getId() == 0) {
            departamentoDAO.inserir(departamento);
        } else {
            departamentoDAO.atualizar(departamento);
        }
        
        response.sendRedirect(request.getContextPath() + "/departamentos");
    }
    
    private void excluirDepartamento(int id, HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        departamentoDAO.excluir(id);
        response.sendRedirect(request.getContextPath() + "/departamentos");
    }
} 