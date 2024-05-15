package net.runelite.rs.api;

import net.runelite.api.Model;
import net.runelite.mapping.Import;

public interface RSModelData extends RSRenderable
{


	@Import("verticesCount")
	int getVerticesCount();

	@Import("recolor")
	Model recolor(short var1, short var2);

	@Import("ambient")
	short getAmbient();

	@Import("contrast")
	short getContrast();
}
