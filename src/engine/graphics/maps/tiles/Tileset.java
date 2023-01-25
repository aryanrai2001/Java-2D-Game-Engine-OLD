package engine.graphics.maps.tiles;

import java.util.HashMap;

public class Tileset
{
	private HashMap<Integer, Tile> tiles;
	public int count;
	public int width, height;
	public Tileset(int width, int height)
	{
		count = 0;
		this.width = width;
		this.height = height;
		tiles = new HashMap<Integer, Tile>();
	}
	public Tileset(int width, int height, String path)
	{
		count = 0;
		this.width = width;
		this.height = height;
		tiles = new HashMap<Integer, Tile>();
	}
	public void addTile(int index, Tile tile)
	{
		if (tile.getWidth() != width || tile.getHeight() != height)return;
		count++;
		tiles.put(index, tile);
	}
	public Tile getTile(int key)
	{
		return tiles.get(key);
	}
}
