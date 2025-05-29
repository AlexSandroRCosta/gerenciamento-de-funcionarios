<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Departamentos</h2>
    <a href="${pageContext.request.contextPath}/departamentos/novo" class="btn btn-primary">
        <i class="fas fa-plus"></i> Novo Departamento
    </a>
</div>

<c:if test="${not empty mensagem}">
    <div class="alert alert-${tipoMensagem} alert-dismissible fade show" role="alert">
        ${mensagem}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="table-responsive">
    <table class="table table-striped table-hover">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Descrição</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${departamentos}" var="departamento">
                <tr>
                    <td>${departamento.id}</td>
                    <td>${departamento.nome}</td>
                    <td>${departamento.descricao}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/departamentos/editar/${departamento.id}" 
                           class="btn btn-sm btn-warning" title="Editar">
                            <i class="fas fa-edit"></i>
                        </a>
                        <button type="button" class="btn btn-sm btn-danger" title="Excluir"
                                onclick="confirmarExclusao(${departamento.id}, '${departamento.nome}')">
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
                <p>Tem certeza que deseja excluir o departamento <span id="nomeDepartamento"></span>?</p>
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
    document.getElementById('nomeDepartamento').textContent = nome;
    document.getElementById('formExclusao').action = '${pageContext.request.contextPath}/departamentos/excluir/' + id;
    new bootstrap.Modal(document.getElementById('modalConfirmacao')).show();
}
</script> 