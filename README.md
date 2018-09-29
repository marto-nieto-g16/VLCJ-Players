
VLC Media Player es un reproductor multimedia de código abierto muy popular desarrollado por el proyecto VideoLAN. 

VLCJ es un proyecto que permite trabajar con una instancia de VLC en java.

<img src="https://github.com/marto-nieto-g16/VLCJ-Players/blob/master/vlcj.png" border="1" alt="Reproductor VLCJ en Java" width="400" height="300">

# VLCJ-Players

Reproductor de videos que usa la librería VLCJ y
librerías de VLC, nuestro reproductor sera capaz 
de abrir un archivo de video (MP4, 3GP, Dat, webm),
contara con los controles básicos (PLAY, PAUSE, STOP, MUTE) 
se usaran slider para mostrar el avance en la reproducción del 
video así como modificar el punto de reproducción moviendo el slider,
finalmente se podrá sacar capturas del video en cualquier punto de reproducción.

# Requisitos

    Librería VLCJ
    Librería JNA 
    Librerías libvlc.dll y libvlccore.dll
    IDE Netbeans 7.x o superior

_______________________________________________________________________________________________________________
La librería VLCJ requiere de la librería JNA (Java Native Access), la librería JNA nos permite tener acceso  las bibliotecas compartidas nativas del sistema operativo sin usar la interfaz nativa de Java. En este tutorial, usaremos la versión de VLC 2.4.1 y JNA 3.5.1, cada versión de VLCJ requiere una versión diferente de JNA, para evitar problemas se recomienda revisar la documentación de VLCJ.

Descarga estas librerías e instala en Netbeans.

Necesitamos las librerías libvlc.dll y libvlccore.dll de VLC, estas por defecto se encuentran en “C:\Program Files\VideoLAN\VLC\”, así que si no tienes el reproductor, debes descargar e instalar en tu pc.
