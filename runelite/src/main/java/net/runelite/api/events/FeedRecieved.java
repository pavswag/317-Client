package net.runelite.api.events;

import lombok.Data;
import lombok.Value;
import net.runelite.http.api.feed.FeedItem;

import java.util.List;

@Value
public class FeedRecieved {
    private List<FeedItem> items;
}
