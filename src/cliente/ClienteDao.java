/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.util.List;

/**
 *
 * @author lipe1
 */
public interface ClienteDao {
    public void inserirCliente(Cliente c);
    public void atualizarCliente(Cliente c);
    public void removerCliente(int id);
    public List<Cliente> buscarTodos(String busca);
}
