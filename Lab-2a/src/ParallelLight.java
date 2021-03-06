public class ParallelLight implements Light {
	Vec3 color;
	Vec3 direction;
	
	ParallelLight(Vec3 color, Vec3 direction) {
		this.color = color;
		this.direction = direction;
	}

	@Override
	public Vec3 getColor() {
		return color;
	}

	public Vec3 getDirection() {
		return direction;
	}
}
