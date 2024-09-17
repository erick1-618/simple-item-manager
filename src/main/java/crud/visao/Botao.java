package crud.visao;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import crud.modelo.DAO;
import crud.modelo.Item;

public class Botao extends JButton implements MouseListener {

	public static enum eventos {
		ADICIONAR, REMOVER
	}

	public eventos evento;

	private JTextField tf;
	private DAO<Item> dao;
	private PainelLista painelLista;

	public Botao(eventos evento, JTextField tf, DAO<Item> dao, PainelLista painelLista) {
		addMouseListener(this);
		if(evento == eventos.ADICIONAR) setText("Adicionar Item");
		else setText("Remover todos");
		this.painelLista = painelLista;
		this.dao = dao;
		this.evento = evento;
		this.tf = tf;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			switch (evento) {
			case ADICIONAR:
				String text = tf.getText();
				if (!Item.checkTextEntry(text)) {
					JOptionPane.showMessageDialog(painelLista,
							"Formato:\n [NOME] [PREÇO UNITÁRIO] [QUANTIDADE] [DATA : DDMMAAAA]", "Entrada inválida",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				tf.setText("");
				String[] array = text.split(" ");
				String nome = "";
				for (int i = 0; i < array.length - 3; i++) {
					nome += array[i] + " ";
				}
				String data = array[array.length - 1].substring(0, 2) + "/" + array[array.length - 1].substring(2, 4)
						+ "/" + array[array.length - 1].substring(4);
				Item p = new Item(nome, Double.parseDouble(array[array.length - 3]),
						Integer.parseInt(array[array.length - 2]), data);
				dao.addItem(p);
				painelLista.atualizarLista();
				break;
			case REMOVER:
				int confirmacao = JOptionPane.showConfirmDialog(painelLista, "Deseja excluir todos os itens desse registro?", "Confirmação de exclusão", JOptionPane.OK_CANCEL_OPTION);
				if(confirmacao == JOptionPane.OK_OPTION) {
					dao.limparList();
					painelLista.atualizarLista();					
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
