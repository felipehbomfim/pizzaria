/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pedido;

import java.util.List;

/**
 *
 * @author lipe1
 */
public interface PedidoDao {
    public void InserirPedido(Pedido p);
    public List<Pedido> buscarTodosPedidos(String busca);
}
