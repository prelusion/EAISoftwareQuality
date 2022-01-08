package bussimulator;

public enum Lijnen {
	
	LIJN1 (new Stop[]{new Stop(Halte.A,1),new Stop(Halte.B,1),new Stop(Halte.C,1),
			new Stop(Halte.D,1),new Stop(Halte.E,1),new Stop(Halte.F,1),new Stop(Halte.G,1)}),
	LIJN2 (new Stop[]{new Stop(Halte.H,1),new Stop(Halte.E,1),new Stop(Halte.I,1),
			new Stop(Halte.K,1),new Stop(Halte.L,1),new Stop(Halte.M,1)}),
	LIJN3 (new Stop[]{new Stop(Halte.N,1),new Stop(Halte.O,1),new Stop(Halte.P,1),
			new Stop(Halte.K,-1),new Stop(Halte.Q,1),new Stop(Halte.R,1)}),
	LIJN4 (new Stop[]{new Stop(Halte.S,1),new Stop(Halte.T,1),new Stop(Halte.U,1),
			new Stop(Halte.V,1),new Stop(Halte.I,1),new Stop(Halte.K,1),new Stop(Halte.L,1),new Stop(Halte.M,1)}),
	LIJN5 (new Stop[]{new Stop(Halte.W,1),new Stop(Halte.X,1),new Stop(Halte.Y,1),new Stop(Halte.G,1),
			new Stop(Halte.Z,1),new Stop(Halte.N,1)}),
	LIJN6 (new Stop[]{new Stop(Halte.A,1),new Stop(Halte.B,1),new Stop(Halte.H,1),new Stop(Halte.T,-1),
			new Stop(Halte.X,-1),new Stop(Halte.W,-1)}),
	LIJN7 (new Stop[]{new Stop(Halte.A,1),new Stop(Halte.R,-1),new Stop(Halte.Q,-1),new Stop(Halte.L,1),
			new Stop(Halte.M,1),new Stop(Halte.O,-1),new Stop(Halte.N,-1),new Stop(Halte.Z,-1),
			new Stop(Halte.G,-1),new Stop(Halte.Y,-1),new Stop(Halte.X,-1),new Stop(Halte.W,-1)}),
	LIJN8 (new Stop[]{new Stop(Halte.M,-1),new Stop(Halte.P,1),new Stop(Halte.J,1),new Stop(Halte.F,-1),
			new Stop(Halte.V,-1),new Stop(Halte.E,-1),new Stop(Halte.H,-1)});
	
	private Stop[] haltes;
	
	Lijnen(Stop...haltes){
		this.haltes=haltes;
	}
	
	int getLengte() {
		return haltes.length;
	}
	
	Halte getHalte(int positie){
		return haltes[positie].halte;
	}
	
	int getRichting(int positie){
		return haltes[positie].richting;
	}
	
	static class Stop{
		Halte halte;
		int richting;
		
		public Stop(Halte halte, int richting){
			this.halte=halte;
			this.richting=richting;
		}
	}
}
