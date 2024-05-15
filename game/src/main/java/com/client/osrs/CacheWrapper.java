package com.client.osrs;

import com.displee.cache.CacheLibrary;
import com.displee.cache.index.Index;
import com.displee.cache.index.archive.Archive;
import lombok.Getter;
import lombok.Setter;

public class CacheWrapper {
    @Getter
    private final CacheLibrary cache;
    private final boolean osrs;
    @Setter
    private static String cachePath;

    public CacheWrapper() {
        cache = CacheLibrary.create(cachePath);
        osrs = cache.isOSRS();
        System.out.println("Initialized osrs cache");
    }

    public Archive getArchive(int index, int archive) {
        return cache.index(index).archive(archive);
    }

    public boolean isOSRS() {
        return osrs;
    }

    public Index getIndex(int index) {
        return cache.index(index);
    }

}