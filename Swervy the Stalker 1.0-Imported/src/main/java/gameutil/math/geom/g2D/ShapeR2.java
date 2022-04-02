package gameutil.math.geom.g2D;

import gameutil.math.geom.g2D.FigureR2;
import gameutil.math.geom.g2D.PointR2;
import gameutil.math.geom.g2D.VectorR2;
import gameutil.math.geom.Vector;

public abstract class ShapeR2 extends FigureR2{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	protected double area;
	protected double perimeter;
	protected PointR2 center;
	
	protected ShapeR2(double area,double perimeter,PointR2 pos) {
		this.area=area;
		this.perimeter=perimeter;
		center=pos;
	}
	
	public PointR2 getCenter() {
		return (PointR2) center.clone();
	}
	
	public void setPos(PointR2 p) {
		center=(PointR2) p.clone();
	}
	
	public void move(VectorR2 v) {
		center.move(v);
	}
	
	public void move(Vector v) {
		center.move(v);
	}

}
