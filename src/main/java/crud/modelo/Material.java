package crud.modelo;

import java.io.Serializable;

public class Material implements Serializable{
	private static final long serialVersionUID = 4015644738215880244L;
	private String nome;
	private double preco;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public Material(String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
	}
	
}
