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
public class PizzaTriangular extends FormaPizza{
    private final String nome = "Triangular";

    @Override
    public Double calcularArea(Double medida) {
        return ((medida*medida) * Math.sqrt(3))/4 ;
    }

    @Override
    public Double calcularMedida(Double area) {
        return Math.sqrt((4*area)/Math.sqrt(3));
    }    
    @Override
    public int getFormaId(){
    	return 3;
    }
    
    @Override
    public void setMedida(Double medida){
        
        if(medida >= 20.00 && medida <= 60.00){
            this.medida = medida;
        }else if(medida < 20.00){
            this.medida  = 20.00;
        }
        else{
            this.medida = 60.00;
        }
        
        this.area = calcularArea(medida);
    }
}
