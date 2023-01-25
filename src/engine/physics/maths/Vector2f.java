package engine.physics.maths;

public class Vector2f
{
    public float x, y;
    
    public Vector2f(float x, float y)
    {
    	this.x = x;
    	this.y = y;
    }

    public Vector2f(Vector2f v)
    {
    	this(v.x, v.y);
    }
    
    public Vector2f()
    {
        this(0, 0);
    }
    
    public void setVector(float x, float y)
    {
    	this.x = x;
    	this.y = y;
    }
    
    public void setVector(Vector2f v)
    {
    	x = v.x;
    	y = v.y;
    }

    public String toString()
    {
        return "[ " + x + " , " + y + " ]";
    }

    public boolean equals(Vector2f v)
    {
        return ((v.x == x) && (v.y == y));
    }

    public float length()
    {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void setLength(float length)
    {
    	if (!(x == 0.0f && y == 0.0f))
    		setVector(normalize().mul(length));
    	else setVector(length, 0);
    }

    public float getAngle()
    {
        return (float)(Math.atan2(y, x) * (180.0/3.14159265359));
    }
   
    public void setAngle(float angle)
    {
    	if(!(x == 0.0f && y == 0.0f))
    	{
    		float length = (float) Math.sqrt(x * x + y * y);
        	setVector((float)Math.cos(angle / (180.0/3.14159265359)) * length, (float)Math.sin(angle / (180.0/3.14159265359)) * length);
    	}
    }

    public Vector2f normalize()
    {
        if (x == 0.0f && y == 0.0f) return this;
        float len = (float) Math.sqrt(x * x + y * y);
        return new Vector2f(x / len, y / len);
    }

    public void addX(float v)
    {
    	x += v;
    }
    
    public void addY(float v)
    {
    	y += v;
    }

    public void subX(float v)
    {
    	x -= v;
    }
    
    public void subY(float v)
    {
    	y -= v;
    }
    
    public void mulX(float v)
    {
    	x *= v;
    }
    
    public void mulY(float v)
    {
    	y *= v;
    }

    public void divX(float v)
    {
    	x /= v;
    }
    
    public void divY(float v)
    {
    	y /= v;
    }

    public Vector2f add(Vector2f v)
    {
        return new Vector2f(x + v.x, y + v.y);
    }

    public Vector2f sub(Vector2f v)
    {
        return new Vector2f(x - v.x, y - v.y);
    }

    public Vector2f mul(float value)
    {
        return new Vector2f(x * value, y * value);
    }

    public Vector2f div(float value)
    {
        return new Vector2f(x / value, y / value);
    }
    
    public void addTo(Vector2f v)
    {
    	x += v.x;
    	y += v.y;
    }
    
    public void subTo(Vector2f v)
    {
    	x -= v.x;
    	y -= v.y;
    }
    
    public void mulTo(Vector2f v)
    {
    	x *= v.x;
    	y *= v.y;
    }
    
    public void divTo(Vector2f v)
    {
    	x /= v.x;
    	y /= v.y;
    }

    public void easeTo(Vector2f pos, float ease)
    {
    	if (pos.sub(this).length() > 0.1)
    		addTo(pos.sub(this).mul(ease));
    }
    
    public float dot(Vector2f v)
    {
        return (x * v.x + y * v.y);
    }

    public float distanceTo(Vector2f v)
    {
    	return v.sub(this).length();
    }
    
    public Vector2f midPointTo(Vector2f v)
    {
    	return new Vector2f((x + v.x)/2, (y + v.y)/2);
    }

    public Vector2f projectOn(Vector2f v)
    {
        return (v.normalize().mul(v.normalize().dot(this)));
    }

    public Vector2f negate()
    {
        return new Vector2f(-x, -y);
    }

    public boolean isZero()
    {
        return (x == 0.0f && y == 0.0f);
    }
    
    public boolean isParallel(Vector2f v)
    {
        return (normalize().equals(v.normalize()));
    }
    
    public boolean isAntiParallel(Vector2f v)
    {
        return (normalize().equals(v.normalize().negate()));
    }

    public boolean isOrtogonal(Vector2f v)
    {
        return (dot(v) == 0.0f);
    }

    public int inQuadrant()
    {
    	if (x == 0.0f && y == 0.0f) return 0;
    	else if (x >= 0 && y >= 0) return 1;
    	else if (x < 0 && y >= 0) return 2;
    	else if (x < 0 && y < 0) return 3;
    	else if (x >= 0 && y < 0) return 4;
    	else return -1;
    }

}
