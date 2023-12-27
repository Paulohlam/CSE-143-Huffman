// Paul Lam
// Section AG, Neel jog
// A8 HuffmanNode
// HuffmanNode takes the given file and is able to compress and decompress it.

import java.util.*;
import java.io.*;

public class HuffmanTree {
   
   private HuffmanNode root;
   
   // Purpose: Creates a stucture that stores the frequencies of each character
   // from the given array. 
   // Pre: No Pre conditions.
   // Post: Creates a tree from the given array of frequencies for each character.
   // This tree will appear in pre order.
   // Parameters: count = The given array containing the frequencies for each character.
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> q = new PriorityQueue<HuffmanNode>();     
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            HuffmanNode node = new HuffmanNode(i, count[i]);
            q.add(node);
         }
      }
      HuffmanNode nodeEof = new HuffmanNode((count.length), 1);
      q.add(nodeEof);      
      while (q.size() > 1) {
         HuffmanNode temp1 = q.remove();
         HuffmanNode temp2 = q.remove();
         HuffmanNode newNode = new HuffmanNode(-1, (temp1.frequency + temp2.frequency));
         newNode.left = temp1;
         newNode.right = temp2;
         q.add(newNode);
      }
      root = q.remove();
   }

   // Purpose: Writes the given tree to the given output file in pre order.
   // Pre: No pre conditions
   // Post: Writes the given tree in pre order to the given output file by
   // the client.
   // Parameters: output = The given output file by the client.
   public void write(PrintStream output) {
      String code = "";
      writeHelper(output, root, code);
   }

   // Purpose: Helps create the code for the specific ascii in order to be
   // printed. ascii first then code in pre order. 
   // Pre: No pre conditions.
   // Post: Same as purpose.
   // Parameters: output = The given output file/print location.
   // node = the given node used to print out.
   // code = the code related to the ascii code.
   private void writeHelper(PrintStream output, HuffmanNode node, String code) {
      if (node.left == null && node.right == null) {
         output.println(node.ascii);
         output.println(code);
      } else {
         writeHelper(output, node.left, code + "0");
         writeHelper(output, node.right, code + "1"); 
      }
   }

   // Purpose: Takes a tree stored in standard format and reconstructs
   // a new tree using the given tree from the client in pre order.
   // Pre: No pre conditions 
   // Post: Takes the given tree from the client in standard format 
   // to construct a new tree.
   // Parameters: input = client given tree in standard format 
   public HuffmanTree(Scanner input) {
      while (input.hasNextLine()) {
         int ascii = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         root = HuffmanTreeHelper(root, code, ascii);  
      }    
   }  

   // Purpose: Checks all of the test cases of the given tree stored in standard
   // format and will build the tree depending on the code.
   // Pre: No pre conditions
   // Post: Creates a tree from the client given tree stored in standard format by
   // going through different test cases and reading the code.
   // New tree will be in pre order!
   // Parameters: ascii = The ascii code for the character.
   // code = The code read to see which way the tree will be built.
   // node = The node passed in to build the tree.
   private HuffmanNode HuffmanTreeHelper(HuffmanNode node, String code, int ascii) {
      if (code.isEmpty()) {
         node = new HuffmanNode(ascii, -1);
      } else {
         if (node == null) {
            node = new HuffmanNode(-1,-1);
         }
         if (code.charAt(0) == '0') {
            code = code.substring(1);
            node.left = HuffmanTreeHelper(node.left, code, ascii);
         } else {
            code = code.substring(1);
            node.right = HuffmanTreeHelper(node.right, code, ascii);
         } 
      }
      return node;
   }

   // Purpose: Takes the client's input of bits and writes the character that 
   // corresponds with that bit in an output file given by the client. The code
   // will stop once it hits the end of file number.
   // Pre: Input must not legal encoded characters.
   // Post: Exports characters corresponding to the given input of bits from the
   // client in an output file given by the client. Once the end of file number
   // is reached the code will stop.
   // Parameters: input= The given bits from the client.
   // output = The given output file to print.
   // eof = The end of file character to know when to stop.
   public void decode(BitInputStream input,
          PrintStream output, int eof) {
      boolean checker = true;
      while (checker) {
         int ascii = decodeHelper(input, root);
         if (ascii == eof) {
            checker = false;
         } else {
            output.print((char)ascii);
         }
      }
   }

   // Purpose: Gets the ascii value from the given input of bits from the client.
   // Reads the bits by using the tree.
   // Pre: Input must not legal encoded characters.
   // Post: Uses the given bits from the client to return the ascii value that 
   // corresponds to the bits.
   // Parameters: input= The given bits from the client. 
   // tempRoot = The tree used to read the bits.
   private int decodeHelper(BitInputStream input, HuffmanNode tempRoot) {
      int n = 0;
      if (tempRoot.left == null && tempRoot.right == null) {
         return tempRoot.ascii;
      } else {
         if (input.readBit() == 0) {
            n = decodeHelper(input, tempRoot.left);
         } else {
            n = decodeHelper(input, tempRoot.right);
         } 
      }
      return n;  
   }
}