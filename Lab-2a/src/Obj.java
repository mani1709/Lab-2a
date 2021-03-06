public interface Obj {

	public Material getMaterial();

	public double intersect(Ray ray);

	public Vec3 getColor();

	public Vec3 getCenter();

	public Vec3 calculateAmbient(Vec3 colorLight);

	public Vec3 calculateDiffuse(double dot, Vec3 colorLight);

	public Vec3 calculateSpecular(double angleViewReflection, Vec3 color);
}