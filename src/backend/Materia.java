package backend;

import java.util.ArrayList;

public class Materia {

	private ArrayList<Practica> practicas;
	private int id;
	private String nombre;
	
	public Materia(int id, String nombre, ArrayList<Practica> practicas) {
		this.id = id;
		this.practicas = practicas;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Practica> getPracticas() {
		return practicas;
	}

	public void setPracticas(ArrayList<Practica> practicas) {
		this.practicas = practicas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
