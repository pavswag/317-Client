package net.runelite.client.plugins.feed;

import net.runelite.http.api.feed.FeedResult;

import java.util.function.Supplier;

public class FeedResultSupplier {
    private FeedResult feedResult; // The value that the supplier will provide

    public void setFeedResult(FeedResult feedResult) {
        this.feedResult = feedResult; // Set the value of the feed result
    }

    public Supplier<FeedResult> getSupplier() {
        return () -> feedResult; // Provide the stored value via the Supplier
    }
}
