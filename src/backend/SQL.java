package backend;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SQL {

	private String ip;

	private Connection conexion;
	private PreparedStatement prepared;
	private ResultSet resultSetMateria;
	private ResultSet resultSetPractica;
	private ResultSet resultSetInsumos;
	private static String driver = null;
	private static String host = null;
	private static String port = null;
	private static String db = null;
	private static String user = null;
	private static String passwd = null;

	public SQL() {
		this.ip = "localhost";
	}

	public ArrayList<Materia> GetData() {

		try {
			conexion = getConnection();

			prepared = conexion.prepareStatement("Select * from Materia");

			resultSetMateria = prepared.executeQuery();

			ArrayList<Materia> res = new ArrayList<Materia>();
			while (resultSetMateria.next()) {
				prepared = conexion.prepareStatement("Select * from Practica where Practica.id_Practica_Materia = ?");
				prepared.setInt(1, resultSetMateria.getInt("id_Materia"));

				resultSetPractica = prepared.executeQuery();

				ArrayList<Practica> practicas = new ArrayList<Practica>();
				while (resultSetPractica.next()) {
					prepared = conexion.prepareStatement("Select * from Insumo where Insumo.id_Insumo_Practica = ?");
					prepared.setInt(1, resultSetPractica.getInt("id_Practica"));

					resultSetInsumos = prepared.executeQuery();

					ArrayList<Insumo> insumos = new ArrayList<Insumo>();
					while (resultSetInsumos.next()) {
						Insumo nuevoInsumo = new Insumo(resultSetInsumos.getInt("id_Insumo"),
								resultSetInsumos.getString("nombre_Insumo"),
								resultSetInsumos.getInt("cantidad_Insumo"));
						insumos.add(nuevoInsumo);
					}

					Practica nuevaPractica = new Practica(resultSetPractica.getInt("id_Practica"),
							resultSetPractica.getString("nombre_Practica"), insumos);
					practicas.add(nuevaPractica);
				}

				Materia nuevaMateria = new Materia(resultSetMateria.getInt("id_Materia"),
						resultSetMateria.getString("nombre_Materia"), practicas);
				res.add(nuevaMateria);
			}

			Close(conexion, prepared, new ResultSet[] { resultSetMateria, resultSetPractica, resultSetInsumos });

			return res;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void SetInsumos(ArrayList<Insumo> insumos) {
		try {
			conexion = getConnection();

			for (Insumo i : insumos) {
				prepared = conexion.prepareStatement("Update Insumo set cantidad_Insumo = ? where id_Insumo = ?");
				prepared.setInt(1, i.getCantidad());
				prepared.setInt(2, i.getId());
				prepared.execute();
			}

			Close(conexion, prepared);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String GetNombreInsumo(int id_Insumo) {
		String res = null;

		try {
			conexion = getConnection();

			prepared = conexion.prepareStatement("Select nombre_Insumo from Insumo where id_Insumo = ?");
			prepared.setInt(1, id_Insumo);

			ResultSet resultSet = prepared.executeQuery();
			resultSet.next();

			res = resultSet.getString("nombre_Insumo");

			Close(conexion, prepared);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	private Connection getConnection() {
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(new FileReader("properties_sql.json"));
		

            JSONObject jsonObject = (JSONObject) obj;
            driver = (String) jsonObject.get("driver");
            host = (String) jsonObject.get("host");
            port = (String) jsonObject.get("port");
            db = (String) jsonObject.get("db");
            user = (String) jsonObject.get("user");
			passwd = (String) jsonObject.get("passwd");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			return DriverManager.getConnection(
					driver + "://" + host + ":"+ port + "/" + db + "?useSSL=false&serverTimezone=UTC", user,
					passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void Close(Connection connect, PreparedStatement statement, ResultSet[] resultSet) {
		try {
			for (ResultSet rs : resultSet)
				rs.close();

			if (statement != null)
				statement.close();

			if (connect != null)
				connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Close(Connection connect, PreparedStatement statement) {
		try {
			if (statement != null)
				statement.close();

			if (connect != null)
				connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
