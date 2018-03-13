/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.webprodutos.validador;
import com.senac.webprodutos.exceptions.ProdutoException;
import com.senac.webprodutos.model.Produto;
/**
 *
 * @author Magno
 */
public class ValidadorProduto {
    //Realização de validação de negócio
    public static void validar(Produto produto) throws ProdutoException{
        
        if(produto == null){
            throw new ProdutoException("Não foi informado um produto");
        }
        
         if(produto.getNome()== null || "".equals(produto.getNome())){
            throw new ProdutoException("Não foi informado um nome");
        }
         
         if(produto.getDesc()== null || "".equals(produto.getDesc())){
            throw new ProdutoException("Não foi informada uma descrição");
        }
         
          if(produto.getCategoria() == null || "".equals(produto.getCategoria())){
            throw new ProdutoException("Não foi informada uma categoria");
        }
           
           try {
            if(String.valueOf(produto.getQtde())==null)
                  throw new ProdutoException("Não foi informado o valor de quantidade");
        }  catch (NumberFormatException e) {
            throw new ProdutoException("É necessário digitar somente "
                    + "números para alimentar o quantidade" + e);
        }
            
            
               try {
                if(String.valueOf(produto.getPrecoCompra())==null)
                    throw new ProdutoException("Não foi informado o preço de compra do produto");
        }  catch (NumberFormatException e) {
            throw new ProdutoException("É necessário digitar somente "
                    + "números para preço" + e);
        }
               
               try {
                if(String.valueOf(produto.getPrecoVenda())==null)
                    throw new ProdutoException("Não foi informado o preço de venda do produto");
        }  catch (NumberFormatException e) {
            throw new ProdutoException("É necessário digitar somente "
                    + "números para preço" + e);
        }
            
    }
}
