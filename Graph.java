public class Graph {
	
	private int numOfNodes;		//number of nodes in the graph
	private double[][] matrix;	//adjacency matrix that represents the graph
	
	public Graph(int numOfNodes) {
		this.numOfNodes = numOfNodes;
		matrix = new double[numOfNodes][numOfNodes];
	}
	
	//adds the distance between 2 airports as the weight of the edge
	public void addEdge(int source, int destination, double weight) {
		matrix[source][destination] = weight;
		matrix[destination][source] = weight;
	}
	
	public void editEdge(int source, int destination, double weight) {
		matrix[source][destination] = weight;
		matrix[destination][source] = weight;
	}
	
	//returns the distance between 2 nodes(airports) on the graph
	public double getDistance(int source, int destination) {
		return matrix[source][destination];
	}
	
	//marks airport as visited by setting the distance between it and all other airports to be 0
	public void visited(int airport) {
		for(int i = 0; i < numOfNodes; i++) {
			matrix[airport][i] = 0.0;
			matrix[i][airport] = 0.0;
		}
	}
	
	//prints the graph
	public void printGraph() {
		for(int i = 0; i < numOfNodes;i++) {
			for(int j = 0; j < numOfNodes; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
