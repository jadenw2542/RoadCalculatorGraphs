/**
 * Jaden Wong
 * SBU ID: 113469617
 * jaden.wong@stonybrook.edu
 * CSE 214.R02 Data Structures - Fall 2021
 */
import java.io.*;
import java.util.*;
import java.util.Comparator;

public class Edge implements Comparable {
    private Node A;
    private Node B;
    private int cost;

    /**
     * Constructor Edge Object
     */
    public Edge(Node A, Node B, int cost){
        this.A = A;
        this.B = B;
        this.cost = cost;

    }

    public String toString(){
        String out = A.toString() + " to " + B.toString() + " " + cost;
        return out;

    }

    /**
     * Compares the current edge’s cost to otherEdge’s cost. Returns -1 if the current edge’s cost is less than otherEdge’s cost, 0 if equal, and 1 if greater than.
     * @param otherEdge Edge to compare to
     * @return Returns -1 if the current edge’s cost is less than otherEdge’s cost, 0 if equal, and 1 if greater than.
     */
    public int compareTo(Object otherEdge) {
        Edge temp = null;
        if (otherEdge instanceof Edge) {
            temp = (Edge) otherEdge;
        }
        if(this.cost < temp.getCost()){
            return -1;
        }
        else if(this.cost > temp.getCost()){
            return 1;
        }
        else{
            return 0;
        }
    }

    public Node getA() {
        return A;
    }

    public void setA(Node a) {
        A = a;
    }

    public Node getB() {
        return B;
    }

    public void setB(Node b) {
        B = b;
    }

    public int getCost() {
        return cost;
    }
}
