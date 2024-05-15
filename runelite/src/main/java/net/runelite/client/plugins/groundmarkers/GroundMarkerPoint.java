//
// Decompiled by Procyon v0.5.36
//

package net.runelite.client.plugins.groundmarkers;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.annotation.Nullable;
import java.awt.Color;

@Value
@EqualsAndHashCode(exclude = { "color", "label" })
final class GroundMarkerPoint
{
	private final int regionId;
	private final int regionX;
	private final int regionY;
	private final int z;
	private final Color color;
	@Nullable
	private final String label;

	public GroundMarkerPoint(final int regionId, final int regionX, final int regionY, final int z, final Color color, @Nullable final String label) {
		this.regionId = regionId;
		this.regionX = regionX;
		this.regionY = regionY;
		this.z = z;
		this.color = color;
		this.label = label;
	}

	public int getRegionId() {
		return this.regionId;
	}

	public int getRegionX() {
		return this.regionX;
	}

	public int getRegionY() {
		return this.regionY;
	}

	public int getZ() {
		return this.z;
	}

	public Color getColor() {
		return this.color;
	}

	@Nullable
	public String getLabel() {
		return this.label;
	}

	@Override
	public String toString() {
		return "GroundMarkerPoint(regionId=" + this.getRegionId() + ", regionX=" + this.getRegionX() + ", regionY=" + this.getRegionY() + ", z=" + this.getZ() + ", color=" + this.getColor() + ", label=" + this.getLabel() + ")";
	}
}
