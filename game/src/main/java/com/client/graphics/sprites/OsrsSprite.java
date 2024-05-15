package com.client.graphics.sprites;

import com.client.Sprite;
import com.client.osrs.CacheWrapper;
import com.client.sign.Signlink;
import com.displee.cache.index.Index;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.SpriteID;

import java.io.File;

public class OsrsSprite {
    @Getter
    @Setter
    private Sprite[] cache = null;

    public Sprite getSprite(int id) {
        if(cache == null)
            return null;
        if(cache[id] == null) {
            cache[id] = load(id);
        }
        return cache[id];
    }

    public void startup() {
        File[] files = spriteDirectory.listFiles();
        if (files == null) {
            System.err.println("Error: Unable to list files in the directory: " + spriteDirectory);
            return;
        }
        cache = new Sprite[files.length];
    }

    public Sprite load(int id) {
        if(cache[id] != null) {
            return cache[id];
        }
        Sprite sprite = new Sprite(id, true);
//        if(sprite != null)
//            System.out.println("Loaded OSRS Sprite " + SpriteID.spriteNames.getOrDefault(id, "UNIDENTIFIED(" + id + ")" + "]"));
        return sprite;
    }

    public int cacheSize() { return cache.length; }

    @Getter
    private static final File spriteDirectory = new File(Signlink.getCacheDirectory() + "sprites/osrs/");
}
