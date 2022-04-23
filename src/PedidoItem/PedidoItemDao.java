/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PedidoItem;

import java.util.List;

/**
 *
 * @author lipe1
 */
public interface PedidoItemDao {
    public void InserirPedidoItem(PedidoItem pi);
    public List<PedidoItem> buscarTodosPedidosItens(String busca, int idCliente);
    public void AtualizarPedidoItem(PedidoItem pi);

}
