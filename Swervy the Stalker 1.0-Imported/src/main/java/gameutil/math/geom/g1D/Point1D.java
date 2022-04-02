package gameutil.math.geom.g1D;

public class Point1D extends Figure1D{
	
	public static final int ID=2;
	
	/*TO DO:
	 *  -Make functions for adding and subtracting other vectors
	 *  
	 */
	private double x;

	public Point1D(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public double distance(Point1D p) {
		return Math.abs(x - p.x);
	}
	
	public boolean intersects(LineSeg1D l) {
		return l.intersects(this);
	}
	
	public Figure1D intersection(LineSeg1D l) {
		if (intersects(l)) {
			return this;
		} else {
			return SPACE;
		}
	}
	
	public Figure1D intersection(Ray1D r) {
		return r.intersection(this);
	}

	@Override
	public int ID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
