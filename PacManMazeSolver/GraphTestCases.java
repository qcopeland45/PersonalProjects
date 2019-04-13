package assignment07;



import java.io.IOException;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphTestCases {
	
	
	String str = "";

	@Before
	public void setUp() throws Exception {
		
//		Scanner sc = new Scanner(new File("bigMaze.txt"));
//		//System.out.println("printing inputfile line by line");
//		while(sc.hasNext()) {
//			str += sc.nextLine() + "\n";
//			//System.out.println(str);
//		}
//		//System.out.println("inputfile complete");
//		sc.close();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException {
		
		
		PathFinder.solveMaze("bigMaze.txt", "BGSolvedMaze.txt");
		
		
	}

}
