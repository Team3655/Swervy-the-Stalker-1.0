package gameutil.math.geom.g2D;

import java.io.Serializable;

import gameutil.math.geom.g2D.FigureR2;
import gameutil.math.geom.Figure;

public abstract class FigureR2 implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final FigureR2 SPACE=new FigureR2() {
		@Override
		public int ID() {
			return ID;
		}
	};
	public static final int ID=0;
	
	public FigureR2(/*int p*/) {
		/*if (p<0) {
			p=0;//dimensionality is always positive
		}
		this.p=p;*/
		// TODO Auto-generated constructor stub
	}
	
	public final FigureR2 intersection(FigureR2 f) {return SPACE;}
	
	public final boolean instersects(FigureR2 f) {return false;}
	
	public final boolean contains(FigureR2 f) {return false;}
	
	@Override
	public FigureR2 clone() {
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
