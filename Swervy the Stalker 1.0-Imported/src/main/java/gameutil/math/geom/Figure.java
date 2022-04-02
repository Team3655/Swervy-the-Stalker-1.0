package gameutil.math.geom;

public abstract class Figure implements Cloneable{
	//protected int p; //dimensionality
	//empty space
	public static final Figure SPACE=new Figure() {
		public int ID() {
			return 0;
		}
	};
	
	public Figure(/*int p*/) {
		/*if (p<0) {
			p=0;//dimensionality is always positive
		}
		this.p=p;*/
		// TODO Auto-generated constructor stub
	}
	
	public final Figure intersection(Figure f) {return SPACE;}
	
	public boolean intersects(Figure f) {return false;}
	
	public boolean contains(Figure f) {return false;}
	
	@Override
	public Figure clone() {
		try {
			super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SPACE;
	}
	
	

}
