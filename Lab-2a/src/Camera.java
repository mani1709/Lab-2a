public class Camera {
	Vec3 eye;
	Vec3 up;
	Vec3 at;
	
	int height;
	int width;
	
	int left;
	int right;
	int bottom;
	int top;
	int far;
	int near;
	
	Vec3 w;
	Vec3 u;
	Vec3 v;
	
	double fov;
	
	double distance;
	Vec3 w_distance_negated;
	
	public Camera(Vec3 eye, Vec3 up, Vec3 at, int width, int height, double fov) {
		this.eye = eye;
		this.up = up;
		this.at = at;
		
		this.width = width;
		this.height = height;
		
		this.fov = fov;
		
		left = -width/2;
		right = width/2;
		bottom = -height/2;
		top = height/2;
	
		w = Utils.normalize(Utils.subtract(at, eye));
		u = Utils.normalize(Utils.crossProduct(w, up));
		v = Utils.normalize(Utils.crossProduct(w, u));
		
		distance = top/Math.tan(fov)/2;
		
		w_distance_negated = Utils.multScalar(w, distance*-1);
	}
	
	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getBottom() {
		return bottom;
	}

	public int getTop() {
		return top;
	}
	
	public Vec3 getU() {
		return u;
	}

	public Vec3 getV() {
		return v;
	}

	public Vec3 getW_distance_negated() {
		return w_distance_negated;
	}
}