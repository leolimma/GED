/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.goku.controller;

import br.com.goku.model.Usuario;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import br.com.goku.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Alan
 */
@Named(value = "contextoController")
@SessionScoped
public class ContextoController implements Serializable {
    private static final long serialVersionUID = -8422066900997874977L;

    private Usuario usuarioLogado = null;
    private Dao usuarioDAO;

    public Usuario getUsuarioLogado() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext external = context.getExternalContext();
        String login = external.getRemoteUser();
        if (this.usuarioLogado == null || !login.equals(this.usuarioLogado.getLogin())) {
            if (login != null) {
                usuarioDAO = new Dao(Usuario.class);
                QueryParameter parametro = new QueryParameter("login", login);
                this.usuarioLogado = (Usuario) usuarioDAO.executeSingleResult("Usuario.findByLogin", parametro);                
            }
        }
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }    

}
