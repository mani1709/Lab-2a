public class Sphere implements Obj {
	double radius;
	Vec3 center;
	Material material;
	
	public Sphere(double radius, Vec3 center, Material material) {
		this.radius = radius;
		this.center = center;
		this.material = material;
	}
	
	@Override
	public double intersect(Ray ray) {
		double a = Utils.dotProduct(ray.getDirection(), ray.getDirection());
		Vec3 pc = Utils.subtract(center, ray.getPosition());
		double b = 2 * Utils.dotProduct(pc, ray.getDirection());
		double c = Utils.dotProduct(pc, pc) - radius * radius;
		
		double discriminant = (b * b) - (4 * a * c);

		if(discriminant < 0 || a == 0) {
			return Double.MAX_VALUE;
		} else if (discriminant == 0) {
			return -b/2*a;
		} else {
			return Math.min((-b+Math.sqrt(discriminant)) / (2*a), (-b-Math.sqrt(discriminant)) / (2*a));
		}
	}

	@Override
	public String toString() {
		return "Sphere [radius=" + radius + ", center=" + center + ", material=" + material + "]";
	}

	public Material getMaterial() {
		return material;
	}

	public Vec3 getCenter() {
		return center;
	}

	@Override
	public Vec3 getColor() {
		return material.getColor();	
	}

	@Override
	public Vec3 calculateAmbient(Vec3 colorLight) {
		Vec3 colorCoefficients = Utils.multScalar(colorLight, material.getKa());
		return Utils.multiplyVectors(colorCoefficients, material.getColor());
	}

	@Override
	public Vec3 calculateDiffuse(double dot, Vec3 colorLight) {
		double coeff = material.getKd() * dot;
		Vec3 colorCoefficients = Utils.multScalar(colorLight, coeff);
		return Utils.multiplyVectors(colorCoefficients, material.getColor());
	}

	@Override
	public Vec3 calculateSpecular(double angleViewReflection, Vec3 colorLight) {
		double coeff = material.getKs() * Math.pow(angleViewReflection, material.getExponent());
		Vec3 colorCoefficients = Utils.multScalar(colorLight, coeff);
		return Utils.multiplyVectors(colorCoefficients, material.getColor());
	}

}
