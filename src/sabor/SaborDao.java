/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sabor;

import java.util.List;
import sabor.Sabor;

/**
 *
 * @author lipe1
 */
public interface SaborDao {
    public void inserirSabor(Sabor s);
    public void removerSabor(int id);
    public List<Sabor> buscarTodos(String busca);
}
