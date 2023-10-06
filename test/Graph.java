import java.util.*;

class Graph {
    private Map<String, List<Node>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(String source, String destination, double weight) {
        adjacencyList.computeIfAbsent(source, k -> new ArrayList<>()).add(new Node(destination, weight));
        adjacencyList.computeIfAbsent(destination, k -> new ArrayList<>()).add(new Node(source, weight));
    }

    public Map<String, Double> dijkstra(String start) {
        Map<String, Double> distances = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.distance));

        distances.put(start, 0.0);
        queue.offer(new Node(start, 0.0));

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            String currentId = currentNode.id;

            if (!adjacencyList.containsKey(currentId)) continue;

            for (Node neighbor : adjacencyList.get(currentId)) {
                double newDistance = distances.get(currentId) + neighbor.distance;
                if (newDistance < distances.getOrDefault(neighbor.id, Double.MAX_VALUE)) {
                    distances.put(neighbor.id, newDistance);
                    queue.offer(new Node(neighbor.id, newDistance));
                }
            }
        }

        return distances;
    }
}
