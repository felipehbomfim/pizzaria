/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PedidoItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import sabor.Sabor;

/**
 *
 * @author lipe1
 */
public class PedidoItemDaoImpl implements PedidoItemDao{
    private Connection con;

    public PedidoItemDaoImpl(Connection con) {
        this.con = con;
    }
    
    public List<PedidoItem> buscarTodosPedidosItens(String busca, int idCliente) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<PedidoItem> lista = new ArrayList<PedidoItem>();
        try {
            String sql = "SELECT tb_pedido_pizza.id, sabor1.nome_sabor AS nome_sabor1, sabor2.nome_sabor AS nome_sabor2, "
                        + " nome_cliente, valor_pedido_item, tb_forma_pizza.nome AS forma_pizza, tipoPizza1.nome_tipo AS nome_tipo1, "
                        + " tipoPizza2.nome_tipo as nome_tipo2"
                        + " FROM tb_pedido_pizza"
                        + " INNER JOIN tb_sabor AS sabor1 ON (sabor1.id_sabor = tb_pedido_pizza.id_sabor)"
                        + " INNER JOIN tb_tipopizza AS tipoPizza1 ON (tipoPizza1.id_tipo = sabor1.tipo_sabor)"
                        + " INNER JOIN tb_sabor AS sabor2 ON (sabor2.id_sabor = tb_pedido_pizza.id_sabor2)"
                        + " INNER JOIN tb_tipopizza AS tipoPizza2 ON (tipoPizza2.id_tipo = sabor2.tipo_sabor)"
                        + " INNER JOIN tb_pedido ON (tb_pedido.id_pedido = tb_pedido_pizza.id_pedido)"
                        + " INNER JOIN tb_cliente ON (tb_pedido.id_cliente = tb_cliente.id_cliente)"
                        + " INNER JOIN tb_forma_pizza ON (tb_forma_pizza.id = tb_pedido_pizza.id_forma)"
                        + " WHERE tb_cliente.id_cliente = "+idCliente+"";
            
            if(!busca.isEmpty()){
                sql = sql + " AND (nome_cliente LIKE '%"+busca+"%' OR sobrenome_cliente LIKE '%"+busca+"%'"
                          + " OR telefone_cliente LIKE '%"+busca+"%' OR endereco_cliente LIKE '%"+busca+"%'"
                          + " OR sabor2.nome_sabor LIKE '%"+busca+"%' OR endereco_cliente LIKE '%"+busca+"%'"
                          + " OR sabor1.nome_sabor LIKE '%"+busca+"%' OR tipoPizza1.nome_tipo LIKE '%"+busca+"%'"
                          + " OR tipoPizza2.nome_tipo LIKE '%"+busca+"%' OR tb_forma_pizza.nome LIKE '%"+busca+"%')";
            }
            sql = sql + " ORDER BY tb_cliente.id_cliente DESC";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                PedidoItem pi = new PedidoItem();
                Sabor s = new Sabor();
                Sabor s2 = new Sabor();
                s.setNome(rs.getString("nome_sabor1"));
                s.setNomeTipo(rs.getString("nome_tipo1"));
                s2.setNome(rs.getString("nome_sabor2"));
                s2.setNomeTipo(rs.getString("nome_tipo2"));
                pi.setId(rs.getInt("id"));
                pi.setSabor1(s);
                pi.setSabor2(s2);
                pi.setFormaNome(rs.getString("forma_pizza"));
                pi.setValor(rs.getDouble("valor_pedido_item"));
                lista.add(pi);
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
   
    public void AtualizarPedidoItem(PedidoItem pi) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE tb_pedido_pizza SET id_pedido = ?, id_sabor = ?, id_sabor2 = ?, valor_pedido_item = ?, id_forma = ?"
                    + " WHERE id = ?");
            st.setInt(1, pi.getIdPedido());
            st.setInt(2, pi.getSabor1().getId());
            st.setInt(3, pi.getSabor2().getId());
            st.setDouble(4, pi.getValor());
            st.setInt(5, pi.getFormaId());
            st.setInt(6, pi.getId());
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
      
    public void removerPedidoItem(int id) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("DELETE FROM tb_pedido_pizza WHERE id = ?");
            st.setString(1, Integer.toString(id));
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
     
    public void InserirPedidoItem(PedidoItem pi) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO tb_pedido_pizza (id_pedido, id_sabor, id_sabor2, valor_pedido_item, id_forma) VALUES (?, ?, ?, ?, ?)");
            st.setInt(1, pi.getIdPedido());
            st.setInt(2, pi.getSabor1().getId());
            st.setInt(3, pi.getSabor2().getId());
            st.setDouble(4, pi.getValor());
            st.setDouble(5, pi.getFormaId());
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
}
