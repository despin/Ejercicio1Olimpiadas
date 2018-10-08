package frontend;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import backend.HTTP;
import backend.Insumo;
import backend.SQL;

public class Consulta extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;

	public Consulta(JFrame marco) {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnGuardar = new JButton("Guardar");
		panel.add(btnGuardar);
		

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Nombre", "Cantidad" });

		JSONArray array = new HTTP().getInsumos();
		Iterator<JSONObject> iterator = array.iterator();

		ArrayList<Insumo> insumos = new ArrayList<Insumo>();

		while (iterator.hasNext()) {
			JSONObject insumo = iterator.next();
			insumos.add(
				new Insumo(
					((Long) insumo.get("id")).intValue(),
					new SQL().GetNombreInsumo(((Long) insumo.get("id")).intValue()),
					((Long) insumo.get("consumo")).intValue()
					)
				);
		}

		for(Insumo i : insumos){
			model.addRow(new Object[] {i.getId(), i.getNombre(), i.getCantidad()});
		}

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SQL().SetInsumos(insumos);
				
				marco.setContentPane(new Lista(marco));
				marco.validate();
			}
		});

		table = new JTable(model);
		scrollPane.setViewportView(table);

	}
}
