package com.client.definitions.anims.defs;

import com.client.InputBuffer;
import com.client.definitions.SeqBase;
import com.client.definitions.anims.decoder.Decoder;
import com.client.definitions.anims.decoder.impl.SeqBaseDecoderOSRS;
import com.client.osrs.CacheWrapper;
import com.displee.cache.index.Index;
import com.displee.cache.index.archive.ArchiveSector;
import com.displee.compress.CompressionExtKt;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class SeqBaseDefs {
    private final List<SeqBase> definitions;
    private final CacheWrapper cacheWrapper;

    @Getter
    private static Index baseIndex;

    public SeqBase get(int id) {
        if (definitions.get(id) == null) {
            loadId(id);
        }

        return definitions.get(id);
    }

    private void loadId(int id) {
        ArchiveSector archiveSector = baseIndex.readArchiveSector(id);
        if (archiveSector != null) {
            byte[] baseData = CompressionExtKt.decompress(archiveSector, null);
            InputBuffer buffer = new InputBuffer(baseData);
            Decoder<SeqBase> decoder = new SeqBaseDecoderOSRS();
            SeqBase base = new SeqBase();
            base.setGroupId(id);
            decoder.decode(buffer, base);
            definitions.set(id, base);
        }
    }

    public static SeqBaseDefs load(CacheWrapper cacheWrapper) {
        baseIndex = cacheWrapper.getIndex(1);
        int length = baseIndex.archives().length;
        List<SeqBase> definitions = new ArrayList<>(Collections.nCopies(length, null));
        SeqBaseDefs seqBaseDefs = new SeqBaseDefs(definitions, cacheWrapper);
        //for (int archiveId : baseIndex.archiveIds()) {
        //  seqBaseDefs.loadId(archiveId);
        //}
        return seqBaseDefs;
    }
}
