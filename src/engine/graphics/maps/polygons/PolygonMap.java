package engine.graphics.maps.polygons;

import java.util.Comparator;
import java.util.LinkedList;

import engine.system.Renderer;

public class PolygonMap
{
	LinkedList<Edge> edges;
	LinkedList<float[]> visibilityPoints;
	Cell[] world;
	int worldWidth, worldHeight;
	float lightX, lightY;
	int cellSize; 
	final int NORTH, SOUTH, EAST, WEST;
	public PolygonMap(int width, int height, int cellSize)
	{
		this.NORTH = 0;
		this.SOUTH = 1;
		this.EAST  = 2;
		this.WEST  = 3;
		this.edges = new LinkedList<Edge>();
		this.world = new Cell[width*height];
		this.worldWidth = width;
		this.worldHeight = height;
		this.lightX = 0;
		this.lightY = 0;
		this.cellSize = cellSize;
		for (int i = 0; i < world.length; i++)
			world[i] = new Cell();
		this.visibilityPoints = new LinkedList<float[]>();
		for (int i = 1 ; i < width-1; i++)
		{
			world[i + worldWidth].exist = true;
			world[i + (height-2) * worldWidth].exist = true;
		}
		for (int i = 1 ; i < height-1; i++)
		{
			world[1 + i * worldWidth].exist = true;
			world[(width-2) + i * worldWidth].exist = true;
		}
	}
	public void toggleBlock(int x, int y)
	{
		int i = (x/cellSize) + (y/cellSize) * worldWidth;
		world[i].exist = !world[i].exist;
	}
	public void calculateLighting(float x, float y)
	{
		visibilityPoints.clear();
		this.lightX = x;
		this.lightY = y;
		for(Edge e1: edges)
		{
			for (int i = 0; i < 2; i++)
			{
				float rdx = (i == 0 ? e1.sx : e1.ex) - x;
				float rdy = (i == 0 ? e1.sy : e1.ey) - y;
				float baseAngle = (float) Math.atan2(rdy, rdx);
				float angle = 0;
				for (int j = 0; j < 3; j++)
				{
					if (j == 0) angle = baseAngle - 0.0001f;
					if (j == 1) angle = baseAngle;
					if (j == 2) angle = baseAngle + 0.0001f;
					rdx = (float) (Math.cos(angle));
					rdy = (float) (Math.sin(angle));
					
					float minT1 = 1000000000;
					float minPx = 0, minPy = 0, minAng = 0;
					boolean valid = false;

					for (Edge e2: edges)
					{
						float sdx = e2.ex - e2.sx;
						float sdy = e2.ey - e2.sy;
						if(Math.abs(sdx-rdx) > 0 && Math.abs(sdy-rdy) > 0)
						{
							float t2 = (rdx * (e2.sy - y) + (rdy * (x - e2.sx)))  / (sdx * rdy - sdy * rdx);
							float t1 = (e2.sx + sdx * t2 - x) / rdx;
							if (t1 > 0 && t2 >= 0 && t2 <= 1.0f)
							{
								if (t1 < minT1)
								{
									minT1 = t1;
									minPx = x + rdx * t1;
									minPy = y + rdy * t1;
									minAng = (float) Math.atan2(minPy - y, minPx - x);
									valid = true;
								}
							}
						}
					}
					if (valid)visibilityPoints.add(new float[]{minAng, minPx, minPy});
				}
			}
		}
		visibilityPoints.sort(new Comparator<float[]>() {

			public int compare(float[] o1, float[] o2)
			{
				if (o1[0] < o2[0])
					return 1;
				else if (o1[0] > o2[0])
					return -1;
				else
					return 0;
			}
			
		});
	}
	public void update()
	{
		edges.clear();
		for (int x = 0 ; x < worldWidth; x++)
		{
			for (int y = 0 ; y < worldHeight; y++)
			{
				for (int i = 0; i < 4; i++)
				{
					world[x+y*worldWidth].edgeExist[i] = false;
					world[x+y*worldWidth].edgeID[i] = 0;
				}
			}
		}
		for (int x = 1 ; x < worldWidth-1; x++)
		{
			for (int y = 1 ; y < worldHeight-1; y++)
			{
				int i = x+y*worldWidth;
				int n = x+(y-1)*worldWidth;
				int s = x+(y+1)*worldWidth;
				int e = (x+1)+y*worldWidth;
				int w = (x-1)+y*worldWidth;
				if (world[i].exist)
				{
					if (!world[w].exist)
					{
						if (world[n].edgeExist[WEST])
						{
							edges.get(world[n].edgeID[WEST]).ey += cellSize;
							world[i].edgeID[WEST] = world[n].edgeID[WEST];
							world[i].edgeExist[WEST] = true;
						}
						else
						{
							edges.add(new Edge(x*cellSize, y*cellSize, x*cellSize, y*cellSize + cellSize));
							world[i].edgeID[WEST] = edges.size()-1;
							world[i].edgeExist[WEST] = true;
						}
					}
					if (!world[e].exist)
					{
						if (world[n].edgeExist[EAST])
						{
							edges.get(world[n].edgeID[EAST]).ey += cellSize;
							world[i].edgeID[EAST] = world[n].edgeID[EAST];
							world[i].edgeExist[EAST] = true;
						}
						else
						{
							edges.add(new Edge(x*cellSize + cellSize, y*cellSize, x*cellSize + cellSize, y*cellSize + cellSize));
							world[i].edgeID[EAST] = edges.size()-1;
							world[i].edgeExist[EAST] = true;
						}
					}
					if (!world[n].exist)
					{
						if (world[w].edgeExist[NORTH])
						{
							edges.get(world[w].edgeID[NORTH]).ex += cellSize;
							world[i].edgeID[NORTH] = world[w].edgeID[NORTH];
							world[i].edgeExist[NORTH] = true;
						}
						else
						{
							edges.add(new Edge(x*cellSize, y*cellSize, x*cellSize + cellSize, y*cellSize));
							world[i].edgeID[NORTH] = edges.size()-1;
							world[i].edgeExist[NORTH] = true;
						}
					}
					if (!world[s].exist)
					{
						if (world[w].edgeExist[SOUTH])
						{
							edges.get(world[w].edgeID[SOUTH]).ex += cellSize;
							world[i].edgeID[SOUTH] = world[w].edgeID[SOUTH];
							world[i].edgeExist[SOUTH] = true;
						}
						else
						{
							edges.add(new Edge(x*cellSize, y*cellSize + cellSize, x*cellSize + cellSize, y*cellSize + cellSize));
							world[i].edgeID[SOUTH] = edges.size()-1;
							world[i].edgeExist[SOUTH] = true;
						}
					}
				}
			}
		}
	}
	public void debug(Renderer renderer, int color)
	{
		for (Edge e : edges)
		{
			renderer.drawLine((int)e.sx, (int)e.sy, (int)e.ex, (int)e.ey, color, true);
			renderer.fillCircle((int)e.sx, (int)e.sy, 5, 0xffff0000, true);
			renderer.fillCircle((int)e.ex, (int)e.ey, 5, 0xffff0000, true);
		}
	}
	public void render(Renderer renderer, int color)
	{
		for (int x = 0 ; x < worldWidth; x++)
		{
			for (int y = 0 ; y < worldHeight; y++)
			{
				if (world[x + y * worldWidth].exist)
					renderer.fillRect(x * cellSize, y * cellSize, cellSize, cellSize, color, true);
			}
		}
	}
	public void renderLight(Renderer renderer, int color)
	{
		if (visibilityPoints.size() == 0) return;
		for (int i = 0; i < visibilityPoints.size()-1; i++)
		{
			renderer.fillTriangle((int)lightX, (int)lightY, (int)visibilityPoints.get(i)[1], (int)visibilityPoints.get(i)[2], (int)visibilityPoints.get(i+1)[1], (int)visibilityPoints.get(i+1)[2], color, true);
		}
		renderer.fillTriangle((int)lightX, (int)lightY, (int)visibilityPoints.get(visibilityPoints.size()-1)[1], (int)visibilityPoints.get(visibilityPoints.size()-1)[2], (int)visibilityPoints.get(0)[1], (int)visibilityPoints.get(0)[2], color, true);
	}
}
