package com.evoluum.cesar.dto;

public abstract class BaseDTO 
{	
	private int id;
	private String nome;
	
	public BaseDTO() 
	{
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}	
}
