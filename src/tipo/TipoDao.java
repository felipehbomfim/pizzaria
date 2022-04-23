package tipo;

/**
 *
 * @author lipe1
 */
public interface TipoDao {
    public float getPrecoSimples();
    public float getPrecoEspecial();
    public float getPrecoPremium();
    public void setPrecoSimples(Float f);
    public void setPrecoEspecial(Float f);
    public void setPrecoPremium(Float f);
}
