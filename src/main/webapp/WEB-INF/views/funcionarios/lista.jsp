<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Funcionários</h2>
    <a href="${pageContext.request.contextPath}/funcionarios/novo" class="btn btn-primary">
        <i class="fas fa-plus"></i> Novo Funcionário
    </a>
</div>

<c:if test="${not empty mensagem}">
    <div class="alert alert-${tipoMensagem} alert-dismissible fade show" role="alert">
        ${mensagem}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="card mb-4">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/funcionarios" method="get" class="row g-3">
            <div class="col-md-4">
                <label for="departamento" class="form-label">Filtrar por Departamento</label>
                <select class="form-select" id="departamento" name="departamentoId">
                    <option value="">Todos os departamentos</option>
                    <c:forEach items="${departamentos}" var="departamento">
                        <option value="${departamento.id}" 
                                ${param.departamentoId == departamento.id ? 'selected' : ''}>
                            ${departamento.nome}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4">
                <label for="busca" class="form-label">Buscar</label>
                <input type="text" class="form-control" id="busca" name="busca" 
                       value="${param.busca}" placeholder="Nome ou CPF">
            </div>
            <div class="col-md-4 d-flex align-items-end">
                <button type="submit" class="btn btn-primary me-2">
                    <i class="fas fa-search"></i> Buscar
                </button>
                <a href="${pageContext.request.contextPath}/funcionarios" class="btn btn-secondary">
                    <i class="fas fa-times"></i> Limpar
                </a>
            </div>
        </form>
    </div>
</div>

<div class="table-responsive">
    <table class="table table-striped table-hover">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>CPF</th>
                <th>Departamento</th>
                <th>Cargo</th>
                <th>Data de Admissão</th>
                <th>Salário</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${funcionarios}" var="funcionario">
                <tr>
                    <td>${funcionario.id}</td>
                    <td>${funcionario.nome}</td>
                    <td>${funcionario.cpf}</td>
                    <td>${funcionario.cargo.departamento.nome}</td>
                    <td>${funcionario.cargo.nome}</td>
                    <td>
                        <fmt:formatDate value="${funcionario.dataAdmissao}" pattern="dd/MM/yyyy"/>
                    </td>
                    <td>R$ ${funcionario.salario}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/funcionarios/editar/${funcionario.id}" 
                           class="btn btn-sm btn-warning" title="Editar">
                            <i class="fas fa-edit"></i>
                        </a>
                        <button type="button" class="btn btn-sm btn-danger" title="Excluir"
                                onclick="confirmarExclusao(${funcionario.id}, '${funcionario.nome}')">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- Modal de Confirmação de Exclusão -->
<div class="modal fade" id="modalConfirmacao" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirmar Exclusão</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Tem certeza que deseja excluir o funcionário <span id="nomeFuncionario"></span>?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <form id="formExclusao" method="post" style="display: inline;">
                    <button type="submit" class="btn btn-danger">Excluir</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
function confirmarExclusao(id, nome) {
    document.getElementById('nomeFuncionario').textContent = nome;
    document.getElementById('formExclusao').action = '${pageContext.request.contextPath}/funcionarios/excluir/' + id;
    new bootstrap.Modal(document.getElementById('modalConfirmacao')).show();
}
</script> 