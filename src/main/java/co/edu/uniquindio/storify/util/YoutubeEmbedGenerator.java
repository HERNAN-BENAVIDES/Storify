package co.edu.uniquindio.storify.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeEmbedGenerator {

    public static String obtenerEmbedCode1212(String url) {
        // Expresión regular para detectar enlaces de YouTube
        Pattern youtubePattern = Pattern.compile("(?:(?:https?:)?\\/\\/)?(?:www\\.)?(?:youtube\\.com\\/watch\\?v=|youtu\\.be\\/)([\\w\\-]+)(?:[?&].*)?$");
        Matcher youtubeMatcher = youtubePattern.matcher(url);

        if (youtubeMatcher.find()) {
            String videoId = youtubeMatcher.group(1);
            return String.format("<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/%s\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>", videoId);
        }

        // Expresión regular para detectar enlaces de Dailymotion
        Pattern dailymotionPattern = Pattern.compile("(?:https?:\\/\\/)?(?:www\\.)?(?:dailymotion\\.com\\/(?:video|embed)\\/|dai\\.ly\\/)([\\w\\-]+)");
        Matcher dailymotionMatcher = dailymotionPattern.matcher(url);

        if (dailymotionMatcher.find()) {
            String videoId = dailymotionMatcher.group(1);
            return String.format("<iframe style=\"width:100%%;height:100%%;position:absolute;left:0px;top:0px;overflow:hidden\" frameborder=\"0\" type=\"text/html\" src=\"https://www.dailymotion.com/embed/video/%s?autoplay=1\" width=\"100%%\" height=\"100%%\" allowfullscreen title=\"Dailymotion Video Player\" allow=\"autoplay\"></iframe>", videoId);
        }

        return null; // Retornar null si la URL no corresponde a YouTube ni a Dailymotion
    }




    public static String obtenerEmbedCode(String urlYoutubeDaily) {
        // Expresión regular para detectar enlaces de YouTube
        Pattern youtubePattern = Pattern.compile("(?:(?:https?:)?\\/\\/)?(?:www\\.)?(?:youtube\\.com\\/watch\\?v=|youtu\\.be\\/)([\\w\\-]+)(?:[?&].*)?$");
        Matcher youtubeMatcher = youtubePattern.matcher(urlYoutubeDaily);

        if (youtubeMatcher.find()) {
            String videoId = youtubeMatcher.group(1);
            return String.format("<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/%s\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>", videoId);
        }

        // Expresión regular para detectar enlaces de Dailymotion
        Pattern dailymotionPattern = Pattern.compile("(?:https?:\\/\\/)?(?:www\\.)?(?:dailymotion\\.com\\/(?:video|embed)\\/|dai\\.ly\\/)([\\w\\-]+)");
        Matcher dailymotionMatcher = dailymotionPattern.matcher(urlYoutubeDaily);

        if (dailymotionMatcher.find()) {
            System.out.println("es dialy");
            String videoId = dailymotionMatcher.group(1);
            return String.format("<div style=\"position:relative;padding-bottom:56.25%%;height:0;overflow:hidden;\"><iframe style=\"width:100%%;height:100%%;position:absolute;left:0px;top:0px;overflow:hidden\" frameborder=\"0\" type=\"text/html\" src=\"https://www.dailymotion.com/embed/video/%s?autoplay=1\" width=\"100%%\" height=\"100%%\" allowfullscreen title=\"Dailymotion Video Player\" allow=\"autoplay\"></iframe></div>", videoId);

        }

        return null; // Retornar null si la URL no corresponde a YouTube ni a Dailymotion
    }


}

