package gameutil.math.geom.g2D;

public class OutsideOfDomainOrRangeException extends Exception {
	public OutsideOfDomainOrRangeException() {
		super("Specified x or y value is outside of the domain or range of this line");
	}
}
