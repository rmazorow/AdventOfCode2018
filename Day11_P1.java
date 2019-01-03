class Day11_P1 {
	public static int[][] fillGrid(int serial) {
		int[][] grid = new int[300][300];
		
		for (int r = 0; r < 300; r++) {
			for (int c = 0; c < 300; c++) {
				/*
				Find the fuel cell's rack ID, which is its X coordinate plus 10.
				Begin with a power level of the rack ID times the Y coordinate.
				Increase the power level by the value of the grid serial number (your puzzle input).
				Set the power level to itself multiplied by the rack ID.
				Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0).
				Subtract 5 from the power level.
				*/
				int id = c + 10;
				grid[r][c] = ((id * r + serial) * id) / 100 % 10 - 5;
			}
		}
		return grid;
	}
	
	public static String maxCell(int[][] grid) {
		String coord = "";
		int max = 0;
		for (int r = 0; r < 298; r++) {
			for (int c = 0; c < 298; c++) {
				int sum = 0;
				for (int rAdd = 0; rAdd < 3; rAdd++) {
					for (int cAdd = 0; cAdd < 3; cAdd++) {
						sum += grid[r + rAdd][c + cAdd];
					}
				}
				if (sum > max) {
					max = sum;
					coord = c + "," + r;
				} 
			}
		}
		System.out.println("Max value is: " + max);
		return coord;
	}
	
	public static void main(String[] args) {
		int[][] grid = fillGrid(3031);
		System.out.println("The X,Y coordinate of the top-left fuel cell of the 3x3 square with the largest total power is " + maxCell(grid));
	}
}