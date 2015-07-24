/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.goku.controller;

import br.com.goku.model.Acesso;
import br.com.goku.persistence.Dao;
import br.com.goku.util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Alan
 */

@Named(value = "acessoController")
@ViewScoped
public class AcessoController implements Serializable{
    
    private final Dao acessoDAO;
    private Acesso acesso; 
    private List<Acesso> acessos;
    private List<Acesso> acessosFiltro;
    
    public AcessoController(){
        this.acessoDAO = new Dao(Acesso.class);
        this.acesso = new Acesso();
    }
    
    public void salvarAcesso(){
        acessoDAO.edit(acesso);
        acesso = new Acesso();
        acesso.setCategoria(null);
        acesso.setGrupo(null);
        acessos = null;
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Permissão cadastrada com sucesso!", "Usuário cadastrado com sucesso!");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("frmLstAcesso", facesMessage);
        RequestContext.getCurrentInstance().execute("PF('dlgNovoAcesso').hide()");
    }
    
    public void excluirAcesso(){
        acesso = (Acesso) acessoDAO.edit(acesso);
        acessoDAO.remove(acesso);
        acessos = null;
        JsfUtil.addSuccessMessage("Permissão removida com sucesso!");
    }

    public List<Acesso> getAcessos() {
        if(acessos == null){
            acessos = acessoDAO.findAll();
        }
        return acessos;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    public List<Acesso> getAcessosFiltro() {
        return acessosFiltro;
    }

    public void setAcessosFiltro(List<Acesso> acessosFiltro) {
        this.acessosFiltro = acessosFiltro;
    }
    
    
    public void limparAcesso(){
        this.acesso = new Acesso();
        this.acesso.setCategoria(null);
        this.acesso.setGrupo(null);
    }
}