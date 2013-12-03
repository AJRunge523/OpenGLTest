package edu.wm.opengltestapp;

public class Vector3f {

	public float x;
	public float y;
	public float z;
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float magnitude()
	{
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		//return (float)Math.sqrt(sum);
	}
	public void normalize()
	{
		float mag = this.magnitude();
		x = x/mag;
		y = y/mag;
		z = z/mag;
	}
	public static Vector3f cross(Vector3f a, Vector3f b)
	{
		float s1 = a.y * b.z - a.z * b.y;
		float s2 = a.z * b.x - a.x * b.z;
		float s3 = a.x * b.y - a.y * b.x;
		return new Vector3f(s1, s2, s3);
	}
	public Vector3f sub(Vector3f v)
	{
		float dx = x - v.x;
		float dy = y - v.y;
		float dz = z - v.z;
		return new Vector3f(dx, dy, dz);
	}
	public Vector3f add(Vector3f v)
	{
		float dx = x + v.x;
		float dy = y + v.y;
		float dz = z + v.z;
		return new Vector3f(dx, dy, dz);
	}
}
