//Code by Máire Murphy G00375722
//CTA Project: Benchmark five different sorting algorithms
package ie.gmit.dip;

public class Runner {
	// sample sizes for the benchmark
	private static int[] arrSampleSizes = { 1, 5, 10, 25, 50, 75, 100, 250, 500, 750, 1000, 2500, 5000, 7500, 10000,
			25000, 50000, 75000, 100000 };
	// 5 sorting algorithm titles
	private static String[] titles = { "Bubble Sort", "Insertion Sort", "Merge Sort", "Counting Sort", "TimSort" };
	// store average of time elapsed in milliseconds
	private static double[][] sortResults = new double[titles.length][arrSampleSizes.length];
	// run each algorithm 10 times
	private static final int runs = 10;

	/*
	 * create a randomly filled integer array of size n code from CTA project
	 * specification
	 */
	static int[] randomArray(int n) {
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = (int) (Math.random() * 100000); // range is 100,000
		}
		return array;
	}

	static void printResults() {
		// print results heading
		System.out.println("\t\t\t\t\tSorting Algorithm Execution Time (Milliseconds)\r");
		// label for sample sizes
		System.out.printf("%-15s", "Sample Size");

		// number of samples
		int numSamples = arrSampleSizes.length;
		// print out the sample sizes as column headings
		for (int i = 0; i < numSamples; ++i)
			System.out.printf("%10d", arrSampleSizes[i]);
		System.out.println();
		System.out.println(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		// number of sorting algorithms
		int size = titles.length;

		// outer loop print the sort algorithm title (row title) and will be the row
		// index for the inner loop
		for (int row = 0; row < size; row++) {
			System.out.printf("%-15s", titles[row]);
			// inner loop prints the results for each sorting algorithm
			for (int col = 0; col < numSamples; ++col) {
				// set decimal format to display 3 decimal places
				System.out.printf("%10.3f", sortResults[row][col]);
			}
			System.out.println();
		}

	}

	/*
	 * The runSort is recursively called as the average of 10 runs is required
	 * parameters are:
	 * - cnt: keeps track of number of recursive iterations, it starts at 10 and counts down 
	 * - double totalElapsedMillis keeps track of total milliseconds taken for each run
	 * - Sorter test is the object that give access to the sorting algorithms in the Sorter class 
	 * - int[] arr passes in the same unsorted array of random numbers for each run
	 * - String sortType is used in the switch statement to execute the correct algorithm 
	 * returns the average of the time taken to execute the sort
	 */
	public static double runSort(int cnt, double totalElapsedMillis, Sorter test, int[] arr, String sortType) {
		double startTime = 0.0;
		double elapsedMillis = 0.0;
		double endTime = 0.0;
		double timeElapsed = 0.0;

		/*
		 * take copy of original unsorted array so subsequent Sorts have level playing
		 * field
		 */
		int[] arrCopy = arr.clone();

		/* each sort will be time how long takes to execute (in nanoseconds) */
		switch (sortType) {
		case "Bubble" -> {
			startTime = System.nanoTime();
			test.bubbleSort(arrCopy); // bubble Sort
			endTime = System.nanoTime();
		}
		case "Insertion" -> {
			startTime = System.nanoTime();
			test.insertionSortArr(arrCopy); // insertion Sort
			endTime = System.nanoTime();
		}
		case "Merge" -> {
			startTime = System.nanoTime();
			test.sort(arrCopy, 0, arrCopy.length - 1); // Merge Sort
			endTime = System.nanoTime();
		}
		case "Counting" -> {
			startTime = System.nanoTime();
			test.countingSort(arrCopy); // Counting Sort
			endTime = System.nanoTime();
		}
		case "TimSort" -> {
			startTime = System.nanoTime();
			test.timSort(arrCopy, arrCopy.length); // Tim Sort
			endTime = System.nanoTime();
		}
		}// switch

		timeElapsed = endTime - startTime;
		elapsedMillis = timeElapsed / 1000000; // convert from nanoseconds to milliseconds
		totalElapsedMillis += elapsedMillis; // keep total

		if (cnt <= 1) {
			return (totalElapsedMillis / runs);// return the average
		}

		return runSort(cnt - 1, totalElapsedMillis, test, arr, sortType);
	}

	/*
	 * main: creates an object for Sorter class. It is passed as a argument to the
	 * runSort function 
	 * - calls randomArray function which create an array of random  number for each 
	 *    sample size used in runSort function
	 * - calls the runSort function for each sample size (sorts the array for each sorting algorithm and
	 *    stores the result in a 2d array 
	 * - calls printResults which displays the results in a tabular format with headings
	 */
	public static void main(String[] args) {
		// array to hold random values of size n
		int[] arr;
		// Sorter class contains the sorting algorithms
		Sorter test = new Sorter();

		// loop for each sample size in sample size array
		for (int x = 0; x <= arrSampleSizes.length - 1; x++) {
			// populate array with random integers with current sample size (in
			// arrSampleSizes array)
			arr = randomArray(arrSampleSizes[x]);

			/*
			 * Each sorting algorithm will be called recursively until 10 runs reached. The
			 * average of 10 execution time will be stored in an array for each sorting type
			 */
			sortResults[0][x] = runSort(runs, 0, test, arr, "Bubble");
			sortResults[1][x] = runSort(runs, 0, test, arr, "Insertion");
			sortResults[2][x] = runSort(runs, 0, test, arr, "Merge");
			sortResults[3][x] = runSort(runs, 0, test, arr, "Counting");
			sortResults[4][x] = runSort(runs, 0, test, arr, "TimSort");

		}
		// print results
		printResults();
	}

}
