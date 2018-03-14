/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.webprodutos.service;

import com.senac.webprodutos.dao.ProdutoDAO;
import com.senac.webprodutos.exceptions.DataSourceException;
import com.senac.webprodutos.exceptions.ProdutoException;
import com.senac.webprodutos.model.Produto;
import com.senac.webprodutos.validador.ValidadorProduto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magno
 */
public class ServicoProduto {
    
    ProdutoDAO produtoDAO = new ProdutoDAO();
    
    //Serviço entra em contato com ProdutoDAO para cadastro de novo Produto
    public void cadastrarProduto(Produto produto) throws ProdutoException, DataSourceException {
        
        ValidadorProduto.validar(produto);

        try {
            //Realiza a chamada de inserção na fonte de dados
            produtoDAO.inserirProduto(produto);
        } catch (Exception e) {
            //Imprime qualquer erro técnico no console e devolve
            //uma exceção e uma mensagem amigável a camada de visão
            e.printStackTrace();
            throw new DataSourceException("Erro na fonte de dados", e);
        }
    }
    
    //Atualiza um produto na fonte de dados
    public void atualizarProduto(Produto produto) throws ProdutoException, DataSourceException {
        ValidadorProduto.validar(produto);

        try {
            produtoDAO.updateProduto(produto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataSourceException("Erro na fonte de dados", e);
        }
    }
    
    //Serviço entra em contato com ProdutoDAO e retorna todos produtos cadastrados
    public List<Produto> listarprodutos() throws ProdutoException,DataSourceException{
        List<Produto> lista = new ArrayList<>();
        
        try {
           return lista = produtoDAO.listarProdutos();
        } catch (Exception e) {
            //Imprime qualquer erro técnico no console e devolve
            //uma exceção e uma mensagem amigável a camada de visão
            e.printStackTrace();
            throw new DataSourceException("Erro na fonte de dados", e);
        }
      
    }
    
    //Serviço entra em contato com ProdutoDAO e retorna produto ou produtos pesquisados por nome em uma lista
    public List<Produto> consultaProdutosNome(String nomeProduto) throws ProdutoException, DataSourceException {
        try {
            //Realiza a chamada de inserção na fonte de dados
            return produtoDAO.encontrarProduto(nomeProduto);
        } catch (Exception e) {
            //Imprime qualquer erro técnico no console e devolve
            //uma exceção e uma mensagem amigável a camada de visão
            e.printStackTrace();
            throw new DataSourceException("Erro na fonte de dados", e);
        }
    }
    
    public boolean encontrarProdutoCadastro(String nomeProduto) throws ProdutoException, DataSourceException {
        try {
            //Realiza a chamada de inserção na fonte de dados
            return produtoDAO.encontrarProdutoCadastro(nomeProduto);
        } catch (Exception e) {
            //Imprime qualquer erro técnico no console e devolve
            //uma exceção e uma mensagem amigável a camada de visão
            e.printStackTrace();
            throw new DataSourceException("Erro na fonte de dados", e);
        }
    }
    
        public Produto encontrarProdutoCodigo(int codigoproduto) throws ProdutoException, DataSourceException {
        try {
            //Realiza a chamada de inserção na fonte de dados
            return produtoDAO.encontrarProdutoCodigo(codigoproduto);
        } catch (Exception e) {
            //Imprime qualquer erro técnico no console e devolve
            //uma exceção e uma mensagem amigável a camada de visão
            e.printStackTrace();
            throw new DataSourceException("Erro na fonte de dados", e);
        }
    }
    
        public void excluirProduto(Integer codigo) throws ProdutoException, DataSourceException {
        try {
            produtoDAO.deletarProduto(codigo);
        } catch (Exception e) {
            throw new DataSourceException("Erro na fonte de dados", e);
        }
    }
    
}
