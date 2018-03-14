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
                        <button type="submit" class="btn btn-success center-block" action="">Pesquisar</button>
                     </form>
                 </div>
             </div>
             <div class="row">
                 <div class="col-12 col-sm-3">
                     <c:forEach items="${ListaProdutos}" var="produto">
                     <table class="table table-selectable table-bordered table-hover">
                         
                         <tr>
                             <th>Produto</th>
                         </tr>
                         <tr>
                             <th><c:out value="${produto.getNome()}"  /></th>
                         </tr>
                     </table>
                     </c:forEach>
                     
                 </div>
             </div>
         </div>
         
    </body>
</html>
