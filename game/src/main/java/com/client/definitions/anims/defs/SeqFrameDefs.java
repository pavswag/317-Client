package com.client.definitions.anims.defs;

import com.client.definitions.SeqBase;
import com.client.definitions.SeqFrame;
import com.client.definitions.anims.decoder.Decoder;
import com.client.definitions.anims.decoder.impl.SeqFrameDecoderOSRS;
import com.client.osrs.CacheWrapper;
import com.displee.cache.index.Index;
import com.displee.cache.index.archive.file.File;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@RequiredArgsConstructor
public class SeqFrameDefs {

    private final Map<Integer, List<SeqFrame>> definitions;
    private final CacheWrapper cacheWrapper;
    private final SeqBaseDefs seqBaseDefs;

    @Getter
    private static Index frameIndex;

    public SeqFrame forID(int frameId) {
        int frameGroupId = frameId >> 16;
        int frameFileId = frameId & 0xFFFF;

        if (definitions.get(frameGroupId) == null) {
            loadGroup(frameGroupId);
        }

        List<SeqFrame> frames = definitions.get(frameGroupId);
        SeqFrame frame = frameFileId >= frames.size() ? null : frames.get(frameFileId);
        if (frame == null) {
            System.out.println(frameFileId + " doesn't exist for " + frameGroupId + " | Has: " + frames.size() + " frames");
        }
        return frame;
    }

    public void loadGroup(int frameGroupId) {
        loadGroupOSRS(frameGroupId);
    }

    private void loadGroupOSRS(int frameGroupId) {

        if (frameIndex.archive(frameGroupId) == null) {
            return;
        }
        Collection<File> frameFiles = frameIndex.archive(frameGroupId).getFiles().values();
        int frameCount = Collections.max(frameFiles, Comparator.comparingInt(File::getId))
                .getId() + 1;

        definitions.put(frameGroupId, new ArrayList<>(Collections.nCopies(frameCount, null)));
        for (File file : frameFiles) {
            byte[] data = file.getData();
            if (data.length == 0) {
                continue;
            }
            int baseGroupId = (data[0] & 0xff) << 8 | data[1] & 0xff;
            SeqBase transform = seqBaseDefs.get(baseGroupId);
            Decoder<SeqFrame> decoder = new SeqFrameDecoderOSRS();
            decoder.decode(data, transform, definitions, frameGroupId, file.getId());
        }
    }

    public static SeqFrameDefs load(CacheWrapper cacheWrapper, SeqBaseDefs seqBaseDefs) {
        frameIndex = cacheWrapper.getIndex(0);
        int count = frameIndex.archives().length;
        Map<Integer, List<SeqFrame>> definitions = new HashMap<>(count);
        SeqFrameDefs seqFrameDefs = new SeqFrameDefs(definitions, cacheWrapper, seqBaseDefs);
        // for (int groupId : frameIndex.archiveIds()) {
        // seqFrameDefs.loadGroupOSRS(groupId);
        //}
        return seqFrameDefs;
    }
}

