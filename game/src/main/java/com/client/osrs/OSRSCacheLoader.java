package com.client.osrs;

import com.client.Script;
import com.client.definitions.SeqDefinition;
import com.client.definitions.SeqFrame;
import com.client.definitions.anims.defs.SeqBaseDefs;
import com.client.definitions.anims.defs.SeqDefs;
import com.client.definitions.anims.defs.SeqFrameDefs;
import com.client.graphics.sprites.OsrsSprite;
import com.client.sign.Signlink;
import lombok.Getter;

public class OSRSCacheLoader {

    private static CacheWrapper cacheWrapper;

    @Getter
    private static SeqDefs seqDefs;

    @Getter
    private static OsrsSprite osrsSprite;

    @Getter
    private static SeqFrameDefs seqFrameDefs;
    private static SeqBaseDefs seqBaseDefs;


    /**
     * Initiator
     */
    public static void init() {
        if(cacheWrapper != null) {
            return;
        }
        CacheWrapper.setCachePath(Signlink.getCacheDirectory() + "osrs/");
        cacheWrapper = new CacheWrapper();
        seqDefs = SeqDefs.load(cacheWrapper);
        seqBaseDefs = SeqBaseDefs.load(cacheWrapper);
        seqFrameDefs = SeqFrameDefs.load(cacheWrapper, seqBaseDefs);
        osrsSprite = new OsrsSprite();
        osrsSprite.startup();
        Script.loadScripts(cacheWrapper);
        System.out.println("Loaded " + osrsSprite.cacheSize() + " OSRS Sprites.");
        System.out.println("Loaded osrs sequence: " + cacheWrapper.isOSRS() + " | " + seqDefs.getDefinitions().length + " and " + seqFrameDefs.getDefinitions().size() + " and " + seqBaseDefs.getDefinitions().size());
    }

    /**
     * Graphics & Animations
     * @param id
     * @return
     */
    public static SeqDefinition getSeqDef(int id) {
        return seqDefs.get(id);
    }

    public static SeqFrame getSeqFrame(int id) {
        return seqFrameDefs.forID(id);
    }

}
