package heuristicSearch;

import heuristicSearch.mapData.MapData;

import java.util.*;

public class Dijkstra {
    private static Map<String, ArrayList<MapData.Destination>> adjList;

    public Dijkstra(Map<String, ArrayList<MapData.Destination>> adjList) {
        this.adjList = adjList;
    }

    public static void main(String[] args) {
        try {
            MapData mapData = new MapData();
            Map<String, ArrayList<MapData.Destination>> graphData = mapData.getGraphData();

            // Run Dijkstra's algorithm
            Dijkstra dijkstra = new Dijkstra(graphData);
            String startNode = "Baden/Gartenstrasse/1590"; // Replace this with the desired start node
            Map<String, Double> shortestPaths = dijkstra.findShortestPaths(startNode);

            // Print shortest paths
            System.out.println("Shortest paths from " + startNode + ":");
            for (Map.Entry<String, Double> entry : shortestPaths.entrySet()) {
                System.out.println("To: " + entry.getKey() + ", Distance: " + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Double> findShortestPaths(String startNode) {
        Map<String, Double> distances = new HashMap<>();
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(NodeDistance::getDistance));
        Set<String> visited = new HashSet<>();

        // Initialize distances
        distances.put(startNode, 0.0);
        priorityQueue.add(new NodeDistance(startNode, 0.0));

        while (!priorityQueue.isEmpty()) {
            NodeDistance currentNode = priorityQueue.poll();
            String current = currentNode.getNode();

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            // Update distances to neighboring nodes
            if (adjList.containsKey(current)) {
                for (MapData.Destination neighbor : adjList.get(current)) {
                    String neighborNode = neighbor.node();
                    double weight = neighbor.distance();
                    double distanceThroughCurrent = distances.get(current) + weight;

                    if (!distances.containsKey(neighborNode) || distanceThroughCurrent < distances.get(neighborNode)) {
                        distances.put(neighborNode, distanceThroughCurrent);
                        priorityQueue.add(new NodeDistance(neighborNode, distanceThroughCurrent));
                    }
                }
            }
        }

        return distances;
    }

    // Helper class for Dijkstra's algorithm
    private static class NodeDistance {
        private final String node;
        private final double distance;

        public NodeDistance(String node, double distance) {
            this.node = node;
            this.distance = distance;
        }

        public String getNode() {
            return node;
        }

        public double getDistance() {
            return distance;
        }
    }
}
