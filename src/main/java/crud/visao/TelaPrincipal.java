package crud.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import crud.modelo.DAO;
import crud.modelo.Item;
import crud.visao.Botao.eventos;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {

	private JTextField textField;
	private PainelLista painelLista;
	private JLabel labelDoTotal;
	private DAO<Item> dao;
	private JScrollPane scrollPane;

	public TelaPrincipal() {
		String file = JOptionPane.showInputDialog(null, "Digite o nome do registro: ", "Nome do Registro",
				JOptionPane.PLAIN_MESSAGE);
		if (file == null || file == "")
			return;
		this.dao = new DAO<Item>(file);
		textField = new JTextField("");
		textField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		textField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String text = textField.getText();
					if (!Item.checkTextEntry(text)) {
						JOptionPane.showMessageDialog(scrollPane,
								"Formato:\n [NOME] [PREÇO UNITÁRIO] [QUANTIDADE] [DATA : DDMMAAAA]", "Entrada inválida",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					textField.setText("");
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
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		
		labelDoTotal = new JLabel();
		labelDoTotal.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		labelDoTotal.setHorizontalAlignment(SwingConstants.CENTER);
		painelLista = new PainelLista(dao, labelDoTotal);
		scrollPane = new JScrollPane(painelLista);
		painelLista.setScrollPane(scrollPane);

		BotaoRelatorio br = new BotaoRelatorio(dao);
		Botao b = new Botao(eventos.ADICIONAR, textField, dao, painelLista);
		Botao c = new Botao(eventos.REMOVER, textField, dao, painelLista);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 3));
		p.add(b);
		p.add(br);
		p.add(c);
		p.add(labelDoTotal);
		
		
		scrollPane.setPreferredSize(new Dimension(15, 600));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));

		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
		add(textField, BorderLayout.SOUTH);
		add(scrollPane, BorderLayout.CENTER);

		setTitle("Registro - " + file);
		setSize(new Dimension(1080, 720));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}

	public static void main(String[] args) {
		try{
			new TelaPrincipal();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
