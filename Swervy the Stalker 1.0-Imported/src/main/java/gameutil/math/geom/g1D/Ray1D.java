package gameutil.math.geom.g1D;

public class Ray1D extends Figure1D{
	public static final int ID=3;
	
	public enum LIMIT_TYPE{min,max,exact};
	protected final LIMIT_TYPE xAxiom;
	protected final double xLimit;
	
	public Ray1D(Point1D tail,Point1D head) {
		if (tail.getX()<head.getX()) {
			xAxiom=LIMIT_TYPE.min;
		} else if (tail.getX()>head.getX()) {
			xAxiom=LIMIT_TYPE.max;
		} else {
			xAxiom=LIMIT_TYPE.exact;
		}
		xLimit=tail.getX();
	}
	
	public Point1D getTail() {
		return new Point1D(xLimit);
	}
	
	public LIMIT_TYPE getXAxiom() {
		return xAxiom;
	}
	
	public double getXLimit() {
		return xLimit;
	}
	
	public Figure1D intersection(Point1D p) {
		if (intersects(p)) {
			return p.clone();
		} else {
			return SPACE;
		}
	}
	
	public Figure1D intersection(LineSeg1D l) {
		if (contains(l)) {
			return l.clone();
		} else if (intersects(new Point1D(l.getMinX()))) {
			if (xAxiom==LIMIT_TYPE.exact) {
				return getTail();
			} else {
				return new LineSeg1D(new Point1D(l.getMinX()),getTail());
			}
		} else if (intersects(new Point1D(l.getMaxX()))) {
			if (xAxiom==LIMIT_TYPE.exact) {
				return getTail();
			} else {
				return new LineSeg1D(new Point1D(l.getMaxX()),getTail());
			}
		} else {
			return SPACE;
		}
	}
	
	public boolean intersects(Point1D p) {
		if (xAxiom==LIMIT_TYPE.min && p.getX()>=xLimit){
			return true;
		} else if (xAxiom==LIMIT_TYPE.max && p.getX()<=xLimit) {
			return true;
		} else if (xAxiom==LIMIT_TYPE.min && p.getX()==xLimit) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean intersects(LineSeg1D l) {
		return intersects(new Point1D(l.getMaxX()))||intersects(new Point1D(l.getMinX()));
	}
	
	public boolean contains(Point1D p) {
		return intersects(p);
	}
	
	public boolean contains(LineSeg1D l) {
		return intersects(new Point1D(l.getMaxX()))&&intersects(new Point1D(l.getMinX()));
	}
	
	public int ID() {
		return ID;
	}
}
