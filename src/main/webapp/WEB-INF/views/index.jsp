<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-md-12 mb-4">
        <div class="card bg-primary text-white">
            <div class="card-body">
                <h1 class="card-title">Sistema de Gerenciamento de Funcionários</h1>
                <p class="card-text">Bem-vindo ao sistema de gerenciamento de funcionários. Aqui você pode gerenciar departamentos, cargos e funcionários de forma eficiente.</p>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-4 mb-4">
        <div class="card h-100">
            <div class="card-body text-center">
                <i class="fas fa-building fa-3x mb-3 text-primary"></i>
                <h5 class="card-title">Departamentos</h5>
                <p class="card-text">Gerencie os departamentos da empresa, adicionando, editando ou removendo conforme necessário.</p>
                <a href="${pageContext.request.contextPath}/departamentos" class="btn btn-primary">
                    <i class="fas fa-arrow-right"></i> Acessar
                </a>
            </div>
        </div>
    </div>
    
    <div class="col-md-4 mb-4">
        <div class="card h-100">
            <div class="card-body text-center">
                <i class="fas fa-briefcase fa-3x mb-3 text-success"></i>
                <h5 class="card-title">Cargos</h5>
                <p class="card-text">Gerencie os cargos disponíveis, definindo salários base e vinculando-os aos departamentos.</p>
                <a href="${pageContext.request.contextPath}/cargos" class="btn btn-success">
                    <i class="fas fa-arrow-right"></i> Acessar
                </a>
            </div>
        </div>
    </div>
    
    <div class="col-md-4 mb-4">
        <div class="card h-100">
            <div class="card-body text-center">
                <i class="fas fa-users fa-3x mb-3 text-info"></i>
                <h5 class="card-title">Funcionários</h5>
                <p class="card-text">Gerencie os funcionários, seus dados pessoais, cargos e salários.</p>
                <a href="${pageContext.request.contextPath}/funcionarios" class="btn btn-info">
                    <i class="fas fa-arrow-right"></i> Acessar
                </a>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-6 mb-4">
        <div class="card">
            <div class="card-header">
                <h5 class="card-title mb-0">Estatísticas</h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4 text-center">
                        <h3 class="text-primary">${totalDepartamentos}</h3>
                        <p class="text-muted">Departamentos</p>
                    </div>
                    <div class="col-md-4 text-center">
                        <h3 class="text-success">${totalCargos}</h3>
                        <p class="text-muted">Cargos</p>
                    </div>
                    <div class="col-md-4 text-center">
                        <h3 class="text-info">${totalFuncionarios}</h3>
                        <p class="text-muted">Funcionários</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="col-md-6 mb-4">
        <div class="card">
            <div class="card-header">
                <h5 class="card-title mb-0">Últimas Atividades</h5>
            </div>
            <div class="card-body">
                <div class="list-group">
                    <c:forEach items="${ultimasAtividades}" var="atividade">
                        <div class="list-group-item">
                            <div class="d-flex w-100 justify-content-between">
                                <h6 class="mb-1">${atividade.descricao}</h6>
                                <small class="text-muted">${atividade.data}</small>
                            </div>
                            <p class="mb-1">${atividade.detalhes}</p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div> 