package engine.graphics.maps.polygons;

public class Cell
{
	int edgeID[];
	boolean edgeExist[];
	boolean exist;
	public Cell()
	{
		this.edgeID = new int[4];
		this.edgeExist = new boolean[4];
		this.exist = false;
	}
}
