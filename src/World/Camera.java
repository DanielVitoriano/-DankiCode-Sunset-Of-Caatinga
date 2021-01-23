package World;

public class Camera {
	private static int x, y;

	public static int clamp(int xAtual, int xMin, int xMax) {
		if(xAtual < xMin) {
			xAtual = xMin;
		}
		else if(xAtual > xMax){
			xAtual = xMax;
		}
		return xAtual;
	}
	
	public static int getX() {
		return x;
	}

	public static int getY() {
		return y;
	}

	public static void setX(int x) {
		Camera.x = x;
	}

	public static void setY(int y) {
		Camera.y = y;
	}
	
	
}
