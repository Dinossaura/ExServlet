
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.webprodutos.servlets;

import com.senac.webprodutos.dao.ProdutoDAO;
import com.senac.webprodutos.model.Categoria;
import com.senac.webprodutos.model.Produto;
import com.senac.webprodutos.service.ServicoProduto;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author magno
 */
@WebServlet(name = "ConsultaProdutoServlet", urlPatterns = {"/consultaprodutos"})
public class ConsultaProdutoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          HttpSession sessao = request.getSession();
          ArrayList<Produto> Lista = new ArrayList();
          ServicoProduto sp = new ServicoProduto();
          
          try {
            Lista = (ArrayList<Produto>) sp.listarprodutos();
        } catch (Exception e) {
        }
                 
          sessao.setAttribute("ListaProdutos", Lista);
          
          RequestDispatcher dispatcher
	    = request.getRequestDispatcher("/listar.jsp");
    dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Produto c = new Produto();
        
        //Instância de ArrayList para acumular fornecedores
        ArrayList<Produto> Lista = new ArrayList();
        ArrayList<Categoria> Listacategoria = new ArrayList();
        
        //Instância serviço de servidor para efetuar consulta e ligação com FornecedorDAO
        ServicoProduto sp = new ServicoProduto();
        
        //Criação se sessão para retorno em tela
        HttpSession sessao = request.getSession();
        
        //Atribuição de valores digitados na tela de fornecedor e código da empresa
        String produto = request.getParameter("produto").toLowerCase();
        
        try {
            Lista = (ArrayList<Produto>) sp.consultaProdutosNome(produto);
        } catch (Exception e) {
        }
        
        ProdutoDAO produtodao = new ProdutoDAO();
     
        try {
            for(int i = 0; i < Lista.size(); i++){
            Listacategoria.add(produtodao.encontraCategoriasProd(Lista.get(i).getId()));
            }
        } catch (Exception e) {
        }
        
        sessao.setAttribute("ListaProdutoCategoria", Listacategoria);
        sessao.setAttribute("ListaProdutos", Lista);
        response.sendRedirect(request.getContextPath() + "/listar.jsp");   
        
    }

}
