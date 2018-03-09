/*******************************************************************************
  * File: Graph.java
  * Author: Andre Berger  
  *
  * Class to represent graphs using adjacency matrix and adjacency lists
  ******************************************************************************/

import java.util.*;
import java.io.*;

public class Graph{
  
  private int numberOfNodes;         
  private int numberOfEdges;
  private boolean[][] adjacent;
  private ArrayList<ArrayList<Integer>> adjacencyLists;
  
  // Constructor for a graph, information is read from file
  // adjacency matrix as well as adjacency list is filled
  
  public Graph(String filename) 
    throws java.io.FileNotFoundException{
    File file = new File(filename);
    Scanner input = new Scanner (file);
    
    this.numberOfNodes = input.nextInt();
    this.numberOfEdges = input.nextInt();
    
    this.adjacent = new boolean[this.numberOfNodes][this.numberOfNodes];
    
    this.adjacencyLists = new ArrayList<ArrayList<Integer>>();
    for (int i=0;i<this.numberOfNodes;i++){
      this.adjacencyLists.add(new ArrayList<Integer>());
    }
    
    for (int i=0;i<this.numberOfEdges;i++){
      int u = input.nextInt();
      int v = input.nextInt();
      
      this.adjacent[u][v] = true;
      this.adjacent[v][u] = true;
      
      this.adjacencyLists.get(u).add(v);
      this.adjacencyLists.get(v).add(u);
    }   
    input.close();
    
  }
  
  // returns the number of nodes of this graph
  public int getNumberOfNodes(){
    return this.numberOfNodes; 
  }
  
  // returns the number of edges of this graph
  public int getNumberOfEdges(){
    return this.numberOfEdges; 
  }
  
  // returns whether node i and node j are adjacent in this graph
  public boolean isAdjacent(int i, int j){
    return this.adjacent[i][j]; 
  }
  
  public int degree(int i){
    
    return this.adjacencyLists.get(i).size();
  }
  
  // returns true if and only if this graph is connected (using a stack)
  public boolean isConnectedStack(){
    boolean[] reached = new boolean[this.numberOfNodes];
    for( int i = 0; i < this.numberOfNodes; i++){
      reached[i] = false;
    }
    IntegerStack stack = new IntegerStack();
    stack.push(0);
    int numberReached = 0;
    while((stack.isEmpty()== false)){
      int top = stack.pop();
      if(reached[top] == false){
        reached[top] = true;
        numberReached = numberReached + 1;
        for( int i = 0; i < this.degree(top); i++){
          int neighbour = this.adjacencyLists.get(top).get(i);
          if( reached[neighbour] == false){
            stack.push(neighbour);
          }
        }
      }
    }
    return (numberReached == this.numberOfNodes); 
  }
  
  // returns true if and only if this graph is connected (using a queue)
  public boolean isConnectedQueue(){
    boolean[] reached = new boolean[this.numberOfNodes];
    for( int i = 0; i < this.numberOfNodes; i++){
      reached[i] = false;
    }
    Queue<Integer> queue = new Queue<Integer>();
    queue.enqueue(0);
    int numberReached = 0;
    while((queue.isEmpty()== false)){
      int top = queue.dequeue();
      if(reached[top] == false){
        reached[top] = true;
        numberReached = numberReached + 1;
        for( int i = 0; i < this.degree(top); i++){
          int neighbour = this.adjacencyLists.get(top).get(i);
          if( reached[neighbour] == false){
            queue.enqueue(neighbour);
          }
        }
      }
    }
    return (numberReached == this.numberOfNodes); 
  }
  
  
  public static void main(String[] args){
    try{
      
      Graph graph = new Graph(args[0]); 
      
      for (int i=0; i < graph.getNumberOfNodes(); i++){
        for (int j=0; j < graph.getNumberOfNodes(); j++){
          System.out.print(graph.isAdjacent(i,j) + " ");
        }
        System.out.println();
      }
      
      
      for (int i=0; i < graph.getNumberOfNodes(); i++){
        System.out.println("degree of node " + i + ": " + graph.degree(i));
      }
      
      System.out.println("Check graph is connected using Stack: " + graph.isConnectedStack());
      System.out.println("Check graph is connected using Queue: " + graph.isConnectedQueue());
      
      
    } // end of main method
    catch (FileNotFoundException e){
      e.printStackTrace();
    }
  }
  
}