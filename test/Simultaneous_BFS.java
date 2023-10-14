import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Simultaneous_BFS {
    private static final Map<Character, char[]> adjList = new HashMap<>();

    public static void main(String[] args) throws IOException {
        initMapData();
        ArrayList<Character> commonPath = simultaneousBFS('A', 'H');
        printPath(commonPath);
    }

    private static void initMapData() {
        adjList.put('A', new char[]{'B'});
        adjList.put('B', new char[]{'A', 'C', 'D'});
        adjList.put('C', new char[]{'B'});
        adjList.put('D', new char[]{'B', 'E'});
        adjList.put('E', new char[]{'D', 'F', 'G'});
        adjList.put('F', new char[]{'E', 'H'});
        adjList.put('G', new char[]{'E', 'H'});
        adjList.put('H', new char[]{'F', 'G'});
    }

    private static void printPath(ArrayList<Character> path) {
        if (path == null) {
            System.out.println("No common node found.");
            return;
        }

        System.out.print("Common path: ");
        for (char node : path) System.out.printf("%c ", node);
        System.out.println();
    }

    private static ArrayList<Character> simultaneousBFS(char start, char end) {
        ArrayList<ArrayList<Character>> pathsStart = new ArrayList<>();
        ArrayList<ArrayList<Character>> pathsEnd = new ArrayList<>();

        ArrayList<Character> startingPathStart = new ArrayList<>();
        startingPathStart.add(start);
        pathsStart.add(startingPathStart);

        ArrayList<Character> startingPathEnd = new ArrayList<>();
        startingPathEnd.add(end);
        pathsEnd.add(startingPathEnd);

        while (true) {
            ArrayList<Character> commonNodePath = findCommonNode(pathsStart, pathsEnd);
            if (commonNodePath != null) {
                return commonNodePath;
            }

            pathsStart = extendPaths(pathsStart);
            pathsEnd = extendPaths(pathsEnd);
        }
    }

    private static ArrayList<Character> findCommonNode(ArrayList<ArrayList<Character>> paths1, ArrayList<ArrayList<Character>> paths2) {
        for (ArrayList<Character> path1 : paths1) {
            for (ArrayList<Character> path2 : paths2) {
                if (path1.get(path1.size() - 1) == path2.get(path2.size() - 1)) {
                    ArrayList<Character> commonPath = new ArrayList<>(path1);
                    commonPath.addAll(path2.subList(1, path2.size())); // Skip the first node of path2
                    return commonPath;
                }
            }
        }
        return null;
    }

    private static ArrayList<ArrayList<Character>> extendPaths(ArrayList<ArrayList<Character>> paths) {
        ArrayList<ArrayList<Character>> newPaths = new ArrayList<>();
        for (ArrayList<Character> path : paths) {
            char current = path.get(path.size() - 1);
            char[] connectedNodes = adjList.get(current);
            for (char c : connectedNodes) {
                ArrayList<Character> newPath = new ArrayList<>(path);
                newPath.add(c);
                newPaths.add(newPath);
            }
        }
        return newPaths;
    }
}
