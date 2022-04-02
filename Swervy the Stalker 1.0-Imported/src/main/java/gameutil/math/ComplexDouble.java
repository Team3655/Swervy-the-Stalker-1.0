package gameutil.math;

public class ComplexDouble {
	
	private double a;
	private double b;
	
	public ComplexDouble(double real,double imaginary) {
		this.a=real;
		this.b=imaginary;
	}
	
	public ComplexDouble $A$(ComplexDouble d) {
		return new ComplexDouble(d.a+this.a,d.b+this.b);
	}
	
	public ComplexDouble $A$(double d) {
		return new ComplexDouble(d+this.a,this.b);
	}
	
	public ComplexDouble $S$(ComplexDouble d) {
		return new ComplexDouble(this.a-d.a,this.b-d.b);
	}
	
	public ComplexDouble $S$(double d) {
		return new ComplexDouble(this.a-d,this.b);
	}
	
	public ComplexDouble $X$(ComplexDouble d) {
		return new ComplexDouble(d.a*this.a-d.b*this.b,d.b*this.a+d.a*this.b);
	}
	
	public ComplexDouble $X$(double d) {
		return new ComplexDouble(d*this.a,d*this.b);
	}
	
	public ComplexDouble $D$(ComplexDouble d) {
		return this. $X$ (new ComplexDouble(d.a,-1*d.b)). $D$ (Math.pow(d.a, 2)+Math.pow(d.b, 2));
	}
	
	public ComplexDouble $D$(double d) {
		return new ComplexDouble(this.a/d,this.b/d);
	}
	
	public ComplexDouble pow(int pow) {//double powers coming soon!
		ComplexDouble answer=this;
		boolean negativePow=false;
		if (pow==0) {
			return new ComplexDouble(1,0);
		} else if (pow<1) {
			negativePow=false;
			pow=-pow;
		}
		for (int i=1; i<pow;i++) {
			answer=answer. $X$ (answer);
		}
		if (negativePow) {
			answer=new ComplexDouble(1,0). $D$ (answer);
		}
		return answer;
	}
}
