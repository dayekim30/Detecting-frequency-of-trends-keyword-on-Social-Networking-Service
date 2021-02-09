package heap_implementation;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class hashtagcounter {


 public static void main(String[] args){
     
     
   String  infile = "C:\\Users\\Daye\\git\\Detecting-frequency-of-trends-keyword-on-Social-Networking-Service\\Advanced_Data_Structure\\src\\heap_implementation\\sampleInput.txt";
   
   // String  infile =args[0];
   PrintStream outwt=null; 

   //path of the input file 
  if(args.length==2) {
	  File file = new File("output_file.txt");
	   try {
		outwt = new PrintStream(file);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	     System.setOut(outwt);
  }
   
  // BufferedWriter outwt=null;
   //path of the output File 
   
   FibonacciHeap fnc = new FibonacciHeap();
   //Create FibocacciHeap object
   
   HashMap<String,Node> hsm = new HashMap();
   //Hash Map for nodes and names
  
    try {

     BufferedReader bfr = new BufferedReader(new FileReader(infile));
     String ph = bfr.readLine();

     Pattern count = Pattern.compile("(\\d+)");
     Pattern htag = Pattern.compile("([#])([a-z_]+)(\\s)(\\d+)");
     

     while (ph != null) {
        
         Matcher ct = count.matcher(ph);
         Matcher hg = htag.matcher(ph);

         if (hg.find()) {
        	 int key = Integer.parseInt(hg.group(4));
             String name = hg.group(2);
                            
             if ( !hsm.containsKey(name))
             {                     
                 Node node = new Node(name,key);
                 fnc.insert(node);
                 hsm.put(name,node);
             }
             //if there is not the name in the hashMap, the new node with the name is created and this node will be stored in hashMap
             else
             {                    
                 int increaseKey = hsm.get(name).key + key;
                 fnc.increaseKey(hsm.get(name),increaseKey);
             }
             //if there is the name in the hashMap, add up the key and then, fibonacciHeap will do increaseKey operation
         } else if (ct.find()) {
                
             int maxNum = Integer.parseInt(ct.group(1));
             ArrayList<Node> maxNumNodes = new ArrayList<Node>(maxNum);
             
             for ( int i=0;i<maxNum;i++)
             {                    
                 Node maxNode = fnc.removeMax();                  
                 hsm.remove(maxNode.getName());
                 //remove nodes from fibonacciHeap and from hashMap
                     
                 Node remaxNode= new Node(maxNode.getName(),maxNode.key);
                 //create nodes which were removed maxNodes
                     
                 maxNumNodes.add(remaxNode);
                 //put removed nodes in the maxNumnodes array
                     
                 if ( i <maxNum-1) {
                	 System.out.print(maxNode.getName() + ",");
                    // outwt.write(maxNode.getName() + ",");
                 }
                 else {
                	 System.out.print(maxNode.getName());
                     //outwt.write(maxNode.getName());
                 }
                 //write the names of maxNodes on outputFile
             }
                
             for ( Node iterate : maxNumNodes)
             {
                 fnc.insert(iterate);
                 hsm.put(iterate.getName(),iterate);
             }
             //insert removed nodes into fibonacciHeap and hashMap
             System.out.println();    
             //outwt.newLine();
         }
        
         ph = bfr.readLine();
     }
 }

 catch(Exception ex){
     System.out.println(ex);
 }
    
 finally {
     if ( outwt != null ) {
         outwt.close();
     }
 }

    

 }


}
