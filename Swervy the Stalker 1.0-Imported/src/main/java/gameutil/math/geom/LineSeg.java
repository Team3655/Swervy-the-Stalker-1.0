package gameutil.math.geom;

public class LineSeg extends Figure{
	//ray 1 and ray 2 should be on the same line
	protected Ray ray1;
	protected Ray ray2;
	
	public LineSeg(Point p1,Point p2){
		ray1=new Ray(p1,p2);
		ray2=new Ray(p2,p1);
	}
	
	public LineSeg(Vector v1,Vector v2){
		ray1=new Ray(v1,v2);
		ray2=new Ray(v2,v1);
	}
	
	//functional
	public boolean intersects(Point p) {
		return ray1.intersects(p)&&ray2.intersects(p);
	}
	
	//functional
	public boolean contains(Point p) {
		return ray1.intersects(p)&&ray2.intersects(p);
	}
	
	//functional
	public Figure intersection(Point p) {
		if (intersects(p)) {
			return p.clone();
		} else {
			return SPACE;
		}
	}
	
	public Figure intersection(Ray r) {
		return r.intersection(this);
	}
	
	public Point[] getEndPoints() {
		return new Point[] {new Point(ray1.P1),new Point(ray2.P1)};
	}
	
}
