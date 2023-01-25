package engine.graphics.maps.tiles;

import engine.physics.maths.Vector2f;

public class Tilemap
{
	public int tileWidth;
	public int tileHeight;
	public int numTileX;
	public int numTileY;
	private int[] tiles;
	private Tile[] map;
	public Tilemap(int numX, int numY, int[] tiles, Tileset set)
	{
		this.tileWidth = set.width;
		this.tileHeight = set.height;
		this.numTileX = numX;
		this.numTileY = numY;
		this.tiles = tiles;
		this.map = new Tile[numTileX * numTileY];
		for (int y = 0; y < numY; y++)
		{
			for (int x = 0; x < numX; x++)
			{
				map[x + y * numX] = set.getTile(tiles[x + y * numX]);
			}
		}
	}
	public Tile getTile(int x, int y)
	{
		return map[x + y * numTileX];
	}
	public int getTileID(int x, int y)
	{
		return tiles[x + y * numTileX];
	}
	public Tile getTile(Vector2f v)
	{
		if (v.x >= 0 && v.y >= 0 && v.x < numTileX && v.y < numTileY)return map[((int)v.x + (int)v.y * numTileX)];
		return new Tile(tileWidth, tileHeight, 0);
	}
	public int getTileID(Vector2f v)
	{
		if (v.x >= 0 && v.y >= 0 && v.x < numTileX && v.y < numTileY)return tiles[((int)v.x + (int)v.y * numTileX)];
		return 0;
	}
}
