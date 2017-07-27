import java.util.ArrayList;
import java.util.Scanner;

public class DominatorChain {

    public static void main(String[] args) {
        
        System.out.println("Enter the number of vertices: ");
        Scanner sc = new Scanner(System.in);
        int v = sc.nextInt();
        System.out.println("Enter the last node: ");
        int r = sc.nextInt();
        Graph g = new Graph(v,r);
        System.out.println("Enter the number of edges in your graph: ");
        int e = sc.nextInt();
        System.out.println("Enter the edges: ");
        for(int i =0; i<e;i++)
        {
            System.out.print("Source: ");
            int src = sc.nextInt();
            System.out.print("Destination: ");
            int dest = sc.nextInt();
            g.addEdge(src,dest);
        }
            
        
        //Graph 1
//        g.addEdge(0,1);
//        g.addEdge(0,2);
//        g.addEdge(1,3);
//        g.addEdge(2,3);
//        g.addEdge(3,4);
//        g.addEdge(1,5);
//        g.addEdge(4,5);
//        g.addEdge(4,6);
//        g.addEdge(4,7);
//        g.addEdge(5,7);
//        g.addEdge(6,8);
//        g.addEdge(6,9);
//        g.addEdge(7,8);
//        g.addEdge(7,9);
//        g.addEdge(8,10);
//        g.addEdge(9,11);
//        g.addEdge(10,12);
//        g.addEdge(11,12);
//     
	 
	 //Graph 2
    /* Graph g=new Graph(8,6);
      g.addEdge(0,1);
      g.addEdge(0,2);
      g.addEdge(1,3);
      g.addEdge(2,3);
      g.addEdge(3,4);
      g.addEdge(4,5);
      g.addEdge(5,6);
      g.addEdge(5,7);
*/

      //Graph 3
//      Graph g=new Graph(9,7);
//      g.addEdge(0,1);
//      g.addEdge(0,2);
//      g.addEdge(0,3);
//      g.addEdge(1,4);
//      g.addEdge(2,4);
//      g.addEdge(3,4);
//      g.addEdge(6,5);
//      g.addEdge(6,8);
//      g.addEdge(5,7);
//      g.addEdge(8,7);


     //Graph 4
    /* Graph g=new Graph(5,4);
     g.addEdge(0,1);
     g.addEdge(1,2);
     g.addEdge(2,3);
     g.addEdge(3,4);*/
    
    //OUR GRAPH
//      Graph g=new Graph(6,4);
//        g.addEdge(0,1);
//        g.addEdge(1,2);
//        g.addEdge(2,3);
//        g.addEdge(3,4);
//        g.addEdge(0,5);
//        g.addEdge(5,2);

    /* zero specifies the root of the graph */ 
      g.dominatorChain1(0);
      
      g.printDominators();
      g.displayVertices();
//      g.display();
      
        }
      
    }