public class Vec3 {
	private double x;
	private double y;
	private double z;
	
	public Vec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3(Vec3 v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void add(Vec3 toAdd) {
		this.x += toAdd.x;
		this.y += toAdd.y;
		this.z += toAdd.z;
	}
	
	public void multScalar(double s) {
		this.x *= s;
		this.y *= s;
		this.z *= s;
	}
	
	public void divideScalar(double s) {
		this.x *= 1/s;
		this.y *= 1/s;
		this.z *= 1/s;
	}
	
	public double length() {
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	@Override
	public String toString() {
		return "Vec3 [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}
