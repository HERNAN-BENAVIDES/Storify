package co.edu.uniquindio.storify.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeEmbedGenerator {

    public static String obtenerEmbedCode(String urlYoutube) {

        Pattern youtubePattern = Pattern.compile("(?:(?:https?:)?\\/\\/)?(?:www\\.)?(?:youtube\\.com\\/watch\\?v=|youtu\\.be\\/)([\\w\\-]+)(?:[?&].*)?$");
        Matcher youtubeMatcher = youtubePattern.matcher(urlYoutube);

        if (youtubeMatcher.find()) {
            String videoId = youtubeMatcher.group(1);
            return String.format("https://www.youtube.com/embed/%s", videoId);
        }

        return null;
    }


}

