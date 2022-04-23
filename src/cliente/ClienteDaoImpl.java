/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lipe1
 */
public class ClienteDaoImpl implements ClienteDao {

    private Connection con;

    public ClienteDaoImpl(Connection con) {
        this.con = con;
    }

    public void inserirCliente(Cliente c) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO tb_cliente (nome_cliente, sobrenome_cliente, telefone_cliente, endereco_cliente) VALUES (?, ?, ?, ?)");
            st.setString(1, c.getNome());
            st.setString(2, c.getSobrenome());
            st.setString(3, c.getTelefone());
            st.setString(4, c.getEndereco());
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

    public void atualizarCliente(Cliente c) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE tb_cliente SET nome_cliente = ?, sobrenome_cliente = ?, telefone_cliente = ?, endereco_cliente = ?"
                    + " WHERE id_cliente = ?");
            st.setString(1, c.getNome());
            st.setString(2, c.getSobrenome());
            st.setString(3, c.getTelefone());
            st.setString(4, c.getEndereco());
            st.setString(5, Integer.toString(c.getId()));
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

    public void removerCliente(int id) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("DELETE FROM tb_cliente WHERE id_cliente = ?");
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

    public List<Cliente> buscarTodos(String busca) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Cliente> lista = new ArrayList<Cliente>();
        try {
            String sql = "SELECT tb_cliente.id_cliente, nome_cliente, sobrenome_cliente, telefone_cliente, endereco_cliente FROM tb_cliente"
                        + " LEFT JOIN tb_pedido ON (tb_pedido.id_cliente = tb_cliente.id_cliente)"
                        + " LEFT JOIN tb_idstatus ON (tb_idstatus.id = tb_pedido.id_status_pedido)";
            
            if(!busca.isEmpty()){
                sql = sql + " WHERE (nome_cliente LIKE '%"+busca+"%' OR sobrenome_cliente LIKE '%"+busca+"%'"
                            + " OR telefone_cliente LIKE '%"+busca+"%' OR endereco_cliente LIKE '%"+busca+"%')";
            }
            sql = sql + " ORDER BY tb_cliente.id_cliente DESC";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(Integer.parseInt(rs.getString("id_cliente")));
                c.setNome(rs.getString("nome_cliente"));
                c.setSobrenome(rs.getString("sobrenome_cliente"));
                c.setTelefone(rs.getString("telefone_cliente"));
                c.setEndereco(rs.getString("endereco_cliente"));
                lista.add(c);
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
}