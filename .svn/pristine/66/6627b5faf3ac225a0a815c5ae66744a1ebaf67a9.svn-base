/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.goku.util;

import javax.faces.context.*;
import javax.servlet.http.HttpSession;
import br.com.goku.controller.ContextoController;

/**
 *
 * @author Alan
 */
public class ContextoUtil {

    public static ContextoController getContextoController() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext external = context.getExternalContext();
        HttpSession session = (HttpSession) external.getSession(true);
        ContextoController contextoController = (ContextoController) session.getAttribute("contextoController");
        return contextoController;
    }

}
