package gameutil.math.geom.g2D;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import gameutil.math.geom.g2D.FigureR2;
import gameutil.math.geom.g2D.LineR2;
import gameutil.math.geom.g2D.LineSegR2;
import gameutil.math.geom.g2D.PointR2;
import gameutil.math.geom.g2D.RectangleR2;

public class LineSegR2 extends LineR2 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public static final int ID=4;
	
	
	/**Constructs a new line segment existing in 2 dimensions.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @throws Exception It's a lie.
	 */
	public LineSegR2(double x1, double y1, double x2, double y2) throws Exception {
		super(new PointR2(x1, y1),new PointR2(x2, y2),true);
	}

	/**Constructs a new line segment existing in 2 dimensions.
	 * 
	 * @param p1
	 * @param p2
	 * @throws Exception It's a lie.
	 */
	public LineSegR2(PointR2 p1, PointR2 p2) throws Exception {
		super(p1, p2,true);
	}
	
	

	public boolean intersects(LineR2 l) {
		System.out.println("Line seg intersects line?");
		try {
			if (new LineR2(p1,p2).intersects(l)) {
				System.out.println(new LineR2(p1,p2).intersects(l));
				FigureR2 intsct = null;
				intsct = new LineR2(p1,p2).intersection(l);
				if (intsct.equals(SPACE)) {
					return false;
				}
				
				if (intsct.equals(l) && b==l.b) {
					return true;
				} else if (contains((PointR2)intsct)&&l.contains((PointR2)intsct)) {
					System.out.println(((PointR2)intsct).getX()+", "+((PointR2)intsct).getY());
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean intersects(LineSegR2 l) {
		System.out.println("Line seg intersects line seg?");
		try {
			if (new LineR2(p1,p2).intersects(l)) {
				FigureR2 intsct = null;
				try {
					intsct = new LineR2(p1,p2).intersection(l);
				} catch (Exception e) {
					return false;
				}
				if (intsct.equals(l) && (l.contains(p1) || l.contains(p2) || contains(l.p1) || contains(l.p2))) {
					System.out.println(l.contains(p1) || l.contains(p2) || contains(l.p1) || contains(l.p2));
					System.out.println(" P1: "+p1.getX()+","+p1.getY()+" P2: "+p2.getX()+","+p2.getY()+" P3: "+l.p1.getX()+","+l.p1.getY()+" P4: "+l.p2.getX()+","+l.p2.getY());
					return true;
				} else if (contains((PointR2)intsct) && l.contains((PointR2)intsct)) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean intersects(Rectangle r) {
		LineSegR2[] segments = rectToLineSegs(r);
		return (r.contains(x1, y1) || r.contains(x2, y2) || intersects(segments[0]) || intersects(segments[1])|| intersects(segments[2]) || intersects(segments[3]));
	}
	
	public boolean intersects(RectangleR2 r) {
		LineSegR2[] segments = rectToLineSegs(r);
		return (r.contains(p1) || r.contains(p2) || intersects(segments[0]) || intersects(segments[1])|| intersects(segments[2]) || intersects(segments[3]));
	}

	/**
	 * Converts a Rectangle to an array of line segments
	 * 
	 * @param r
	 *            rectangle to be converted
	 * @return returns an array of line segments that bound the input rectangle
	 *         starting with the top and index 0 and going clockwise
	 */
	public static LineSegR2[] rectToLineSegs(Rectangle r) {
		LineSegR2 l1;
		LineSegR2 l2;
		LineSegR2 l3;
		LineSegR2 l4;
		try {
			// top line
			l1 = new LineSegR2(r.getMinX(), r.getMinY(), r.getMaxX(), r.getMinY());
			// right line
			l2 = new LineSegR2(r.getMaxX(), r.getMaxY(), r.getMaxX(), r.getMinY());
			// bottom line
			l3 = new LineSegR2(r.getMinX(), r.getMaxY(), r.getMaxX(), r.getMaxY());
			// left line
			l4 = new LineSegR2(r.getMinX(), r.getMinY(), r.getMinX(), r.getMaxY());
		} catch (Exception e) {
			System.err.println("rectToLineSegs(Rectangle r) could not produce segments from rectangle. (returning null for all indicies)");
			l1 = null;
			l2 = null;
			l3 = null;
			l4 = null;
		}
		LineSegR2[] segments = { l1, l2, l3, l4 };
		return segments;
	}
	
	public static LineSegR2[] rectToLineSegs(RectangleR2 r) {
		LineSegR2 l1;
		LineSegR2 l2;
		LineSegR2 l3;
		LineSegR2 l4;
		try {
			// top line
			l1 = new LineSegR2(r.getMinX(), r.getMinY(), r.getMaxX(), r.getMinY());
			// right line
			l2 = new LineSegR2(r.getMaxX(), r.getMaxY(), r.getMaxX(), r.getMinY());
			// bottom line
			l3 = new LineSegR2(r.getMinX(), r.getMaxY(), r.getMaxX(), r.getMaxY());
			// left line
			l4 = new LineSegR2(r.getMinX(), r.getMinY(), r.getMinX(), r.getMaxY());
		} catch (Exception e) {
			System.err.println(
					"rectToLineSegs(Rectangle r) could not produce segments from rectangle. (returning null for all indicies)");
			l1 = null;
			l2 = null;
			l3 = null;
			l4 = null;
		}
		LineSegR2[] segments = { l1, l2, l3, l4 };
		return segments;
	}

	/*
	 * public Point intersection(Line l) throws Exception{ if (intersects(l)){
	 * if ((vertical&&l.vertical)){ return null; } else if (vertical) { return
	 * new Point(b,l.equation(b)); } else if (l.vertical){ return new
	 * Point(l.b,equation(l.b)); } else { double x=(l.b-b)/(m-l.m); return new
	 * Point(x,equation(x)); } } else { throw new Exception(
	 * "Can't find intersection point of lines that don't intersect."); } }
	 * 
	 * public Point intersection(LineSeg l) throws Exception{ if
	 * (intersects(l)){ if ((vertical&&l.vertical)){ return null; } else if
	 * (vertical) { return new Point(b,l.equation(b)); } else if (l.vertical){
	 * return new Point(l.b,equation(l.b)); } else { double x=(l.b-b)/(m-l.m);
	 * return new Point(x,equation(x)); } } else { throw new Exception(
	 * "Can't find intersection point of lines that don't intersect."); } }
	 */

	public boolean contains(PointR2 p) {
		//return (Line2D.ptSegDist(x1, y1, x2, y2, p.getX(), p.getY()) == 0);
		if (super.contains(p)) {
			if (p.getX()>=getMinX()&&p.getX()<=getMaxX()&&p.getY()>=getMinY()&&p.getY()<=getMaxY()) {
				return true;
			}
		}
		return false;
	}
	
	public double getMaxX() {
		if (x1>x2) {
			return x1;
		} else {
			return x2;
		}
	}
	
	public double getMinX() {
		if (x1<x2) {
			return x1;
		} else {
			return x2;
		}
	}
	
	public double getMaxY() {
		if (y1>y2) {
			return y1;
		} else {
			return y2;
		}
	}
	
	public double getMinY() {
		if (y1<y2) {
			return y1;
		} else {
			return y2;
		}
	}
	
	public static LineSegR2 New(double x1, double y1, double x2, double y2) {
		try {
			return new LineSegR2(x1, y1, x2, y2);
		} catch (Exception e) {
			//this will never happen
			return null;
		}
	}
	
	public static LineSegR2 New(PointR2 p1, PointR2 p2) {
		try {
			return new LineSegR2(p1, p2);
		} catch (Exception e) {
			//this will never happen
			return null;
		}
	}
	
	public FigureR2 intersection(LineR2 l) {
		FigureR2 intsct=super.intersection(l);
		if (intsct.equals(FigureR2.SPACE)) {
			return SPACE;
		} else if (intsct.equals(l)) {
			return clone();
		} else {
			return (PointR2) intsct;
		}
	}
	
	public LineSegR2 clone() {
		return New(p1.clone(),p2.clone());
	}
	
	@Override
	public int ID() {
		return ID;
	}
}
