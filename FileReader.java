import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
	public static void main(String[] args) throws FileNotFoundException {
		
		long startTime = System.nanoTime(); //set a timer to check how long the program runs for
		File file = new File("C:\\Users\\shano\\Documents\\College Maynooth\\Second Year\\Semester 2\\CS211\\End of year project\\CS211_Project_Workspace\\CS211_Project\\Equipment.csv"); //take in the equipment csv file
		Scanner scan = new Scanner(file);
		String line = "";
		String [] temp = new String[2]; 
		int length = 1001;	//number of airports in the file, 
		
		Airport airport[] = new Airport[length]; //create array of airport objects to store each airport
		Graph graph = new Graph(length);		//create a new graph with 1001 nodes
		
		//this loop takes in the latitude and longitude on each line of the csv file and stores each pair in a separate airport object 
		for(int i = 0; i < length; i++) {
			line += scan.nextLine();
			temp = line.split(",");
			airport[i] = new Airport(i, Double.parseDouble(temp[0]), Double.parseDouble(temp[1]));
			line = "";
		}
		scan.close();
		
		fillGraph(graph, length, airport);  //this function fills the graph making each airport a node and each edge the distance between each airport
		
		String solution = tsp(graph, length); //calls the tsp function which calculates a solution string
		String ar[] = solution.split(",");	//put all the numbers in the solution into an array
		while(ar[1000].equalsIgnoreCase(ar[999]) || totalDistance(solution, length, airport) > 495000.0) { //ensure the 2nd and 3rd last numbers aren't the same and also that the solution string is less than 495000km traveled 
			fillGraph(graph, length, airport);	//fill graph again as the tsp function will set all edges to 0
			solution = tsp(graph, length);		//set solution equal to the new solution string
			ar = solution.split(",");
			System.out.println("Calculating...");
		}
		
		System.out.println("Solution String: " + solution);		//print solution string
		System.out.println("Distance Travelled: " + totalDistance(solution, length, airport) + "km");	//print time taken to travel the solution string
		long endTime = System.nanoTime();	//stop the timer on the program
		long totalTime = endTime - startTime;
		double seconds = (double)totalTime / 1_000_000_000.0;	//convert the program time into seconds
		System.out.println("Program took: " + seconds + " seconds");	
	}
	
	//this method takes in the graph object, airport object array and number of airports and fills the graph with nodes
	public static void fillGraph(Graph graph, int length, Airport[] airport) {
		double distance = 0;
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < i; j++) { //the inner loop only needs to go as far as i because one half of the matrix will be the same as the other
				distance = haversine(airport[i].getLat(), airport[i].getLon(), airport[j].getLat(), airport[j].getLon()); //checks the distance between all of the airports 
				if(distance > 100.0) {
					graph.addEdge(i, j, distance); //if a distance between an airport is greater than 100km put it into the graph otherwise set the distance to 0
				}	
			}
		}
	}
	
	//this method takes in the graph object and calculates a solution string
	public static String tsp(Graph graph, int length) {
		double checkDistance = 0.0;
		double shortDistance = Integer.MAX_VALUE;
		int shortNode = 0;
		int row = 0;
		String solution = "0,";	//start the solution at the first airport
		
		//the outer loop goes from 0 to 1001
		for(int x = 0; x < length; x++) {
			if(x == length - 1) {
				solution += "0";	//on the last iteration of the loop, set the end of the solution to be the first airport
				break;
			}
			
			//this loop checks the distance between 1 airport and all of the other ones
			for(int i = 0; i < length;i++) {
				checkDistance = graph.getDistance(row, i);	
				if(checkDistance != 0.0 && (checkDistance + (Math.random())*400) < shortDistance) { //if the distance isn't 0, and is shorter than the previous shortest distance(with randomness), set this to be the new shortest distance 
					shortDistance = checkDistance;	//set new shortest distance
					shortNode = i;	//store the airport which was the shortest distance away
				}
			}
			
			shortDistance = Integer.MAX_VALUE;	//reset shortest distance to a large number
			graph.visited(row);	//mark the last checked airport as visited
			row = shortNode;
			solution += Integer.toString(shortNode) + ",";	//add the airport with shortest distance to the solution string
		}
		return solution;
	}
	
	//checks the total distance traveled by the solution string
	public static double totalDistance(String solution, int length, Airport[] airport) { 
		String ar[] = solution.split(",");
		double total = 0.0; 
		double lat1 = 0.0;
		double lon1 = 0.0;
		double lat2 = 0.0;
		double lon2 = 0.0;
		for(int i = 0; i < length; i++) {
			lat1 = airport[Integer.parseInt(ar[i])].getLat();
			lon1 = airport[Integer.parseInt(ar[i])].getLon();
			lat2 = airport[Integer.parseInt(ar[i+1])].getLat();
			lon2 = airport[Integer.parseInt(ar[i+1])].getLon();
			total += haversine(lat1, lon1, lat2, lon2);
		}
		return total;
	}
	
	//gets the distance between 2 airports given their latitude and longitude
	public static double haversine (double lat1, double lon1, double lat2, double lon2) {
		double radius = 6371.0;
		double rLatitude = (lat2 - lat1)*(Math.PI/180);
		double rLongitude = (lon1 - lon2)*(Math.PI/180);
		
		lat1 = lat1*(Math.PI/180);
		lat2 = lat2*(Math.PI/180);
		
		double a = (Math.sin(rLatitude/2) * Math.sin(rLatitude/2)) + 
                (Math.sin(rLongitude/2) * Math.sin(rLongitude/2) * Math.cos(lat1) * Math.cos(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return c*radius;
	}

}
