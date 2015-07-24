/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.goku.util;

/**
 *
 * @author Alan
 */

import br.com.goku.model.Categoria;
import java.util.List;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class CategoriaArvore {
    
     public static TreeNode getCategoriasTree(List<Categoria> categorias) {
        TreeNode categoriasTree;
        categoriasTree = new DefaultTreeNode(null, null);
        montaDadosTree(categoriasTree, categorias);
        return categoriasTree;
    }

    private static void montaDadosTree(TreeNode pai, List<Categoria> lista) {
        if (lista != null && lista.size() > 0) {
            TreeNode filho = null;
            for (Categoria categoria : lista) {
                filho = new DefaultTreeNode(categoria, pai);
                montaDadosTree(filho, categoria.getFilhos());
            }
        }
    }
    
}
