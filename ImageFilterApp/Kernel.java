package ie.gmit.dip;
/**
 * A sample of convolution kernel filters stored as constants in an Enum.
 * 
 * @author Máire Murphy
 *
 */
public enum Kernel {
	
	//original image. Multiplication factor 1.0. Bias 0.0
	IDENTITY (new double[][] {
		{0, 0, 0},
		{ 0, 1, 0},
		{0, 0, 0}
	}),
	
	//outside edges. Multiplication factor 1.0. Bias 0.0
	EDGE_DETECTION_1 (new double[][] {
		{-1, -1, -1},
		{-1, 8, -1},
		{-1, -1, -1}
	}),
	
	//lace effect. Multiplication factor 1.0. Bias 0.0
	LAPLACIAN (new double[][] {
		{0, -1, 0},	
		{-1, 4, -1},
		{0, -1, 0}
	}),
	
	//inside edges. Multiplication factor 1.0. Bias 0.0
	EDGE_DETECTION_2 (new double[][] {
		{1, 0, -1},
		{0, 0, 0},
		{-1, 0, 1}
	}),
	
	//sharpen image. Multiplication factor 1.0. Bias 0.0
	SHARPEN (new double[][] {
			{-1, -1, -1},
			{-1, 9, -1},
			{-1, -1, -1}
	}),
	
	//emphasis vertical lines. Multiplication factor 1.0. Bias 0.0
 VERTICAL_LINES (new double[][] {
			{-1, 2, -1},
			{-1, 2, -1},
			{-1, 2, -1}
	}),
	
//emphasis horizontal lines. Multiplication factor 1.0. Bias 0.0
 HORIZONTAL_LINES(new double[][] {
			{-1, -1, -1},
			{2, 2, 2},
			{-1, -1, -1}
	}),
//emphasis diagonal lines. Multiplication factor 1.0. Bias 0.0
 DIAGONAL_45_LINES (new double[][] {
	 {-1,  0,  0,  0,  0},
	 { 0, -2,  0,  0,  0},
	 { 0,  0,  6,  0,  0},
	 { 0,  0,  0, -2,  0},
	 { 0,  0,  0,  0, -1}

	}),
 
 //Soft blur. Multiplication factor is 1.0/256.0. Bias 0.0
 GAUSSIAN_BLUR (new double[][] {
		  {1,  4,  6,  4,  1},
		  {4, 16, 24, 16,  4},
		  {6, 24, 36, 24,  6},
		  {4, 16, 24, 16,  4},
		  {1,  4,  6,  4,  1},
		
}),

// Blur image. Sum of values .999
 BOX_BLUR (new double[][] {
			{0.111, 0.111, 0.111},
			{0.111, 0.111, 0.111},
			{0.111, 0.111, 0.111}
	}),
	
 //Emphasize horizontal edges. Multiplication factor 1.0. Bias 0.0
 SOBEL_HORIZONTAL (new double[][] {
			{-1, -2, -1},
			{0, 0, 0},
			{1, 2, 1}
	}),
	
 //Emphasize vertical edges. Multiplication factor 1.0. Bias 0.0
 SOBEL_VERTICAL (new double[][]  {
			{-1, 0, 1},
			{-2, 0, 2},
			{-1, 0, 1}
	}),
	
 //shows movement in image. Multiplication factor is 1.0/9.0. Bias 0.0
 MOTION (new double[][]  {
 {1, 0, 0, 0, 0, 0, 0, 0, 0},
 {0, 1, 0, 0, 0, 0, 0, 0, 0},
 {0, 0, 1, 0, 0, 0, 0, 0, 0},
 {0, 0, 0, 1, 0, 0, 0, 0, 0},
 {0, 0, 0, 0, 1, 0, 0, 0, 0},
 {0, 0, 0, 0, 0, 1, 0, 0, 0},
 {0, 0, 0, 0, 0, 0, 1, 0, 0},
 {0, 0, 0, 0, 0, 0, 0, 1, 0},
 {0, 0, 0, 0, 0, 0, 0, 0, 1},
 }),

 //Emboss the image. Multiplication factor is 1.0. Bias 128.0
 EMBOSS (new double[][]  {
	 {-1, -1,  0},
	 { -1,  0,  1},
	 { 0,  1,  1}
		
	});
	
	
	private final double[][] values;
	
	Kernel(double[][] vals){
	 this.values = vals;
	}

	public double[][] getValues() {
		return values;
	}
	



}

