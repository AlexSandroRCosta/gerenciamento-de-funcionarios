<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>${funcionario.id == null ? 'Novo Funcionário' : 'Editar Funcionário'}</h2>
    <a href="${pageContext.request.contextPath}/funcionarios" class="btn btn-secondary">
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
        <form action="${pageContext.request.contextPath}/funcionarios/salvar" method="post">
            <input type="hidden" name="id" value="${funcionario.id}">
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="nome" class="form-label">Nome</label>
                    <input type="text" class="form-control" id="nome" name="nome" 
                           value="${funcionario.nome}" required>
                </div>
                
                <div class="col-md-6 mb-3">
                    <label for="cpf" class="form-label">CPF</label>
                    <input type="text" class="form-control" id="cpf" name="cpf" 
                           value="${funcionario.cpf}" required>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="departamento" class="form-label">Departamento</label>
                    <select class="form-select" id="departamento" name="departamentoId" required
                            onchange="carregarCargos(this.value)">
                        <option value="">Selecione um departamento</option>
                        <c:forEach items="${departamentos}" var="departamento">
                            <option value="${departamento.id}" 
                                    ${funcionario.cargo.departamento.id == departamento.id ? 'selected' : ''}>
                                ${departamento.nome}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="col-md-6 mb-3">
                    <label for="cargo" class="form-label">Cargo</label>
                    <select class="form-select" id="cargo" name="cargoId" required>
                        <option value="">Selecione um cargo</option>
                        <c:forEach items="${cargos}" var="cargo">
                            <option value="${cargo.id}" 
                                    ${funcionario.cargo.id == cargo.id ? 'selected' : ''}
                                    data-salario="${cargo.salarioBase}">
                                ${cargo.nome}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="dataAdmissao" class="form-label">Data de Admissão</label>
                    <input type="date" class="form-control" id="dataAdmissao" name="dataAdmissao" 
                           value="<fmt:formatDate value='${funcionario.dataAdmissao}' pattern='yyyy-MM-dd'/>" 
                           required>
                </div>
                
                <div class="col-md-6 mb-3">
                    <label for="salario" class="form-label">Salário</label>
                    <div class="input-group">
                        <span class="input-group-text">R$</span>
                        <input type="number" class="form-control" id="salario" name="salario" 
                               value="${funcionario.salario}" step="0.01" min="0" required>
                    </div>
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

<script>
function carregarCargos(departamentoId) {
    if (!departamentoId) {
        document.getElementById('cargo').innerHTML = '<option value="">Selecione um cargo</option>';
        return;
    }
    
    fetch('${pageContext.request.contextPath}/cargos/por-departamento/' + departamentoId)
        .then(response => response.json())
        .then(cargos => {
            const select = document.getElementById('cargo');
            select.innerHTML = '<option value="">Selecione um cargo</option>';
            
            cargos.forEach(cargo => {
                const option = document.createElement('option');
                option.value = cargo.id;
                option.textContent = cargo.nome;
                option.dataset.salario = cargo.salarioBase;
                select.appendChild(option);
            });
        });
}

document.getElementById('cargo').addEventListener('change', function() {
    const selectedOption = this.options[this.selectedIndex];
    if (selectedOption.value) {
        document.getElementById('salario').value = selectedOption.dataset.salario;
    }
});
</script> 