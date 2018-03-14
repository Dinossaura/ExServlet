package com.senac.webprodutos.dao;

import com.senac.webprodutos.model.Categoria;
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
        
        String query2 = "insert into produto_categoria(id_produto, id_categoria) values(?, ?)";
        
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
            
            for(int i = 0; i < produto.getCategoria().size(); i++){
                ps.setInt(2, Integer.parseInt(produto.getCategoria().get(i)));
                ps.executeUpdate();
            }
            
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
         String query = "UPDATE produtos SET nome=?, descricao=?, preco_compra=?, preco_venda=?, quantidade=? WHERE id=?";
         String query2 = "UPDATE  produto_categoria SET id_categoria = ? WHERE id_produto = ? and id_categoria = ?";
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
            
            for(int i = 0; i < produto.getCategoria().size(); i++){
                ps.setInt(1, Integer.parseInt(produto.getCategoria().get(i)));//Código categoria
                ps.setInt(1, produto.getId());//Código Produto
                ps.setInt(1, Integer.parseInt(produto.getCategoria().get(i)));//Código categoria
                ps.executeUpdate();
            }
            
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setString(2, produto.getDesc());
            preparedStatement.setDouble(3, produto.getPrecoCompra());
            preparedStatement.setDouble(4, produto.getPrecoVenda());
            preparedStatement.setInt(5, produto.getQtde());
            preparedStatement.setInt(6, produto.getId());
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar produto");
            throw new Exception("Erro ao atualizar produto", ex);
        }

        return produto;
    }
    
    //lista produtos
    public List<Produto> listarProdutos(){ //retorna todos itens
        List<Produto> lista = new ArrayList<>();
        System.out.println("Buscando produto na base de dados...");
        String query = "";
        
        query = "SELECT * FROM produto";
      
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            ResultSet rs = preparedStatement.executeQuery();

            
            while (rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getInt(1));
                produto.setNome(rs.getString(2));
                produto.setDesc(rs.getString(3));
                produto.setPrecoCompra(rs.getDouble(4));
                produto.setPrecoVenda(rs.getDouble(5));
                produto.setQtde(rs.getInt(6));
                produto.setDataCadastro(rs.getString(7));

                lista.add(produto);
            }

            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto"+ex);
        }        
        return lista;
    
    }
    
    
    //encontra produto por nome
    public List<Produto> encontrarProduto(String nome){//retorna um item
        List<Produto> lista = new ArrayList<>();
        System.out.println("Buscando produto na base de dados...");
        String query = "";
        boolean vazio = false;
        
        if (nome.length() == 0){
           query = "SELECT * FROM produto";
        }else{
           query = "SELECT * FROM produto WHERE nome like ?";//addicionar o % % 
        }
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            if(nome.length()>0){
               preparedStatement.setString(1,'%' + nome + '%'); 
            }
                        
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getInt(1));
                produto.setNome(rs.getString(2));
                produto.setDesc(rs.getString(3));
                produto.setPrecoCompra(rs.getDouble(4));
                produto.setPrecoVenda(rs.getDouble(5));
                produto.setQtde(rs.getInt(6));
                produto.setDataCadastro(rs.getString(7));
                
                lista.add(produto);
            }
            
            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto"+ex);
        }        
        return lista;
    
    }
    
        //encontra produto por nome
    public Produto encontrarProdutoCodigo(int codigoProduto){//retorna um item
        Produto pretorno = new Produto();
        System.out.println("Buscando produto na base de dados...");
        String query = "";
       
        query = "SELECT * FROM produto WHERE id = ?";
       
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, codigoProduto); 
         
                        
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getInt(1));
                produto.setNome(rs.getString(2));
                produto.setDesc(rs.getString(3));
                produto.setPrecoCompra(rs.getDouble(4));
                produto.setPrecoVenda(rs.getDouble(5));
                produto.setQtde(rs.getInt(6));
                produto.setDataCadastro(rs.getString(7));
                
                pretorno = produto;
            }
            
            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto"+ex);
        }        
        return pretorno;
    
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
    
    public Categoria encontraCategoriasProd(int idproduto){
        ArrayList<Categoria> lista = new ArrayList();
        Categoria cat = new Categoria();
        
        String query = "SELECT * FROM produto_categoria where id_produto = ?";
        
         try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            

            preparedStatement.setInt(1, idproduto); 
            
                        
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()){
                cat.setId(rs.getInt(1));
                cat.setIdproduto(rs.getInt(2));
            }
            
            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto"+ex);
        }        
        return cat;
    }
    //
    public void deletarProduto(int codigo) throws Exception{
        System.out.println("Deletando produto de codigo: "+codigo);
        String query = "DELETE FROM produto WHERE id= ?";
        String query2 = "DELETE FROM produto_categoria WHERE id_produto = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            PreparedStatement ps = conn.prepareStatement(query2);
            
            ps.setInt(1, codigo);
            ps.execute();
            
            preparedStatement.setInt(1, codigo); 
            preparedStatement.execute();
            
            System.out.println("Produto deletado");
        } catch (SQLException ex) {
            throw new Exception("Erro ao deletar produto", ex);
        }
    }
}
