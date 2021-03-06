public class MaterialSolid implements Material {
	Vec3 color;
	double ka;
	double kd;
	double ks;
	int exponent;
	double reflectance;
	double transmittance;
	double refraction;

	public MaterialSolid(Vec3 color, double ka, double kd, double ks, int exponent, double reflectance, double transmittance, double refraction) {
		this.color = color;
		this.ka = ka;
		this.kd = kd;
		this.ks = ks;
		this.exponent = exponent;
		this.reflectance = reflectance;
		this.transmittance = transmittance;
		this.refraction = refraction;
	}

	@Override
	public String toString() {
		return "MaterialSolid [color=" + color + ", ka=" + ka + ", kd=" + kd + ", ks=" + ks + ", exponent=" + exponent
				+ ", reflectance=" + reflectance + ", transmittance=" + transmittance + ", refraction=" + refraction
				+ "]";
	}

	public Vec3 getColor() {
		return color;
	}

	public double getKa() {
		return ka;
	}

	public double getKd() {
		return kd;
	}

	public double getKs() {
		return ks;
	}

	@Override
	public double getExponent() {
		return exponent;
	}

}
