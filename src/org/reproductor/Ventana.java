package org.reproductor;

import java.awt.EventQueue;
import javax.swing.JFrame;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import javax.swing.event.ChangeEvent;
import javax.swing.JLabel;
import org.base.Reproduccion;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.awt.Toolkit;

public class Ventana implements FullScreenStrategy{

	public JFrame frame;
	private JMenuItem mntmAbrir;
	private JMenuItem mntmSalir;
	private JMenuItem mntmAbrirCds;
	private JMenuItem mntmTamano;
	private JMenuItem mntmSubtitulos;
	private JMenuItem mntmListaDeReproduccion;
	private JMenuItem mntmRecientes;
	private JMenuItem mntmCaptura;
	private JMenuItem menuItem;
	private JMenuItem menuItem_1;
	private JMenuItem mntmPantallaCompleta;
	private JMenuItem mntmPredeterminada;
	
	public JButton btRetrasar;
	public JButton btPlay;
	public JButton btPause;
	public JButton btStop;
	public JButton btAdelantar;
	public JSlider slVolumen;
	public JSlider slTiempo;
        
	private static String ruta_libreria; //= "C:\\Program Files (x86)\\VideoLAN\\VLC";
	
	public EmbeddedMediaPlayerComponent mediaPlayer;
	private JLabel lbVolumen;
	public boolean tiempo = true;
	public String dimension;
	public Reproduccion reproduccion;
	private ListaReproduccion listaReproduccion;
	private Recientes reciente;
	private JPanel panel;
	private JMenuItem mntmAbrirStriming;
	private JMenuItem mntmRetornarPantalla;
	private FullScreenStrategy fullScreenStrategy;
	
	/**
	 * Launch the application.
     * @param args
	 */
	public static void main(String[] args) {
            try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		EventQueue.invokeLater(() -> {
                    try {
                        Ventana window = new Ventana();
                        window.frame.setVisible(true);
                    } catch (Exception e) {}
            });
	}

	/**
	 * Create the application.
	 */
	public Ventana() {
		initialize();
		inicializar();		
		frame.setTitle("VLCJ Players");
	}
	
	private void inicializar(){
		try {
			PrintWriter recientes = new PrintWriter (new BufferedWriter(new FileWriter("Reciente", true)));
			recientes.close();
		} catch (IOException e) {}		
				
		ruta_libreria = "C:\\Program Files (x86)\\VideoLAN\\VLC";
                cargarReproductor();
		reproduccion = new Reproduccion();
		reproduccion.inicializar(this);
		
		listaReproduccion = new ListaReproduccion(this);
		reciente = new Recientes(this);
	}
	
	private void cargarReproductor(){
		
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), ruta_libreria);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		panel.setLayout(new BorderLayout(0, 0));
		
		mediaPlayer = new EmbeddedMediaPlayerComponent();
		
		this.panel.add(mediaPlayer);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
		frame.setMinimumSize(new Dimension(615, 425));
		frame.setBounds(100, 100, 615, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelOpciones = new JPanel();
		frame.getContentPane().add(panelOpciones, BorderLayout.SOUTH);
		
		btRetrasar = new JButton("");
		btRetrasar.setEnabled(false);
		btRetrasar.setBounds(0, 0, 25, 25);
		btRetrasar.addActionListener((ActionEvent arg0) -> {

                        mediaPlayer.getMediaPlayer().setTime(mediaPlayer.getMediaPlayer().getTime() - 3000);
                    
                });
		btRetrasar.setIcon(new ImageIcon("btn/media-seek-backward-7.png"));
		panelOpciones.add(btRetrasar);
		
		btPlay = new JButton("");
		btPlay.setEnabled(false);
		btPlay.addActionListener((ActionEvent e) -> {
                    reproduccion.play();
                });
		btPlay.setIcon(new ImageIcon("btn/media-playback-start-7.png"));
		panelOpciones.add(btPlay);
                
		btPause = new JButton("");
		btPause.setEnabled(false);
		btPause.addActionListener((ActionEvent arg0) -> {
                    reproduccion.pausar();
                });
		btPause.setIcon(new ImageIcon("btn/media-playback-pause-7.png"));
		panelOpciones.add(btPause);
		
		btStop = new JButton("");
		btStop.setEnabled(false);
		btStop.addActionListener((ActionEvent e) -> {
                    reproduccion.stop();
                });
		btStop.setIcon(new ImageIcon("btn/media-playback-stop-7.png"));
		panelOpciones.add(btStop);
		
		btAdelantar = new JButton("");
		btAdelantar.addActionListener((ActionEvent arg0) -> {
                    mediaPlayer.getMediaPlayer().setTime(mediaPlayer.getMediaPlayer().getTime() + 3000);
                });
		btAdelantar.setEnabled(false);
		btAdelantar.setIcon(new ImageIcon("btn/media-seek-forward-7.png"));
		panelOpciones.add(btAdelantar);
                slVolumen = new JSlider();
		slVolumen.setValue(50);
		slVolumen.setMaximum(120);
		lbVolumen = new JLabel(Integer.toString(slVolumen.getValue()));
                lbVolumen.setIcon(new ImageIcon("btn/volumen.png"));
		panelOpciones.add(lbVolumen);

		slVolumen.addChangeListener((ChangeEvent evento) -> {
                    mediaPlayer.getMediaPlayer().setVolume(slVolumen.getValue());
                    lbVolumen.setText(Integer.toString(slVolumen.getValue()));
                    if(slVolumen.getValue() <= 20){
                        lbVolumen.setIcon(new ImageIcon("btn/silencio.png"));
                    }else{
                        lbVolumen.setIcon(new ImageIcon("btn/volumen.png"));
                    }
                });
		slVolumen.setEnabled(false);
		panelOpciones.add(slVolumen);
				
		JPanel panelVentana = new JPanel();
		frame.getContentPane().add(panelVentana, BorderLayout.CENTER);
		panelVentana.setLayout(new BorderLayout(0, 0));
		
		slTiempo = new JSlider();
		slTiempo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				mediaPlayer.getMediaPlayer().setTime(slTiempo.getValue());
				tiempo = true;
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				tiempo = false;
			}
		});
		slTiempo.setValue(0);
		slTiempo.setEnabled(false);
		panelVentana.add(slTiempo, BorderLayout.SOUTH);
		
		panel = new JPanel();
		panelVentana.add(panel, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		mntmAbrir = new JMenuItem("Abrir archivo");
                mntmAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
		mntmAbrir.addActionListener((ActionEvent arg0) -> {
                    reproduccion.abrirArchivo();
                });
		mnArchivo.add(mntmAbrir);
		             
                mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener((ActionEvent e) -> {
                    System.exit(0);
                });
		mnArchivo.add(mntmSalir);
		
		JMenu mnReproductor = new JMenu("Reproductor");
		menuBar.add(mnReproductor);
		
		mntmListaDeReproduccion = new JMenuItem("Lista de reproduccion");
                this.mntmListaDeReproduccion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
		mntmListaDeReproduccion.addActionListener((ActionEvent arg0) -> {
                    listaReproduccion.mostrar();
                });
		mnReproductor.add(mntmListaDeReproduccion);
		
		mntmRecientes = new JMenuItem("Recientes");
		mntmRecientes.addActionListener((ActionEvent arg0) -> {
                    reciente.mostrar();
                });
		mnReproductor.add(mntmRecientes);
		
		mntmCaptura = new JMenuItem("Captura");
		mntmCaptura.addActionListener((ActionEvent e) -> {
                    mediaPlayer.getMediaPlayer().saveSnapshot(new File(""));
                });
		mnReproductor.add(mntmCaptura);
		
		mntmSubtitulos = new JMenuItem("Subtitulos");
		mntmSubtitulos.addActionListener((ActionEvent arg0) -> {
                    reproduccion.subtitulos();
                });
		mnReproductor.add(mntmSubtitulos);
		
		JMenu mnAjustes = new JMenu("Tama\u00F1o");
		menuBar.add(mnAjustes);
		
		mntmTamano = new JMenuItem("4 : 3");
		mntmTamano.addActionListener((ActionEvent arg0) -> {
                    mediaPlayer.getMediaPlayer().setCropGeometry("4:3");
                });
		
		mntmPredeterminada = new JMenuItem("Predeterminada");
		mntmPredeterminada.addActionListener((ActionEvent e) -> {
                    mediaPlayer.getMediaPlayer().setCropGeometry(dimension);
                });
		mnAjustes.add(mntmPredeterminada);
		mnAjustes.add(mntmTamano);
		
		menuItem = new JMenuItem("16 : 9");
		menuItem.addActionListener((ActionEvent e) -> {
                    mediaPlayer.getMediaPlayer().setCropGeometry("16:9");
                });
		mnAjustes.add(menuItem);
		
		menuItem_1 = new JMenuItem("16 : 10");
		menuItem_1.addActionListener((ActionEvent e) -> {
                    mediaPlayer.getMediaPlayer().setCropGeometry("16:10");
                });
		mnAjustes.add(menuItem_1);
		
		mntmPantallaCompleta = new JMenuItem("Pantalla Completa");
                mntmPantallaCompleta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11,0));
		mntmPantallaCompleta.addActionListener((ActionEvent e) -> {
                    fullScreenStrategy = new DefaultFullScreenStrategy(frame);
                    fullScreenStrategy.enterFullScreenMode();
                    
                    mntmPantallaCompleta.setEnabled(false);
                    mntmRetornarPantalla.setEnabled(true);
                });
		mnAjustes.add(mntmPantallaCompleta);
		
		mntmRetornarPantalla = new JMenuItem("Retornar Pantalla");
                mntmRetornarPantalla.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
		mntmRetornarPantalla.setEnabled(false);
		mntmRetornarPantalla.addActionListener((ActionEvent arg0) -> {
                    fullScreenStrategy.exitFullScreenMode();
                    mntmPantallaCompleta.setEnabled(true);
                    mntmRetornarPantalla.setEnabled(false);
                });
		mnAjustes.add(mntmRetornarPantalla);
		
		frame.setLocationRelativeTo(null);
	}

	@Override
	public void enterFullScreenMode() {}

	@Override
	public void exitFullScreenMode() {}

	@Override
	public boolean isFullScreenMode() {		
		return false;
	}	
}