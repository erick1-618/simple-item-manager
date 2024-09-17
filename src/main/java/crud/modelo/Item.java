package crud.modelo;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import crud.visao.BotaoRelatorio;

public class Item implements Serializable, Comparable<Item> {
	private static final long serialVersionUID = 1L;
	private Material material;
	private int quantidade;
	private double total;
	private String data;
	private String id;

	public Item(String nome, double preco, int q, String data) {
		this.material = new Material(nome, preco);
		this.quantidade = q;
		this.total = material.getPreco() * quantidade;
		this.data = data;
		this.id = UUID.randomUUID().toString();
	}
	
	public String getAno() {
		return data.split("/")[2];
	}
	
	public String getMes() {
		return data.split("/")[1];
	}

	public Material getMaterial() {
		return material;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public String toString() {
		return this.material.getNome().toUpperCase() + " | Preço unitário: " + BotaoRelatorio.formatarValor(this.material.getPreco()) + " | QUANTIDADE: " + this.quantidade + " | TOTAL: " + BotaoRelatorio.formatarValor(this.total) + " | DATA: " + this.data;
	}
	
	public static boolean checkTextEntry(String s) {
		String[] array = s.split(" ");
		if(array.length < 4) return false;
		try {
			Double.parseDouble(array[array.length - 3]);
			Integer.parseInt(array[array.length - 2]);
			String testData = array[array.length - 1];
			if(testData.length() != 8) return false;
			int dia = Integer.parseInt(testData.substring(0, 2));
			int mes = Integer.parseInt(testData.substring(2, 4));
			if(dia > 31 || dia < 1) return false;
			if(mes > 12 || mes < 1) return false;
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
    @Override
    public int hashCode() {
        return Objects.hash(material.getNome(), material.getPreco(), quantidade, id);
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(data, other.data) && Objects.equals(id, other.id)
				&& Objects.equals(material.getNome(), other.material.getNome()) && quantidade == other.quantidade
				&& Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total);
	}

	@Override
	public int compareTo(Item o) {
		int esseDia = Integer.parseInt(data.split("/")[0]);
		int outroDia = Integer.parseInt(o.data.split("/")[0]);
		return Integer.compare(esseDia, outroDia);
	}
}
