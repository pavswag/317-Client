package net.runelite.api;

public interface ObjectComposition extends ParamHolder {
	int getId();

	String getName();

	String[] getActions();

	int getMapSceneId();

	int getMapIconId();

	int[] getImpostorIds();

	ObjectComposition getImpostor();

	int getAccessBitMask();
}
