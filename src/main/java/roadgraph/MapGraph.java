/**
 * @author UCSD MOOC development team and YOU
 * <p>
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between
 */
package roadgraph;


import java.util.*;
import java.util.function.Consumer;

import geography.GeographicPoint;
import geography.RoadSegment;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 *
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {

    //COMPLETED: Add your member variables here in WEEK 2
    private Map<GeographicPoint, List<RoadSegment>> adjacencyList;
    private int numberOfEdges;

    /**
     * Create a new empty MapGraph
     */
    public MapGraph() {
        // COMPLETED: Implement in this constructor in WEEK 2
        adjacencyList = new HashMap<>();
    }

    /**
     * Get the number of vertices (road intersections) in the graph
     * @return The number of vertices in the graph.
     */
    public int getNumVertices() {
        //COMPLETED: Implement this method in WEEK 2
        return adjacencyList.keySet().size();
    }

    /**
     * Return the intersections, which are the vertices in this graph.
     * @return The vertices in this graph as GeographicPoints
     */
    public Set<GeographicPoint> getVertices() {
        //COMPLETED: Implement this method in WEEK 2
        return adjacencyList.keySet();
    }

    /**
     * Get the number of road segments in the graph
     * @return The number of edges in the graph.
     */
    public int getNumEdges() {
        //COMPLETED: Implement this method in WEEK 2
        return numberOfEdges;
    }


    /** Add a node corresponding to an intersection at a Geographic Point
     * If the location is already in the graph or null, this method does
     * not change the graph.
     * @param location  The location of the intersection
     * @return true if a node was added, false if it was not (the node
     * was already in the graph, or the parameter is null).
     */
    public boolean addVertex(GeographicPoint location) {
        // COMPLETED: Implement this method in WEEK 2
        if (location == null || adjacencyList.containsKey(location)) {
            return false;
        }

        adjacencyList.put(location, new ArrayList<>());

        return true;
    }

    /**
     * Adds a directed edge to the graph from pt1 to pt2.
     * Precondition: Both GeographicPoints have already been added to the graph
     * @param from The starting point of the edge
     * @param to The ending point of the edge
     * @param roadName The name of the road
     * @param roadType The type of the road
     * @param length The length of the road, in km
     * @throws IllegalArgumentException If the points have not already been
     *   added as nodes to the graph, if any of the arguments is null,
     *   or if the length is less than 0.
     */
    public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
                        String roadType, double length) throws IllegalArgumentException {
        //COMPLETED: Implement this method in WEEK 2
        if (!(adjacencyList.containsKey(from) && adjacencyList.containsKey(to))
                || roadName == null
                || roadType == null
                || length < 0) {
            throw new IllegalArgumentException();
        }

        RoadSegment roadSegment = new RoadSegment(from, to, new ArrayList<>(), roadName, roadType, length);
        adjacencyList.get(from).add(roadSegment);

        numberOfEdges++;
    }

    public void printGraph() {
        StringBuilder builder = new StringBuilder();
        for (GeographicPoint point : adjacencyList.keySet()) {
            builder.append(point).append(": ");
            for (RoadSegment segment : adjacencyList.get(point)) {
                builder.append('(')
                        .append(segment.getOtherPoint(point))
                        .append("), ");
            }
            builder.append('\n');
        }
        System.out.println(builder.toString());
    }

    /** Find the path from start to goal using breadth first search
     *
     * @param start The starting location
     * @param goal The goal location
     * @return The list of intersections that form the shortest (unweighted)
     *   path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return bfs(start, goal, temp);
    }

    /** Find the path from start to goal using breadth first search
     *
     * @param start The starting location
     * @param goal The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest (unweighted)
     *   path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start,
                                     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        // TODO: Implement this method in WEEK 2
        Queue<GeographicPoint> queue = new LinkedList<>();
        Set<GeographicPoint> visited = new HashSet<>();
        Map<GeographicPoint, GeographicPoint> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);

        return bfsSearch(start, goal, queue, visited, parent, nodeSearched);
    }

    private List<GeographicPoint> bfsSearch(GeographicPoint start, GeographicPoint goal, Queue<GeographicPoint> queue, Set<GeographicPoint> visited, Map<GeographicPoint, GeographicPoint> parent, Consumer<GeographicPoint> nodeSearched) {
        while (!queue.isEmpty()) {
            GeographicPoint pointBeingVisited = queue.remove();

            nodeSearched.accept(pointBeingVisited);

            if (pointBeingVisited.equals(goal)) {
                return getPathToGoal(start, goal, parent);
            }
            visitNeighbors(queue, visited, parent, pointBeingVisited);
        }

        return null;
    }

    private void visitNeighbors(Queue<GeographicPoint> queue, Set<GeographicPoint> visited, Map<GeographicPoint, GeographicPoint> parent, GeographicPoint pointBeingVisited) {
        for (RoadSegment segment : adjacencyList.get(pointBeingVisited)) {
            GeographicPoint neighbor = segment.getOtherPoint(pointBeingVisited);

            if (visited.contains(neighbor)) {
                continue;
            }

            visited.add(neighbor);
            parent.put(neighbor, pointBeingVisited);
            queue.add(neighbor);
        }
    }

    private List<GeographicPoint> getPathToGoal(GeographicPoint start, GeographicPoint goal, Map<GeographicPoint, GeographicPoint> parent) {
        List<GeographicPoint> path = new ArrayList<>();
        GeographicPoint currentPoint = goal;
        while (!currentPoint.equals(start)) {
            path.add(0, currentPoint);
            currentPoint = parent.get(currentPoint);
        }
        path.add(0, currentPoint);
        return path;
    }

    /** Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start The starting location
     * @param goal The goal location
     * @return The list of intersections that form the shortest path from
     *   start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        // You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return dijkstra(start, goal, temp);
    }

    /** Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start The starting location
     * @param goal The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest path from
     *   start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start,
                                          GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        // TODO: Implement this method in WEEK 3

        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());

        return null;
    }

    /** Find the path from start to goal using A-Star search
     *
     * @param start The starting location
     * @param goal The goal location
     * @return The list of intersections that form the shortest path from
     *   start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return aStarSearch(start, goal, temp);
    }

    /** Find the path from start to goal using A-Star search
     *
     * @param start The starting location
     * @param goal The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest path from
     *   start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start,
                                             GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        // TODO: Implement this method in WEEK 3

        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());

        return null;
    }


    public static void main(String[] args) {
        System.out.print("Making a new map...");
        MapGraph firstMap = new MapGraph();
        System.out.print("DONE. \nLoading the map...");
        GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
        System.out.println("DONE.");
        firstMap.printGraph();

        MapGraph simpleTestMap = new MapGraph();
        GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);

        GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
        GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

        List<GeographicPoint> path = simpleTestMap.bfs(testStart, testEnd);
        System.out.println(path);

        // You can use this method for testing.

		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		/*
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);

		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/

    }

}
