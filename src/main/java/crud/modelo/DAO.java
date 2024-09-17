package crud.modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DAO<T> {

	private final String FILENAME;
	
	public DAO(String fileName) {
		this.FILENAME = fileName;
	}
	
	public String getFILENAME() {
		return FILENAME;
	}

	public List<T> getList(){
		FileInputStream fis;
		ObjectInputStream ois;
		try {
			fis = new FileInputStream(FILENAME + ".ser");
			ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			List<T> lista = (List<T>) ois.readObject();
			fis.close();
			ois.close();
			return lista;
		} catch (FileNotFoundException e) {
			return new ArrayList<T>();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Problemas para pegar lista de pedidos");
			return null;
		}
	}
	
	public void addItem(T item) {
		List<T> lista = getList();
		lista.add(item);
		salvarAlteracoes(lista);
	}
	
	public void excluirPedido(T item) {
		List<T> lista = getList();
		lista.remove(item);
		salvarAlteracoes(lista);
	}
	
	public void limparList() {
		List<T> lista = getList();
		lista.clear();
		salvarAlteracoes(lista);
	}

	public void salvarAlteracoes(List<T> l) {
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(FILENAME + ".ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(l);
			fos.close();
			oos.close();
		} catch (IOException e) {
			System.out.println("Problemas para persistir alterações"); 
		}
	}
}
