package co.edu.uniquindio.storify.util;

import co.edu.uniquindio.storify.model.*;

@SuppressWarnings("all")
public class StorifyUtil {

    public static TiendaMusica inicializarDatosPrueba() {
        TiendaMusica tiendaMusica = new TiendaMusica();

        //Datos de administrador
        Administrador administrador = new Administrador();
        Cliente persona1 = new Cliente("Mary", "Saire");

        Usuario usuario = new Usuario("admin", "$aDmiN", "admin@gmail", administrador);
        Usuario usuario1 = new Usuario("Mary", "1234", "mary@gmail.com", persona1);
        tiendaMusica.getUsuarios().put("admin", usuario);
        tiendaMusica.getUsuarios().put(usuario.getUsername(), usuario1);

        // Datos de artistas
        Artista artista1 = new Artista("ART001", "Michael Jackson", "Estados Unidos", TipoArtista.SOLISTA);
        Artista artista2 = new Artista("ART002", "The Beatles", "Reino Unido", TipoArtista.BANDA);
        Artista artista3 = new Artista("ART003", "Beyoncé", "Estados Unidos", TipoArtista.SOLISTA);
        Artista artista4 = new Artista("ART004", "Queen", "Reino Unido", TipoArtista.BANDA);
        Artista artista5 = new Artista("ART005", "The Weeknd", "Canada", TipoArtista.SOLISTA);
        Artista artista6 =new Artista("ART006", "Miro", "Mexico", TipoArtista.BANDA);
        Artista artista7 = new Artista("ART007", "Coldplay", "Inglaterra", TipoArtista.BANDA);

        tiendaMusica.getArtistas().insert(artista1);
        tiendaMusica.getArtistas().insert(artista2);
        tiendaMusica.getArtistas().insert(artista3);
        tiendaMusica.getArtistas().insert(artista4);
        tiendaMusica.getArtistas().insert(artista5);
        tiendaMusica.getArtistas().insert(artista6);
        tiendaMusica.getArtistas().insert(artista7);

        // Datos adicionales de artistas y canciones
        Cancion cancion1 = new Cancion("SONG001", "Die For You", "Starboy", "/imagenes/starboy.jpeg", 2016, "4:20", TipoGenero.OTRO, "https://www.youtube.com/watch?v=mTLQhPFx2nM&list=PLWGXKDxW301QZrzSl7hLzdYakFdayHC4l&index=17&ab_channel=TheWeeknd-Topic");
        Cancion cancion2 = new Cancion("SONG002","I Feel It Coming", "Starboy", "/imagenes/starboy.jpeg", 2016, "4:29", TipoGenero.OTRO, "https://www.youtube.com/watch?v=qPRNIHxLhmc&list=PLWGXKDxW301QZrzSl7hLzdYakFdayHC4l&index=18&ab_channel=TheWeeknd-Topic");
        Cancion cancion3 = new Cancion("SONG003","En Un Segundo", "Miró", "/imagenes/miroalbum.jpg", 2010, "3:32", TipoGenero.ROCK, "https://www.youtube.com/watch?v=vHXhIY45t7I&ab_channel=Miro-Topic");
        Cancion cancion4 = new Cancion("SONG004","Sparks", "Parachutes", "/imagenes/parachutes.jpeg", 2000, "3:47", TipoGenero.ROCK, "https://www.youtube.com/watch?v=Ar48yzjn1PE&ab_channel=SomberSounds");

        artista5.agregarCancion(cancion1);
        artista5.agregarCancion(cancion2);
        artista6.agregarCancion(cancion3);
        artista7.agregarCancion(cancion4);


        persona1.agregarCancionFavorita(cancion1);
        persona1.agregarCancionFavorita(cancion3);
        persona1.agregarCancionFavorita(cancion2);
        persona1.agregarCancionFavorita(cancion4);

        return tiendaMusica;
    }
}
