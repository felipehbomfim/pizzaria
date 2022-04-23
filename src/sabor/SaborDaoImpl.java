/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sabor;

import cliente.Cliente;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sabor.Sabor;

/**
 *
 * @author lipe1
 */
public class SaborDaoImpl implements SaborDao {

    private Connection con;

    public SaborDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public void inserirSabor(Sabor s) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("insert into tb_sabor(nome_sabor, tipo_sabor) values (?, ?)");
            st.setString(1, s.getNome());
            st.setString(2, Integer.toString(s.getTipo()));
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

    @Override
    public void removerSabor(int id) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("delete from tb_sabor where id_sabor = ?");
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

    @Override
    public List<Sabor> buscarTodos(String busca) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Sabor> lista = new ArrayList<Sabor>();
        try {
            String sql = "SELECT id_sabor, nome_sabor, tipo_sabor, tb_tipopizza.nome_tipo FROM tb_sabor "
                    + "INNER JOIN tb_tipopizza ON (tb_tipopizza.id_tipo = tb_sabor.tipo_sabor)";
            if(!busca.isEmpty()){
                sql = sql + " WHERE (nome_sabor LIKE '%"+busca+"%' OR tb_tipopizza.nome_tipo LIKE '%"+busca+"%')";
            }
            sql = sql + " ORDER BY id_sabor DESC";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                Sabor s = new Sabor();
                s.setId(Integer.parseInt(rs.getString("id_sabor")));
                s.setNome(rs.getString("nome_sabor"));
                s.setNomeTipo(rs.getString("nome_tipo"));
                s.setTipo(Integer.parseInt(rs.getString("tipo_sabor")));
                lista.add(s);
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
