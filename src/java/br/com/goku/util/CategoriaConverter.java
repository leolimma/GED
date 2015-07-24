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

import javax.faces.convert.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import br.com.goku.model.Categoria;
import br.com.goku.persistence.Dao;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value){
        if(value != null && value.trim().length() > 0){
            Integer codigo = Integer.valueOf(value);
            try{
                Dao categoriaDAO = new Dao(Categoria.class);
                return categoriaDAO.findById(codigo);
            }catch(Exception e){
                throw new ConverterException("Não foi possível encontrar a categoria de código" + value + ". " + e.getMessage());
            }
        }
        return null;
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value){
        if(value != null){
            Categoria categoria = (Categoria) value;
            return categoria.getCodigo().toString();
        }
        return "";
    }
    
}
