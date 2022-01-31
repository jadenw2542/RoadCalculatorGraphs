/**
 * Jaden Wong
 * SBU ID: 113469617
 * jaden.wong@stonybrook.edu
 * CSE 214.R02 Data Structures - Fall 2021
 */
import java.util.HashSet;
import java.util.LinkedList;

public class Node {
    private String name;
    private HashSet<Edge> edge;
    private Boolean visited;
    private LinkedList<String> path;
    private int distance;
    /**
     * Constructor for Node object
     */
    public Node(String name){
        this.name = name;
        edge = new HashSet<Edge>();
        visited = false;
        path = new LinkedList<>();
        distance = 0;
    }

    public String getName() {
        return name;
    }

    public HashSet<Edge> getEdge() {
        return edge;
    }

    public Boolean getVisited() {
        return visited;
    }

    public LinkedList<String> getPath() {
        return path;
    }

    public int getDistance() {
        return distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEdge(HashSet<Edge> edge) {
        this.edge = edge;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public void setPath(LinkedList<String> path) {
        this.path = path;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    public String toString(){
        String out = name;
        return out;
    }

}
