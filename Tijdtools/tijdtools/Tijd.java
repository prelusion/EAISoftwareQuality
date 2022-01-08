package tijdtools;

public class Tijd
{
	private int uur;
	private int minuut;
	private int seconde;
	
	public Tijd(){
		this.uur = 0;
		this.minuut = 0;
		this.seconde = 0;		
	}
	
	public Tijd(int uur, int minuut, int seconde) {
		super();
		this.uur = uur;
		this.minuut = minuut;
		this.seconde = seconde;
	}

	public int getUur() {
		return uur;
	}

	public void setUur(int uur) {
		this.uur = uur;
	}

	public int getMinuut() {
		return minuut;
	}

	public void setMinuut(int minuut) {
		this.minuut = minuut;
	}

	public int getSeconde() {
		return seconde;
	}

	public void setSeconde(int seconde) {
		this.seconde = seconde;
	}
	
	public void increment(Tijd step){
		this.seconde += step.seconde;
		this.minuut += step.minuut;
		this.uur += step.uur;
		if (this.seconde>=60){
			this.seconde-=60;
			this.minuut++;
		}
		if (this.minuut>=60){
			this.minuut-=60;
			this.uur++;
		}
	}

	public Tijd copyTijd(){
		return new Tijd(this.uur, this.minuut, this.seconde);
	}
	
	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", uur,minuut,seconde);
	}    	
}
