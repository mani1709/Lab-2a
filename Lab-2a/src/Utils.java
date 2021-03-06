public class Utils {
	//if we dont want the vectors to change, we can use this class to create a new vector from the return
	//also used for dot and cross product
	
	public static Vec3 add(Vec3 a, Vec3 b) {
		double x = a.getX() + b.getX();
		double y = a.getY() + b.getY();
		double z = a.getZ() + b.getZ();
		return new Vec3(x, y, z);
	}
	
	public static Vec3 subtract(Vec3 a, Vec3 b) {
		double x = a.getX() - b.getX();
		double y = a.getY() - b.getY();
		double z = a.getZ() - b.getZ();
		return new Vec3(x, y, z);
	}
	
	public static Vec3 multScalar(Vec3 a, double t) {
		return new Vec3(t * a.getX(), t* a.getY(), t*a.getZ());
	}
	
	public static double dotProduct(Vec3 a, Vec3 b) {
		return a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();
	}
	
	public static Vec3 crossProduct (Vec3 a, Vec3 b) {
		double x = a.getY()*b.getZ() - a.getZ()*b.getY();
		double y = a.getZ()*b.getX() - a.getX()*b.getZ();
		double z = a.getX()*b.getY() - a.getY()*b.getX();
		return new Vec3(x, y, z);
	}
	
	public static Vec3 normalize(Vec3 a) {
		double length = a.length();
		//care for Vector of length 0, since we cant divide through 0
		if (length != 0) {
			return new Vec3(a.getX()/length, a.getY()/length, a.getZ()/length);
		} else {
			return new Vec3(0, 0, 0);
		}
	}
	
	public static Vec3 multiplyVectors(Vec3 a, Vec3 b) {
		return new Vec3(a.getX() * b.getX(), a.getY() * b.getY(), a.getZ() * b.getZ());
	}
}