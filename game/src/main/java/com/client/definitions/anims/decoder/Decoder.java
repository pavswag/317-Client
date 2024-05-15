package com.client.definitions.anims.decoder;

import com.client.InputBuffer;
import com.client.definitions.SeqBase;
import com.client.definitions.SeqFrame;

import java.util.List;
import java.util.Map;

public interface Decoder<T> {
    default void decode(InputBuffer buffer, T definition) {

    }

    default void decode(byte[] data, T definition) {

    }

    default void decode(byte[] data, SeqBase transform, Map<Integer, List<SeqFrame>> definitions, int frameGroupId, int frameFileId) {

    }

    default void decode(InputBuffer buffer, Map<Integer, List<SeqFrame>> definitions, int frameGroupId) {

    }

    default void decode(InputBuffer buffer, int id, T definition) {

    }

    default void decode(InputBuffer buffer, int id, List<T> definitions) {

    }

}
