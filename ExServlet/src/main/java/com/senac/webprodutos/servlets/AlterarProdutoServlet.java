/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.webprodutos.servlets;


import com.senac.webprodutos.model.Categoria;
import com.senac.webprodutos.model.Produto;
import com.senac.webprodutos.service.ServicoProduto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "AlterarProdutoServlet", urlPatterns = {"/alterar-produto"})
public class AlterarProdutoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          RequestDispatcher dispatcher
	    = request.getRequestDispatcher("/listar.jsp");
    dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Instância Objeto Cliente
        Produto produto = new Produto();
        ArrayList<String> categoria = new ArrayList();
        
        //Instância serviço de servidor para efetuar consulta e ligação com ClienteDAO
        ServicoProduto sp = new ServicoProduto();
        
        //Criação se sessão para retorno em tela
        request.setCharacterEncoding("UTF-8");
        HttpSession sessao = request.getSession();
        
        //Para verificação se é alteração
        String alteracao = "";
        try {
            alteracao = sessao.getAttribute("Altera").toString();
        } catch (Exception e) {
        }
        
        if ((alteracao == null)||(alteracao.length() == 0)){
              //Atribuição de valores digitados na tela de fornecedor e código da empresa
            String codigoproduto = request.getParameter("codigoproduto");
            
            
            try {
            produto = sp.encontrarProdutoCodigo(Integer.parseInt(codigoproduto));
            } catch (Exception e) {
            }
        
            sessao.setAttribute("pro", produto);
            sessao.setAttribute("Altera", "alteracao");
            response.sendRedirect(request.getContextPath() + "/cadastrar.jsp");
            
        }else{
            Produto p = new Produto();
            p = (Produto) sessao.getAttribute("pro");
            request.setCharacterEncoding("UTF-8");
            String nomeProduto = request.getParameter("produto").toLowerCase();
            String descProduto = request.getParameter("descricao").toLowerCase();
            String precocompra = request.getParameter("precocompra");
            precocompra = precocompra.replace(".", "");
            precocompra = precocompra.replace(",", ".");
            String precovenda = request.getParameter("precovenda");
            precovenda = precovenda.replace(".", "");
            precovenda =precovenda.replace(",", ".");
            String quantidade = request.getParameter("quantidade");
        
            //Verifica campos obrigatórios
            if((nomeProduto.length() == 0)||(descProduto.length() == 0)||
               (precocompra.length() == 0)||(precovenda.length() == 0)){
                sessao.setAttribute("mensagemErroCampos", "Verifique campos obrigatórios!");
                RequestDispatcher dispatcher
                = request.getRequestDispatcher("/cadastrar.jsp");
                dispatcher.forward(request, response);
            }else{
                sessao.setAttribute("mensagemErroCampos", "");
                
                try{
                    p.setNome(nomeProduto);
                    p.setDesc(descProduto);
                    p.setQtde(Integer.parseInt(quantidade));
                    p.setPrecoCompra(Double.parseDouble(precocompra));
                    p.setPrecoVenda(Double.parseDouble(precovenda));
                
                sp.atualizarProduto(p);
                } catch (Exception e) {
                }
                response.sendRedirect(request.getContextPath() + "/listar.jsp");
                sessao.removeAttribute("Altera");
            }
            
        }
           
        
    }

}
