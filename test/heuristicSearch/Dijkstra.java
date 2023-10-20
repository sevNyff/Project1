package heuristicSearch;

import heuristicSearch.mapData.MapData;

import java.util.*;

public class Dijkstra {
    private static Map<String, ArrayList<MapData.Destination>> adjList;

    private record Path(ArrayList<String> shortestPath, double totalDistance) {};

    private record NodeDistance(String node, double distance, String previousNode) {};

    public static void main(String[] args) {
        double timeBefore = System.nanoTime();
        try {
            MapData mapData = new MapData();

            adjList = mapData.getAdjacencyList();
            String[] userInput = AStar.userInput();
            printPath(findShortestPath(userInput[0], userInput[1]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        double timeAfter = System.nanoTime();
        double totalRuntime = timeAfter - timeBefore;
        System.out.print("Runtime in nanoseconds: " + totalRuntime);
    }

    public static Path findShortestPath(String startNode, String endNode) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(NodeDistance::distance));
        Set<String> visited = new HashSet<>();

        // Initialize distances
        distances.put(startNode, 0.0);
        priorityQueue.add(new NodeDistance(startNode, 0.0, null));

        while (!priorityQueue.isEmpty()) {
            NodeDistance currentNode = priorityQueue.poll();
            String current = currentNode.node;

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            // If the current node is the end node, stop the algorithm
            if (current.equals(endNode)) {
                break;
            }

            // Update distances to neighboring nodes
            if (adjList.containsKey(current)) {
                for (MapData.Destination neighbor : adjList.get(current)) {
                    String neighborNode = neighbor.node();
                    double weight = neighbor.distance();
                    double distanceThroughCurrent = distances.get(current) + weight;

                    if (!distances.containsKey(neighborNode) || distanceThroughCurrent < distances.get(neighborNode)) {
                        distances.put(neighborNode, distanceThroughCurrent);
                        previousNodes.put(neighborNode, current); // Keep track of previous node for path reconstruction
                        priorityQueue.add(new NodeDistance(neighborNode, distanceThroughCurrent, current));
                    }
                }
            }
        }

        // Reconstruct the shortest path from startNode to endNode
        List<String> shortestPath = new ArrayList<>();
        String currentNode = endNode;
        while (currentNode != null) {
            shortestPath.add(currentNode);
            currentNode = previousNodes.get(currentNode);
        }
        Collections.reverse(shortestPath);

        // Calculate total distance
        double totalDistance = distances.get(endNode);

        return new Path((ArrayList<String>) shortestPath, totalDistance);
    }

    private static void printPath(Path path) {
        List<String> nodes = path.shortestPath;
        double totalDistance = path.totalDistance;

        System.out.print("Shortest Path: ");
        for (String node : nodes) {
            System.out.printf("%s ", node);
        }
        System.out.println();

        System.out.println("Total Distance Traveled: " + totalDistance);
    }
}
