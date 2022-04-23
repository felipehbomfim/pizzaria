package sabor;

/**
 *
 * @author lipe1
 */
public class Sabor {
    private int id;
    private String nome;
    private String nome_tipo;
    private int tipo;

    public String getNomeTipo() {
        return nome_tipo;
    }

    public void setNomeTipo(String nome_tipo) {
        this.nome_tipo = nome_tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
