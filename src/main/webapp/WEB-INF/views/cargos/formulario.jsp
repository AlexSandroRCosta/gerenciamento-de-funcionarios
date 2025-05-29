<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>${cargo.id == null ? 'Novo Cargo' : 'Editar Cargo'}</h2>
    <a href="${pageContext.request.contextPath}/cargos" class="btn btn-secondary">
        <i class="fas fa-arrow-left"></i> Voltar
    </a>
</div>

<c:if test="${not empty mensagem}">
    <div class="alert alert-${tipoMensagem} alert-dismissible fade show" role="alert">
        ${mensagem}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/cargos/salvar" method="post">
            <input type="hidden" name="id" value="${cargo.id}">
            
            <div class="mb-3">
                <label for="nome" class="form-label">Nome</label>
                <input type="text" class="form-control" id="nome" name="nome" 
                       value="${cargo.nome}" required>
            </div>
            
            <div class="mb-3">
                <label for="departamento" class="form-label">Departamento</label>
                <select class="form-select" id="departamento" name="departamentoId" required>
                    <option value="">Selecione um departamento</option>
                    <c:forEach items="${departamentos}" var="departamento">
                        <option value="${departamento.id}" 
                                ${cargo.departamento.id == departamento.id ? 'selected' : ''}>
                            ${departamento.nome}
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="mb-3">
                <label for="salarioBase" class="form-label">Salário Base</label>
                <div class="input-group">
                    <span class="input-group-text">R$</span>
                    <input type="number" class="form-control" id="salarioBase" name="salarioBase" 
                           value="${cargo.salarioBase}" step="0.01" min="0" required>
                </div>
            </div>
            
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Salvar
                </button>
            </div>
        </form>
    </div>
</div> 