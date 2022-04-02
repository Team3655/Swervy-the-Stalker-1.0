package gameutil.math.geom.g1D;

import gameutil.math.geom.g2D.FigureR2;

public abstract class Figure1D implements Cloneable{
	
	public static final Figure1D SPACE=new Figure1D(){
		public int ID() {
			return 0;
		}
	};
	
	public static final int ID=0;
	
	protected Figure1D() {}
	
	public boolean intersects(Figure1D f) {
		return false;
	}
	
	public boolean contains(Figure1D f) {
		return false;
	}
	
	public Figure1D intersection(Figure1D f) {
		return f;
	}
	
	@Override
	public Figure1D clone() {
		try {
			super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SPACE;
	}
	
	public abstract int ID();
}
