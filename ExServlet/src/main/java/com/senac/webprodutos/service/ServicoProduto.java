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

/**
 *
 * @author magno
 */
public class ServicoProduto {
    
    ProdutoDAO produtoDAO = new ProdutoDAO();
    
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
    
}
