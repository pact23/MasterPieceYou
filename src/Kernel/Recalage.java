package Kernel;

import com.example.masterpieceyou.BufferedImage;

import Jama.Matrix;


public class Recalage implements RecalageInterface {
	private BufferedImage img;
	private BufferedImage tab;
	private double x1, y1, d1, x2, y2, d2,w1,w2,h1,h2;
	
	public Recalage(BufferedImage img, BufferedImage tab, double eyeCenterX , double eyeCenterY, double eyeDistance, double eyeCenterX2, double eyeCenterY2, double eyeDistance2, double d, double e, double f, double g) {
		this.img = img;
		this.tab = tab;
		this.x1 = eyeCenterX;
		this.x2 = eyeCenterX2;
		this.d1 = eyeDistance;
		this.y1 = eyeCenterY;
		this.y2 = eyeCenterY2;
		this.d2 = eyeDistance2;
		this.w1 = d;
		this.w2 = e;
		this.h1 = f;
		this.h2 = g;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public BufferedImage getTab() {
		return tab;
	}

	public void setTab(BufferedImage tab) {
		this.tab = tab;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getD1() {
		return d1;
	}

	public void setD1(double d1) {
		this.d1 = d1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	public double getD2() {
		return d2;
	}

	public void setD2(double d2) {
		this.d2 = d2;
	}
	
	public BufferedImage recaler() {
		Couple valCenter1 = new Couple(0,0);
		valCenter1 = valCenter1.ToCoordinnates(this.x1, this.y1);
		Couple valCenter2 = new Couple(0,0);
		valCenter2 = valCenter2.ToCoordinnates(this.x2, this.y2);
		
		double D1 = (d1);
	    double D2 = (d2);
	    
	    double W1 = (w1);
	    double W2 = (w2);
	    double H1 = (h1);
	    double H2 = (h2);
	    
	    
	    valCenter1.setX(valCenter1.getX()*img.getWidth()/W1);
	    valCenter1.setY(valCenter1.getY()*img.getHeight()/H1);
	    valCenter2.setX(valCenter2.getX()*tab.getWidth()/W2);
	    valCenter2.setY(valCenter2.getY()*tab.getHeight()/H2);
	    
	    D1 = D1*img.getWidth()/W1;
	    D2 = D2*tab.getWidth()/W2;
	    
	    System.out.println(valCenter1.getX()+" - "+valCenter1.getY()+" - "+D1+"/"+d1);
	    System.out.println(valCenter2.getX()+" - "+valCenter2.getY()+" - "+D2);
		
		Matrice matrice = new Matrice(null,null);
		Couple oeilg1= new Couple(valCenter1.getX()-D1/2,valCenter1.getY());
		Couple oeild1= new Couple(valCenter1.getX()+D1/2,valCenter1.getY());
		
		Couple oeilg2= new Couple(valCenter2.getX()-D2/2,valCenter2.getY());
		Couple oeild2= new Couple(valCenter2.getX()+D2/2,valCenter2.getY());
		
		Position position1 = new Position(oeilg1,oeild1,valCenter1);
		Position position2 = new Position(oeilg2,oeild2,valCenter2);
		
		Matrice A = matrice.equation(position1, position2);
		Matrix B = A.solve1();
		
		Couple pixel = new Couple(0,0);
		int X = 0;
		int Y= 0;
		int v=0; int v1=0;
		int r=0; int r1=0;
		int b=0; int b1=0;
		int p1=0; int p2=0;
		double t=0; double t1=0.; double t2=0.;
		int[][] tabRGB, picRGB = null;
		
		OvalFaceParameters ovalTab = new OvalFaceParameters(valCenter2,D2);
		double aOvalTab = ovalTab.getPetitAxe();
		double bOvalTab = ovalTab.getGrandAxe();
		
		OvalFaceParameters ovalPic = new OvalFaceParameters(valCenter1,D1);
		double aOvalPic = ovalPic.getPetitAxe();
		double bOvalPic = ovalPic.getGrandAxe();
		
		int iMin = (int) Math.max(0,(ovalTab.getFaceCenter().getX()-aOvalTab));
		int iMax = (int) Math.min(tab.getWidth(),(ovalTab.getFaceCenter().getX()+aOvalTab));
		int jMin = (int) Math.max(0,(ovalTab.getFaceCenter().getY()-bOvalTab));
		int jMax = (int) Math.min(tab.getHeight(),(ovalTab.getFaceCenter().getY()+bOvalTab));
		tabRGB = new int[iMax][jMax];
		for(int i=iMin; i<iMax; i++) {
			for(int j=jMin; j<jMax; j++) {			
				if (ovalTab.InOvale(i, j)) {
					tabRGB[i][j] = tab.getRGB(i, j);
					
					v=v+((tabRGB[i][j]>>8) & 0xFF);
					r=r+((tabRGB[i][j]>>16) & 0xFF);
					b=b+((tabRGB[i][j]) & 0xFF);
					p1=p1+1;
				}
			}
		}
		
		int iMin1 = (int) Math.max(0,(ovalPic.getFaceCenter().getX()-aOvalPic));
		int iMax1 = (int) Math.min(img.getWidth(),(ovalPic.getFaceCenter().getX()+aOvalPic));
		int jMin1 = (int) Math.max(0,(ovalPic.getFaceCenter().getY()-bOvalPic));
		int jMax1 = (int) Math.min(img.getHeight(),(ovalPic.getFaceCenter().getY()+bOvalPic));
		picRGB = new int[iMax1][jMax1];
		for(int i=iMin1; i<iMax1; i++) {
			for(int j=jMin1; j<jMax1; j++) {			
				if (ovalPic.InOvale(i, j)) {
					picRGB[i][j] = img.getRGB(i, j);
					
					v1=v1+((picRGB[i][j]>>8) & 0xFF);
					r1=r1+((picRGB[i][j]>>16) & 0xFF);
					b1=b1+((picRGB[i][j]) & 0xFF);
					p2=p2+1;
				}
			}
		}
		
		
		t=(v+r+b)/(3*p1); t1=(v1+r1+b1)/(3*p2);
		t2=t/t1;
	
		int diffFlou = (int) (0.3*D1);
		OvalFaceParameters ovalPicReduced = new OvalFaceParameters(valCenter1, D1-diffFlou);
		
		OvalFaceParameters[] ovalsPicReduced = new OvalFaceParameters[diffFlou+1];
		for (int k=0;k<(diffFlou+1);k++)
			ovalsPicReduced[k] = new OvalFaceParameters(valCenter1, D1-diffFlou+k);
		
		for(int i=(int) Math.max(0,(ovalTab.getFaceCenter().getX()-aOvalTab)); i<(int) Math.min(tab.getWidth(),(ovalTab.getFaceCenter().getX()+aOvalTab)); i++) {
			double valInt = bOvalTab*Math.sqrt(1-(i-(ovalTab.getFaceCenter().getX()))*(i-(ovalTab.getFaceCenter().getX()))/(aOvalTab*aOvalTab));
			int j1 = Math.max(0, (int)(ovalTab.getFaceCenter().getY()-valInt));
			int j2 = Math.min((int)(ovalTab.getFaceCenter().getY()+valInt),tab.getHeight());
			
			for(int j=j1; j<j2; j++) {
				pixel.setX(i);pixel.setY(j);
			
				Couple pixel1 = pixel.pixelCorrespondant(B);
				X=(int) pixel1.getX(); Y=(int) pixel1.getY();
	
				if ((X>0)&&(Y>0)&&(X<iMax1)&&(Y<jMax1)&&ovalPicReduced.InOvale(pixel1.getX(),pixel1.getY())) {
					int newRGB1 = tabRGB[i][j];
					int n= (newRGB1>>16) & 0xFF;
					//int n1= (newRGB1>>8) & 0xFF; 
					int n2= newRGB1 & 0xFF;
					
					int rgb = picRGB[X][Y];
					int alpha = (rgb >>24 ) & 0xFF;
					int rouge = (rgb >>16 ) & 0xFF;
					int vert = (rgb >> 8 ) & 0xFF;
					int bleu = rgb & 0xFF;
					
					if ((rouge*t2)>255) {rouge=255;} else rouge = (int)(rouge*t2);
					if ((vert*t2)>255) {vert=255;} else vert = (int)(vert*t2);
					if ((bleu*t2)>255) {bleu=255;} else bleu = (int)(bleu*t2);
					
					bleu = bleu*5/6+n2/6;
					rouge = (rouge*5/6+n/6);
					
					int newRGB = (alpha<<24) + (rouge<<16) + ((vert)<<8) + (bleu);
					
					if ((newRGB& 0x00ffffff) != 0)
						tab.setRGB(i,j,newRGB);				
				}
				
				for (int k=0;k<diffFlou;k++) {
					if((ovalsPicReduced[k+1].InOvale(pixel1.getX(), pixel1.getY()))&&(!ovalsPicReduced[k].InOvale(pixel1.getX(), pixel1.getY()))&&(X<iMax1)&&(Y<jMax1)){
						double k1 = ((double) k)/diffFlou;
						
						int newRGB1 = tabRGB[i][j];
						double n= (double) ((newRGB1>>16) & 0xFF);
						double n1= (double) ((newRGB1>>8) & 0xFF); 
						double n2= (double) (newRGB1 & 0xFF);
		
						int rgb = picRGB[X][Y];
						int alpha = 255;
						double rouge = (rgb >>16 ) & 0xFF;
						double vert = (rgb >> 8 ) & 0xFF;
						double bleu = rgb & 0xFF;
						
						if ((rouge*t2)>255) {rouge=255;} else rouge = (int)(rouge*t2);
						if ((vert*t2)>255) {vert=255;} else vert = (int)(vert*t2);
						if ((bleu*t2)>255) {bleu=255;} else bleu = (int)(bleu*t2);
						
						int rougeI =  (int) ((rouge)*(1.0-k1) + (n *k1));
						int bleuI  =  (int) ((bleu )*(1.0-k1) + (n2*k1));
						int vertI  =  (int) ((vert )*(1.0-k1) + (n1*k1));
						
						int newRGB = (alpha<<24) + (rougeI<<16) + (vertI<<8) + (bleuI);
						
						if ((rouge>0)||(bleu>0)||(vert>0))
							tab.setRGB(i,j,newRGB);
					}
				}
			}
		}		
		return tab;
	}
}