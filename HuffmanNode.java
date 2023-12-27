// Paul Lam
// Section AG, Neel jog
// A8 HuffmanNode
// HuffmanNode creates a structre that stores the information of the the HuffmanTree.

public class HuffmanNode implements Comparable<HuffmanNode> {	
   
   public int ascii;
   
   public int frequency;	
    
   public HuffmanNode left;
   
   public HuffmanNode right;
   
   // Purpose: Creates a node with the given information.
   // Pre: No pre conditions.
   // Post: Takes the given information and consturcts a node.
   // Parameters: asciiKey = Given ascii value.
   // frequencyC = Given frequency value.
   public HuffmanNode(int asciiKey, int frequencyC) {
      this.ascii = asciiKey;
      this.frequency = frequencyC;
      this.left = null;
      this.right = null;
   }		
   
   // Purpose: Compares two huffman nodes to eachother. 
   // Pre: No pre conditions.
   // Post: Uses the given huffmanNode to compare with the main
   // huffman node and returns which is greater.
   // Parameters: other = value of another huffmanNode.
   public int compareTo(HuffmanNode other) {
      return this.frequency - other.frequency;
   }
}