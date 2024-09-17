package crud.visao;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import crud.modelo.DAO;
import crud.modelo.Item;

public class PainelLista extends JPanel{
	
	private DAO<Item> dao;
	private JLabel labelDoTotal;
	private JScrollPane scrollPane;

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public PainelLista(DAO<Item> dao, JLabel labelDoTotal) {
		this.dao = dao;
		this.labelDoTotal = labelDoTotal;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		atualizarLista();
	}

	public void atualizarLista() {
		removeAll();
		List<Item> lista = dao.getList();
		double acumulador = 0;
		for(Item p: lista) {
			add(new JLabel(p.toString()));
			add(new BotaoExcluir(p, this, dao));
			acumulador += p.getTotal();
		}
		labelDoTotal.setText("Total: " + BotaoRelatorio.formatarValor(acumulador));
		revalidate();
		repaint();
		
		SwingUtilities.invokeLater(() -> {
			if(scrollPane != null) scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());        
		});
	}
}
