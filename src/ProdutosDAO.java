
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
     String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

    try {
        conn = new conectaDAO().connectDB(); // conecta ao banco
        prep = conn.prepareStatement(sql);

        // Define os valores da consulta
        prep.setString(1, produto.getNome());
        prep.setInt(2, produto.getValor());
        prep.setString(3, produto.getStatus());
        
        prep.executeUpdate();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
    } finally {
        try {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
        }
    }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
       String sql = "SELECT * FROM produtos";
        listagem = new ArrayList<>();
        
        
        try {
        conn = new conectaDAO().connectDB();
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));

            listagem.add(produto);
        }
        }catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        }
    
    return listagem;
    }    
    
        public ArrayList<ProdutosDTO> listarVendas(){
       String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        listagem = new ArrayList<>();
        
        
        try {
        conn = new conectaDAO().connectDB();
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));

            listagem.add(produto);
        }
        }catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar vendas: " + e.getMessage());
        }
    
    return listagem;
    }

    public void venderProduto(int id) {
         String selectSql = "SELECT status FROM produtos WHERE id = ?";
         String updateSql = "UPDATE produtos SET status = ? WHERE id = ?";

    try {
        conn = new conectaDAO().connectDB();
        prep = conn.prepareStatement(selectSql);
        prep.setInt(1, id);
        resultset = prep.executeQuery();
        
        if (resultset.next()) {
            String statusAtual = resultset.getString("status");

            if ("Vendido".equalsIgnoreCase(statusAtual)) {
                JOptionPane.showMessageDialog(null, "Este produto já foi vendido.");
                return; // Cancela a venda
            }
        }
        
          // Atualiza status para "Vendido"
        prep = conn.prepareStatement(updateSql);
        prep.setString(1, "Vendido");
        prep.setInt(2, id);
        prep.executeUpdate();
        JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    }
    finally {
        try {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
        }
    }
} 
}

