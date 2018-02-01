//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2018.01.14 às 08:36:48 PM BRST 
//


package br.senai.sp.informatica.albunsmusicais.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "banda",
    "album",
    "genero",
    "lancamento",
    "del",
    "capa"
})
@XmlRootElement(name = "album")
@Entity
public class Album {
    @XmlElement(required = true)
    @Id
    @Column(name="idAlbum")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Long id;
    @XmlElement(required = true)
    protected String banda;
    @XmlElement(required = true, name = "album")
    protected String nome;
    @XmlElement(required = true)
    protected String genero;
    @XmlElement(required = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lancamento;
    @XmlElement(required = true)
    private boolean del;
    @Lob
    @Column(length=100000)
    protected byte[] capa;
    
    /**
     * Obtém o valor da propriedade id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Obtém o valor da propriedade banda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBanda() {
        return banda;
    }

    /**
     * Define o valor da propriedade banda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBanda(String value) {
        this.banda = value;
    }

    /**
     * Obtém o valor da propriedade nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o valor da propriedade nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Obtém o valor da propriedade genero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Define o valor da propriedade genero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenero(String value) {
        this.genero = value;
    }

    /**
     * Obtém o valor da propriedade lancamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getLancamento() {
        return lancamento;
    }

    /**
     * Define o valor da propriedade lancamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLancamento(Date value) {
        this.lancamento = value;
    }

    /**
     * Obtém o valor da propriedade capa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getCapa() {
        return capa;
    }

    /**
     * Define o valor da propriedade capa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapa(byte[] value) {
        this.capa = value;
    }

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	@Override
	public String toString() {
		return "id: " + id + "  banda: " + banda + "  nome: " + nome + "  genero: " + genero + "  lancamento: "
				+ lancamento + "  del: " + del;
	}

}
