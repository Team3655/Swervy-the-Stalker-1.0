package gameutil.math.geom.g2D;

import java.awt.geom.Point2D;

import gameutil.math.geom.g2D.FigureR2;
import gameutil.math.geom.g2D.PointR2;
import gameutil.math.geom.g2D.VectorR2;
import gameutil.math.geom.Vector;
import gameutil.math.geom.g1D.Vector1D;

public class PointR2 extends FigureR2{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public static final int ID=2;
	
	private double x;
	private double y;
	
	public PointR2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public PointR2(VectorR2 v) {
		x=v.mX;
		y=v.mY;
	}

	public double distance(PointR2 p) {
		return Point2D.distance(x, y, p.x, p.y);
	}

	public double distanceSq(PointR2 p) {
		return Point2D.distanceSq(x, y, p.x, p.y);
	}

	public void move(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(VectorR2 v) {
		x+=v.mX;
		y+=v.mY;
	}
	
	public void move(Vector v) {
		x+=v.getSpds().i(0);
		y+=v.getSpds().i(1);
	}
	
	public void move(Vector1D v) {
		x+=v.getMagnetude();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public PointR2 clone() {
		return new PointR2(x,y);
	}
	
	@Override
	public int ID() {
		return ID;
	}
}
