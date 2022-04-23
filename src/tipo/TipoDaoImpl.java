package tipo;

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
public class TipoDaoImpl implements TipoDao {

    private Connection con;

    public TipoDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public float getPrecoSimples() {
        return getDefault("Simples");
    }

    @Override
    public float getPrecoEspecial() {
        return getDefault("Especial");
    }

    @Override
    public float getPrecoPremium() {
        return getDefault("Premium");
    }

    @Override
    public void setPrecoSimples(Float f) {
        setDefault(f, "Simples");
    }

    @Override
    public void setPrecoEspecial(Float f) {
        setDefault(f, "Especial");
    }

    @Override
    public void setPrecoPremium(Float f) {
        setDefault(f, "Premium");
    }

    public float getDefault(String s) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement("SELECT preco_tipo FROM tb_tipopizza WHERE nome_tipo = ?");
            st.setString(1, s);
            rs = st.executeQuery();
            while (rs.next()) {
                return rs.getFloat("preco_tipo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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
        return 0;
    }

    public void setDefault(Float f, String s) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE tb_tipopizza SET preco_tipo = ? WHERE nome_tipo = ?");
            st.setFloat(1, f);
            st.setString(2, s);
            st.executeUpdate();
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
    
    public double findValorByTipo(int tipo) throws IOException, ClassNotFoundException, SQLException{
        
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection con2;
        con2 = ConnectionFactory.getConnection("jdbc:mysql://localhost:3307/db_pizza", "root", "", ConnectionFactory.MYSQL);
        try{
           stm = con2.prepareStatement("SELECT preco_tipo FROM tb_tipopizza WHERE id_tipo = ?");
           stm.setInt(1, tipo);           

           rs = stm.executeQuery();
           rs.next();
           return rs.getDouble("preco_tipo");
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
