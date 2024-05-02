package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDoble;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimple;
import co.edu.uniquindio.storify.exceptions.*;
import co.edu.uniquindio.storify.util.ArchivoUtil;
import co.edu.uniquindio.storify.util.YouTubeHelper;
import lombok.Data;
import lombok.ToString;


import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Data
@ToString
@SuppressWarnings("All")
public class TiendaMusica implements Serializable {

    private final String nombre = "Storify";
    private final String version = "1.0.0";
    private HashMap<String, Usuario> usuarios;
    private HashMap<String, Artista> artistas;
    private  Administrador administrador;

    public TiendaMusica() {
        this.usuarios = new HashMap<>();
        this.artistas = new HashMap<>();
        this.administrador = crearAdministrador();
    }

    private Administrador crearAdministrador() {
        Administrador administrador = new Administrador();
        return administrador;
    }

    public boolean agregarArtista(Artista artista) throws ArtistasYaEnTiendaException {
        String codigoArtista = artista.getCodigo();
        Artista artistaExistente = getArtistas().putIfAbsent(codigoArtista, artista);
        if (artistaExistente != null) {
            throw new ArtistasYaEnTiendaException("El artista ya existe en la tienda.");
        }
        return true;
    }

    public Artista crearArtista(String nombre, String codigo, String nacionalidad, String tipoArtista)throws AtributoVacioException{

        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre es obligatorio");
        }
        if (codigo == null || codigo.isBlank()) {
            throw new AtributoVacioException("El código es obligatorio");
        }
        if (nacionalidad == null || nacionalidad.isBlank()) {
            throw new AtributoVacioException("La nacionalidad es obligatoria");
        }
        if (tipoArtista == null || tipoArtista.isBlank()) {
            throw new AtributoVacioException("El tipo de artista es obligatorio");
        }

        Artista artistaNuevo = Artista.builder()
                .codigo(codigo)
                .nombre(nombre)
                .nacionalidad(nacionalidad)
                .tipoArtista(TipoArtista.valueOf(tipoArtista.toUpperCase()))
                .build();

        return artistaNuevo;

    }


    public Cancion crearCancion(String nombre, String nombreAlbum, String caratula, String anio, String duracion, String genero, String urlYoutube)throws AtributoVacioException{
        String codigoRandom= generarCodigoAleatorio();

        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre es obligatorio");
        }
        if (nombreAlbum == null || nombreAlbum.isBlank()) {
            throw new AtributoVacioException("El nombre del album es obligatorio");
        }
        if (caratula == null || caratula.isBlank()) {
            throw new AtributoVacioException("La caratula es obligatoria");
        }
        if (anio == null || anio.isBlank()) { //agregar formato booleano para comprobar que se ingreso un anio
            throw new AtributoVacioException("El año es obligatorio");
        }
        if (duracion == null || duracion.isBlank()) { //lo mismo con double
            throw new AtributoVacioException("La duración de la canción es obligatoria");
        }
        if (genero == null || genero.isBlank()) {
            throw new AtributoVacioException("El genero de la canción es obligatorio");
        }
        if (urlYoutube == null || urlYoutube.isBlank()) {
            throw new AtributoVacioException("El url Youtube de la canción es obligatorio");
        }

        Cancion cancionNueva = Cancion.builder()
                .codigo(codigoRandom)
                .nombre(nombre)
                .album(nombreAlbum)
                .caratula(caratula)
                .anioLanzamiento(Integer.parseInt(anio))
                .duracion(duracion)
                .genero(TipoGenero.valueOf(genero.toUpperCase()))
                .urlYoutube(urlYoutube)
                .build();

        return cancionNueva;

    }

    public static String generarCodigoAleatorio() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        // Generar 3 letras aleatorias
        for (int i = 0; i < 3; i++) {
            char letra = (char) (random.nextInt(26) + 'a'); // Letras minúsculas
            codigo.append(letra);
        }

        // Generar 3 dígitos aleatorios
        for (int i = 0; i < 3; i++) {
            int digito = random.nextInt(10); // Dígitos del 0 al 9
            codigo.append(digito);
        }

        return codigo.toString();
    }

    public boolean agregarCancion(Cancion cancion, Artista artista) throws CancionYaRegistradaException {
        ListaEnlazadaDoble<Cancion> canciones = artista.getCanciones();
        if(canciones.contains(cancion)) {
            throw new CancionYaRegistradaException("La cancion ya esta en las canciones del  artista");
        }
        canciones.add(cancion);
        return true;
    }

    public Cliente crearCliente(String nombre, String apellido) throws AtributoVacioException {

        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre es obligatorio");
        }
        if (apellido == null || apellido.isBlank()) {
            throw new AtributoVacioException("El apellido es obligatorio");
        }

        Cliente clienteNuevo = Cliente.builder()
                .nombre(nombre)
                .apellido(apellido)
                .build();

        return clienteNuevo;
    }


    public Usuario crearUsuario(String username, String password, String email, Persona persona) throws AtributoVacioException{
        if (username == null || username.isBlank()) {
            throw new AtributoVacioException("El username es obligatorio");
        }
        if (password == null || password.isBlank()) {
            throw new AtributoVacioException("La contraseña es obligatoria");
        }
        if (email == null || email.isBlank()) {
            throw new AtributoVacioException("El email es obligatorio");
        }

        Usuario usuarioNuevo= Usuario.builder()
                .username(username)
                .password(password)
                .email(email)
                .persona(persona)
                .build();

        return usuarioNuevo;
    }

    public boolean agregarUsuario(Usuario usuario) throws UsuarioYaRegistradoException {
        String usernameCliente = usuario.getUsername();
        Usuario usuarioExistente = getUsuarios().putIfAbsent(usernameCliente, usuario);
        if (usuarioExistente != null) {
            throw new UsuarioYaRegistradoException("El usuario ya existe en la base de datos");
        }
        return true;
    }

    public Usuario buscarUsuario(String username, String contrasenia) throws AtributoVacioException, UsuarioNoExistenteException{
        if (username == null || username.isBlank()) {
            throw new AtributoVacioException("El username es obligatorio");
        }
        if (contrasenia == null || contrasenia.isBlank()) {
            throw new AtributoVacioException("La contrasenia es obligatorio");
        }

        for (Usuario usuario : usuarios.values()) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(contrasenia)) {
                return usuario;
            }
        }
        throw new UsuarioNoExistenteException("Usuario no encontrado");
    }

    public String obtenerTipoUsuario(String username, String contrasenia)throws AtributoVacioException, UsuarioNoExistenteException{

        Usuario usuario = buscarUsuario(username, contrasenia);
        Persona persona = usuario.getPersona();
        if (persona instanceof Cliente) {
            return "Cliente";
        } else if (persona instanceof Administrador) {
            return "Administrador";
        }

        throw new UsuarioNoExistenteException("Usuario no encontrado");
    }

    public ListaEnlazadaDoble<Cancion> buscarCancionesPorArtista(String nombreArtista) throws ArtistaNoEncontradoException {
        for (Artista artista : artistas.values()) {
            if (artista.getNombre().equalsIgnoreCase(nombreArtista)) {
                return artista.getCanciones();
            }
        }
        throw new ArtistaNoEncontradoException("El artista seleccionado no existe");
    }

    public Artista buscarArtistaCancion(Cancion cancion) throws ArtistaNoEncontradoException {
        for (Artista artista : artistas.values()) {
            ListaEnlazadaDoble<Cancion> cancionesArtista = artista.getCanciones();
            for (Cancion cancionArtista : cancionesArtista) {
                if (cancionArtista.equals(cancion)) {
                    return artista;
                }
            }
        }
        throw new ArtistaNoEncontradoException("Ningun artista coincide con la cancion especificada");
    }



    public void cargarArtistasDesdeArchivo(String ruta) throws IOException, ArtistasYaEnTiendaException {
        HashMap<String, Artista> artistas = ArchivoUtil.cargarArtistasDesdeArchivo(ruta);
        HashMap<String, Artista> artistasExistentes = getArtistas();
        ListaEnlazadaSimple<Artista> artistasYaEnTienda = new ListaEnlazadaSimple<>();

        for (Map.Entry<String, Artista> entry : artistas.entrySet()) {
            String nombreArtista = entry.getKey();
            Artista nuevoArtista = entry.getValue();

            if (artistasExistentes.containsKey(nombreArtista)) {
                // El artista ya está en la tienda, agregalo a la lista de artistas existentes
                artistasYaEnTienda.add(nuevoArtista);
            } else {
                // El artista es nuevo, agrégalo a la tienda
                artistasExistentes.put(nombreArtista, nuevoArtista);
            }
        }

        if (!artistasYaEnTienda.isEmpty()) {
            throw new ArtistasYaEnTiendaException("Al menos un artista ya está en la tienda");
        }

        // Si no se lanza la excepción, significa que todos los artistas se han agregado correctamente
    }

    public String obtenerGeneroConMasCanciones() {
        HashMap<TipoGenero, Integer> contadorGeneros = new HashMap<>();

        // Iterar sobre todos los artistas en la tienda
        for (Artista artista : artistas.values()) {
            // Iterar sobre todas las canciones del artista
            for (Cancion cancion : artista.getCanciones()) {
                // Obtener el género de la canción
                TipoGenero genero = cancion.getGenero();

                // Incrementar el contador para el género actual
                contadorGeneros.put(genero, contadorGeneros.getOrDefault(genero, 0) + 1);
            }
        }

        // Encontrar el género con más canciones
        String generoConMasCanciones = null;
        int maxCanciones = 0;
        for (Map.Entry<TipoGenero, Integer> entry : contadorGeneros.entrySet()) {
            if (entry.getValue() > maxCanciones) {
                generoConMasCanciones = entry.getKey().toString();
                maxCanciones = entry.getValue();
            }
        }

        return generoConMasCanciones;
    }

    public Artista obtenerArtistaMaspopular() throws IOException, GeneralSecurityException {
        Artista artistaMasPopular = null;
        long maxReproducciones = 0;

        // Iterar sobre todos los artistas en la tienda
        for (Artista artista : artistas.values()) {
            long totalReproducciones = 0;

            // Iterar sobre todas las canciones del artista
            for (Cancion cancion : artista.getCanciones()) {
                // Obtener el enlace de YouTube de la canción
                String enlaceYouTube = cancion.getUrlYoutube();

                // Simular obtener la cantidad de reproducciones del video de YouTube
                long reproducciones = obtenerCantidadReproducciones(enlaceYouTube);

                // Sumar las reproducciones de la canción al total del artista
                totalReproducciones += reproducciones;
            }

            // Si el total de reproducciones del artista actual es mayor que el máximo encontrado hasta ahora,
            // actualizar el artista más popular y la cantidad máxima de reproducciones
            if (totalReproducciones > maxReproducciones) {
                maxReproducciones = totalReproducciones;
                artistaMasPopular = artista;
            }
        }

        return artistaMasPopular;
    }

    private long obtenerCantidadReproducciones(String enlaceYouTube) throws IOException, GeneralSecurityException {
        return YouTubeHelper.obtenerVistasVideo(enlaceYouTube); // Llama a la clase YouTubeHelper
    }


}