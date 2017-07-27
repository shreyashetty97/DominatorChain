import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph {

    int edge[][];
    Vertex vertices[];
    ArrayList<ArrayList<ArrayList<Vertex>>> D;
    int root;
    int n;
    ArrayList<ArrayList<Integer>> chain_0;
    ArrayList<ArrayList<Integer>> chain_1;

    public Graph(int n, int root) {
        this.n = n;
        D = new ArrayList<>();
        chain_0=new ArrayList();
        chain_1=new ArrayList();
        edge = new int[20][20];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                edge[i][j] = 0;
            }
        }
        this.root = root;
        vertices = new Vertex[20];
        int i;
        for (i = 0; i < n + 1; i++) {
            vertices[i] = new Vertex(i);

        }
    }

    void addEdge(int src, int dest) {
        edge[src][dest] = 1;
    }

    void display() {
        int i;
        for (i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("=> " + edge[i][j]);
            }
            System.out.println("");
        }
    }

    void initialiseValues(){
        
        this.chain_0=new ArrayList();
        this.chain_1=new ArrayList();  
        for(int i=0;i<n;i++){
            
           
            vertices[i].index=-1;
            vertices[i].min_index=-1;
            vertices[i].max_index=-1;
            vertices[i].flag=-1;
        
        }
            
        
    }
    
    
    void dominatorChain1(int u){
        
        initialiseValues();
        
        ArrayList<Integer> singleDom=new ArrayList();
        singleIDom(edge,u,root,n,singleDom);
        if(singleDom.isEmpty()|| u==root){
            System.out.println("No double vertex dominators");
            return;
        }
//       System.out.println("The dominators are "+singleDom);
        Iterator itr=singleDom.iterator();       
        
        ArrayList<Integer> vSet=new ArrayList();
        vSet.add(u);
        int index_1=0;
        int index_0=0;
        int iDom=u; 
        while(itr.hasNext()){
            int prevDom=iDom;
            iDom=(int)itr.next();
            
            while(true){
                ArrayList<Integer> list_0=new ArrayList();
                ArrayList<Integer> list_1=new ArrayList();
                vSet=generateChain(prevDom,list_0,list_1,vSet,iDom,edge,index_0,index_1);
                if(vSet==null){
                    vSet=new ArrayList();
                    vSet.add(iDom);
                    break;
                }
                chain_0.add(list_0);
                chain_1.add(list_1);
                index_0+=list_0.size();
                index_1+=list_1.size();
                
            }
            if(iDom==root)
                break;
                
        }
        System.out.println("Chain zero is "+chain_0);
        System.out.println("Chain one is "+chain_1);    
    }
    
    void displayVertices(){
        
        System.out.println("Vertex values");
        System.out.println("Vertex no\tFlag\tIndex\tmin_index\tmax_index");
        for(int i=0;i<n;i++){
            System.out.print("Vertex "+i);
            System.out.print("\tFlag = "+vertices[i].flag);
            System.out.print("\t\tindex = "+vertices[i].index);
            System.out.print("\tmin index = "+vertices[i].min_index);
            System.out.println("\tmax index = "+vertices[i].max_index);
            
                    
        }    
        
    }
    
    ArrayList<Integer> generateChain(int q,ArrayList<Integer> list_0,ArrayList<Integer> list_1,ArrayList<Integer> vSet,int t,int e[][],int index_0,int index_1){
        
        
        ArrayList<Integer> cDom=new ArrayList();
       
        
        
        int s=q;
        Integer v;
        v=doubleIDom(vSet, e, t,cDom);
        if(v==null){
//           System.out.println("no double vertex dominators");
            return null;
        }
          
        if(!cDom.isEmpty()){
            list_0.add(v);
            vertices[v].index=index_0;
            list_1.add(cDom.get(0));
            vertices[cDom.get(0)].index=index_1;
            vertices[v].min_index=index_1;
            vertices[v].max_index=index_1;
            vertices[cDom.get(0)].min_index=index_0;
            vertices[cDom.get(0)].max_index=index_0;
            vertices[cDom.get(0)].flag=1;
            vertices[v].flag=0;
            index_0++;
            index_1++;
            int zero_size=list_0.size();
            int one_size=list_1.size();
            int zero_count=0,one_count=0;
            while(zero_count<zero_size || one_count<one_size)
            {
                if(zero_count<zero_size)
                {              
                Integer x=null;
                int u=list_0.get(zero_count);
                int a[][]=new int[20][20];
                for(int i=0;i<n;i++)
                {
                    for(int j=0;j<n;j++)
                    {
                        if(i==u||j==u)
                            a[i][j]=0;
                        else
                            a[i][j]=e[i][j];
                    }
                }
//                System.out.println("A: ");
//                
//                 for(int i=0;i<n;i++)
//                {
//                    for(int j=0;j<n;j++)
//                    {
//                        System.out.print(a[i][j]+ " ");
//                    }
//                    System.out.println();
//                }
                ArrayList<Integer> single=new ArrayList();
                singleIDom(a,s,t,n,single);  //Checking if list 0.get(i)-->(u) has anymore doubleDominator pairs.
                single.remove((Integer)t);
                Iterator itr=single.iterator();
                while(itr.hasNext()){
                  x=(Integer)itr.next();
                  if(!list_1.contains(x)){
                    list_1.add(x);
                    vertices[x].min_index=vertices[u].index;
                    vertices[x].max_index=vertices[u].index;
                    vertices[x].flag=1;
                    vertices[x].index=index_1;
                    index_1++;
                    
                   }
                   else{
                      
                    vertices[x].min_index=Integer.min(vertices[u].index,vertices[x].min_index);
                    vertices[x].max_index=Integer.max(vertices[u].index,vertices[x].max_index);
                    
                   }
                
                }
                
                vertices[u].min_index=vertices[x].index-single.size()+1;
                vertices[u].max_index=vertices[x].index;
                
                zero_count++;
                }
                
                if(one_count<one_size){
                Integer x=null;
                int u=list_1.get(one_count);
                int a[][]=new int[20][20];
                for(int i=0;i<n;i++){
                    for(int j=0;j<n;j++){
                        if(i==u||j==u)
                            a[i][j]=0;
                        else
                            a[i][j]=e[i][j];
                    }
                }
                ArrayList<Integer> single=new ArrayList();
                singleIDom(a,s,t,n,single);
                single.remove((Integer)t);
                Iterator itr=single.iterator();
                while(itr.hasNext()){
                  x=(Integer)itr.next();
                  if(!list_0.contains(x)){
                    list_0.add(x);
                    vertices[x].min_index=vertices[u].index;
                    vertices[x].max_index=vertices[u].index;
                    vertices[x].flag=0;
                    vertices[x].index=index_0;
                    index_0++;
                    
                   }
                   else{
                    vertices[x].min_index=Integer.min(vertices[u].index,vertices[x].min_index);
                    vertices[x].max_index=Integer.max(vertices[u].index,vertices[x].max_index);
                    
                   }
                
                }
                
                vertices[u].min_index=vertices[x].index-single.size()+1;
                vertices[u].max_index=vertices[x].index;
                one_count++; 
                }
                zero_size=list_0.size();
                one_size=list_1.size();
            }
        }
        
        vSet=new ArrayList();
        if(list_0.isEmpty())
            return null;
        vSet.add(list_0.get(list_0.size()-1));
        vSet.add(list_1.get(list_1.size()-1));
        return vSet;
 
    }
    
    

    void singleIDom(int e[][], int s,int t,int len,ArrayList<Integer> singleDominators) {

        // Create a residual graph and fill the residual graph with
        // given capacities in the original graph as residual capacities
        // in residual graph
        int rGraph[][] = new int[20][20]; // rGraph[i][j] indicates residual capacity of edge i-j
        for (int u = 0; u < len; u++) {
            for (int v = 0; v < len; v++) {
                rGraph[u][v] = e[u][v];
            }
        }

        int parent[] = new int[len]; 
        // This array is filled by BFS and to store path
        
        // Augment the flow while tere is path from source to sink
        ArrayList<Integer> path = new ArrayList();
        
        int flag=bfs(rGraph, s, t, parent,len);
        /* Find minimum residual capacity of the edhes along the
         path filled by BFS. Or we can say find the maximum flow
         through the path found. */
        if(flag!=0){
        for (int v = t; v != s; v = parent[v]) {
            int u = parent[v];
            if (u != s ) {

                path.add(u);
            
            }
            
        }

        
        Collections.reverse(path);
        Iterator itr = path.iterator();

        while (itr.hasNext()) {

            int v = (int) itr.next();
//            System.out.println("v: " + v);
            Integer transpose[][] = new Integer[20][20];
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if ((i == v) || (j == v)) {
                        transpose[i][j] = 0;
                    } else {
                        transpose[i][j] = rGraph[i][j];
                    }
                }

            }

            Boolean notDom = modifiedBfs(transpose, s,t,len);

            if (notDom == false) {
                singleDominators.add(v);
            }

        }
        singleDominators.add(t);
//        System.out.println("The single dominators are " + singleDominators);

        }
        

    }

    boolean modifiedBfs(Integer array[][], int src,int t,int len) {
        boolean visited[] = new boolean[len];
        for (int i = 0; i < len; i++) {
            visited[i] = false;
        }
         //int t=root;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(src);
        visited[src] = true;
        while (queue.size() != 0) {
            int u = queue.poll();
            for (int v = 0; v < len; v++) {
                if (visited[v] == false && array[u][v] > 0) {
                    queue.add(v);
                    visited[v] = true;
                    if (v == t) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    void doubleDomHelper(ArrayList<Integer> vSet, int e[][], int a[][]) {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (vSet.contains(i) && vSet.contains(j)) {
                    a[i][j] = 0;
                } else if (vSet.contains(i)) {
                    if (e[i][j] == 1) {

                        a[n][j] = 1;

                    } else {
                        a[n][j] = 0;
                    }
                    a[i][j] = 0;
                } else if (vSet.contains(j)) {
                    if (e[i][j] == 1) {
                        a[i][n] = 1;
                    } else {
                        a[i][n] = 0;
                    }
                    a[i][j] = 0;
                } else {
                    a[i][j] = e[i][j];
                }

            }
        }
//        System.out.println("The combined matrix is");
//        for (int i = 0; i < n + 1; i++) {
//            for (int j = 0; j < n + 1; j++) {
//                System.out.print(a[i][j] + "\t");
//            }
//            System.out.println();
//        }
        

    }

    Integer doubleIDom(ArrayList<Integer> vSet, int e[][], int sink,ArrayList<Integer> singleDom) {
        
        Integer v=null;
        int a[][] = new int[20][20];
        doubleDomHelper(vSet, e, a);
        int src = n;// 13 combines 6 and 7
        int alen=n+1;//length of array

        int parent[] = new int[20];
        
        bfs(a, src, sink, parent,alen);
        ArrayList<Integer> path = new ArrayList();
        
        for (int w = sink; w != src; w = parent[w]) {
            int u = parent[w];
            if (u != src) {
                path.add(u);
            }
            
        }
        Collections.reverse(path);
        Iterator itr = path.iterator();
        
        while (itr.hasNext()) {

            v = (Integer) itr.next();
          
                for (int i = 0; i < n; i++) {
                  a[i][v] = 0;
                  a[v][i] = 0;
                }
            
            singleIDom(a, src,sink,alen,singleDom);
            singleDom.remove((Integer)sink);
            if(!singleDom.isEmpty())
                break;
        }
        if(v!=null){
        
        return v;
        }
        else
            return null;

    }

    // for double vertex dominators
    

    int bfs(int rgraph[][], int s, int t, int parent[],int len) {
        boolean visited[] = new boolean[len];
        for (int i = 0; i < len; i++) {
            visited[i] = false;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;
        int flag=0;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < len; v++) {
                if (visited[v] == false && rgraph[u][v] > 0) {
                    if(v==t)
                        flag=1;
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;

                }
            }
        }
        return flag;
    }
    
    void printDominators(){
        int index=0;
        int outer=chain_0.size();
        System.out.println("chain_0.size: "+outer);
        for(int i=0;i<outer;i++){
            int inner=chain_0.get(i).size();
            System.out.println("chain_0.get(i): " + chain_0.get(i));
            System.out.println("inner: " + inner);
            for(int j=0;j<inner;j++){
                int val=chain_0.get(i).get(j);
                System.out.println("val: " + val);
                int min=vertices[val].min_index;
                int max=vertices[val].max_index;
                min-=index;
                max-=index;
                for(int k=min;k<=max;k++){
                    
                    int pair=chain_1.get(i).get(k);
                    System.out.println("Dominators "+val+" "+pair);
                }
                
            }
            index+=chain_1.get(i).size();
        }
    }
}