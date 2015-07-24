/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.goku.util;

import br.com.goku.model.Grupo;
import br.com.goku.persistence.Dao;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Alan
 */
@FacesConverter(forClass = Grupo.class, value = "grupoConverter")
public class GrupoConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value){
        if(value != null && value.trim().length() > 0){
            Integer codigo = Integer.valueOf(value);
            try{
                Dao grupoDAO = new Dao(Grupo.class);
                return grupoDAO.findById(codigo);
            }catch(Exception e){
                throw new ConverterException("Não foi possível encontrar o grupo de código" + value + ". " + e.getMessage());
            }
        }
        return null;
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value){
        if(value != null){
            Grupo grupo = (Grupo) value;
            return grupo.getCodigo().toString();
        }
        return "";
    }
    
}
