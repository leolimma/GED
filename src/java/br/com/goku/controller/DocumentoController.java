/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.goku.controller;

import br.com.goku.model.Acesso;
import br.com.goku.util.CategoriaArvore;
import br.com.goku.model.Categoria;
import br.com.goku.model.Documento;
import br.com.goku.model.Grupo;
import br.com.goku.model.Tipologia; 
import br.com.goku.persistence.*;
import br.com.goku.util.FileUpload;
import br.com.goku.util.JsfUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Alan
 */
@Named(value = "documentoController")
@ViewScoped
public class DocumentoController implements Serializable {

    private static final long serialVersionUID = 7117316198889763545L;

    private InputStream arquivo;
    private Documento documento = new Documento();

    private Categoria categoriaSelecionada;
    private Tipologia tipologiaSelecionada;
    private TreeNode categoriasTree;

    private String nomeDocumento;
    private String tamanhoDocumento;
    private String formatoDocumento;

    QueryParameter paramCategoria;

    private final Dao documentoDAO;
    private final Dao categoriaDAO;
    private final Dao tipologiaDAO;

    private boolean habilitaBotao;

    private List<Documento> lista;
    private List<Documento> listaFiltro;

    private List<Acesso> permissoesUsuario;
    
    private String msgEnvDocCategoria;
    private boolean permissaoEnvDocCategoria;

    @Inject
    private ContextoController contexto;

    public DocumentoController() {
        this.habilitaBotao = true;
        this.documento = new Documento();
        this.documentoDAO = new Dao(Documento.class);
        this.categoriaDAO = new Dao(Categoria.class);
        this.tipologiaDAO = new Dao(Tipologia.class);
    }

    public TreeNode getCategoriasTree() {
        return CategoriaArvore.getCategoriasTree(categoriaDAO.findAll());
    }

    public void selecionar(NodeSelectEvent event) {
        this.lista = null;
        this.categoriaSelecionada = (Categoria) event.getTreeNode().getData();
        paramCategoria = QueryParameter.create("codigo", this.categoriaSelecionada.getCodigo());
        if(!this.verificarPermissao()){
            this.msgEnvDocCategoria = "Sem permissão para utilizar a categoria: " + this.categoriaSelecionada.getDescricao();
            this.permissaoEnvDocCategoria = false;
            return;
        }
        if(!this.verificarPermissaoEscrita()){
            this.msgEnvDocCategoria = "Sem permissão para utilizar a categoria: " + this.categoriaSelecionada.getDescricao();
            this.permissaoEnvDocCategoria = false;
            return;
        }
        
        //Caso tenha permissão de escrita
        this.documento.setCategoria(this.categoriaSelecionada);
        this.msgEnvDocCategoria = this.categoriaSelecionada.getDescricao();
        this.permissaoEnvDocCategoria = true;
        this.categoriasTree = null;
        this.lista = null;
    }

    public void setTipologia(ValueChangeEvent event) {
        String tipologia = event.getNewValue().toString();
        this.tipologiaSelecionada = (Tipologia) tipologiaDAO.findById(Integer.valueOf(tipologia));
        this.documento.setTipologia(tipologiaSelecionada);
    }

    public void upload(FileUploadEvent event) throws IOException {
        this.arquivo = event.getFile().getInputstream();
        this.nomeDocumento = (event.getFile().getFileName());
        this.tamanhoDocumento = (String.valueOf(event.getFile().getSize()));
        this.formatoDocumento = (event.getFile().getContentType());
        this.habilitaBotao = false;
    }

    public void salvar() {

        if (this.documento.getCategoria() == null) {
            JsfUtil.addErrorMessage("É necessário informar uma categoria");
            return;
        }

        if (this.documento.getTipologia() == null) {
            JsfUtil.addErrorMessage("É necessário informar uma tipologia");
            return;
        }

        if ("".equals(this.documento.getMetadadoTitulo())) {
            JsfUtil.addErrorMessage("É necessário informar um título");
            return;
        }

        this.documento.setNome(this.nomeDocumento);
        this.documento.setTamanho(this.tamanhoDocumento);
        this.documento.setFormato(this.formatoDocumento);
        this.documento.setUsuario(contexto.getUsuarioLogado());
        this.documento.setDataArquivo(new Date());

        this.documento = (Documento) documentoDAO.edit(this.documento);

        FileUpload fileUpload = new FileUpload();
        boolean salvou = fileUpload.salvarArquivo(documento.getCodigo().toString() + ".pdf", arquivo);
        if (salvou) {
            JsfUtil.addSuccessMessage("Arquivo '" + this.documento.getMetadadoTitulo() + "' salvo com sucesso!");
            this.documento = new Documento();
            this.categoriaSelecionada = new Categoria();
            this.tipologiaSelecionada = null;
            this.permissaoEnvDocCategoria = false;
            this.msgEnvDocCategoria = "";
            RequestContext.getCurrentInstance().execute("PF('docEnvSucesso').show()");
        } else {
            JsfUtil.addErrorMessage("Arquivo '" + this.documento.getMetadadoTitulo() + "' não foi salvo!");
        }

    }

    public void excluir() {
        FileUpload file = new FileUpload();
        if (file.excluirArquivo(this.documento.getCodigo() + ".pdf")) {
            documentoDAO.remove(this.documento);
        } else {
            JsfUtil.addErrorMessage("Não foi possível excluir documento");
        }
        this.lista = null;
    }

    public List<Documento> getLista() {
        if (this.lista == null) {
            if (this.categoriaSelecionada != null) {
                if (this.categoriaSelecionada.getCodigo() > 0) {
                    paramCategoria = QueryParameter.create("codigo", this.categoriaSelecionada.getCodigo());
                    //Verificar permissão do usuário de acordo com os grupos                    
                    if (!verificarPermissao()) {
                        return null;
                    } else {
                        this.lista = documentoDAO.executeResultList("Documento.findByCategoria", paramCategoria);
                    }
                }
            } else {
                //this.lista = documentoDAO.findAll();
            }
        }
        return lista;
    }

    public boolean verificarPermissao() {
        this.permissoesUsuario = new ArrayList<Acesso>();
        List<Acesso> acessos = documentoDAO.executeResultList("Acesso.findByCategoria", paramCategoria);
        if (acessos.size() > 0) {
            List<Grupo> grupos = contexto.getUsuarioLogado().getGrupos();
            if (grupos.size() > 0) {
                for (Acesso acesso : acessos) {
                    for (Grupo grupo : grupos) {
                        if (acesso.getGrupo().getCodigo() == grupo.getCodigo()) {
                            permissoesUsuario.add(acesso);
                        }
                    }
                }
            }
        }
        if (this.permissoesUsuario.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
 
    public boolean verificarPermissaoLeitura() {
        if (this.permissoesUsuario.isEmpty()) {
            return false;
        }
        for (Acesso acesso : permissoesUsuario) {
            if (acesso.isLeitura()) {
                return true;
            }
        }
        return false;
    }

    public boolean verificarPermissaoEscrita() {
        if (this.permissoesUsuario.isEmpty()) {
            return false;
        }
        for (Acesso acesso : permissoesUsuario) {
            if (acesso.isEscrita()) {
                return true;
            }
        }
        return false;
    }
    
    public void documentoEnviado(){
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        handler.performNavigation("/restrito/documento.xhtml?faces-redurect=truei=1");
    }

    public void setLista(List<Documento> lista) {
        this.lista = lista;
    }

    public List<Documento> getListaFiltro() {
        return listaFiltro;
    }

    public void setListaFiltro(List<Documento> listaFiltro) {
        this.listaFiltro = listaFiltro;
    }

    public List<Acesso> getPermissoesUsuario() {
        return permissoesUsuario;
    }

    public void setPermissoesUsuario(List<Acesso> permissoesUsuario) {
        this.permissoesUsuario = permissoesUsuario;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Categoria getCategoriaSelecionada() {
        return categoriaSelecionada;
    }

    public void setCategoriaSelecionada(Categoria categoria) {
        this.categoriaSelecionada = categoria;
    }

    public Tipologia getTipologiaSelecionada() {
        return tipologiaSelecionada;
    }

    public void setTipologiaSelecionada(Tipologia tipologiaSelecionada) {
        this.tipologiaSelecionada = tipologiaSelecionada;
    }

    public boolean isHabilitaBotao() {
        return habilitaBotao;
    }

    public void setHabilitaBotao(boolean habilitaBotao) {
        this.habilitaBotao = habilitaBotao;
    }

    public InputStream getArquivo() {
        return arquivo;
    }

    public void setArquivo(InputStream arquivo) {
        this.arquivo = arquivo;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    public String getTamanhoDocumento() {
        return tamanhoDocumento;
    }

    public void setTamanhoDocumento(String tamanhoDocumento) {
        this.tamanhoDocumento = tamanhoDocumento;
    }

    public String getFormatoDocumento() {
        return formatoDocumento;
    }

    public void setFormatoDocumento(String formatoDocumento) {
        this.formatoDocumento = formatoDocumento;
    }

    public String getMsgEnvDocCategoria() {
        return msgEnvDocCategoria;
    }

    public void setMsgEnvDocCategoria(String msgEnvDocCategoria) {
        this.msgEnvDocCategoria = msgEnvDocCategoria;
    }

    public boolean isPermissaoEnvDocCategoria() {
        return permissaoEnvDocCategoria;
    }

    public void setPermissaoEnvDocCategoria(boolean permissaoEnvDocCategoria) {
        this.permissaoEnvDocCategoria = permissaoEnvDocCategoria;
    }

}
