/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.goku.model;

/**
 *
 * @author Alan
 */

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name="documento")
@NamedQueries({
    @NamedQuery(name="Documento.findAll", query="select d from Documento d"),
    @NamedQuery(name="Documento.findByCategoria", query="select d from Documento d where d.categoria.codigo = :codigo")
})
public class Documento implements Serializable {
    private static final long serialVersionUID = -7470163782290664232L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="codigo", nullable=false, unique=true)
    private Integer codigo;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @ManyToOne
    @JoinColumn(name="usuario", nullable = false, updatable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="tipologia", nullable = false)
    private Tipologia tipologia;
    
    @ManyToOne
    @JoinColumn(name="categoria", nullable = false)
    private Categoria categoria;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data_arquivo", nullable = false, updatable = false)
    private Date dataArquivo;
    
    @Column(name = "formato", nullable = false)
    private String formato;
    
    @Column(name = "tamanho", nullable = false)
    private String tamanho;
    
    @Column(name = "metadado_titulo", nullable = false)
    private String metadadoTitulo;
    
    @Column(name = "metadado_data")
    private String metadadoData;
    
    @Column(name = "metadado_autor")
    private String metadadoAutor;
    
    @Column(name = "metadado_palabra_chave")
    private String metadadoPalavraChave;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Date getDataArquivo() {
        return dataArquivo;
    }

    public void setDataArquivo(Date dataArquivo) {
        this.dataArquivo = dataArquivo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getMetadadoTitulo() {
        return metadadoTitulo;
    }

    public void setMetadadoTitulo(String metadadoTitulo) {
        this.metadadoTitulo = metadadoTitulo;
    }

    public String getMetadadoData() {
        return metadadoData;
    }

    public void setMetadadoData(String metadadoData) {
        this.metadadoData = metadadoData;
    }

    public String getMetadadoAutor() {
        return metadadoAutor;
    }

    public void setMetadadoAutor(String metadadoAutor) {
        this.metadadoAutor = metadadoAutor;
    }

    public String getMetadadoPalavraChave() {
        return metadadoPalavraChave;
    }

    public void setMetadadoPalavraChave(String metadadoPalavraChave) {
        this.metadadoPalavraChave = metadadoPalavraChave;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.codigo);
        hash = 89 * hash + Objects.hashCode(this.nome);
        hash = 89 * hash + Objects.hashCode(this.usuario);
        hash = 89 * hash + Objects.hashCode(this.tipologia);
        hash = 89 * hash + Objects.hashCode(this.categoria);
        hash = 89 * hash + Objects.hashCode(this.dataArquivo);
        hash = 89 * hash + Objects.hashCode(this.formato);
        hash = 89 * hash + Objects.hashCode(this.tamanho);
        hash = 89 * hash + Objects.hashCode(this.metadadoTitulo);
        hash = 89 * hash + Objects.hashCode(this.metadadoData);
        hash = 89 * hash + Objects.hashCode(this.metadadoAutor);
        hash = 89 * hash + Objects.hashCode(this.metadadoPalavraChave);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Documento other = (Documento) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.tipologia, other.tipologia)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.dataArquivo, other.dataArquivo)) {
            return false;
        }
        if (!Objects.equals(this.formato, other.formato)) {
            return false;
        }
        if (!Objects.equals(this.tamanho, other.tamanho)) {
            return false;
        }
        if (!Objects.equals(this.metadadoTitulo, other.metadadoTitulo)) {
            return false;
        }
        if (!Objects.equals(this.metadadoData, other.metadadoData)) {
            return false;
        }
        if (!Objects.equals(this.metadadoAutor, other.metadadoAutor)) {
            return false;
        }
        if (!Objects.equals(this.metadadoPalavraChave, other.metadadoPalavraChave)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Documento{" + "codigo=" + codigo + ", nome=" + nome + ", usuario=" + usuario + ", tipologia=" + tipologia + ", categoria=" + categoria + ", dataArquivo=" + dataArquivo + ", formato=" + formato + ", tamanho=" + tamanho + ", metadadoTitulo=" + metadadoTitulo + ", metadadoData=" + metadadoData + ", metadadoAutor=" + metadadoAutor + ", metadadoPalavraChave=" + metadadoPalavraChave + '}';
    }

}
