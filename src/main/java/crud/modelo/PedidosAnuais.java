package crud.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PedidosAnuais implements Comparable<PedidosAnuais>{
	private String ano;
	private List<PedidosMensais> lista = new ArrayList<PedidosMensais>();
	
	public void addPedidoMensal(PedidosMensais pm) {
		String ano = pm.getAno();
		try {
			if(!(ano.equals(this.ano))) return;			
			lista.add(pm);
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Formato da data inválido");
		}
	}
	
	public double getTotalAnual() {
		double acumulador = 0;
		for(PedidosMensais pm : lista) {
			acumulador += pm.getTotalMensal();
		}
		return acumulador;
	}
	
	public PedidosAnuais(String ano) {
		this.ano = ano;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public List<PedidosMensais> getMensais() {
		return lista;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ano);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidosAnuais other = (PedidosAnuais) obj;
		return Objects.equals(ano, other.ano);
	}

	@Override
	public int compareTo(PedidosAnuais o) {
		return Integer.compare(Integer.parseInt(ano), Integer.parseInt(o.ano));
	}
}
