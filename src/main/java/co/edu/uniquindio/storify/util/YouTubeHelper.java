package co.edu.uniquindio.storify.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class YouTubeHelper {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String API_KEY = "AIzaSyAJsq8canZkjSv_6SvKVSbHcXhoVk5PJUM"; // Reemplaza con tu clave de API


    public static int obtenerVistasVideo(String url) throws GeneralSecurityException, IOException {
        String videoId = obtenerVideoId(url); // El ID del video que deseas analizar
       return getYouTubeVideoViews(videoId);
    }
    
    private static String obtenerVideoId(String url) {
        return url.substring(url.lastIndexOf("=") + 1);
    }

    private static int getYouTubeVideoViews(String videoId) throws IOException, GeneralSecurityException {
        final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        YouTube youtubeService = new YouTube.Builder(httpTransport, JSON_FACTORY, getRequestInitializer()).setApplicationName("YouTube-Video-Stats").build();

        YouTube.Videos.List request = youtubeService.videos().list(Collections.singletonList("statistics"));
        VideoListResponse response = request.setKey(API_KEY).setId(Collections.singletonList(videoId)).execute();
        List<Video> videos = response.getItems();

        if (videos != null && !videos.isEmpty()) {
            Video video = videos.get(0);
            return Integer.parseInt(String.valueOf(video.getStatistics().getViewCount()));
        } else {
            System.out.println("No se encontró información para el video con ID: " + videoId);
            return -1;
        }
    }

    private static HttpRequestInitializer getRequestInitializer() {
        return httpRequest -> {
            // No hay inicializaciones especiales necesarias
        };
    }
}
