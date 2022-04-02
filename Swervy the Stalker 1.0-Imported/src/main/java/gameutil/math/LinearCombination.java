package gameutil.math;

public class LinearCombination {
	public static enum STATE {known,unknown};
	private STATE vectorState=STATE.unknown;
	private STATE constantState=STATE.unknown;
	
	int n=0;
	
	public LinearCombination(int n) {
		this.n=n;
	}
}
