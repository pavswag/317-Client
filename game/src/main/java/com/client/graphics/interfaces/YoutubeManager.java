package com.client.graphics.interfaces;

import com.client.Client;
import com.client.RSFont;

import java.util.ArrayList;

/**
 * @author ArkCane
 * @social Discord: ArkCane
 * Website: www.arkcane.net
 * @since 19/03/2024
 */
public class YoutubeManager {

    public static ArrayList<YouTubeVideo> videos = new ArrayList<>();

    public static void addVideo(YouTubeVideo video) {
        videos.add(video);

        update();
    }
    public static void update() {

        int itemChildId = 24810;

        itemChildId++;

        for(int childId = 0; childId < 20; childId++) {

            boolean visible = childId < videos.size();

            RSInterface.interfaceCache[itemChildId].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 1].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 2].invisible = !visible;

            RSInterface.interfaceCache[itemChildId + 3].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 4].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 5].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 6].invisible = !visible;

            YouTubeVideo youTubeVideo = visible ? videos.get(childId) : null;

            if(youTubeVideo != null) {
                RSInterface.interfaceCache[itemChildId].setSprite("https://img.youtube.com/vi/" + youTubeVideo.getVideoId() + "/mqdefault.jpg", 115, 64);
                RSInterface.interfaceCache[itemChildId + 1].message = youTubeVideo.getTitle();
                RSInterface.interfaceCache[itemChildId + 2].message = wrapLine(Client.instance.newSmallFont, youTubeVideo.getDescription(), 300);
                RSInterface.interfaceCache[itemChildId + 3].message = youTubeVideo.getUploader();
            }

            itemChildId += 7;

        }
    }

    private static String wrapLine(RSFont font, String text, int maxWidth) {
        if (text == null) {
            return "";
        }
        if (maxWidth <= 0) {
            return text;
        }

        if (font.getTextWidth(text) <= maxWidth && !text.contains("\\n")) {
            return text;
        }

        char[] chars = text.toCharArray();

        StringBuilder lines = new StringBuilder();

        StringBuilder line = new StringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            word.append(chars[i]);
            if (chars[i] == ' ') {
                if (font.getTextWidth(line.toString() + word.toString()) > maxWidth) {
                    lines.append(line.toString()+"\\n");
                    line.delete(0, line.length());
                }
                line.append(word);
                word.delete(0, word.length());
            }
        }
        if (font.getTextWidth(word.toString()) > 0) {
            if (font.getTextWidth(line.toString() + word.toString()) > maxWidth) {
                lines.append(line.toString()+"\\n");
                line.delete(0, line.length());
            }
            line.append(word);
        }
        if (font.getTextWidth(line.toString()) > 0) {
            lines.append(line.toString()+"\\n");
        }
        return lines.toString();
    }
}
