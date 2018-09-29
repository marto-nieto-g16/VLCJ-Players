package org.reproductor;

import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import org.beans.TablaRecientes;
import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class Recientes extends JFrame {

	private final JPanel contentPane;
	private final JScrollPane scrollPane;
	private TablaRecientes tablaRecientes;
	private final JButton btnReproducir;
	private final Ventana ventana;
	
	public void mostrar(){
		setVisible(true);
                setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
                setTitle("Archivos Recientes");
                setResizable(false);
		tablaRecientes.listar();
	}

	/**
	 * Create the frame.
     * @param ventana
	 */
	public Recientes(Ventana ventana) {
		this.ventana = ventana;
		
		setBounds(100, 100, 594, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 558, 321);
		contentPane.add(scrollPane);
		
		tablaRecientes = new TablaRecientes(ventana);
		scrollPane.setViewportView(tablaRecientes);
		
		btnReproducir = new JButton("Reproducir");
		btnReproducir.addActionListener((ActionEvent arg0) -> {
                    tablaRecientes.reproducir();
                });
		btnReproducir.setBounds(10, 339, 558, 23);
		contentPane.add(btnReproducir);
		
		this.setLocationRelativeTo(null);
	}
}
