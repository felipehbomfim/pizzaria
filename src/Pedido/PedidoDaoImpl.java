/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pedido;

import cliente.Cliente;
import connectionFactory.ConnectionFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lipe1
 */
public class PedidoDaoImpl implements PedidoDao{
    
    private Connection con;

    public PedidoDaoImpl(Connection con) {
        this.con = con;
    }
    
    public List<Pedido> buscarTodosPedidos(String busca) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Pedido> lista = new ArrayList<Pedido>();
        try {
            String sql = "SELECT tb_pedido.id_pedido, nome_cliente, tb_idstatus.nome AS nome_status,"
                    + " (SELECT SUM(valor_pedido_item) FROM tb_pedido_pizza WHERE tb_pedido_pizza.id_pedido = tb_pedido.id_pedido) AS total_pedido,"
                    + " (SELECT COUNT(id) FROM tb_pedido_pizza WHERE tb_pedido_pizza.id_pedido = tb_pedido.id_pedido) AS qtd_itens"
                    + " FROM tb_pedido"
                        + " INNER JOIN tb_cliente ON (tb_pedido.id_cliente = tb_cliente.id_cliente)"
                        + " INNER JOIN tb_idstatus ON (tb_idstatus.id = tb_pedido.id_status_pedido)";
            
            if(!busca.isEmpty()){
                sql = sql + " WHERE (nome_cliente LIKE '%"+busca+"%' OR sobrenome_cliente LIKE '%"+busca+"%'"
                          + " OR telefone_cliente LIKE '%"+busca+"%' OR endereco_cliente LIKE '%"+busca+"%'"
                          + " OR tb_idstatus.nome LIKE '%"+busca+"%')";
            }
            sql = sql + " ORDER BY tb_cliente.id_cliente DESC";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id_pedido"));
                p.setNomeStatus(rs.getString("nome_status"));
                p.setNomeCliente(rs.getString("nome_cliente"));
                p.setQtdItens(rs.getInt("qtd_itens"));
                p.setValorTotal(rs.getDouble("total_pedido"));
                lista.add(p);
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (Exception ex) {
                }
            }
        }
    }
    
    public void UpdateStatus(int id, int idStatus) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE tb_pedido SET id_status_pedido = ? WHERE id_pedido = ?");
            st.setInt(1, idStatus);
            st.setInt(2, id);
            int rowsAffected = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception ex) {
                }
            }
        }
    }
            
    public void InserirPedido(Pedido p) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO tb_pedido (id_status_pedido, id_cliente) VALUES (?, ?) ON DUPLICATE KEY UPDATE id_cliente = ?");
            st.setInt(1, p.getStatus());
            st.setInt(2, p.getIdCliente());
            st.setInt(3, p.getIdCliente());
            int rowsAffected = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception ex) {
                }
            }
        }
    }
    
    public int GetIdPedidoByIdCliente(int idCliente) throws IOException, ClassNotFoundException, SQLException{
        
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection con2;
        con2 = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
        try{
           stm = con2.prepareStatement("SELECT id_pedido FROM tb_pedido WHERE id_cliente = ?");
           stm.setInt(1, idCliente);           

           rs = stm.executeQuery();
           rs.next();
           return rs.getInt("id_pedido");
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally{
            try{stm.close();}catch(Exception ex){
               throw new RuntimeException(ex);
            }
            try{con.close();}catch(Exception ex){
                throw new RuntimeException(ex);
            }
        }
    }
    
    public int GetIdClienteByIdPedido(int idPedido) throws IOException, ClassNotFoundException, SQLException{
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection con2;
        con2 = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
        try{
           stm = con2.prepareStatement("SELECT id_cliente FROM tb_pedido WHERE id_pedido = ?");
           stm.setInt(1, idPedido);           

           rs = stm.executeQuery();
           rs.next();
           return rs.getInt("id_cliente");
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally{
            try{stm.close();}catch(Exception ex){
               throw new RuntimeException(ex);
            }
            try{con.close();}catch(Exception ex){
                throw new RuntimeException(ex);
            }
        }
    }
}
