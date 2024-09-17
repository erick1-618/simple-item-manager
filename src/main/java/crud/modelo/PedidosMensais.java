package crud.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PedidosMensais implements Comparable<PedidosMensais>{
	
	private String ano;
	private String mes;
	private List<Item> pedidos = new ArrayList<>();
	private String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto"
			, "Setembro", "Outubro", "Novembro", "Dezembro"
	};
	
	public PedidosMensais(String ano, String mes) {
		this.ano = ano;
		this.mes = mes;
	}
	
	public List<Item> getPedidos() {
		return pedidos;
	}

	public void addPedido(Item p) {
		String[] data = p.getData().split("/");
		try {
			if(!(data[1].equals(mes)) || !(data[2].equals(ano))) return;			
			pedidos.add(p);
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Formato da data inválido");
		}
	}
	
	public double getTotalMensal() {
		double acumulador = 0;
		for(Item p : pedidos) {
			acumulador += p.getTotal();
		}
		return acumulador;
	}
	
	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getMes() {
		return mes;
	}
	
	public String getNomeMes() {
		return meses[Integer.parseInt(mes) - 1];
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ano, mes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidosMensais other = (PedidosMensais) obj;
		return Objects.equals(ano, other.ano) && Objects.equals(mes, other.mes);
	}

	@Override
	public int compareTo(PedidosMensais o) {
		return Integer.compare(Integer.parseInt(mes), Integer.parseInt(o.mes));
	}
}
