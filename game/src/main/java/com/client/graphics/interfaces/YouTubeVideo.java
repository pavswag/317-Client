package com.client.graphics.interfaces;

/**
 * @author ArkCane
 * @social Discord: ArkCane
 * Website: www.arkcane.net
 * @since 19/03/2024
 */
public class YouTubeVideo {
    private final String videoId;
    private final String uploader;
    private final String title;
    private final String description;

    public YouTubeVideo(String videoId, String uploader, String title, String description) {
        this.videoId = videoId;
        this.uploader = uploader;
        this.title = title;
        this.description = description;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getUploader() {
        return uploader;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
