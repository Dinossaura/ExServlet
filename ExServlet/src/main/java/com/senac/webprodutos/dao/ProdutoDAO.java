package com.senac.webprodutos.dao;

import com.senac.webprodutos.model.Produto;
import com.senac.webprodutos.utils.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDAO {
        ConexaoBanco conexaoBanco = new ConexaoBanco();    
        Connection conn = conexaoBanco.createConnection();
    //insere produto
    public void inserirProduto(Produto produto){
        System.out.println("Iniciando processo de inserção de produto...");
        String query = "insert into produto (nome, descricao, preco_compra, preco_venda, quantidade, dt_cadastro) values" +
                        "(?, ?, ?, ?, ?, ?)";
        
        String query2 = "insert into categoria(id_produto, id_categoria) values(?, ?)";
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setString(2, produto.getDesc());
            preparedStatement.setDouble(3, produto.getPrecoCompra());
            preparedStatement.setDouble(4, produto.getPrecoVenda());
            preparedStatement.setInt(5, produto.getQtde());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(produto.getDataCadastro()));
            
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int ultimocodigo = 0;
            if(rs.next()){
               ultimocodigo = rs.getInt(1);
            }
            
            PreparedStatement ps = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ultimocodigo);
            ps.setInt(2, Integer.parseInt(produto.getCategoria()));
            
            ps.executeUpdate();
            
            ps.close();
            preparedStatement.close();
            System.out.println("Produto inserido com sucesso.");
            
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Erro ao salvar produto");
        }
    }
    //atualiza produto
    public Produto updateProduto(Produto produto) throws Exception{
        System.out.println("Atualizando produto...");
         String query = "UPDATE produtos SET codigoempresa=?, nome=?, descricao=?, codigofornecedor=?, codigocategoria=?, precocompra=?, precovenda=?, estoque=? WHERE codigo=?";
        
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(2, produto.getNome());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar produto");
            throw new Exception("Erro ao atualizar produto", ex);
        }

        return produto;
    }
    //atualiza estoque
    public void atualizarEstoque(int codigo, int estoque) throws Exception{
        System.out.println("Atualizando produto...");
         String query = "UPDATE produtos SET estoque=? WHERE codigo=?";
        
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
          
            preparedStatement.setInt(1, estoque);
            preparedStatement.setInt(2, codigo);
            System.out.println("Estoque:"+estoque);
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar produto");
            throw new Exception("Erro ao atualizar produto", ex);
        }
    }
    
    //lista produtos
    public List<Produto> listarProduto(String nome, int codigoempresa){ //retorna todos itens
        List<Produto> lista = new ArrayList<>();
        System.out.println("Buscando produto na base de dados...");
        String query = "";
        
        boolean vazio = true;
        
        if(nome.length() == 0){
            vazio = true;
            query = "SELECT * FROM produtos WHERE codigoempresa = ?";
        }else{
            vazio = false;
            query = "SELECT * FROM produtos WHERE nome LIKE ? and codigoempresa = ?";
        }
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            if(vazio != true){
                preparedStatement.setString(1, nome+"%");
                preparedStatement.setInt(2,codigoempresa);
            }else{
                preparedStatement.setInt(1,codigoempresa);
            }
            
            ResultSet rs = preparedStatement.executeQuery();

            
                while (rs.next()){
                    Produto produto = new Produto();
                    produto.setNome(rs.getString(3));

                    lista.add(produto);
                }

            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto"+ex);
        }        
        return lista;
    
    }
    //lista produtos
    public List<Produto> listarProdutos(int codigoempresa){ //retorna todos itens
        List<Produto> lista = new ArrayList<>();
        System.out.println("Buscando produto na base de dados...");
        String query = "";
        
        query = "SELECT * FROM produtos WHERE codigoempresa = ?";
      
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setInt(1,codigoempresa);
            
            ResultSet rs = preparedStatement.executeQuery();

            
            while (rs.next()){
                Produto produto = new Produto();
                produto.setNome(rs.getString(3));

                lista.add(produto);
            }

            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto"+ex);
        }        
        return lista;
    
    }
    
    //lista produtos
    public List<Produto> listarProdutostotais(){ //retorna todos itens
        List<Produto> lista = new ArrayList<>();
        System.out.println("Buscando produto na base de dados...");
        String query = "";
        
        query = "SELECT * FROM produtos";
      
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            ResultSet rs = preparedStatement.executeQuery();

            
            while (rs.next()){
                Produto produto = new Produto();
                produto.setNome(rs.getString(3));

                lista.add(produto);
            }

            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto"+ex);
        }        
        return lista;
    
    }
    
    
    //encontra produto por nome
    public Produto encontrarProduto(String nome, int codigoempresa){//retorna um item
        Produto produto = new Produto();
        System.out.println("Buscando produto na base de dados...");
        String query = "";
        boolean vazio = false;
        
        if(nome.length() == 0){
            vazio = true;
            query = "SELECT * FROM produtos WHERE codigoempresa=?";//addicionar o % %
        }else{
            query = "SELECT * FROM produtos WHERE nome=? and codigoempresa=?";//addicionar o % %
        }
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            if(vazio = false){
                preparedStatement.setString(1,nome);
                preparedStatement.setInt(2,codigoempresa);
            }else{
                preparedStatement.setInt(1,codigoempresa);
            }
            
                        
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()){
                produto.setNome(rs.getString(3));
            }
            
            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto"+ex);
        }        
        return produto;
    
    }
    
    //encontra produto por nome
    public Produto encontrarProdutoCodigo(int codigo, int codigoempresa){//retorna um item
        Produto produto = new Produto();
        System.out.println("Buscando produto na base de dados...");
        String query = "";
        query = "SELECT * FROM produtos WHERE codigo=? and codigoempresa=?";//addicionar o % %

        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setInt(1,codigo);
            preparedStatement.setInt(2,codigoempresa);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()){
                produto.setNome(rs.getString(3));

            }
            
            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto"+ex);
        }        
        return produto;
    
    }
    
    //encontra produto por nome
    public boolean encontrarProdutoCadastro(String nome){//retorna um item
        Produto produto = new Produto();
        System.out.println("Buscando produto na base de dados...");
        String query = "SELECT * FROM produto WHERE nome =?";//addicionar o % %
        boolean encontra = false;
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1,nome);
                        
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()){
                encontra = true;
            }
            
            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto"+ex);
        }        
        return encontra;
    
    }
    //
    public void deletarProduto(int codigo, int codigoempresa) throws Exception{
        System.out.println("Deletando produto de codigo: "+codigo);
        String query = "DELETE FROM produtos WHERE codigo=? and codigoempresa=?";
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            
            preparedStatement.setInt(1, codigo);
            preparedStatement.setInt(2, codigoempresa);   
            preparedStatement.execute();
            
            System.out.println("Produto deletado");
        } catch (SQLException ex) {
            throw new Exception("Erro ao deletar produto", ex);
        }
    }
}
