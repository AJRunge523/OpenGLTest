package edu.wm.opengltestapp;

public class Vector2f {

	public float x;
	public float y;
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float magnitude()
	{
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		//return (float)Math.sqrt(sum);
	}
	public void normalize()
	{
		float mag = this.magnitude();
		x = x/mag;
		y = y/mag;
	}

	public Vector2f sub(Vector2f v)
	{
		float dx = x - v.x;
		float dy = y - v.y;
		return new Vector2f(dx, dy);
	}
	public Vector2f add(Vector2f v)
	{
		float dx = x + v.x;
		float dy = y + v.y;
		return new Vector2f(dx, dy);
	}
}
