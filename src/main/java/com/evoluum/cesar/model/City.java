package com.evoluum.cesar.model;

/**
 * The city Resource contains info about city
 * 
 * @author cesar
 *
 */
public class City 
{

	private Integer idEstado;
	private String siglaEstado;
	private String regiaoNome;
	private String nomeCidade;
	private String nomeMesorregiao;
	private String nomeFormatado;

	public City(Integer idEstado, String siglaEstado, String regiaoNome, String nomecity, String nomeMesorregiao, String nomeFormatado) 
	{
		this.idEstado = idEstado;
		this.siglaEstado = siglaEstado;
		this.regiaoNome = regiaoNome;
		this.nomeCidade = nomecity;
		this.nomeMesorregiao = nomeMesorregiao;
		this.nomeFormatado = nomeFormatado;
	}

	public Integer getIdEstado() 
	{
		return idEstado;
	}

	public String getSiglaEstado() 
	{
		return siglaEstado;
	}

	public String getRegiaoNome() 
	{
		return regiaoNome;
	}

	public String getNomecity() 
	{
		return nomeCidade;
	}

	public String getNomeMesorregiao() 
	{
		return nomeMesorregiao;
	}

	public String getNomeFormatado() 
	{
		return nomeFormatado;
	}

	@Override
	public String toString() 
	{
		return "city [idEstado=" + idEstado + ", siglaEstado=" + siglaEstado + ", regiaoNome=" + regiaoNome
				+ ", nomecity=" + nomeCidade + ", nomeMesorregiao=" + nomeMesorregiao + ", nomeFormatado="
				+ nomeFormatado + "]";
	}

}
