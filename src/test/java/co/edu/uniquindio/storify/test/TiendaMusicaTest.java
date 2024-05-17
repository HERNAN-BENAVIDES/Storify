package co.edu.uniquindio.storify.test;

import co.edu.uniquindio.storify.controllers.controladorFlujo.Comando;
import co.edu.uniquindio.storify.controllers.controladorFlujo.ComandoAgregarCancion;
import co.edu.uniquindio.storify.controllers.controladorFlujo.ComandoEliminarCancion;
import co.edu.uniquindio.storify.estructurasDeDatos.arbolBinario.BinaryTree;
import co.edu.uniquindio.storify.exceptions.*;
import co.edu.uniquindio.storify.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TiendaMusicaTest {

    private TiendaMusica tienda;

    @BeforeEach
    void setUp() {
        tienda = new TiendaMusica();
    }

    @Test
    void agregarArtista() throws ArtistasYaEnTiendaException {
        Artista artista = new Artista("123", "Cristian", "Colombia", TipoArtista.SOLISTA);
        assertDoesNotThrow(() -> tienda.agregarArtista(artista));
        assertThrows(ArtistasYaEnTiendaException.class, () -> tienda.agregarArtista(artista));
    }

    @Test
    void crearArtista() throws AtributoVacioException {
        assertDoesNotThrow(() -> tienda.crearArtista("Cristian", "123", "Colombia", "SOLISTA"));
        assertThrows(AtributoVacioException.class, () -> tienda.crearArtista("Cristian", "123", "Colombia", ""));
    }

    @Test
    void crearCancion() {
        assertDoesNotThrow(() -> tienda.crearCancion("Reminisencias", "Exitos",
                "caratula", "1980", "5.30", "otro","www.youtube.com"));
        assertThrows(AtributoVacioException.class, () -> tienda.crearCancion("Reminisencias", "Exitos",
                "caratula", "1980", "5:30", "","www.youtube.com"));
    }

    @Test
    void generarCodigoAleatorio() {
        assertNotNull(tienda.generarCodigoAleatorio());
    }

    @Test
    void agregarCancion() throws CancionYaRegistradaException {
        Artista artista = new Artista("123", "Cristian", "Colombia", TipoArtista.SOLISTA);
        Cancion cancion = new Cancion("1234","Reminisencias", "Exitos", "caratula", 1980, "5.30", TipoGenero.POP, "www.youtube.com");
        assertTrue(tienda.agregarCancion(cancion, artista));
        assertThrows(CancionYaRegistradaException.class, () -> tienda.agregarCancion(cancion, artista));
    }

    @Test
    void crearCliente() {
        assertDoesNotThrow(() -> tienda.crearCliente("Maria", "Velez"));
        assertThrows(AtributoVacioException.class, () -> tienda.crearCliente("Maria", ""));
    }

    @Test
    void crearUsuario() {
        Persona persona = new Cliente("Maria", "Velez");
        assertDoesNotThrow(() -> tienda.crearUsuario("MariaVelez", "123", "mariaVelez@gmail.com", persona));
        assertThrows(AtributoVacioException.class, () -> tienda.crearUsuario("MariaVelez", "123", "", persona));
    }

    @Test
    void agregarUsuario() {
        Usuario usuario = new Usuario("MariaVelez", "123", "mariaVelez@gmail.com", new Persona("Maria", "Velez"));
        assertDoesNotThrow(() -> tienda.agregarUsuario(usuario));
        assertThrows(UsuarioYaRegistradoException.class, () -> tienda.agregarUsuario(new Usuario("MariaVelez", "123", "mariaVelez@gmail.com", new Persona("Maria", "Velez"))));
    }

    @Test
    void buscarUsuario() throws UsuarioYaRegistradoException {
        Usuario usuario = new Usuario("MariaVelez", "123", "mariaVelez@gmail.com", new Persona("Maria", "Velez"));
        tienda.agregarUsuario(usuario);
        assertDoesNotThrow(() -> tienda.buscarUsuario("MariaVelez", "123"));
        assertThrows(AtributoVacioException.class, () -> tienda.buscarUsuario("", "123"));
        assertThrows(UsuarioNoExistenteException.class, () -> tienda.buscarUsuario("MariaVeles", "1234"));
    }

    @Test
    void obtenerTipoUsuario() throws UsuarioYaRegistradoException {
        Usuario usuario = new Usuario("MariaVelez", "123", "mariaVelez@gmail.com", new Cliente("Maria", "Velez"));
        tienda.agregarUsuario(usuario);

        assertDoesNotThrow(() -> tienda.obtenerTipoUsuario("MariaVelez", "123"));
        assertThrows(AtributoVacioException.class, () -> tienda.obtenerTipoUsuario("", "123"));
        assertThrows(UsuarioNoExistenteException.class, () -> tienda.obtenerTipoUsuario("MariaVeles", "1234"));
    }

    @Test
    void buscarCancionesPorArtista() throws ArtistaNoEncontradoException, ArtistasYaEnTiendaException, CancionYaRegistradaException, InterruptedException {
        Artista artista = new Artista("Juan", "Codigo", "Nacionalidad", TipoArtista.SOLISTA);
        Cancion cancion1 = new Cancion("123","Cancion1", "Album1", "Caratula1", 2022, "4.5", TipoGenero.ROCK, "url1");
        Cancion cancion2 = new Cancion("1233","Cancion2", "Album2", "Caratula2", 2023, "3.5", TipoGenero.POP, "url2");
        tienda.agregarArtista(artista);
        tienda.agregarCancion(cancion1,artista);
        tienda.agregarCancion(cancion2,artista);

        assertEquals(2, tienda.buscarCancionesPorArtista("Codigo").size());
        assertThrows(ArtistaNoEncontradoException.class, () -> tienda.buscarCancionesPorArtista("ArtistaInexistente"));

    }

    @Test
    void cargarArtistasDesdeArchivo() {

    }

    @Test
    void obtenerGeneroConMasCanciones() throws ArtistasYaEnTiendaException, InterruptedException {
        Artista artista = new Artista("1234", "Codigo", "Nacionalidad", TipoArtista.SOLISTA);
        tienda.agregarArtista(artista);
        assertDoesNotThrow(() -> tienda.obtenerGeneroConMasCanciones());
    }

    @Test
    void obtenerArtistaMaspopular() {

        // assertDoesNotThrow(() -> tienda.obtenerArtistaMaspopular());
    }

    @Test
    public void testComandoAgregarCancion() {
        Cliente usuario = new Cliente("usuarioPrueba","Velez");
        Cancion cancion = new Cancion("SONG001", "Die For You", "Starboy", "/imagenes/starboy.jpeg", 2016, "4:20", TipoGenero.OTRO, "https://www.youtube.com/watch?v=mTLQhPFx2nM&list=PLWGXKDxW301QZrzSl7hLzdYakFdayHC4l&index=17&ab_channel=TheWeeknd-Topic");
        Cancion cancion1 = new Cancion("SONG002", "Die For You34", "Starbo3434y", "/imagenes/starboy.jpeg", 2016, "4:20", TipoGenero.OTRO, "https://www.youtube.com/watch?v=mTLQhPFx2nM&list=PLWGXKDxW301QZrzSl7hLzdYakFdayHC4l&index=17&ab_channel=TheWeeknd-Topic");
        usuario.agregarCancionFavorita(cancion1);
        Comando agregarCancion = new ComandoAgregarCancion(usuario, cancion);

        agregarCancion.ejecutar();
        assertTrue(usuario.getCancionesFavoritas().contains(cancion));

        agregarCancion.deshacer();
        assertFalse(usuario.getCancionesFavoritas().contains(cancion));

        agregarCancion.rehacer();
        assertTrue(usuario.getCancionesFavoritas().contains(cancion));
    }

    @Test
    public void testComandoEliminarCancion() {
        Cliente usuario = new Cliente("usuarioPrueba","Velez");
        Cancion cancion = new Cancion("SONG001", "Die For You", "Starboy", "/imagenes/starboy.jpeg", 2016, "4:20", TipoGenero.OTRO, "https://www.youtube.com/watch?v=mTLQhPFx2nM&list=PLWGXKDxW301QZrzSl7hLzdYakFdayHC4l&index=17&ab_channel=TheWeeknd-Topic");
        Cancion cancion1 = new Cancion("SONG002", "Die For You", "Starboy", "/imagenes/starboy.jpeg", 2016, "4:20", TipoGenero.OTRO, "https://www.youtube.com/watch?v=mTLQhPFx2nM&list=PLWGXKDxW301QZrzSl7hLzdYakFdayHC4l&index=17&ab_channel=TheWeeknd-Topic");
        usuario.agregarCancionFavorita(cancion);
        usuario.agregarCancionFavorita(cancion1);
        Comando eliminarCancion = new ComandoEliminarCancion(usuario, cancion);

        eliminarCancion.ejecutar();
        assertFalse(usuario.getCancionesFavoritas().contains(cancion));

        eliminarCancion.deshacer();
        assertTrue(usuario.getCancionesFavoritas().contains(cancion));

        eliminarCancion.rehacer();
        assertFalse(usuario.getCancionesFavoritas().contains(cancion));
    }

    @Test
    void testFind() {
        BinaryTree<String> arbol = new BinaryTree<>();
        arbol.insert("Beatles");
        arbol.insert("Queen");
        arbol.insert("Led Zeppelin");
        arbol.insert("Pink Floyd");

        try {
            String resultado = arbol.find("Queen");
            assertNotNull(resultado);
            assertEquals("Queen", resultado);
        } catch (InterruptedException e) {
            fail("La búsqueda fue interrumpida: " + e.getMessage());
        }
    }

    @Test
    void testFindNotFound() {
        BinaryTree<String> arbol = new BinaryTree<>();
        arbol.insert("Beatles");
        arbol.insert("Queen");
        arbol.insert("Led Zeppelin");
        arbol.insert("Pink Floyd");

        try {
            String resultado = arbol.find("Nirvana");
            assertNull(resultado);
        } catch (InterruptedException e) {
            fail("La búsqueda fue interrumpida: " + e.getMessage());
        }
    }

    @Test
    void testFindRoot() {
        BinaryTree<String> arbol = new BinaryTree<>();
        arbol.insert("Beatles");
        arbol.insert("Queen");
        arbol.insert("Led Zeppelin");
        arbol.insert("Pink Floyd");

        try {
            String resultado = arbol.find("Beatles");
            assertNotNull(resultado);
            assertEquals("Beatles", resultado);
        } catch (InterruptedException e) {
            fail("La búsqueda fue interrumpida: " + e.getMessage());
        }
    }

}