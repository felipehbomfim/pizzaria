/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PedidoItem;

import Pedido.Pedido;
import pizza.FormaPizza;
import sabor.Sabor;

/**
 *
 * @author lipe1
 */
public class PedidoItem {
    private int id;
    private int idPedido;
    private FormaPizza forma;
    private Double valor;
    private Sabor sabor1,sabor2;
    private int formaId;
    private String formaNome;

    public String getFormaNome() {
        return formaNome;
    }

    public void setFormaNome(String formaNome) {
        this.formaNome = formaNome;
    }
    
    public int getFormaId() {
        return formaId;
    }

    public void setFormaId(int formaId) {
        this.formaId = formaId;
    }
    
    public Sabor getSabor1() {
        return sabor1;
    }

    public void setSabor1(Sabor sabor1) {
        this.sabor1 = sabor1;
    }

    public Sabor getSabor2() {
        return sabor2;
    }

    public void setSabor2(Sabor sabor2) {
        this.sabor2 = sabor2;
    }
    
    private Double area;

    private Double medida;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public FormaPizza getForma() {
        return forma;
    }

    public void setForma(FormaPizza forma) {
        this.forma = forma;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getMedida() {
        return medida;
    }

    public void setMedida(Double medida) {
        this.medida = medida;
    }
    
    public Double calcularValor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
