package org.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.reproductor.Ventana;

public class TablaRecientes extends JTable{

	private final DefaultTableModel modelo;
	private final ArrayList<String> lista;
	private final Ventana ventana;
	
	public TablaRecientes(Ventana ventana){
		this.ventana = ventana;
		
		String[] columna = {"Archivos Reproducidos Recientes"};
		
		modelo = new DefaultTableModel(columna, 0);
		
		setModel(modelo);
		
		lista = new ArrayList<String>();
	}
	
	public void listar(){
		
		modelo.setNumRows(0);
		for(int i = lista.size()-1; i >=0; i--){
			lista.remove(i);
		}
		
		try {
			BufferedReader reciente = new BufferedReader (new FileReader("Reciente"));
			String frase = reciente.readLine();
			while(frase != null){
				
				lista.add(new File(frase).getName());
				frase = reciente.readLine();
			}
			reciente.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = lista.size()-1; i >=0; i--){
			String[] fila = {lista.get(i)};
			modelo.addRow(fila);
		}
	}
	
	public void reproducir(){
		
		ventana.reproduccion.reproducirDVD(lista.get(lista.size()-getSelectedRow()-1));
	}
}
