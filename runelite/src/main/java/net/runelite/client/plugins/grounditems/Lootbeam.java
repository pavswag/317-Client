/*
 * Copyright (c) 2021, Trevor <https://github.com/Trevor159>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.grounditems;

import lombok.RequiredArgsConstructor;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.callback.ClientThread;
import net.runelite.rs.api.RSModelData;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

class Lootbeam {
	private final RuneLiteObject runeLiteObject;

	private final Client client;

	private final ClientThread clientThread;

	private Color color;

	private Style style;

	public enum Style {
		LIGHT(l -> l.client.loadModel(5809, new short[] { 6371 }, new short[] { JagexColor.rgbToHSL(l.color.getRGB(), 1.0D) }), Lootbeam.anim(3101))
		, MODERN(l -> {
			short hsl = JagexColor.rgbToHSL(l.color.getRGB(), 1.0D);
			int hue = JagexColor.unpackHue(hsl);
			int sat = JagexColor.unpackSaturation(hsl);
			int lum = JagexColor.unpackLuminance(hsl);
			int satDelta = (sat > 2) ? 1 : 0;
			Model md = l.client.loadModel(43330, new short[] { 26432, 26584 }, new short[] { JagexColor.packHSL(hue, sat - satDelta, lum), JagexColor.packHSL(hue, sat, Math.min(lum + 24, 127)) });
			if (md == null)
				return null;
			md.light(139, 2643, -50, -10, -50);
			return md;
		}, Lootbeam.anim(9260));

		private final Function<Lootbeam, Model> modelSupplier;

		private final Function<Lootbeam, Animation> animationSupplier;

		Style(Function<Lootbeam, Model> modelSupplier, Function<Lootbeam, Animation> animationSupplier) {
			this.modelSupplier = modelSupplier;
			this.animationSupplier = animationSupplier;
		}
	}

	private static Function<Lootbeam, Animation> anim(int id) {
		return b -> b.client.loadAnimation(id);
	}

	public Lootbeam(Client client, ClientThread clientThread, WorldPoint worldPoint, Color color, Style style) {
		this.client = client;
		this.clientThread = clientThread;
		this.runeLiteObject = client.createRuneLiteObject();
		this.color = color;
		this.style = style;
		update();
		this.runeLiteObject.setShouldLoop(true);
		LocalPoint lp = LocalPoint.fromWorld(client, worldPoint);
		this.runeLiteObject.setLocation(lp, client.getPlane());
		this.runeLiteObject.setActive(true);
	}

	public void setColor(Color color) {
		if (this.color != null && this.color.equals(color))
			return;
		this.color = color;
		update();
	}

	public void setStyle(Style style) {
		if (this.style == style)
			return;
		this.style = style;
		update();
	}

	private void update() {
		this.clientThread.invoke(() -> {
			Model model = this.style.modelSupplier.apply(this);
			if (model == null)
				return false;
			Animation anim = this.style.animationSupplier.apply(this);
			this.runeLiteObject.setAnimation(anim);
			this.runeLiteObject.setModel(model);
			return true;
		});
	}

	public void remove() {
		this.runeLiteObject.setActive(false);
	}
}
