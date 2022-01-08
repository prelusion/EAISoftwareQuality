package bussimulator;

public enum Halte {
	A (new Positie(1,1)),
	B (new Positie(2,3)),
	C (new Positie(4,4)),
	D (new Positie(7,5)),
	E (new Positie(6,9)),
	F (new Positie(11,11)),
	G (new Positie(15,14)),
	H (new Positie(3,10)),
	I (new Positie(19,8)),
	J (new Positie(13,8)),
	K (new Positie(11,5)),
	L (new Positie(12,2)),	
	M (new Positie(17,1)),
	N (new Positie(20,9)),
	O (new Positie(19,6)),
	P (new Positie(15,5)),
	Q (new Positie(9,2)),
	R (new Positie(6,1)),
	S (new Positie(5,22)),
	T (new Positie(6,17)),
	U (new Positie(7,14)),
	V (new Positie(8,11)),
	W (new Positie(12,22)),
	X (new Positie(11,18)),
	Y (new Positie(12,15)),
	Z (new Positie(12,8));
	
	private final Positie positie;
	
	Halte(Positie positie){
		this.positie = positie;
	}
	
	int afstand(Positie vanaf) {
		return 2*Math.abs(positie.x-vanaf.x)+Math.abs(positie.y-vanaf.y);
	}
	
	Positie getPositie() {
		return positie;
	}
	
	static class Positie {
		int x;
		int y;
		
		Positie(int x, int y){
			this.x=x;
			this.y=y;
		}
	}
}
