package com.gerenciamento.servlet;

import com.gerenciamento.dao.CargoDAO;
import com.gerenciamento.dao.DepartamentoDAO;
import com.gerenciamento.model.Cargo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cargos/*")
public class CargoServlet extends HttpServlet {
    
    private CargoDAO cargoDAO;
    private DepartamentoDAO departamentoDAO;
    
    @Override
    public void init() {
        cargoDAO = new CargoDAO();
        departamentoDAO = new DepartamentoDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getPathInfo();
        
        try {
            if (action == null || action.equals("/")) {
                listarCargos(request, response);
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
                    excluirCargo(Integer.parseInt(deleteId), request, response);
                } else {
                    salvarCargo(request, response);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao processar requisição", e);
        }
    }
    
    private void listarCargos(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<Cargo> cargos = cargoDAO.listar();
        request.setAttribute("cargos", cargos);
        request.getRequestDispatcher("/WEB-INF/views/cargo/listar.jsp")
               .forward(request, response);
    }
    
    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        request.setAttribute("departamentos", departamentoDAO.listar());
        request.getRequestDispatcher("/WEB-INF/views/cargo/form.jsp")
               .forward(request, response);
    }
    
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Cargo cargo = cargoDAO.buscarPorId(id);
        
        if (cargo != null) {
            request.setAttribute("cargo", cargo);
            request.setAttribute("departamentos", departamentoDAO.listar());
            request.getRequestDispatcher("/WEB-INF/views/cargo/form.jsp")
                   .forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void salvarCargo(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String departamentoId = request.getParameter("departamentoId");
        String salarioBase = request.getParameter("salarioBase");
        
        Cargo cargo = new Cargo();
        if (id != null && !id.isEmpty()) {
            cargo.setId(Integer.parseInt(id));
        }
        cargo.setNome(nome);
        cargo.setDepartamentoId(Integer.parseInt(departamentoId));
        cargo.setSalarioBase(Double.parseDouble(salarioBase));
        
        if (cargo.getId() == 0) {
            cargoDAO.inserir(cargo);
        } else {
            cargoDAO.atualizar(cargo);
        }
        
        response.sendRedirect(request.getContextPath() + "/cargos");
    }
    
    private void excluirCargo(int id, HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        cargoDAO.excluir(id);
        response.sendRedirect(request.getContextPath() + "/cargos");
    }
} 