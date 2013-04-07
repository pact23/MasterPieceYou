package Kernel;

import Jama.Matrix;


public interface MatriceInterface {
	public Matrice equation(Position position1, Position position2);
	public Matrice equation1(Position position1, Position position2);
	public Matrix solve1();

}
