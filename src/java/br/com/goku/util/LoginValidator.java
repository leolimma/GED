/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.goku.util;

import br.com.goku.controller.ContextoController;
import br.com.goku.controller.UsuarioController;
import br.com.goku.model.Usuario;
import br.com.goku.persistence.Dao;
import br.com.goku.persistence.QueryParameter;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ronaldo
 */
@Named
@RequestScoped
public class LoginValidator implements Validator {

    private final Dao usuarioDao = new Dao(Usuario.class);
    @Inject
    private UsuarioController usuarioController;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String login = (String) o;
        Usuario u = (Usuario) usuarioDao.executeSingleResult("Usuario.findByLogin", new QueryParameter("login", login));
        if (u != null) {
            if (!Objects.equals(u.getCodigo(), usuarioController.getUsuario().getCodigo())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este login já existe!", null);
                throw new ValidatorException(message);
            }
        }
    }

}
