/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.goku.controller;

import br.com.goku.model.Grupo;
import br.com.goku.model.Usuario;
import br.com.goku.persistence.Dao;
import br.com.goku.util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Alan
 */
@Named
@ViewScoped
public class UsuarioController implements Serializable {

    private static final long serialVersionUID = -5377427892923684862L;

    private final Dao usuarioDao;
    private final Dao grupoDao;
    private Usuario usuario = new Usuario();
    private List<Usuario> lista;
    private List<Usuario> listaFiltro;
    private String destinoSalvar;
    private String[] grupos;
    private String senhaAtual, novaSenha, senhaConfirm;

    public UsuarioController() {
        this.usuarioDao = new Dao(Usuario.class);
        this.usuario = new Usuario();
        this.grupoDao = new Dao(Grupo.class);
    }

    public void salvarUsuario() {
        if (usuario.getCodigo() == null) {
            this.usuario.setAtivo(true);
            this.usuario.getPermissao().add("ROLE_USUARIO");
            usuario = (Usuario) usuarioDao.edit(usuario);
            this.usuario = new Usuario();
            this.lista = null;
            limpaUsuario();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso!", "Usuário cadastrado com sucesso!");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("usuario_listagem", facesMessage);
            RequestContext.getCurrentInstance().execute("PF('dlg1').hide()");
        } else {
            editarUsuario();
        }
    }

    public void editarUsuario() {
        usuarioDao.edit(usuario);
        limpaUsuario();
        this.lista = null;
        //JsfUtil.addSuccessMessage("Usuário editado com sucesso!");
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário editado com sucesso!", "Usuário editado com sucesso!");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("usuario_listagem", facesMessage);
        RequestContext.getCurrentInstance().execute("PF('dlg1').hide()");
    }

    /**
     * Verifica a igualdade da senha cadastrada com a senha digitada, em caso de
     * igualdade a nova senha do usuário é alterada.
     */
    public void trocarSenha() {
        if (senhaAtual.equals(usuario.getSenha())) {
            if (novaSenha.equals(senhaConfirm)) {
                usuario.setSenha(novaSenha);
                editarUsuario();
            } else {
                JsfUtil.addErrorMessage("A nova senha não é igual a senha de confirmação.");
            }
        } else {
            JsfUtil.addErrorMessage("A senha atual está incorreta!");
        }
    }

    /**
     * Atribui um novo objeto do tipo Usuario para a variável de referência
     * usuario.
     */
    public void limpaUsuario() {
        this.usuario = new Usuario();
    }

    /**
     * Recarrega a página usuario.html
     */
    public void cancela() {
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        handler.performNavigation("usuario.html?faces-redurect=true");
    }

    public String excluirUsuario() {
        usuarioDao.remove(this.usuario);
        this.usuario = new Usuario();
        this.lista = null;
        JsfUtil.addSuccessMessage("Usuário excluído com sucesso!");
        return null;
    }

    public String ativar() {
        if (this.usuario.isAtivo()) {
            this.usuario.setAtivo(false);
        } else {
            this.usuario.setAtivo(true);
        }
        usuarioDao.edit(usuario);
        return null;
    }

    public void atribuiPermissao() {
        java.util.Set<String> permissoes = this.usuario.getPermissao();
        if (permissoes.contains("ROLE_ADMINISTRADOR")) {
            permissoes.remove("ROLE_ADMINISTRADOR");
        } else {
            permissoes.add("ROLE_ADMINISTRADOR");
        }
        this.usuario.setPermissao(permissoes);
        usuarioDao.edit(this.usuario);
        this.usuario = new Usuario();
    }

    /*########################## GETTERS & SETTERS ############################*/
    public List<Usuario> getLista() {
        if (this.lista == null) {
            this.lista = usuarioDao.findAll();
        }
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public List<Usuario> getListaFiltro() {
        return listaFiltro;
    }

    public void setListaFiltro(List<Usuario> listaFiltro) {
        this.listaFiltro = listaFiltro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String[] getGrupos() {
        return grupos;
    }

    public void setGrupos(String[] grupos) {
        this.grupos = grupos;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getSenhaConfirm() {
        return senhaConfirm;
    }

    public void setSenhaConfirm(String senhaConfirm) {
        this.senhaConfirm = senhaConfirm;
    }

    public String getDestinoSalvar() {
        return destinoSalvar;
    }

    public void setDestinoSalvar(String destinoSalvar) {
        this.destinoSalvar = destinoSalvar;
    }
}
