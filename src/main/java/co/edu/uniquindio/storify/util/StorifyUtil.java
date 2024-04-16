package co.edu.uniquindio.storify.util;

import co.edu.uniquindio.storify.model.*;

@SuppressWarnings("all")
public class StorifyUtil {

    public static TiendaMusica inicializarDatosPrueba() {
        TiendaMusica tiendaMusica = new TiendaMusica();

        // Datos de artistas
        Artista artista1 = new Artista("ART001", "Michael Jackson", "Estados Unidos", false);
        artista1.getCanciones().add(new Cancion("001", "Thriller", "Thriller", "", 1982, 5.57, TipoGenero.POP,""));
        artista1.getCanciones().add(new Cancion("002", "Billie Jean", "Thriller", "", 1982, 4.54, TipoGenero.POP,""));
        tiendaMusica.getArtistas().put("ART001", artista1);

        Artista artista2 = new Artista("ART002", "The Beatles", "Reino Unido", true);
        artista2.getCanciones().add(new Cancion("003", "Hey Jude", "The Beatles (The White Album)", "", 1968, 7.11, TipoGenero.ROCK,""));
        artista2.getCanciones().add(new Cancion("004", "Let It Be", "Let It Be", "", 1970, 3.50, TipoGenero.ROCK,""));
        tiendaMusica.getArtistas().put("ART002", artista2);

        Artista artista3 = new Artista("ART003", "Beyoncé", "Estados Unidos", false);
        artista3.getCanciones().add(new Cancion("005", "Single Ladies (Put a Ring on It)", "I Am... Sasha Fierce", "", 2008, 3.13, TipoGenero.POP,""));
        artista3.getCanciones().add(new Cancion("006", "Halo", "I Am... Sasha Fierce", "", 2008, 4.22, TipoGenero.POP,""));
        tiendaMusica.getArtistas().put("ART003", artista3);

        Artista artista4 = new Artista("ART004", "Queen", "Reino Unido", true);
        artista4.getCanciones().add(new Cancion("007", "Bohemian Rhapsody", "A Night at the Opera", "", 1975, 5.55, TipoGenero.ROCK,""));
        artista4.getCanciones().add(new Cancion("008", "We Will Rock You", "News of the World", "", 1977, 2.02, TipoGenero.ROCK,""));
        tiendaMusica.getArtistas().put("ART004", artista4);

        // Datos de usuarios
        tiendaMusica.getUsuarios().put("user1", new Usuario("user1", "password1", "user1@example.com"));
        tiendaMusica.getUsuarios().put("user2", new Usuario("user2", "password2", "user2@example.com"));
        tiendaMusica.getUsuarios().put("user3", new Usuario("user3", "password3", "user3@example.com"));
        tiendaMusica.getUsuarios().put("user4", new Usuario("user4", "password4", "user4@example.com"));

        return tiendaMusica;
    }
}
