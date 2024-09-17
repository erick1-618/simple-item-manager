package crud.visao;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import crud.modelo.DAO;
import crud.modelo.Item;
import crud.modelo.PedidosAnuais;
import crud.modelo.PedidosMensais;

public class BotaoRelatorio extends JButton implements MouseListener {

	private DAO<Item> dao;

	public BotaoRelatorio(DAO<Item> dao) {
		this.setText("Gerar Relatório");
		this.dao = dao;
		addMouseListener(this);
	}

	public void gerarRelatorio() {
		String fileName = dao.getFILENAME() + ".pdf";
		List<PedidosAnuais> anual = getRelatorioAnual();
		Document doc = new Document();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Selecione o diretório para salvar seu relatório");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedDirectory = fileChooser.getSelectedFile();
			File file = new File(selectedDirectory, fileName);
			try {
				PdfWriter.getInstance(doc, new FileOutputStream(file));
				doc.open();
				com.itextpdf.text.Font f = new com.itextpdf.text.Font(FontFamily.HELVETICA, 18, Font.BOLD);
				Paragraph pg = new Paragraph("RELATÓRIO DE VALORES", f);
				pg.setAlignment(Paragraph.ALIGN_CENTER);
				Paragraph nome = new Paragraph(dao.getFILENAME(),
						new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 16, Font.ITALIC));
				nome.setAlignment(Paragraph.ALIGN_CENTER);
				doc.add(pg);
				doc.add(nome);
				doc.add(new Paragraph(" "));

				Paragraph geral = new Paragraph("TOTAL",
						new com.itextpdf.text.Font(FontFamily.HELVETICA, 16, Font.BOLD));
				geral.setAlignment(Paragraph.ALIGN_CENTER);
				doc.add(geral);

				double totGeral = 0;
				int totPedidos = 0;
				for (PedidosAnuais pa : anual) {
					totGeral += pa.getTotalAnual();
					for (PedidosMensais pm : pa.getMensais()) {
						totPedidos += pm.getPedidos().size();
					}
				}

				Paragraph totG = new Paragraph("Total Geral: " + formatarValor(totGeral),
						new com.itextpdf.text.Font(FontFamily.HELVETICA, 14, Font.BOLD));
				totG.setAlignment(Paragraph.ALIGN_CENTER);
				doc.add(totG);
				Paragraph totPed = new Paragraph("Total de Itens: " + totPedidos,
						new com.itextpdf.text.Font(FontFamily.HELVETICA, 14, Font.BOLD));
				totPed.setAlignment(Paragraph.ALIGN_CENTER);
				doc.add(totPed);
				Paragraph medA = new Paragraph("Média dos Anos: " + formatarValor((totGeral / anual.size())),
						new com.itextpdf.text.Font(FontFamily.HELVETICA, 14, Font.BOLD));
				medA.setAlignment(Paragraph.ALIGN_CENTER);
				doc.add(medA);
				doc.add(new Paragraph(" "));

				for (PedidosAnuais pa : anual) {
					int pedidosAnuais = 0;
					for (PedidosMensais pm : pa.getMensais()) {
						pedidosAnuais += pm.getPedidos().size();
					}
					Paragraph ano = new Paragraph("----------------------------------------------   " + pa.getAno() + "   ----------------------------------------------",
							new com.itextpdf.text.Font(FontFamily.HELVETICA, 15, Font.BOLD));
					ano.setAlignment(Paragraph.ALIGN_CENTER);
					doc.add(ano);

					Paragraph totA = new Paragraph("Total Anual: " + formatarValor(pa.getTotalAnual()));
					totA.setAlignment(Paragraph.ALIGN_CENTER);
					Paragraph totP = new Paragraph("Total de Itens: " + pedidosAnuais);
					totP.setAlignment(Paragraph.ALIGN_CENTER);
					Paragraph medM = new Paragraph("Média Anual: " + formatarValor((pa.getTotalAnual() / pa.getMensais().size())));
					medM.setAlignment(Paragraph.ALIGN_CENTER);

					doc.add(totA);
					doc.add(totP);
					doc.add(medM);
					doc.add(new Paragraph(" "));

					for (PedidosMensais pm : pa.getMensais()) {
						doc.add(new Paragraph(pm.getNomeMes(),
								new com.itextpdf.text.Font(FontFamily.HELVETICA, 14, Font.BOLD)));
						doc.add(new Paragraph("Itens: " + pm.getPedidos().size()));
						doc.add(new Paragraph("Total Mensal: " + formatarValor(pm.getTotalMensal())));
						doc.add(new Paragraph("Média desse mês: " + formatarValor((pm.getTotalMensal() / pm.getPedidos().size()))));
						doc.add(new Paragraph(" "));
						doc.add(new Paragraph("---  Itens do Mês  -----------------------",
								new com.itextpdf.text.Font(FontFamily.HELVETICA, 10, Font.BOLD)));
						for (Item p : pm.getPedidos()) {
							doc.add(new Paragraph(p.toString(),
									new com.itextpdf.text.Font(FontFamily.HELVETICA, 10, Font.PLAIN)));
						}
						doc.add(new Paragraph("------------------------------------------------",
								new com.itextpdf.text.Font(FontFamily.HELVETICA, 10, Font.BOLD)));
						doc.add(new Paragraph(" "));
					}
				}

				doc.close();
			} catch (DocumentException | IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório");
			}
		}
	}

	public List<PedidosAnuais> getRelatorioAnual() {
		List<Item> lista = dao.getList();
		List<PedidosMensais> listaM = new ArrayList<PedidosMensais>();
		List<PedidosAnuais> listaA = new ArrayList<PedidosAnuais>();
		for (Item p : lista) {
			String ano = p.getAno();
			String mes = p.getMes();
			PedidosMensais pm = new PedidosMensais(ano, mes);
			PedidosAnuais pa = new PedidosAnuais(ano);
			if (!(listaM.contains(pm)))
				listaM.add(pm);
			if (!(listaA.contains(pa)))
				listaA.add(pa);
		}
		for (PedidosAnuais pa : listaA) {
			for (PedidosMensais pm : listaM) {
				pa.addPedidoMensal(pm);
				for (Item p : lista) {
					if (!(pm.getPedidos().contains(p)))
						pm.addPedido(p);
				}
				Collections.sort(pm.getPedidos());
			}
			Collections.sort(pa.getMensais());
		}
		Collections.sort(listaA);

		return listaA;
	}
	
	public static String formatarValor(double valor) {
		Locale brasil = Locale.of("pt", "BR");
		NumberFormat formato = NumberFormat.getCurrencyInstance(brasil);
		formato.setMaximumFractionDigits(2);
		formato.setMinimumFractionDigits(2);
		String valorFormatado = formato.format(valor);
		return valorFormatado;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			gerarRelatorio();
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
