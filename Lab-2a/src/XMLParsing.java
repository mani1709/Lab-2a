import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParsing {
	private Document document;

	public XMLParsing(Document document) {
		this.document = document;
	}

	public String parseOutputFile() {
		NodeList sceneList = document.getElementsByTagName("scene");
		return ((Element) sceneList.item(0)).getAttribute("output_file");
	}

	public Vec3 parseBackgroundColor() {
		NodeList bgList = document.getElementsByTagName("background_color");
		Element bg = (Element) bgList.item(0);
		return new Vec3(Double.parseDouble(bg.getAttribute("r")), Double.parseDouble(bg.getAttribute("g")), Double.parseDouble(bg.getAttribute("b")));
	}

	public Vec3 parsePositionCamera() {
		Node cameras = document.getElementsByTagName("camera").item(0);
		Element camera = (Element) cameras;
		NodeList cameraStats = camera.getChildNodes();
		for(int i = 0; i < cameraStats.getLength(); i++) {
			Node n = cameraStats.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element stat = (Element) n;
				switch(stat.getTagName()) {
					case "position":
						return new Vec3(Double.parseDouble(stat.getAttribute("x")), Double.parseDouble(stat.getAttribute("y")), Double.parseDouble(stat.getAttribute("z")));
				}
			}
		}
		return null;
	}

	public Vec3 parseLookAt() {
		Node cameras = document.getElementsByTagName("camera").item(0);
		Element camera = (Element) cameras;
		NodeList cameraStats = camera.getChildNodes();
		for(int i = 0; i < cameraStats.getLength(); i++) {
			Node n = cameraStats.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element stat = (Element) n;
				switch(stat.getTagName()) {
				case "lookat":
					return new Vec3(Double.parseDouble(stat.getAttribute("x")), Double.parseDouble(stat.getAttribute("y")), Double.parseDouble(stat.getAttribute("z")));
				}
			}
		}
		return null;
	}

	public Vec3 parseUp() {
		Node cameras = document.getElementsByTagName("camera").item(0);
		Element camera = (Element) cameras;
		NodeList cameraStats = camera.getChildNodes();
		for(int i = 0; i < cameraStats.getLength(); i++) {
			Node n = cameraStats.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element stat = (Element) n;
				switch(stat.getTagName()) {
				case "up":
					return new Vec3(Double.parseDouble(stat.getAttribute("x")), Double.parseDouble(stat.getAttribute("y")), Double.parseDouble(stat.getAttribute("z")));
				}
			}
		}
		return null;
	}

	public double parseHorizontalFov() {
		Node cameras = document.getElementsByTagName("camera").item(0);
		Element camera = (Element) cameras;
		NodeList cameraStats = camera.getChildNodes();
		for(int i = 0; i < cameraStats.getLength(); i++) {
			Node n = cameraStats.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element stat = (Element) n;
				switch(stat.getTagName()) {
				case "horizontal_fov":
					return Math.toRadians(Double.parseDouble(stat.getAttribute("angle")));
				}
			}
		}
		return 0;
	}

	public int parseWidth() {
		Node cameras = document.getElementsByTagName("camera").item(0);
		Element camera = (Element) cameras;
		NodeList cameraStats = camera.getChildNodes();
		for(int i = 0; i < cameraStats.getLength(); i++) {
			Node n = cameraStats.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element stat = (Element) n;
				switch(stat.getTagName()) {
				case "resolution":
					return Integer.parseInt(stat.getAttribute("horizontal"));
				}
			}
		}
		return 0;
	}

	public int parseHeight() {
		Node cameras = document.getElementsByTagName("camera").item(0);
		Element camera = (Element) cameras;
		NodeList cameraStats = camera.getChildNodes();
		for(int i = 0; i < cameraStats.getLength(); i++) {
			Node n = cameraStats.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element stat = (Element) n;
				switch(stat.getTagName()) {
				case "resolution":
					return Integer.parseInt(stat.getAttribute("vertical"));
				}
			}
		}
		return 0;
	}

	public int parseMaxBounces() {
		Node cameras = document.getElementsByTagName("camera").item(0);
		Element camera = (Element) cameras;
		NodeList cameraStats = camera.getChildNodes();
		for(int i = 0; i < cameraStats.getLength(); i++) {
			Node n = cameraStats.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element stat = (Element) n;
				switch(stat.getTagName()) {
				case "max_bounces":
					return Integer.parseInt(stat.getAttribute("n"));
				}
			}
		}
		return 0;
	}

	public List<Light> parseLights() {
		List<Light> lights = new ArrayList<Light>();

		NodeList ambientLights = document.getElementsByTagName("ambient_light");
		for(int i = 0; i < ambientLights.getLength(); i++) {
			Node ambientLight = ambientLights.item(i);
			Element l = (Element) ambientLight;
			if(l.getNodeType() == Node.ELEMENT_NODE) {
				NodeList lightStats = l.getChildNodes();
				Vec3 c = new Vec3(0, 0, 0);
				for(int j = 0; j < lightStats.getLength(); j++) {
					Node s = lightStats.item(j);
					if(s.getNodeType() == Node.ELEMENT_NODE) {
						Element stat = (Element) s;
						if(stat.getNodeName() == "color") {
							c = new Vec3(Double.parseDouble(stat.getAttribute("r")), Double.parseDouble(stat.getAttribute("g")), Double.parseDouble(stat.getAttribute("b")));
						}
					}
				}
				lights.add(new AmbientLight(c));
			}
		}
		
		NodeList parallelLights = document.getElementsByTagName("parallel_light");
		for(int i = 0; i < parallelLights.getLength(); i++) {
			Node parallelLight = parallelLights.item(i);
			Element l = (Element) parallelLight;
			if(l.getNodeType() == Node.ELEMENT_NODE) {
				NodeList lightStats = l.getChildNodes();
				Vec3 c = new Vec3(0, 0, 0);
				Vec3 p = new Vec3(0, 0, 0);
				for(int j = 0; j < lightStats.getLength(); j++) {
					Node s = lightStats.item(j);
					if(s.getNodeType() == Node.ELEMENT_NODE) {
						Element stat = (Element) s;
						if(stat.getNodeName() == "color") {
							c = new Vec3(Double.parseDouble(stat.getAttribute("r")), Double.parseDouble(stat.getAttribute("g")), Double.parseDouble(stat.getAttribute("b")));
						} else if(stat.getNodeName() == "direction") {
							p = new Vec3(Double.parseDouble(stat.getAttribute("x")), Double.parseDouble(stat.getAttribute("y")), Double.parseDouble(stat.getAttribute("z")));
						}
						
					}
				}
				lights.add(new ParallelLight(c, p));
			}
		}
		
		NodeList pointLights = document.getElementsByTagName("point_light");
		for(int i = 0; i < pointLights.getLength(); i++) {
			Node pointLight = pointLights.item(i);
			Element l = (Element) pointLight;
			if(l.getNodeType() == Node.ELEMENT_NODE) {
				NodeList lightStats = l.getChildNodes();
				Vec3 c = new Vec3(0, 0, 0);
				Vec3 p = new Vec3(0, 0, 0);
				for(int j = 0; j < lightStats.getLength(); j++) {
					Node s = lightStats.item(j);
					if(s.getNodeType() == Node.ELEMENT_NODE) {
						Element stat = (Element) s;
						if(stat.getNodeName() == "color") {
							c = new Vec3(Double.parseDouble(stat.getAttribute("r")), Double.parseDouble(stat.getAttribute("g")), Double.parseDouble(stat.getAttribute("b")));
						} else if(stat.getNodeName() == "position") {
							p = new Vec3(Double.parseDouble(stat.getAttribute("x")), Double.parseDouble(stat.getAttribute("y")), Double.parseDouble(stat.getAttribute("z")));
						}
						
					}
				}
				lights.add(new PointLight(c, p));
			}
		}
		
		return lights;
	}

	public List<Obj> parseSurfaces() {
		List<Obj> objects = new ArrayList<Obj>();
		
		NodeList sphereList = document.getElementsByTagName("sphere");
		for(int i = 0; i < sphereList.getLength(); i++) {
			Double radius = 0.0;
			Vec3 pos = new Vec3(0, 0, 0);
			Vec3 color = new Vec3(0, 0, 0);
			Double ka = 0.0;
			Double kd = 0.0;
			Double ks = 0.0;
			int exponent = 0;
			Double reflectance = 0.0;
			Double transmittance = 0.0;
			Double refraction = 0.0;
			Material material = new MaterialSolid(new Vec3(0, 0, 0), 0, 0, 0, 0, 0, 0, 0);
			
			Node s = sphereList.item(i);
			if(s.getNodeType() == Node.ELEMENT_NODE) {
				Element sphere = (Element) s;
				radius = Double.parseDouble(sphere.getAttribute("radius"));
				NodeList sphereStatList = sphere.getChildNodes();
				for(int j = 0; j < sphereStatList.getLength(); j++) {
					Node t = sphereStatList.item(j);
					if(t.getNodeType() == Node.ELEMENT_NODE) {
						Element sphereStat = (Element) t;
						if(sphereStat.getTagName() == "position") {
							pos = new Vec3(Double.parseDouble(sphereStat.getAttribute("x")), Double.parseDouble(sphereStat.getAttribute("y")), Double.parseDouble(sphereStat.getAttribute("z")));
						} else if(sphereStat.getTagName() == "material_solid") {
							NodeList materialStatList = sphereStat.getChildNodes();
							for(int k = 0; k < materialStatList.getLength(); k++) {
								Node u = materialStatList.item(k);
								if(u.getNodeType() == Node.ELEMENT_NODE) {
									Element materialStat = (Element) u;
									if(materialStat.getTagName() == "color") {
										color = new Vec3(Double.parseDouble(materialStat.getAttribute("r")), Double.parseDouble(materialStat.getAttribute("g")), Double.parseDouble(materialStat.getAttribute("b")));
									} else if (materialStat.getTagName() == "phong") {
										ka = Double.parseDouble(materialStat.getAttribute("ka"));
										kd = Double.parseDouble(materialStat.getAttribute("kd"));
										ks = Double.parseDouble(materialStat.getAttribute("ks"));
										exponent = Integer.parseInt(materialStat.getAttribute("exponent"));
									} else if (materialStat.getTagName() == "reflectance") {
										reflectance = Double.parseDouble(materialStat.getAttribute("r"));
									} else if (materialStat.getTagName() == "transmittance") {
										transmittance = Double.parseDouble(materialStat.getAttribute("t"));
									} else if (materialStat.getTagName() == "refraction") {
										refraction = Double.parseDouble(materialStat.getAttribute("iof"));
									}
								}
							}
						}
					}
				}
			}
			material = new MaterialSolid(color, ka, kd, ks, exponent, reflectance, transmittance, refraction);
			
			objects.add(new Sphere(radius, pos, material));
		}
	
		return objects;
	}
}