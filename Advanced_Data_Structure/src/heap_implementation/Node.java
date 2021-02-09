package heap_implementation;

public class Node
{
		int key;
		private String name;
		boolean mark = false;
		int degree = 0;
		Node child, parent, left, right;
	
	
	
	

        Node(String name,int key)
        {
           this.left = this;
           this.right = this;
           this.parent = null;
           this.degree = 0;
           this.name = name;
           this.key = key;

        }

        public  String  getName(){
            return this.name;
        }

    }
