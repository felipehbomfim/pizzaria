/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizza;

/**
 *
 * @author lipe1
 */
public abstract class FormaPizza {
    protected Double area;
    protected Double medida;
    private String nome;

    public abstract Double calcularArea(Double medida);
    public abstract Double calcularMedida(Double area);
    public Double getArea() {
        return area;
    }
    
    public void setArea(Double area) {
        
        if(area >= 100.00 && area <= 1670.00){
            this.area = area;
        }
        else if(area < 100.00){
            this.area = 100.00;
        }
        else{
            this.area = 1670.00;
        }
            
        this.medida = calcularMedida(this.area);
    }
    
    public Double getMedida() {
        return medida;
    }
     
    public abstract void setMedida(Double medida);
    
    public String getNome() {
        return nome;
    }
    public abstract int getFormaId();
}
