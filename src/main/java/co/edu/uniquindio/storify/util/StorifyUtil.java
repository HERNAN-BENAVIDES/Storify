package co.edu.uniquindio.storify.util;

import co.edu.uniquindio.storify.model.*;

@SuppressWarnings("all")
public class StorifyUtil {

    public static TiendaMusica inicializarDatosPrueba() {
        TiendaMusica tiendaMusica = new TiendaMusica();

        Administrador administrador = new Administrador();

        Cliente persona1 = new Cliente("Pedro", "Perez");

        persona1.getCancionesFavoritas().add(new Cancion("001", "Thriller", "Thriller", "", 1982, "5:57", TipoGenero.POP,""));
        persona1.getCancionesFavoritas().add(new Cancion("002", "Billie Jean", "Thriller", "", 1982, "4.54", TipoGenero.POP,""));

        Usuario usuario = new Usuario("admin", "$aDmiN", "admin@gmail", administrador);
        Usuario usuario1 = new Usuario("client", "$cLiEnT", "client@gmail", persona1);


        tiendaMusica.getUsuarios().put("admin", usuario);
        tiendaMusica.getUsuarios().put("client", usuario1);

        // Datos de artistas
        Artista artista1 = new Artista("ART001", "Michael Jackson", "Estados Unidos", TipoArtista.SOLISTA);
        artista1.getCanciones().add(new Cancion("001", "Thriller", "Thriller", "", 1982, "5.57", TipoGenero.POP,""));
        artista1.getCanciones().add(new Cancion("002", "Billie Jean", "Thriller", "", 1982, "4.54", TipoGenero.POP,""));
        tiendaMusica.getArtistas().put("ART001", artista1);

        Artista artista2 = new Artista("ART002", "The Beatles", "Reino Unido", TipoArtista.BANDA);
        artista2.getCanciones().add(new Cancion("003", "Hey Jude", "The Beatles (The White Album)", "", 1968, "7.11", TipoGenero.ROCK,""));
        artista2.getCanciones().add(new Cancion("004", "Let It Be", "Let It Be", "", 1970, "3.50", TipoGenero.ROCK,""));
        tiendaMusica.getArtistas().put("ART002", artista2);

        Artista artista3 = new Artista("ART003", "Beyoncé", "Estados Unidos", TipoArtista.SOLISTA);
        artista3.getCanciones().add(new Cancion("005", "Single Ladies (Put a Ring on It)", "I Am... Sasha Fierce", "", 2008, "3.13", TipoGenero.POP,""));
        artista3.getCanciones().add(new Cancion("006", "Halo", "I Am... Sasha Fierce", "", 2008, "4.22", TipoGenero.POP,""));
        tiendaMusica.getArtistas().put("ART003", artista3);

        Artista artista4 = new Artista("ART004", "Queen", "Reino Unido", TipoArtista.BANDA);
        artista4.getCanciones().add(new Cancion("007", "Bohemian Rhapsody", "A Night at the Opera", "", 1975, "5.55", TipoGenero.ROCK,""));
        artista4.getCanciones().add(new Cancion("008", "We Will Rock You", "News of the World", "", 1977, "2.02", TipoGenero.ROCK,""));
        tiendaMusica.getArtistas().put("ART004", artista4);


        return tiendaMusica;
    }
}