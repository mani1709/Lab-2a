public class AmbientLight implements Light{
	Vec3 color;
	
	AmbientLight(Vec3 color) {
		this.color = color;
	}

	public Vec3 getColor() {
		return color;
	}
}
