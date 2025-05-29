<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="text-center py-5">
    <h1 class="display-1 text-muted">404</h1>
    <h2 class="mb-4">Página não encontrada</h2>
    <p class="lead mb-4">A página que você está procurando não existe ou foi movida.</p>
    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
        <i class="fas fa-home"></i> Voltar para a página inicial
    </a>
</div> 