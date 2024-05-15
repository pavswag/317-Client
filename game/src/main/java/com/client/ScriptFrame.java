package com.client;

import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

public class ScriptFrame {
    @ObfuscatedName("df")
    static boolean field460;
    @ObfuscatedName("az")
    @ObfuscatedSignature(
            descriptor = "Ldn;"
    )
    @Export("script")
    Script script;
    @ObfuscatedName("ah")
    @ObfuscatedGetter(
            intValue = -1861957827
    )
    @Export("pc")
    int pc;
    @ObfuscatedName("af")
    @Export("intLocals")
    int[] intLocals;
    @ObfuscatedName("at")
    @Export("stringLocals")
    String[] stringLocals;

    ScriptFrame() {
        this.pc = -1;
    }
}
