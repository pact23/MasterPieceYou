package Kernel;



public class Position {
	private Couple oeilg;
	private Couple oeild;
	private Couple milieu;
	public Position(Couple oeilg, Couple oeild, Couple milieu){
		this.oeilg=oeilg;
		this.oeild=oeild;
		this.milieu=milieu;
	}
	public Couple getOeilg() {
		return oeilg;
	}
	public void setOeilg(Couple oeilg) {
		this.oeilg = oeilg;
	}
	public Couple getOeild() {
		return oeild;
	}
	public void setOeild(Couple oeild) {
		this.oeild = oeild;
	}
	public Couple getMilieu() {
		return milieu;
	}
	public void setMilieu(Couple milieu) {
		this.milieu = milieu;
	}
	
}
