package heap_implementation;

import java.util.*;

public class FibonacciHeap
{

    private Node maximumNode;
    private int totalNumNodes;  
 
    public Node removeMax()
    {
        Node c = maximumNode;
        
        if (c != null) {
        	Node rightNode;
        	Node a = c.child;
        	int numChildren = c.degree;                         
            
            while (numChildren > 0) {
                rightNode = a.right;
                a.right.left = a.left;
                a.left.right = a.right;               
                //disconnect a from child list
          
                a.left = maximumNode;
                a.right = maximumNode.right;
                maximumNode.right = a;
                a.right.left = a;
                //put a into roots
                
                a.parent = null;
                a = rightNode;                
                numChildren--;
            }
            
            c.right.left = c.left;
            c.left.right = c.right;
            //disconnect c from root list
            
            if (c == c.right) {
                maximumNode = null;
            } else {
               maximumNode = c.right;
               samedegreeMeld();
           }
           totalNumNodes--;
           return c;
       }
        return null;
    }
    //remove maximumNode
    
    public void insert(Node node)
    {        
        if (maximumNode != null) {
           
            node.left = maximumNode;
            node.right = maximumNode.right;
            maximumNode.right = node;
            //if there is maximumNode, add new node maximumNode's right
            
            if (node.right!=null) {                               
                node.right.left = node;
            }
            if (node.right==null)
            {
                node.right = maximumNode;
                maximumNode.left = node;
            }
            //check if there is already right node of maximumNode
            if (node.key > maximumNode.key) {
                maximumNode = node;
            }
            
        } else {
            maximumNode = node;
        }
        totalNumNodes++;
    }
    //insert new node
    
    public void increaseKey(Node a, int vkey)
    {
        a.key = vkey;
        Node b = a.parent;

        if ((b != null) && (a.key > b.key)) {
            cut(a, b);
            casCading(b);
        }
        //if a's key is greater than b's, node a is removed from b's child and b's degree and mark is changed
        if (a.key > maximumNode.key) {
            maximumNode = a;
        }
        //if a's key is greatest in heap, a is the maximumNode
    }
    //increase the key after adding up, and then, performs cut

   
    public void cut(Node a, Node b)
    {        
        a.left.right = a.right;
        a.right.left = a.left;
        b.degree--;
        //node a is removed from b's child and b's degree decreases
       
        if (b.child == a) {
            b.child = a.right;
        }
        if (b.degree == 0) {
            b.child = null;
        }

        a.left = maximumNode;
        a.right = maximumNode.right;
        maximumNode.right = a;
        a.right.left = a;
        a.parent = null;
        //put a into roots
        a.mark = false;
    }
    //node a is removed from b's child and a is put into roots
    
    public void casCading(Node b)
    {
        Node p = b.parent;       
        if (p != null) {           
            if (!b.mark) {
                b.mark = true;
            }
            //b lost kid, so b should be marked
            else {
               cut(b, p);             
               casCading(p);
               //p lost kid, so p should be marked

            }
        }
    }
        
    public void generateChild(Node ch, Node a)
    {   
    	ch.right.left = ch.left; 
        ch.left.right = ch.right;          
        ch.parent = a;
        //disconnect ch node from the other node and attach it to a as child node
        if (a.child == null) {
            a.child = ch;            
            ch.left = ch;
            ch.right = ch;
            
        } else {
            ch.left = a.child;
            ch.right = a.child.right;
            a.child.right = ch;
            ch.right.left = ch;
        }

        a.degree++;
        //x has one more child node and a's degree increase by 1
        ch.mark = false;
        //ch node turns to child node and mark is changed to false
    }
   
    public void samedegreeMeld()
    {        
        int degreeRange = 45;
        List<Node> degreeRT = new ArrayList<Node>(degreeRange);
        //degreeRangeTable is created, 45 is optimized number in this heap 
        for (int i = 0; i < degreeRange; i++) {
            degreeRT.add(null);
        }
        
        Node a = maximumNode;
        int rootNum = 0;

        if (a != null) {
            rootNum++;
            a = a.right;                 
            while (a != maximumNode) {
                rootNum++;
                a = a.right;
            }
        }

            while (rootNum > 0) {

            Node roots = a.right;
            int eachDegree = a.degree;

                for (;;) {
                Node b = degreeRT.get(eachDegree);
                if (b == null) {
                    break;
                }
                //check each degree if the degree is in degreeRangeTable,if yes, meld
                //if no, insert degree in the table
                if (a.key < b.key) {
                    Node str = b;
                    b = a;
                    a = str;
                }
                //change the order in order of key
                generateChild(b, a);
                //make b a child of a           
                degreeRT.set(eachDegree, null);
                eachDegree++;
                //change the degree after melding
            }
                degreeRT.set(eachDegree, a);
                //put the changed degree in the Table
                a = roots;
                rootNum--;
        }
                
        maximumNode = null;
        //maximum node pointer is removed
        	for (int i = 0; i < degreeRange; i++) {
            Node b = degreeRT.get(i);
            if (b == null) {
                continue;
            }
            //fill the table
            
            if (maximumNode != null) {
            	b.right.left = b.left;
                b.left.right = b.right;
                //disconnect the b node from roots and will check if it is maximum in below operations                
                b.left = maximumNode;
                b.right = maximumNode.right;
                maximumNode.right = b;
                b.right.left = b;
               
                if (b.key > maximumNode.key) {
                    maximumNode = b;
                }
            } else {
                maximumNode = b;
            }
        }
    }
  

}