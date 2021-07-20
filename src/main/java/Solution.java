import java.util.*;

public class Solution {
    /*
     * the below graph represents the cities given in the problem v1 is the source airport and v2 is the destination dist is the distance in hours between them
     * */
    private static final Graph.Edge[] GRAPH = {


            new Graph.Edge("dub","lhr", 1),
            new Graph.Edge("dub","cdg", 2),
            new Graph.Edge("cdg","bos", 6),
            new Graph.Edge( "cdg","bkk", 9),
            new Graph.Edge(     "ord","las", 2),
            new Graph.Edge( "lhr","nyc", 5),
            new Graph.Edge(     "nyc","las", 3),
            new Graph.Edge(     "bos","lax", 4),
            new Graph.Edge(    "lhr","bkk", 9),
            new Graph.Edge(    "bkk","syd", 11),
            new Graph.Edge(    "lax","las", 2),
            new Graph.Edge(    "dub","ord", 6),
            new Graph.Edge(   "lax","syd", 13),
            new Graph.Edge(   "las","syd", 14)
    };


    /** main function Will run the code with "GRAPH" that was defined above. */
    public static void main(String[] args) {
        Graph g = new Graph(GRAPH);
        System.out.println("enter the source and destination in SSS - DDD format");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        System.out.println(input);
        String [] sourceAndDestination =  input.split("-");
        if (sourceAndDestination.length == 2) {
            g.dijkstra(sourceAndDestination[0].trim().toLowerCase());
            g.printPath(sourceAndDestination[1].trim().toLowerCase());
        }
        else{
            System.err.println("enter two 3 letter airports seperated by \'-\' ,\n eg : dub - ord");
        }

    }
}

class Graph {
    // mapping of vertex names to Vertex objects, built from a set of Edges

    private final Map<String, Vertex> graph;

    /** One edge of the graph (only used by Graph constructor) */
    public static class Edge {
        public final String v1, v2;
        public final int dist;

        public Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    /** One vertex of the graph, complete with mappings to neighbouring vertices */
    public static class Vertex implements Comparable<Vertex> {
        public final String name;
        // MAX_VALUE assumed to be infinity
        public int dist = Integer.MAX_VALUE;
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbours = new HashMap<>();

        public Vertex(String name) {
            this.name = name;
        }

        private void printPath() {

            String destination;
            if (this == this.previous) {

            } else if (this.previous == null) {
                System.out.printf("destination cannot be reached from %s", this.name);
                System.out.println();
            }
//            else if(this.name == destination ) {
//
//            }
            else {
                this.previous.printPath();
                System.out.printf("%s -- %s(%d)",this.previous.name ,this.name, this.dist - previous.dist);
                System.out.println();

            }

        }




        public int compareTo(Vertex other) {
            if (dist == other.dist) return name.compareTo(other.name);

            return Integer.compare(dist, other.dist);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            if (!super.equals(object)) return false;

            Vertex vertex = (Vertex) object;
            //check all the attributes if even one condition fails , they are not equal
            if (dist != vertex.dist) return false;
            if (name != null ? !name.equals(vertex.name) : vertex.name != null) return false;
            if (previous != null ? !previous.equals(vertex.previous) : vertex.previous != null)
                return false;
            if (neighbours != null ? !neighbours.equals(vertex.neighbours) : vertex.neighbours != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + dist;
            result = 31 * result + (previous != null ? previous.hashCode() : 0);
            result = 31 * result + (neighbours != null ? neighbours.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + name + ", " + dist + ")";
        }
    }

    /** Builds a graph from a array of edges */
    public Graph(Edge[] edges) {
        graph = new HashMap<>(edges.length);

        // one pass to find all vertices
        for (Edge e : edges) {
            if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
            if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
        }

        // another pass to set neighbouring vertices
        for (Edge e : edges) {
            graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
            // graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected
            // graph
        }
    }

    /** Runs dijkstra using a specified source vertex */
    public void dijkstra(String startName) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startName);
            return;
        }
        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();

        // set-up vertices
        for (Vertex v : graph.values()) {
            v.previous = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }

        dijkstra(q);
    }

    /** Implementation of dijkstra's algorithm using a binary heap. */
    private void dijkstra(final NavigableSet<Vertex> q) {
        Vertex u, v;
        while (!q.isEmpty()) {
            // vertex with shortest distance (first iteration will return source)
            u = q.pollFirst();
            if (u.dist == Integer.MAX_VALUE)
                break; // we can ignore u (and any other remaining vertices) since they are unreachable

            // look at distances to each neighbour
            for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
                v = a.getKey(); // the neighbour in this iteration

                final int alternateDist = u.dist + a.getValue();
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    q.remove(v);
                    v.dist = alternateDist;
                    v.previous = u;
                    q.add(v);
                }
            }
        }
    }

    /** Prints a path from the source to the specified vertex */
    public void printPath(String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endName);
            return;
        }

        graph.get(endName).printPath();
        if (+graph.get(endName).dist != Integer.MAX_VALUE)
            System.out.println("Time :"+graph.get(endName).dist);
    }




}