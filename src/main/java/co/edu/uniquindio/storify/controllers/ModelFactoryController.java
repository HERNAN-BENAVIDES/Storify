package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDoble;
import co.edu.uniquindio.storify.exceptions.ArtistasYaEnTiendaException;
import co.edu.uniquindio.storify.exceptions.AtributoVacioException;
import co.edu.uniquindio.storify.exceptions.UsuarioYaRegistradoException;
import co.edu.uniquindio.storify.model.*;
import co.edu.uniquindio.storify.util.Persistencia;
import co.edu.uniquindio.storify.util.StorifyUtil;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SuppressWarnings("all")
public class ModelFactoryController {
    private TiendaMusica tiendaMusica=null;
    private Aplicacion aplicacion=null;
    private Stage ventana;

    public ModelFactoryController()  {
        cargarDatosBinario();

        if(tiendaMusica == null){
            cargarDatosPrueba();
        }

        //guardarDatosBinario();
    }

    public void cargarDatosQuemados() throws AtributoVacioException, UsuarioYaRegistradoException, ArtistasYaEnTiendaException {


        Artista artista=getTiendaMusica().crearArtista("The Weeknd", "34234", "Canada", "SOLISTA");
        Artista artista2=getTiendaMusica().crearArtista("Miró", "12341", "Mexico", "BANDA");
        Artista artista3=getTiendaMusica().crearArtista("Coldplay", "56547", "Inglaterra", "BANDA");

        Cancion cancion1= getTiendaMusica().crearCancion("Die For You", "Starboy", "/imagenes/starboy.jpeg", "2016", "4:20", "RB", "https://www.youtube.com/watch?v=mTLQhPFx2nM&list=PLWGXKDxW301QZrzSl7hLzdYakFdayHC4l&index=17&ab_channel=TheWeeknd-Topic");
        artista.agregarCancion(cancion1);

        Cancion cancion2= getTiendaMusica().crearCancion("I Feel It Coming", "Starboy", "/imagenes/starboy.jpeg", "2016", "4:29", "RB", "https://www.youtube.com/watch?v=qPRNIHxLhmc&list=PLWGXKDxW301QZrzSl7hLzdYakFdayHC4l&index=18&ab_channel=TheWeeknd-Topic");
        artista.agregarCancion(cancion2);

        Cancion cancion3= getTiendaMusica().crearCancion("En Un Segundo", "Miró", "/imagenes/miroalbum.jpg", "2010", "3:32", "ROCK", "https://www.youtube.com/watch?v=vHXhIY45t7I&ab_channel=Miro-Topic");
        artista2.agregarCancion(cancion3);

        Cancion cancion4= getTiendaMusica().crearCancion("Sparks", "Parachutes", "/imagenes/parachutes.jpeg", "2000", "3:47", "ROCK", "https://www.youtube.com/watch?v=Ar48yzjn1PE&ab_channel=SomberSounds");
        artista3.agregarCancion(cancion4);


        getTiendaMusica().agregarArtista(artista);
        getTiendaMusica().agregarArtista(artista2);
        getTiendaMusica().agregarArtista(artista3);


        Persona persona1= getTiendaMusica().crearCliente("Mary", "Saire");
        Usuario usuario=getTiendaMusica().crearUsuario("Mary", "1234", "mary@gmail.com", persona1);
        getTiendaMusica().agregarUsuario(usuario);

        Cliente cliente1 = (Cliente) persona1;
        cliente1.agregarCancionFavorita(cancion1);
        cliente1.agregarCancionFavorita(cancion3);
        cliente1.agregarCancionFavorita(cancion2);
        cliente1.agregarCancionFavorita(cancion4);





    }

    private void cargarDatosBinario() {
        this.tiendaMusica = Persistencia.cargarRecursoBancoBinario();
    }

    private void guardarDatosBinario() {
        Persistencia.guardarRecursoBancoBinario(tiendaMusica);
    }

    private void cargarArtistasDesdeArchivo(String ruta) {
        try {
            tiendaMusica.cargarArtistasDesdeArchivo(ruta);
        } catch (ArtistasYaEnTiendaException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosPrueba() {
        tiendaMusica = StorifyUtil.inicializarDatosPrueba();
    }

    private String obtenerGeneroConMasCanciones() {
        return tiendaMusica.obtenerGeneroConMasCanciones();
    }

    private Artista obtenerArtistaMasPopular() throws IOException, GeneralSecurityException {
        return tiendaMusica.obtenerArtistaMaspopular();
    }

    /**
     * Metodo que obtiene la TiendaMusica
     * @return TiendaMusica
     */
    public TiendaMusica getTiendaMusica() {
        return tiendaMusica;
    }

    /**
     * Metodo que cambia la tiendaMusica
     * @param tiendaMusica Nueva tiendaMusica
     */
    public void setConcesionario(TiendaMusica tiendaMusica) {
        this.tiendaMusica = tiendaMusica;
    }

    /**
     * Obtiene la ventana
     * @return ventana
     */
    public Stage getVentana() {
        return ventana;
    }

    /**
     * Establece la ventana
     * @param ventana La ventana
     */
    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }

    /**
     * Obtiene la direccion de la aplicacion principal
     * @return Aplicacion principal
     */
    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    /**
     * Establece la direccion de la aplicacion principal
     * @param aplicacion Nueva aplicacion principal
     */
    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }



     /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------GET INSTANCE---------------------------------------------------------
    */
    /**
     * Obtiene una instancia única de la clase ModelFactoryController.
     * @return Una instancia única de ModelFactoryController.
     */
    public static ModelFactoryController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Clase interna que contiene la instancia única de ModelFactoryController.
     */
    private static class SingletonHolder {
        private static final ModelFactoryController INSTANCE = new ModelFactoryController();
    }
}