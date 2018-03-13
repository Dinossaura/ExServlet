/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.webprodutos.servlets;

import com.senac.webprodutos.dao.ProdutoDAO;
import com.senac.webprodutos.model.Produto;
import com.senac.webprodutos.service.ServicoProduto;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CadastrarProdutoServlet", urlPatterns = {"/cadastrar-produto"})
public class CadastrarProdutoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

          RequestDispatcher dispatcher
	    = request.getRequestDispatcher("/cadastrar.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession sessao = request.getSession();
                
        String nomeProduto = request.getParameter("produto").toLowerCase();
        String descProduto = request.getParameter("descricao").toLowerCase();
        String cat = request.getParameter("categ");
        String precocompra = request.getParameter("precocompra");
        precocompra = precocompra.replace(".", "");
        precocompra = precocompra.replace(",", ".");
        String precovenda = request.getParameter("precovenda");
        precovenda = precovenda.replace(".", "");
        precovenda =precovenda.replace(",", ".");
       String quantidade = request.getParameter("quantidade");
       
        //Verifica campos obrigatórios
            if((nomeProduto.length() == 0)||(descProduto.length() == 0)||
                    (cat.length() == 0)||(precocompra.length() == 0)||
                    (precovenda.length() == 0)){
                sessao.setAttribute("mensagemErroCampos", "Verifique campos obrigatórios!");
                RequestDispatcher dispatcher
                = request.getRequestDispatcher("/cadastrar.jsp");
                dispatcher.forward(request, response);
            }else{
                 ServicoProduto sp = new ServicoProduto();
                 boolean proexiste = false;
                 try {
                proexiste = sp.encontrarProdutoCadastro(nomeProduto);
                } catch (Exception e) {
                    
                }
                 if(!proexiste){
                     sessao.setAttribute("mensagemErroCampos", "");
                     
                     Produto p = new Produto();
                     p.setNome(nomeProduto);
                     p.setDesc(descProduto);
                     p.setCategoria(cat);
                     p.setQtde(Integer.parseInt(quantidade));
                     p.setPrecoCompra(Double.parseDouble(precocompra));
                     p.setPrecoVenda(Double.parseDouble(precovenda));
                     
                    Timestamp dataDeHoje = new Timestamp(System.currentTimeMillis());
                    
                    p.setDataCadastro(String.valueOf(dataDeHoje));
                     
                     //Cadastra novo produto na tabela
                    try {
                        sp.cadastrarProduto(p);
                        sessao.setAttribute("Produto", p);
                        sessao.setAttribute("produtoexiste", "");
                        response.sendRedirect(request.getContextPath() + "/cadastrar.jsp");
                        System.out.println("Produto Inserido com sucesso!");
            
                    } catch (Exception e) {
                        request.setAttribute("mensagemErro", "Produto não cadastrado");
                        sessao.setAttribute("produtoexiste", "");
                        RequestDispatcher dispatcher
                        = request.getRequestDispatcher("/cadastrar.jsp");
                        dispatcher.forward(request, response);
                        System.out.println("Erro na inserção de novo produto!");
                    }   
                }else{
                     sessao.setAttribute("produtoexiste", "Produto já existe!");
                     request.setAttribute("produtoexiste", "Produto já existe!");
                     RequestDispatcher dispatcher
                     = request.getRequestDispatcher("/cadastrar.jsp");
                     dispatcher.forward(request, response);
                    System.out.println("Erro na inserção de novo Produto!");
                 }
            }
                

    }
}
