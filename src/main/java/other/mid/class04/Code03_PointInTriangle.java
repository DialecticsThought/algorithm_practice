package other.mid.class04;

public class  Code03_PointInTriangle {

	public static boolean isInside1(double x1, double y1, double x2, double y2, double x3, double y3, double x,
			double y) {
		double area1 = getArea(x1, y1, x2, y2, x, y);
		double area2 = getArea(x1, y1, x3, y3, x, y);
		double area3 = getArea(x2, y2, x3, y3, x, y);
		double allArea = getArea(x1, y1, x2, y2, x3, y3);
		return area1 + area2 + area3 <= allArea;
	}
	//海伦公式 通过三角形的边长求出面积
	public static double getArea(double x1, double y1, double x2, double y2, double x3, double y3) {
		double side1Len = getSideLength(x1, y1, x2, y2);
		double side2Len = getSideLength(x1, y1, x3, y3);
		double side3Len = getSideLength(x2, y2, x3, y3);
		double p = (side1Len + side2Len + side3Len) / 2;
		return Math.sqrt((p - side1Len) * (p - side2Len) * (p - side3Len) * p);
	}

	public static double getSideLength(double x1, double y1, double x2, double y2) {
		double a = Math.abs(x1 - x2);
		double b = Math.abs(y1 - y2);
		return Math.sqrt(a * a + b * b);
	}

	public static boolean isInside2(double x1, double y1, double x2, double y2, double x3, double y3, double x,
			double y) {
		if (crossProduct(x3 - x1, y3 - y1, x2 - x1, y2 - y1) >= 0) {
			double tmpx = x2;
			double tmpy = y2;
			x2 = x3;
			y2 = y3;
			x3 = tmpx;
			y3 = tmpy;
		}
		if (crossProduct(x2 - x1, y2 - y1, x - x1, y - y1) < 0) {
			return false;
		}
		if (crossProduct(x3 - x2, y3 - y2, x - x2, y - y2) < 0) {
			return false;
		}
		if (crossProduct(x1 - x3, y1 - y3, x - x3, y - y3) < 0) {
			return false;
		}
		return true;
	}
	//叉乘方法
	// If return negative value, it means (x1, y1) in (x2,y2) right
	// If return positive value, it means (x1, y1) in (x2,y2) left
	public static double crossProduct(double x1, double y1, double x2, double y2) {
		return x1 * y2 - x2 * y1;
	}

	public static void main(String[] args) {
		double x1 = -5;
		double y1 = 0;
		double x2 = 0;
		double y2 = 8;
		double x3 = 5;
		double y3 = 0;
		double x = 0;
		double y = 5;
		System.out.println(isInside1(x1, y1, x2, y2, x3, y3, x, y));
		System.out.println(isInside2(x1, y1, x2, y2, x3, y3, x, y));
	}

}
