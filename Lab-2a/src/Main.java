import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		String outputFile = "";
		Vec3 backgroundColor = new Vec3(0, 0, 0);
		
		Vec3 position = new Vec3(0, 0, 0);
		Vec3 lookAt = new Vec3(0, 0, 0);
		Vec3 up = new Vec3(0, 0, 0);
		
		double fovx = 0;
		double fovy = 0;
		
		int width = 0;
	    int height = 0;
	    
	    int maxBounces = 0;
	    
	    List<Light> lights = new ArrayList<Light>();
	    
	    List<Obj> objects = new ArrayList<Obj>();
	    
	    //parsing xml document
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(args[0]);
	    
			XMLParsing x = new XMLParsing(document);
	    
			outputFile = x.parseOutputFile();
			backgroundColor = x.parseBackgroundColor();
			position = x.parsePositionCamera();
			lookAt = x.parseLookAt();
			up = x.parseUp();
			fovy = x.parseHorizontalFov();
			width = x.parseWidth();
			height = x.parseHeight();
			fovx = height/width*fovy;
			maxBounces = x.parseMaxBounces();
			lights = x.parseLights();
			objects = x.parseSurfaces();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
	    //creation of image
		BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Camera camera = new Camera(position, up, lookAt, width, height, fovx);
		
		double differenceWidth = camera.getRight() - camera.getLeft();
		double differenceHeight = camera.getBottom() - camera.getTop();
		
        for(int i = 0; i < width; i++) {
        	for(int j = 0; j < height; j++) {
        		//u and v are the values of the plane from -width/2 to width/2 changing by 1 per value for u and the same as height for v
        		double u = camera.getLeft() + (differenceWidth) * (i + 0.5)/width;
        		double v = camera.getTop() + (differenceHeight) * (j + 0.5)/height;
        		
        		Vec3 uScaled = Utils.multScalar(camera.getU(), u);
        		Vec3 vScaled = Utils.multScalar(camera.getV(), v);
        		Vec3 vectorToPlane = Utils.add(Utils.add(uScaled, vScaled), camera.getW_distance_negated());
        		Vec3 direction = Utils.normalize(vectorToPlane);
        		
        		Ray ray = new Ray(position, direction);
        		
        		Obj intersect = null;
        		double tNearestObject = Double.MAX_VALUE;
        		
        		for(int k = 0; k < objects.size(); k++) {
        			double tCuttingObject = objects.get(k).intersect(ray);
        			if(tCuttingObject > 0 && tCuttingObject < tNearestObject) {
        				intersect = objects.get(k);
        				tNearestObject = tCuttingObject;
        			}
        		}
        		
        		if(intersect == null) {
        			int r = (int) (backgroundColor.getX() * 256);
            		int g = (int) (backgroundColor.getY() * 256);
            		int b = (int) (backgroundColor.getZ() * 256);
            		Color color = new Color(r, g, b);
            		canvas.setRGB(i, j, color.getRGB());
        		} else {
        			Vec3 colorAmbient = new Vec3(0, 0, 0);
        			Vec3 colorDiffuse = new Vec3(0, 0, 0);
        			Vec3 colorSpecular = new Vec3(0, 0, 0);
        			
        			for(int k = 0; k < lights.size(); k++) {
        				if(lights.get(k) instanceof AmbientLight) {
        					colorAmbient = intersect.calculateAmbient(lights.get(k).getColor());
        				} else if (lights.get(k) instanceof ParallelLight) {
        					Vec3 dir = Utils.normalize(((ParallelLight) lights.get(k)).getDirection());
        					Vec3 vecToLight = new Vec3(-dir.getX(), -dir.getY(), -dir.getZ());
        					Vec3 normal = Utils.normalize(Utils.subtract(ray.positionAt(-tNearestObject), intersect.getCenter()));
        					
        					Boolean shadowed = false;
        					
        					Ray shadowRay = new Ray(ray.positionAt(-tNearestObject), dir);
        					
        					for(int l = 0; l < objects.size(); l++) {
        						if(objects.get(l).intersect(shadowRay) != Double.MAX_VALUE && objects.get(l).intersect(shadowRay) > 0) {
        							shadowed = true;
        						}
        					}
        					
        					if(!shadowed) {
        						//diffuse
            					double dot = Math.max(Utils.dotProduct(normal, vecToLight), 0);
            					colorDiffuse = intersect.calculateDiffuse(dot, lights.get(k).getColor());
            					
            					//specular
            					Vec3 reflection = Utils.normalize(Utils.subtract(Utils.multScalar(normal, 2*dot), vecToLight));
            					double angleViewReflection = Math.max(Utils.dotProduct(ray.getDirection(), reflection), 0);
            					colorSpecular = intersect.calculateSpecular(angleViewReflection, lights.get(k).getColor());
        					}
        				}
        			}
        			
        			int r = (int) ((colorAmbient.getX() + colorDiffuse.getX() + colorSpecular.getX()) * 255.999);
        			int g = (int) ((colorAmbient.getY() + colorDiffuse.getY() + colorSpecular.getY()) * 255.999);
        			int b = (int) ((colorAmbient.getZ() + colorDiffuse.getZ() + colorSpecular.getZ()) * 255.999);
        			
        			Color color = new Color(r, g, b);
        			
            		canvas.setRGB(i, j, color.getRGB());
        		}
        	}
        }
        
        BufferedImage canvas2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < width; i++) {
        	for(int j = 0; j < height; j++) {
        		canvas2.setRGB(i, j, canvas.getRGB(width - i - 1, j));
        	}
        }
        
        //write array to image
        File file = new File(outputFile);
        ImageIO.write(canvas2, "png", file);
	}
}
