package assignment07;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Graph {

	
	private Node[][] nodes;
	private Node _start;
	private Node _goal;
	private int _width;
	private int _height;
	private LinkedList<Node> _queue = new LinkedList<Node>();
	
	
	//Graph constructor
	public Graph(int height, int width) {
		this._height = height;
		this._width = width;
		nodes = new Node[height][width];
	}

	
	//Node inner class 
	private class Node {
		char _data;
		int _row;
		int _col;
		Node _cameFrom;
		boolean _visited;
			
		//Node constructor
		public Node(char data, int row, int col) {
			this._data = data;
			this._row = row;
			this._col = col;
			_visited = false;
			_cameFrom = null;
		}
	}
	
	
	//Read graph
	/**
	 * Creating the maze
	 * @param sc - scanner object 
	 * @param inputFile - file name to be read 
	 */
	public void createMaze(Scanner sc, String inputFile) {
		
		//creating maze
		for(int i = 0; i < _height; i ++) {
			String nextElement = sc.nextLine();
			for(int j = 0; j < _width; j++) {
				char element = nextElement.charAt(j);
				if(element == 'S') {
					_start = new Node(element, i, j);
					nodes[i][j] = _start;
				} 
				else if(element == 'G') {
					_goal = new Node(element, i, j);
					nodes[i][j] = _goal;
				}
				else {
					nodes[i][j] = new Node(element, i, j);
				}
			}
		}
	}
	
	/**
	 * Helper to visualize the maze
	 */
	public String printMaze() {
		String mazeStr = "";
		for(int i = 0; i < _height; i ++) {
			for(int j = 0; j < _width; j++) {
				mazeStr += nodes[i][j]._data;
				//System.out.print(nodes[i][j]._data);
			}
			mazeStr+= "\n";
			//System.out.print("\n");
		}
		return mazeStr;
	}
	
	//Finds valid neighbors
	/**
	 * 
	 * @param curNode
	 * @return
	 */
	public ArrayList<Node> findNeighbors(Node curNode) {
		
		ArrayList<Node> neighbors = new ArrayList<Node>();
			if(nodes[curNode._row + 1][curNode._col]._data != 'X') {
				neighbors.add(nodes[curNode._row + 1][curNode._col]);
			}
			if(nodes[curNode._row - 1][curNode._col]._data != 'X') {
				neighbors.add(nodes[curNode._row - 1][curNode._col]);
			}
			if(nodes[curNode._row][curNode._col - 1]._data != 'X') {
				neighbors.add(nodes[curNode._row][curNode._col - 1]);
			}
			if(nodes[curNode._row][curNode._col + 1]._data != 'X') {
				neighbors.add(nodes[curNode._row][curNode._col + 1]);
			}
	
		return neighbors;
		
	}
	
	
	
	/**
	 *  Finds Shortest path (void function)
	 *  
	 */
	public void findShortestPath() {
	
		//still want to output the maze just with no path
		//and no 'S' and 'G' characters
		if(_start == null) {//|| _goal == null) {
			throw new NoSuchElementException("Maze not valid: Must contain Start and Goal");
		}
		_queue.add(_start);
		_start._visited = true;
		Node curNode = _queue.peekFirst();
		while(curNode._data != 'G') {
			
			//if no goal this keeps from infinitely looping 
			if(_queue.isEmpty()) {
				return;
			}
			
			//System.out.println("current node data" + curNode._data);
			curNode = _queue.remove();
			//curNode._visited = true;
			ArrayList<Node> neighbors = findNeighbors(curNode);
			for(Node n : neighbors) {
				if(!n._visited) {
					n._visited = true;
					n._cameFrom = curNode;
					_queue.add(n);
				}
				if(n == _goal) {
					recursiveWritePath(_goal);
					return;
				}			
			}
		}		
	}
	
	
	//Print to file path
	/**
	 * 
	 * @param outputFile - string name of file to with solved mazed
	 * @throws IOException 
	 */
	public void createMazePath(String outputFile) throws IOException {

		try (PrintWriter pr = new PrintWriter(new FileWriter(outputFile))) {
			pr.print(_height + " " + _width + "\n");
			pr.print(printMaze());
		}

	}
	
	/**
	 * @param curNode - node 
	 */
	public void recursiveWritePath(Node curNode) {
		//base case 
		if(curNode._data == 'S') {
			return;
		} 
		else if(curNode._data == 'G') {
			recursiveWritePath(curNode._cameFrom);
		}
		else {
			curNode._data = '.';
			recursiveWritePath(curNode._cameFrom);
		}
	}
		
	
}// ending class bracket
