package ie.gmit.dip;

import java.util.Arrays;

public class Sorter {

	/*
	 * ************************ Java program to perform TimSort. code taken from
	 * https://www.geeksforgeeks.org/timsort/ by 29AjayKumar Tim Sort uses
	 * components of insertion sort and merge sort. This a simplified version of
	 * timsort - the original implementation is in C and contains 3409 lines of code
	 */

	// sub arrays to be merged are set with a minimum size 32
	static int MIN_MERGE = 32;

	/*
	 * 1. Calculate the minimum length of run minRun a) If the array size is 2 to
	 * the Nth power, then return 16 (MIN_MERGE / 2) b) In other cases, shift to the
	 * right bit by bit (ie, divide by 2) until you find a number between 16 and 32
	 */
	private static int minRunLength(int n) {
		assert n >= 0;

		// Becomes 1 if any 1 bits are shifted off
		int r = 0;
		while (n >= MIN_MERGE) {
			r |= (n & 1);
			n >>= 1;
		}
		return n + r;
	}

	// This function sorts the chunk of the array from left index to right index
	// to right index which is of max size RUN
	private void insertionSort(int[] arr, int left, int right) {
		for (int i = left + 1; i <= right; i++) {
			int temp = arr[i];
			int j = i - 1;
			while (j >= left && arr[j] > temp) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = temp;
		}
	}

	// Merge function merges the sorted runs
	private void mergeTS(int[] arr, int l, int m, int r) {
		// Original array is broken in two parts
		// left and right array
		int len1 = m - l + 1, len2 = r - m;
		int[] left = new int[len1];
		int[] right = new int[len2];
		for (int x = 0; x < len1; x++) {
			left[x] = arr[l + x];
		}
		for (int x = 0; x < len2; x++) {
			right[x] = arr[m + 1 + x];
		}

		int i = 0;
		int j = 0;
		int k = l;

		// After comparing, we merge those two array
		// in larger sub array
		while (i < len1 && j < len2) {
			if (left[i] <= right[j]) {
				arr[k] = left[i];
				i++;
			} else {
				arr[k] = right[j];
				j++;
			}
			k++;
		}

		// Copy remaining elements
		// of left, if any
		while (i < len1) {
			arr[k] = left[i];
			k++;
			i++;
		}

		// Copy remaining element
		// of right, if any
		while (j < len2) {
			arr[k] = right[j];
			k++;
			j++;
		}
	}

	// Iterative Timsort function to sort the
	// array[0...n-1] (similar to merge sort)
	public void timSort(int[] arr, int n) {
		int minRun = minRunLength(MIN_MERGE);

		// Sort individual subarrays of size RUN
		for (int i = 0; i < n; i += minRun) {
			// math.min() returns the smallest of two numbers
			insertionSort(arr, i, Math.min((i + MIN_MERGE - 1), (n - 1)));
		}

		// merging sub arrays on the stack work best if they are powers of 2
		// Start merging from size
		// RUN (or 32). It will
		// merge to form size 64,
		// then 128, 256 and so on
		// ....
		for (int size = minRun; size < n; size = 2 * size) {

			// Pick starting point
			// of left sub array. We
			// are going to merge
			// arr[left..left+size-1]
			// and arr[left+size, left+2*size-1]
			// After every merge, we
			// increase left by 2*size
			for (int left = 0; left < n; left += 2 * size) {

				// Find ending point of left sub array
				// mid+1 is starting point of right sub
				// array
				int mid = left + size - 1;
				int right = Math.min((left + 2 * size - 1), (n - 1));

				// Merge sub array arr[left.....mid] &
				// arr[mid+1....right]
				if (mid < right)
					mergeTS(arr, left, mid, right);
			}
		}
	}

	/*
	 * ******************** End of Tim Sort functions	 * *******************************************
	 */

	/*
	 * *************** Bubble Sort **************************************************

	 * https://www.geeksforgeeks.org/bubble-sort. start at left element. compare if
	 * element to left is greater than element to right. if yes swap elements
	 * otherwise move up one element. inner loop is shorted by number of iterations
	 * already performed in the outer loop
	 */
	public void bubbleSort(int[] arr) {
		int n = arr.length;

		for (int i = 0; i < n - 1; i++) { // for each element in the array

			// as largest element bubbles to end of array inner loop gets shortened to
			// ignore already sorted larger elements
			for (int j = 0; j < n - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					// swap left and right elements
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}

	}
	/*
	 * *************************** End of Bubble Sort	 * *************************************
	 */

	/*
	 * ****************************** Insertion Sort * *****************************
	 */
	// https://www.geeksforgeeks.org/insertion-sort/
	// nested loop is used to sort the data
	public void insertionSortArr(int arr[]) {
		int n = arr.length;
		// start at second position in array
		for (int i = 1; i < n; ++i) {
			// key is the current value that is being sorted
			int key = arr[i];

			int j = i - 1;

			/*
			 * Move elements of arr[0..i-1], that are greater than key, to one position
			 * ahead of their current position
			 */
			while (j >= 0 && arr[j] > key) { // loop while not at start of array AND value before key is bigger
				// bump up prior value
				arr[j + 1] = arr[j];
				// decrement j to lower element so key can be check against the next lower value
				j = j - 1;
			}
			arr[j + 1] = key; // slot in the key at the calculated position

		}
	}

	/*
	 * *********************** Merge Sort ******************************************
	 * Code taken from https://www.geeksforgeeks.org/merge-sort/ The sort() function
	 * splits input array recursively into sub arrays until base case of 1 element
	 * sub array is reached, then merge() will merge elements in sorted order back
	 * to the array
	 */
	public void sort(int arr[], int l, int r) {
		if (l < r) { // while there are values
			// Find the middle point of incoming array
			int m = l + (r - l) / 2;

			sort(arr, l, m); // Sort first half
			sort(arr, m + 1, r);// sort second half

			// Merge the left and right arrays in sorted order
			merge(arr, l, m, r);
		}
	}

	// https://www.geeksforgeeks.org/merge-sort/
	// Merges two subarrays of arr[] in sorted order.
	// First subarray is arr[l..m]
	// Second subarray is arr[m+1..r]
	public void merge(int arr[], int l, int m, int r) {
		// Find sizes of two subarrays to be merged
		int n1 = m - l + 1; // (will hold the odd half if any)
		int n2 = r - m;

		// Create temp arrays represents left and right this algorithm uses auxiliary
		// storage
		int L[] = new int[n1];
		int R[] = new int[n2];

		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i)
			L[i] = arr[l + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[m + 1 + j];

		// Merge the temp arrays back to arr[] in sorted order
		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarry array
		int k = l;
		while (i < n1 && j < n2) {
			if (L[i] <= R[j]) { // check if value on right is larger
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */
		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}

		/* Copy remaining elements of R[] if any */
		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}
	/*
	 * ********************************* End of Merge Sort ************************
	 */

	/* ******************************Counting Sort ****************************** */
	// https://www.geeksforgeeks.org/counting-sort/
	// This code is contributed by princiRaj1992
	public void countingSort(int[] arr) {

		// get the largest and smallest number in arr
		int max = Arrays.stream(arr).max().getAsInt();
		int min = Arrays.stream(arr).min().getAsInt();

		// the range will be the size of the count array
		// while will contain a count for each value occurrence
		int range = max - min + 1;
		int count[] = new int[range];
		int output[] = new int[arr.length]; // output[] will be a new sorted array

		// store count of each value
		for (int i = 0; i < arr.length; i++) {
			count[arr[i] - min]++;
		}

		// shift index positions one place to the right
		for (int i = 1; i < count.length; i++) {
			count[i] += count[i - 1];
		}

		// working from right to left
		// place values in output array in the index specified by count array
		// update count value
		for (int i = arr.length - 1; i >= 0; i--) {
			output[count[arr[i] - min] - 1] = arr[i];
			count[arr[i] - min]--;
		}

		// copy new sorted array back to the original array
		for (int i = 0; i < arr.length; i++) {
			arr[i] = output[i];
		}
	}
	/*
	 * *********************** End of Counting Sort  * ********************************
	 */

}
