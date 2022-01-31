/**
 * Jaden Wong
 * SBU ID: 113469617
 * jaden.wong@stonybrook.edu
 * CSE 214.R02 Data Structures - Fall 2021
 */
import java.io.*;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.*;

import big.data.DataSource;
import sun.plugin.javascript.navig.Link;

public class RoadCalculator {
    private  static HashMap<String, Node> graph;
    private static LinkedList<Edge> mst;
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter graph URL");
        String url = in.nextLine();
        System.out.println("Loading Map...");
        graph = buildGraph(url);
        //graph = buildGraph("https://www3.cs.stonybrook.edu/~cse214/hw/hw7-images/hw7.xml");
        mst = new LinkedList<>();
        mst = buildMST(graph);
        for(int i = 0; i < mst.size(); i = i + 2){
            System.out.println(mst.get(i));
        }

        Boolean quit = false;
        while(!(quit)) {
            System.out.println("Enter a starting point for shortest path or Q to quit:");
            String choice = in.nextLine();
            if(choice.equalsIgnoreCase("Q")){
                quit = true;
            }
            else{
                System.out.println("Enter a destination:");
                String destination = in.nextLine();
                System.out.println("Distance: "+ Djikstra(graph,choice,destination));
            }
        }

    }

    /**
     * Builds hashmap given xml
     * @param location map to be built
     * @return Hash Map with key of String and value of Nodes
     */
    public static HashMap<String, Node> buildGraph(String location){
        HashMap<String, Node> graphBuild = new HashMap<String, Node>();
        DataSource ds = DataSource.connectXML(location);
        ds.load();
        String cityNamesStr=ds.fetchString("cities");
        String[] cityNames=cityNamesStr.substring(1,cityNamesStr.length()-1).replace("\"","").split(",");
        String roadNamesStr=ds.fetchString("roads");
        String[] roadNames=roadNamesStr.substring(2,roadNamesStr.length()-2).split("\",\"");

        System.out.println("Cities: ");
        for(String i : cityNames){
            graphBuild.put(i, new Node(i));
            System.out.println(i);
        }

        System.out.println("Roads: ");
        for(String i : roadNames){
            String nodeA = i.substring(0, i.indexOf(","));
            String nodeB = i.substring(i.indexOf(",") + 1, i.lastIndexOf(","));
            int distance = Integer.parseInt(i.substring(i.lastIndexOf(",") + 1));
            System.out.println(nodeA + " to " + nodeB + " " + distance);

            graphBuild.get(nodeA).getEdge().add(new Edge(graphBuild.get(nodeA),graphBuild.get(nodeB),distance));
            graphBuild.get(nodeB).getEdge().add(new Edge(graphBuild.get(nodeB),graphBuild.get(nodeA),distance));
        }

        return graphBuild;
    }

    /**
     * Finds minimum spanning tree
     * @param graph graph of nodes
     * @return  linked list of edges wehre the total distance of all edges is minimized
     */
    public static LinkedList<Edge> buildMST(HashMap<String, Node> graph) {
        System.out.println("MST: ");
        LinkedList<Node> nodes = new LinkedList<Node>();
        LinkedList<Edge> edges = new LinkedList<Edge>();
        Collection temp = graph.values();
        if (temp.isEmpty()) {
            return null;
        }
        nodes.add((Node)temp.toArray()[0]);


        LinkedList<Edge> edgeList0 = new LinkedList<Edge>();
        for (Edge a : nodes.get(0).getEdge()) {
            edgeList0.add(a);
        }
        Collections.sort(edgeList0);
        int minCost = edgeList0.get(0).getCost();
        Edge addEdge = edgeList0.get(0);

            while(edges.size() < graph.size() *2) {
                    for (int i = 0; i < nodes.size(); i++) {
                        for(Edge a : nodes.get(i).getEdge()){
                        if ((a.getCost() < minCost) && (!(edges.contains(a)))) {
                            minCost = a.getCost();
                            addEdge = a;

                        }
                    }
                }


                    if(!(nodes.contains(addEdge.getB()))) {
                        nodes.add(addEdge.getB());
                    }
                edges.add(addEdge);
                for(Edge i: addEdge.getB().getEdge()){
                    if(i.getA().equals(addEdge.getB()) && (i.getB().equals(addEdge.getA())) && !(edges.contains(i))){
                        edges.add(i);
                    }
                }

                minCost = 99999;
            }

        return edges;
    }

    /**
     * Finds shortest path from source to dest using Dijkstra's shortest path algorithm
     * @param graph graph of nodes
     * @param source source node
     * @param dest destination node
     * @return shortest distance from source to destination
     */
    public static int Djikstra(HashMap<String, Node> graph, String source, String dest) {

        Boolean sourceExist = false;
        Boolean destExist = false;

        Iterator entries = graph.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Node value = (Node) entry.getValue();
            if (key.equalsIgnoreCase(source)) {
               sourceExist = true;
            }
            if(key.equalsIgnoreCase(dest)){
                destExist = true;
            }
        }
        if(!(sourceExist) || !(destExist)){
            System.out.println("Invalid input");
            return  0;
        }


        Node sourceVertex = null;
        LinkedList<Node> visitedList = new LinkedList<>();
        LinkedList<String> parent = new LinkedList<>();

        entries = graph.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Node value = (Node) entry.getValue();
            if (key.equalsIgnoreCase(source)) {
                graph.get(key).setDistance(0);
                graph.get(key).setVisited(true);
                visitedList.add(graph.get(key));
                sourceVertex = graph.get(key);
            } else {
                graph.get(key).setDistance(99999);
                graph.get(key).setVisited(false);
            }
        }




        for(Edge i : sourceVertex.getEdge()){
            i.getB().setDistance(i.getCost());
            i.getB().getPath().add(i.getA().getName());
        }

        while (visitedList.size() != graph.size() - 1) {

            int minDistance = 9999;
            Node minKey = null;

            for(Node i : visitedList){
                for(Edge a : i.getEdge()    ) {
                    if (a.getCost() < minDistance && !(visitedList.contains(a.getB()))) {
                        minDistance = a.getCost();
                        minKey = a.getB();
                    }
                }
            }
            minKey.setVisited(true);
            visitedList.add(minKey);

            for(Edge i : minKey.getEdge()){
                if(!(i.getB().getVisited()) && (i.getA().getDistance() + i.getCost() < i.getB().getDistance()) ) {
                    i.getB().setDistance(i.getCost() + i.getA().getDistance());
                    for(String s: i.getA().getPath()){
                        i.getB().getPath().add(s);
                    }
                    i.getB().getPath().add(i.getA().getName());
                }
            }
        }

        entries = graph.entrySet().iterator();
        int distance = 0;
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Node value = (Node) entry.getValue();
            if (key.equalsIgnoreCase(dest)) {
                System.out.println("Path: ");
                for(String i : graph.get(key).getPath()){
                    System.out.print(i + ", " );
                }
                System.out.println();
                distance = value.getDistance();
            }
        }
        entries = graph.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Node value = (Node) entry.getValue();
            value.setVisited(false);
            value.setPath(new LinkedList<>());
        }
        return distance;
    }
}
