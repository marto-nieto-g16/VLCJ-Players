package org.beans;

import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import javax.swing.table.DefaultTableModel;
import org.util.Filtro;

public class TablaReproduccion extends JTable{

	private final DefaultTableModel modelo;
	private ArrayList<String> lista;
	
	private final JButton btEliminar;
	private final JButton btReproducir;
	
	public TablaReproduccion(JButton eliminar, JButton reproducir){
            String[] columna = {"Lista de Reproduccion"};
            modelo = new DefaultTableModel(columna, 0);
            this.setModel(modelo);
            
            this.lista = new ArrayList<>();
            this.btReproducir = reproducir;
            this.btEliminar = eliminar;
	}
	
	public void listar(){
            modelo.setNumRows(0);
            for(int i = 0; i < lista.size(); i++){
                String[] fila = {new File(lista.get(i)).getName()};
		modelo.addRow(fila);
            }
	}
	
	public void anadir(){
            JFileChooser fileChooser = new JFileChooser(new File("Videos"));
            fileChooser.setFileView(new FileView(){
                @Override
                public Icon getIcon(File f){
                    return FileSystemView.getFileSystemView().getSystemIcon(f);
                }
            });
            fileChooser.setFileFilter(new Filtro());
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setDialogTitle("Abrir Archivo de Audio o Vodeo");
            JDialog dialogo = new JDialog();
            dialogo.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
            if (fileChooser.showOpenDialog(dialogo) == JFileChooser.CANCEL_OPTION){
                return;
            }
            lista.add(fileChooser.getSelectedFile().getAbsolutePath());
            listar();
            
            btEliminar.setEnabled(true);
            btReproducir.setEnabled(true);
	}
	
	public void eliminar(){
            if(getSelectedRow() == -1){
                return;
            }
            lista.remove(getSelectedRow());
            listar();
            if(lista.isEmpty()){
                btEliminar.setEnabled(false);
		btReproducir.setEnabled(false);
            }		
	}

	public ArrayList<String> getLista() {
		return lista;
	}

	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}
}