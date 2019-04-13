package assignment07;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PathFinder {

	
	
	
	/**
	 * 
	 * @param inputFile - String created from file contents 
	 * @param outputFile - String name of file to be saved with contents
	 * @throws IOException
	 */
	public static void solveMaze(String inputFile, String outputFile) throws IOException {
		
	
		
		//System.out.println(inputFile);
		Scanner sc = new Scanner(new File(inputFile));
		String[] dimensions = sc.nextLine().split(" ");
		int height = Integer.parseInt(dimensions[0]);
		int width = Integer.parseInt(dimensions[1]);
		System.out.println(height + " " + width);
		
	
		Graph graph = new Graph(height, width);
		graph.createMaze(sc, inputFile);	
		graph.findShortestPath();
		graph.createMazePath(outputFile);
		String mazePath = graph.printMaze();
		System.out.println(mazePath);
		

	}

	
	
	
	
}// ending class bracket



