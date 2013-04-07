package Kernel;

import Jama.*;

public class Matrice implements MatriceInterface {

	private Matrix MATRICE;
	private Matrix VECTEUR;
	public Matrice(Matrix MATRICE, Matrix VECTEUR){
		this.MATRICE=MATRICE;
		this.VECTEUR=VECTEUR;
	}
	public Matrix getMATRICE() {
		return MATRICE;
	}
	public void setMATRICE(Matrix mATRICE) {
		MATRICE = mATRICE;
	}
	public Matrix getVECTEUR() {
		return VECTEUR;
	}
	public void setVECTEUR(Matrix vECTEUR) {
		VECTEUR = vECTEUR;
	}


	
	public Matrice  equation(Position position2, Position position1) {
		double[][] matrice = new double[4][4];
		double[][] vecteur = new double[4][1];
		
		matrice[0][0]=1; 
		matrice[0][1]= position2.getOeilg().getX(); 
		matrice[0][2]=position2.getOeilg().getY();
		matrice[0][3]=0;
		
		matrice[0][0]=1;
		matrice[0][1]= position1.getOeilg().getX();
		matrice[0][2]=position1.getOeilg().getY();
		matrice[0][3]=0;
		
		matrice[1][0]=0;
		matrice[1][1]=position1.getOeilg().getY();
		matrice[1][2]=-position1.getOeilg().getX();
		matrice[1][3]=1;
		
		matrice[2][0]=1;
		matrice[2][1]= position1.getOeild().getX();
		matrice[2][2]=position1.getOeild().getY();
		matrice[2][3]=0;
		
		matrice[3][0]=0;
		matrice[3][1]=position1.getOeild().getY();
		matrice[3][2]=-position1.getOeild().getX();
		matrice[3][3]=1;
		
		
		vecteur[0][0]=position2.getOeilg().getX();
		vecteur[1][0]=position2.getOeilg().getY();
		vecteur[2][0]=position2.getOeild().getX();
		vecteur[3][0]=position2.getOeild().getY();
		
		this.setMATRICE(new Matrix(matrice));
		this.setVECTEUR(new Matrix(vecteur));	
		return this;
	}
	
	
	
	public Matrix solve1() 
	 // A est la matrice des coordonn�es des points du tableau et 
	 // Y le vecteur colonne des points de la photo
	 {
		 Matrix A1=((this.getMATRICE().transpose()).times(this.getMATRICE())).inverse();
		 
		 Matrix B = (A1.times(this.getMATRICE().transpose())).times(this.getVECTEUR());
		 return B;
	 }
	@Override
	public Matrice equation1(Position position1, Position position2) {
		double[][] matrice = new double[6][6];
		double[][] vecteur = new double[6][1];
		
		matrice[0][0]=1; matrice[0][1]= position2.getOeilg().getX(); matrice[0][2]=position2.getOeilg().getY();
		matrice[0][3]=0;matrice[0][4]=0;matrice[0][5]=0;matrice[1][0]=0;matrice[1][1]=0;matrice[1][2]=0;
		matrice[1][3]=1;matrice[1][4]=position2.getOeilg().getX();matrice[1][5]=position2.getOeilg().getY();
		
		matrice[2][0]=1; matrice[2][1]= position2.getOeild().getX(); matrice[2][2]=position2.getOeild().getY();
		matrice[2][3]=0;matrice[2][4]=0;matrice[2][5]=0;matrice[3][0]=0;matrice[3][1]=0;matrice[3][2]=0;
		matrice[3][3]=1;matrice[3][4]=position2.getOeild().getX();matrice[3][5]=position2.getOeild().getY();

		matrice[4][0]=1; matrice[4][1]= position2.getMilieu().getX(); matrice[4][2]=position2.getMilieu().getY();
		matrice[4][3]=0;matrice[4][4]=0;matrice[4][5]=0;matrice[5][0]=0;matrice[5][1]=0;matrice[5][2]=0;
		matrice[5][3]=1;matrice[5][4]=position2.getMilieu().getX();matrice[5][5]=position2.getMilieu().getY();
		
		vecteur[0][0]=position1.getOeilg().getX(); vecteur[1][0]=position1.getOeilg().getY();
		vecteur[2][0]=position1.getOeild().getX();vecteur[3][0]=position1.getOeild().getY();
		vecteur[4][0]=position1.getMilieu().getX();vecteur[5][0]=position1.getMilieu().getY();
		
		this.setMATRICE(new Matrix(matrice));
		this.setVECTEUR(new Matrix(vecteur));	
		return this;	}
	
}