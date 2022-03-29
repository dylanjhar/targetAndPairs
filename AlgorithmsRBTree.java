import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class AlgorithmsRBTree {
	
	/*
	 * General Info
	 * TreeMap is a tree structure based on the red-black tree that holds key-value pairs
	 * It has guaranteed O(logn) insertion, deletion, and search
	 * 
	 * Task 2
	 * Load list of numbers into an array and binary tree
	 * Binary tree holds the number (key) and frequency that the number appears (value)
	 * O(nlogn) because traversing through list of numbers O(n) and inserting them into binary tree O(logn)
	 *
	 * Traverse the array and find the difference between the target and the number in the array
	 * If this number exists in the tree and equals the number in the array
	 * Increase the number of pairs by the decreasing summation sequence of its frequency - 1
	 * Ex. pairs that equal 4 in sequence 2 2 2 2 is 3 + 2 + 1 = 6 or ((3 + 1) * 3) / 2 = 6
	 *
	 * If the number does not equal the number in the array
	 * Increase the number of pairs by the multiplication of the two numbers frequencies
	 * Ex. pairs that equal 5 in sequence 2 2 2 3 3 is 3 * 2 = 6
	 *
	 * After pairs has been increased or if the number does not exist in the tree
	 * Remove the numbers from the tree
	 * O(nlogn) because traversing through the array O(n) and search/deletion in binary tree O(logn)
	 */
	
	public static void numOfPairs() {
		TreeMap<Integer, Integer> tm = new TreeMap<>();
		
		try {
			File data = new File("data.txt");
			Scanner scanner = new Scanner(data);
			
			int length = scanner.nextInt();
			int target = scanner.nextInt();
			
			int array[] = new int[length];
			int i = 0;
			
			int num;
			
			//Load array and tree with numbers in the list
			while(scanner.hasNextInt()) {
				num = scanner.nextInt();
				array[i] = num;
				i++;
				//If number does not exist in tree, insert into tree with frequency 1
				if(tm.get(num) == null) {
					tm.put(num, 1);
				//If number exists in tree, increase its frequency by 1
				} else {
					tm.replace(num, tm.get(num) + 1);
				}
			}
		
			int match;
			int pairs = 0;
			
			for(int j = 0; j < length && !tm.isEmpty(); j++) {
				match = target - array[j];
				if(tm.get(match) != null) {
					if(match == array[j]) {
						//Summation sequence formula
						pairs += (tm.get(match) * (tm.get(match) - 1)) / 2;
						tm.remove(match);
					} else {
						pairs += tm.get(match) * tm.get(array[j]);
						tm.remove(match);
						tm.remove(array[j]);
					}
				} else {
					tm.remove(array[j]);
				}
			}
			
			System.out.println(pairs);
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	/*
	 * Task 3
	 * Load list of numbers into an array
	 * Traverse the array with two for loops to find the sum of a number in the array
	 * and all numbers in front of it (in pairs)
	 * If the sum does not exist in the tree, insert it into tree with frequency 1
	 * If the sum exists in the tree, increase its frequency by 1
	 * If the frequency of the sum in the tree is greater than current max frequency
	 * Set max frequency to the sums frequency and the target to the sum
	 * If the frequency of the sum in the tree is equal to the current max frequency
	 * Set the target to the smaller sum
	 * O(n^2logn) because traversing the array in two nested for loops O(n^2)
	 * And search/deletion in binary tree O(logn)
	 */
	
	public static void findTarget() {
		TreeMap<Integer, Integer> tm = new TreeMap<>();
		
		try {
			File data = new File("data.txt");
			Scanner scanner = new Scanner(data);
			
			int length = scanner.nextInt();
			
			int array[] = new int[length];
			int i = 0;
			
			//Load array with numbers in the list
			while(scanner.hasNextInt()) {
				array[i] = scanner.nextInt();
				i++;
			}
			
			int sum;
			int target = 0;
			int maxFreq = 0;
			
			for(int j = 0; j < array.length - 1; j++) {
				for(int k = j + 1; k < array.length; k++) {
					sum = array[j] + array[k];
					//If number does not exist in tree, insert into tree with frequency 1
					if(tm.get(sum) == null) {
						tm.put(sum, 1);
					//If number exists in tree, increase its frequency by 1
					} else {
						tm.replace(sum, tm.get(sum) + 1);
					}
					//Set max frequency to larger frequency
					if(tm.get(sum) > maxFreq) {
						maxFreq = tm.get(sum);
						target = sum;
					//Set target to smaller sum
					} else if(tm.get(sum) == maxFreq) {
						if(target > sum) {
							target = sum;
						}
					}
				}
			}
			
			System.out.println(target);
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args){
		//Task 2
    	numOfPairs();
		
		//Task 3
		//findTarget();
	}
}
