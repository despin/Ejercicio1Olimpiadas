package backend;

import java.util.ArrayList;

public class Practica {

	private ArrayList<Insumo> insumos;
	private int id;
	private String nombre;
	
	public Practica(int id, String nombre, ArrayList<Insumo> insumos) {
		this.insumos = insumos;
		this.id = id;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Insumo> getInsumos() {
		return insumos;
	}

	public void setInsumos(ArrayList<Insumo> insumos) {
		this.insumos = insumos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
