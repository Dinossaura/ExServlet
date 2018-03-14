<%-- 
    Document   : listar.jsp
    Created on : 13/03/2018, 16:40:23
    Author     : magno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listagem de Produtos</title>
    </head>
    <body>
         <jsp:include page="menu.jsp"/>
         <div class="container">
             <div class="row">
                 <div class="col-12">
                     <h3>Listar Produtos</h3>
                 </div>
             </div>
             <div class="row">
                 <div class="col-12">
                     <form class="form-inline" action="${pageContext.request.contextPath}/consultaprodutos" method="post">
                        <input type="text" class="form-control" name="produto" placeholder="Digite nome Produto" maxlength="70"/>
                        <div class="botao">
                        <button type="submit" class="btn btn-success center-block" action="">Pesquisar</button>
                        </div>
                     </form>
                 </div>
             </div>
             <div class="row">
                     <c:forEach items="${ListaProdutos}" var="produto">
                     <div class="col-12 col-sm-3">
                     <table class="table table-bordered ">
                         <tr>
                             <th>
                                 <c:out value="Nome: ${produto.getNome()}"/> <br/>
                                 <c:out value="Descrição: ${produto.getDesc()}"/> <br/>
                                 <c:out value="Preço: R$ ${produto.getPrecoVenda()}"/> <br/>
                                 <c:out value="Qtd Diponível: ${produto.getQtde()}"/><br/>
                             
                             
                                 <div class="form-inline">
                                    <form class="form-group alteracoes" action="${pageContext.request.contextPath}/alterarProduto" method="post" >
                                        <button type="submit" name="codigoproduto" value="${produto.getId()}" 
                                                class="btn btn-success center-block">Alterar</button>
                                    </form>
                                 
                                 
                                    <form class="form-group alteracoes" action="${pageContext.request.contextPath}/excluirProduto" method="post" >         
                                        <button type="submit" name="codigoproduto" value="${produto.getId()}" 
                                        class="btn btn-danger center-block">Excluir</button>
                                    </form>
                                 </div>
                             </th>
                         </tr>
                     </table>
                     </div>
                     </c:forEach>
             </div>
             
         </div>
         
    </body>
</html>
