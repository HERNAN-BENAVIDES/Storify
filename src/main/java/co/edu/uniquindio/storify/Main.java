package co.edu.uniquindio.storify;

import co.edu.uniquindio.storify.util.YouTubeHelper;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
     public static void main(String[] args) throws GeneralSecurityException, IOException {
          String url = "https://www.youtube.com/watch?v=QLCpqdqeoII";
          long vistas = YouTubeHelper.obtenerVistasVideo(url);
          System.out.println(vistas);
     }
}
