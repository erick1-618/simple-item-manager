package crud.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import crud.modelo.DAO;
import crud.modelo.Item;

public class BotaoExcluir extends JButton implements MouseListener{

	private Item pedido;
	private PainelLista painel;
	private DAO<Item> dao;
	public static final Color cor = new Color(248, 123, 85);

	public BotaoExcluir(Item p, PainelLista painel, DAO<Item> dao) {
		addMouseListener(this);
		setBackground(cor);
		setText("X");
		this.pedido = p;
		this.dao = dao;
		this.painel = painel;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			dao.excluirPedido(pedido);
			painel.atualizarLista();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
