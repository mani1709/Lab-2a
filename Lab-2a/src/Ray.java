public class Ray {
	Vec3 position;
	Vec3 direction;
	
	public Ray(Vec3 position, Vec3 direction) {
		this.position = position;
		this.direction = Utils.normalize(direction);
	}
	
	public Vec3 getPosition() {
		return this.position;
	}
	
	public Vec3 getDirection() {
		return this.direction;
	}
	
	public Vec3 positionAt(double t) {
		//ray is startpoint + t * direction
		return Utils.add(position, Utils.multScalar(direction, t));
	}

	@Override
	public String toString() {
		return "Ray [position=" + position + ", direction=" + direction + "]";
	}

	public Boolean getShadowedAtPosition(Obj obj) {
		if(obj.intersect(this) == Double.MAX_VALUE) {
			return false;
		}
		return true;
	}
}