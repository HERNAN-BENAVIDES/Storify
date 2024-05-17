package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.arbolBinario.BinaryTree;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDoble;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimple;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimpleCircular;
import co.edu.uniquindio.storify.estructurasDeDatos.nodo.BinaryNode;
import co.edu.uniquindio.storify.estructurasDeDatos.nodo.Node;
import co.edu.uniquindio.storify.exceptions.*;
import co.edu.uniquindio.storify.util.ArchivoUtil;
import co.edu.uniquindio.storify.util.YouTubeHelper;
import lombok.Data;
import lombok.ToString;


import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.*;

@Data
@ToString
@SuppressWarnings("All")
public class TiendaMusica implements Serializable {

    private final String nombre = "Storify";
    private final String version = "1.0.0";
    private HashMap<String, Usuario> usuarios;
    private BinaryTree<Artista> artistas;
    private  Administrador administrador;

    public TiendaMusica() {
        this.usuarios = new HashMap<>();
        this.artistas = new BinaryTree<>();
        this.administrador = crearAdministrador();
    }

    private Administrador crearAdministrador() {
        Administrador administrador = new Administrador();
        return administrador;
    }

    public void agregarArtista(Artista artista) throws ArtistasYaEnTiendaException, InterruptedException {
        if (artistas.find(artista) != null){
            throw new ArtistasYaEnTiendaException("El artista ya existe en la tienda.");
        }
        artistas.insert(artista);
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
                .codigo(generarCodigoAleatorio())
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



    public ListaEnlazadaDoble<Cancion> buscarCancionesPorArtista(String nombreArtista) throws ArtistaNoEncontradoException, InterruptedException {
        ListaEnlazadaDoble<Cancion> cancionesArtista = new ListaEnlazadaDoble<>();
        Artista artista = new Artista(null, nombreArtista, null, null);

        Artista artista1  = artistas.find(artista);

        throw new ArtistaNoEncontradoException("El artista seleccionado no existe");
    }

    public Artista buscarArtistaCancion(Cancion cancion) throws ArtistaNoEncontradoException {
        for (Artista artista : artistas.iterator()) {
            ListaEnlazadaDoble<Cancion> cancionesArtista = artista.getCanciones();
            for (Cancion cancionArtista : cancionesArtista) {
                if (cancionArtista.equals(cancion)) {
                    return artista;
                }
            }
        }
        throw new ArtistaNoEncontradoException("Ningun artista coincide con la cancion especificada");
    }

    /**
     * Metodo para obtener todas las canciones en el sistema
     * @return
     */
    public ListaEnlazadaSimpleCircular<Cancion> obtenerCancionesGenerales() {
        ListaEnlazadaSimpleCircular<Cancion> cancionesGenerales = new ListaEnlazadaSimpleCircular<>();
        ListaEnlazadaSimple<Artista> artistas = getArtistas().iterator();

        for (Artista artista : artistas) {
            for (Cancion cancion : artista.getCanciones()) {
                cancionesGenerales.add(cancion);
            }
        }

        return cancionesGenerales;
    }




    public void cargarArtistasDesdeArchivo(String ruta) throws IOException, ArtistasYaEnTiendaException, InterruptedException {
        ListaEnlazadaSimple<Artista> artistas = ArchivoUtil.cargarArtistasDesdeArchivo(ruta);
        BinaryTree<Artista> artistasExistentes = getArtistas();
        ListaEnlazadaSimple<Artista> artistasYaEnTienda = new ListaEnlazadaSimple<>();

        for (Artista artista : artistas) {
            if (artistasExistentes.find(artista) != null) {
                // El artista ya está en la tienda, agregalo a la lista de artistas existentes
                artistasYaEnTienda.add(artista);
            } else {
                // El artista es nuevo, agrégalo a la tienda
                artistasExistentes.insert(artista);
            }
        }

        if (!artistasYaEnTienda.isEmpty()) {
            throw new ArtistasYaEnTiendaException("Al menos un artista ya está en la tienda");
        }

        // Si no se lanza la excepción, significa que todos los artistas se han agregado correctamente
    }

    public String obtenerGeneroConMasCanciones() {
        HashMap<TipoGenero, Integer> contadorGeneros = new HashMap<>();
        ListaEnlazadaSimple<Artista> artistas = getArtistas().iterator();
        // Iterar sobre todos los artistas en la tienda
        for (Artista artista : artistas) {
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

    public Artista obtenerArtistaMasPopular() throws IOException, GeneralSecurityException {
        ListaEnlazadaSimple<Artista> artistas = getArtistas().iterator();
        Artista artistaMasPopular = null;
        long maxReproducciones = 0;

        // Iterar sobre todos los artistas en la tienda
        for (Artista artista : artistas) {
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

    public <T extends Comparable<T>> List<T> convertirArbolALista(BinaryTree<T> arbol) {
        List<T> lista = new ArrayList<>();

        // Obtener un iterador para recorrer el árbol en orden
        ListaEnlazadaSimple<T> iterador = arbol.iterator();

        // Recorrer el árbol con el iterador y agregar cada elemento a la lista
        for (T elemento : iterador) {
            lista.add(elemento);
        }

        return lista;
    }

    public List<String> obtenerNacionalidadesSinRepetir() {
        List<String> nacionalidades = new ArrayList<>();
        Set<String> nacionalidadesSet = new HashSet<>(); // Usamos un Set para evitar duplicados

        // Recorremos el árbol de artistas utilizando el iterador
        for (Artista artista : this.artistas.iterator()) {
            String nacionalidad = artista.getNacionalidad();
            nacionalidadesSet.add(nacionalidad);
        }

        // Convertimos el Set a una lista para retornarla
        nacionalidades.addAll(nacionalidadesSet);
        return nacionalidades;
    }

    public List<String> obtenerAniosLanzamientoSinRepetir() {
        List<String> lanzamiento = new ArrayList<>();
        Set<String> lanzamientoSet = new HashSet<>(); // Usamos un Set para evitar duplicados

        // Recorremos el árbol de artistas utilizando el iterador
        for (Artista artista : this.artistas.iterator()) {
            for (Cancion cancion : artista.getCanciones()){
                String cancionLanzamiento= String.valueOf(cancion.getAnioLanzamiento());
                lanzamientoSet.add(cancionLanzamiento);
            }
        }
        lanzamientoSet.add("Todos");

        lanzamiento.addAll(lanzamientoSet);
        return lanzamiento;
    }

    public List<String> obtenerTipoGeneroCancionesSinRepetir() {
        List<String> genero = new ArrayList<>();
        Set<String> generoSet = new HashSet<>(); // Usamos un Set para evitar duplicados

        // Recorremos el árbol de artistas utilizando el iterador
        for (Artista artista : this.artistas.iterator()) {
            for (Cancion cancion : artista.getCanciones()){
                String tipoCancion= cancion.obtenerGeneroComoString();
                generoSet.add(tipoCancion);
            }
        }

        generoSet.add("Todos");

        genero.addAll(generoSet);
        return genero;
    }

    public List<String> obtenerDuracionCancionesSinRepetir() {
        List<String> duracion = new ArrayList<>();
        Set<String> duracionSet = new HashSet<>(); // Usamos un Set para evitar duplicados

        // Recorremos el árbol de artistas utilizando el iterador
        for (Artista artista : this.artistas.iterator()) {
            for (Cancion cancion : artista.getCanciones()){
                String cancionDuracion= cancion.getDuracion();
                duracionSet.add(cancionDuracion);
            }
        }

        duracionSet.add("Todos");

        duracion.addAll(duracionSet);
        return duracion;
    }

    public BinaryTree<Artista> obtenerMinimoFiltroArtistas(String minimoNacionalidad, String minimoTipo){
        BinaryTree<Artista> artistasFiltrados = new BinaryTree<>();

        // Iterar sobre el árbol de artistas
        for (Artista artista : this.artistas.iterator()) {
            // Verificar si el artista cumple con los criterios de filtro
            if (artista.getNacionalidad().equals(minimoNacionalidad) || artista.obtenerTipoArtistaString().equals(minimoTipo)) {
                artistasFiltrados.insert(artista); // Agregar el artista al nuevo árbol
            }
        }

        return artistasFiltrados;
    }

    public BinaryTree<Artista> obtenerMaximoFiltroArtistas(String minimoNacionalidad, String minimoTipo){
        BinaryTree<Artista> artistasFiltrados = new BinaryTree<>();

        // Iterar sobre el árbol de artistas
        for (Artista artista : this.artistas.iterator()) {
            // Verificar si el artista cumple con los criterios de filtro
            if (artista.getNacionalidad().equals(minimoNacionalidad) && artista.obtenerTipoArtistaString().equals(minimoTipo)) {
                artistasFiltrados.insert(artista); // Agregar el artista al nuevo árbol
            }
        }

        return artistasFiltrados;
    }

    /**
     * Este metodo toma en parametro la lista favorita de un cliente, y verifica los filtros tambien especificados por parametro
     * Nota: no se uso directamente en esta parte la busqueda por hilos ya que la lista de canciones favoritas
     * de un cliente se obtiene directamente del cliente, si se trata de usar un albor binario se abarcaran
     * otras canciones NO favoritas del mismo artista
     * @param listaFavs
     * @param genero
     * @param anio
     * @param duracion
     * @return
     */
    public ListaEnlazadaSimpleCircular<Cancion> obtenerListaMinimoFiltroDeFavoritos(ListaEnlazadaSimpleCircular<Cancion> listaFavs, String genero, String anio, String duracion){
        ListaEnlazadaSimpleCircular<Cancion> nuevaLista = new ListaEnlazadaSimpleCircular<>();

        // Utilizar un iterador para recorrer la lista
        Node<Cancion> currentNode = listaFavs.getHeadNode();
        if (currentNode != null) {
            do {
                Cancion cancion = currentNode.getData();
                if (cumpleMinimoUnFiltro(cancion, genero, anio, duracion)) {
                    nuevaLista.add(cancion);
                }
                currentNode = currentNode.getNextNode();
            } while (currentNode != null && currentNode != listaFavs.getHeadNode());
        }

        return nuevaLista;
    }



    /**
     * Este metodo toma en parametro la lista favorita de un cliente, y verifica los filtros tambien especificados por parametro
     * Nota: no se uso directamente en esta parte la busqueda por hilos ya que la lista de canciones favoritas
     * de un cliente se obtiene directamente del cliente, si se trata de usar un albor binario se abarcaran
     * otras canciones NO favoritas del mismo artista
     * @param listaFavs
     * @param genero
     * @param anio
     * @param duracion
     * @return
     */
    public ListaEnlazadaSimpleCircular<Cancion> obtenerListaMaximoFiltroDeFavoritos(ListaEnlazadaSimpleCircular<Cancion> listaFavs, String genero, String anio, String duracion){
        ListaEnlazadaSimpleCircular<Cancion> nuevaLista = new ListaEnlazadaSimpleCircular<>();

        // Utilizar un iterador para recorrer la lista
        Node<Cancion> currentNode = listaFavs.getHeadNode();
        if (currentNode != null) {
            do {
                Cancion cancion = currentNode.getData();
                if (cumpleTodosLosFiltros(cancion, genero, anio, duracion)) {
                    nuevaLista.add(cancion);
                }
                currentNode = currentNode.getNextNode();
            } while (currentNode != null && currentNode != listaFavs.getHeadNode());
        }

        return nuevaLista;
    }



    public static boolean cumpleMinimoUnFiltro(Cancion cancion, String genero, String anioLanzamiento, String duracion) {
        boolean cumpleGenero = genero.equals("Todos") || cancion.obtenerGeneroComoString().equals(genero);
        boolean cumpleAnioLanzamiento = anioLanzamiento.equals("Todos") || String.valueOf(cancion.getAnioLanzamiento()).equals(anioLanzamiento);
        boolean cumpleDuracion = duracion.equals("Todos") || cancion.getDuracion().equals(duracion);

        return cumpleGenero || cumpleAnioLanzamiento || cumpleDuracion;
    }

    public static boolean cumpleTodosLosFiltros(Cancion cancion, String genero, String anioLanzamiento, String duracion) {
        boolean cumpleGenero = genero.equals("Todos") || cancion.obtenerGeneroComoString().equals(genero) || genero.equals("Vacio");
        boolean cumpleAnioLanzamiento = anioLanzamiento.equals("Todos") || String.valueOf(cancion.getAnioLanzamiento()).equals(anioLanzamiento) || anioLanzamiento.equals("Vacio");
        boolean cumpleDuracion = duracion.equals("Todos") || cancion.getDuracion().equals(duracion) || duracion.equals("Vacio");

        return cumpleGenero && cumpleAnioLanzamiento && cumpleDuracion;
    }

    public BinaryTree<Artista> obtenerArbolPorArtista(Artista artistaPorBuscar) {
        BinaryTree<Artista> resultadoArbol= new BinaryTree<>();
        resultadoArbol.insert(artistaPorBuscar);
        return resultadoArbol;
    }



    private long obtenerCantidadReproducciones(String enlaceYouTube) throws IOException, GeneralSecurityException {
        return YouTubeHelper.obtenerVistasVideo(enlaceYouTube); // Llama a la clase YouTubeHelper
    }




}