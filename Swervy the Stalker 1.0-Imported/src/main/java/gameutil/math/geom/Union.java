package gameutil.math.geom;

public class Union extends Figure {
	Figure f1;
	Figure f2;
	public Union(Figure figure1,Figure figure2) {
		super();
	}
	
	public boolean intersects(Figure f) {
		return f1.intersects(f)||f2.intersects(f);
	}
	
	public boolean contains(Figure f) {
		return f1.contains(f)||f2.contains(f);//also if it is partially contained by both then this could be true but I'll add that later
	}
}
