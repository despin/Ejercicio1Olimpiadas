package frontend;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import backend.Insumo;
import backend.Materia;
import backend.Practica;
import backend.SQL;

public class Lista extends JPanel {

	private static final long serialVersionUID = 1L;

	public Lista(JFrame marco) {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnConsultar = new JButton("Consultar");
		panel.add(btnConsultar);
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				marco.setContentPane(new Consulta(marco));
				marco.validate();
			}
		});

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

		ArrayList<Materia> materias = new SQL().GetData();

		for (Materia m : materias) {
			DefaultMutableTreeNode matNode = new DefaultMutableTreeNode(m.getNombre());
			for (Practica p : m.getPracticas()) {
				Insumo insumoMax = p.getInsumos().stream().max(Comparator.comparing(Insumo::getCantidad)).get();
				Insumo insumoMin = p.getInsumos().stream().min(Comparator.comparing(Insumo::getCantidad)).get();

				DefaultMutableTreeNode practNode = new DefaultMutableTreeNode(p.getNombre() + " (Promedio: "
						+ (int) Math.ceil((insumoMin.getCantidad() + insumoMax.getCantidad()) / 2f) + ")");
				for (Insumo i : p.getInsumos()) {
					if (i.getId() == insumoMin.getId())
						practNode.add(new DefaultMutableTreeNode(
								(Object) (i.getNombre()) + " - " + i.getCantidad() + " (Min)"));
					else if (i.getId() == insumoMax.getId())
						practNode.add(new DefaultMutableTreeNode(
								(Object) (i.getNombre()) + " - " + i.getCantidad() + " (Max)"));
					else
						practNode.add(new DefaultMutableTreeNode((Object) (i.getNombre()) + " - " + i.getCantidad()));
				}
				matNode.add(practNode);
			}
			root.add(matNode);
		}

		JTree tree = new JTree(root);
		add(tree, BorderLayout.CENTER);

		tree.setRootVisible(false);

		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}

	}

	public DefaultMutableTreeNode GenerarTree() {
		return null;
	}
}
