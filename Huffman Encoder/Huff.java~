/* 
 * Huff.java
 *
 * A program that compresses a file using Huffman encoding.
 *
 * Luke Walsh, lukewalshct@gmail.com
 * 12/1/2015
 */ 

import java.io.*;
import java.util.*;

public class Huff {

    /*
     * Accepts and reads through a FileReader object, and then returns an 
     * int array of the frequencies of each Ascii character in the input file.
     * Assumes that all characters in the input file are Ascii characters.
     */
    private static int[] getAsciiFreqs(FileReader in)
    {
      int[] asciiFreqs = new int[128];
      
      //Read in each Ascii character from input file; for each character
      //increase the frequency in the asciiFreqs array.
      try
      {
        int next = in.read();
        while(next != -1)
        {
          asciiFreqs[next] +=1;
          next = in.read();
        } 
      }
      catch (IOException e)
      {
        System.out.println("Error reading input file. System exiting...");
        System.exit(1);
      }
      return asciiFreqs;
    }
    
    /**
     * Accepts an array of frequencies for the Ascii characters and builds
     * an ordered linked list of input character by their frequencies.
     */ 
    public static CharNode buildInputCharFreqs(int[] asciiFreqs)
    {
      //The CharNode class represents a node in either a linked list or linked tree of characters.
      //charFreqs points to the first node in the linked list.
      CharNode charFreqs = null;            
      
      //Read through all the ascii freqs and create a new CharNode object for each
      //asciiFreq >0 and then add to the charFreqs linked list in sorted order      
      for (int i = 0; i < asciiFreqs.length; i++)
      {
        if (asciiFreqs[i] > 0)
        {
          //if it's the first CharNode, set the first node in charFreqs to this char freq object
          if (charFreqs == null)
          {
            charFreqs = new CharNode(i, asciiFreqs[i]);
          }
          //else create a new CharNode and add it in sorted order
          else
          {
            CharNode newNode = new CharNode(i, asciiFreqs[i]);
            charFreqs = insertCharNodeSorted(charFreqs, newNode);
          }
        }        
      }
      
      return charFreqs;
    }
    
    /*
     * Accepts a reference to the first node in a linked list and a new CharNode
     * to be inserted, and inserts the new node in sorted order. Returns reference
     * to the first node in the list.
     */ 
    private static CharNode insertCharNodeSorted(CharNode first, CharNode newNode)
    {
      if (first == null || newNode == null) throw new IllegalArgumentException();
      
      CharNode trav = first;
      
      //handle special case where newNode is the smallest and belongs at the front
      if (newNode.getFreq() <= first.getFreq())
      {
        newNode.next = trav;
        trav.parent = newNode;
        first = newNode;
      }
      //else if newNode freq is greater than the first node, insert newNode in order
      else
      {
        //advance trav until the newNode is <= trav.next
        while(trav.next != null && newNode.getFreq() > trav.getFreq())
        {
          if (newNode.getFreq() > trav.getFreq()) trav = trav.next;
        }
              
        //if it's the end of the list simply add newNode to the end
        if (trav.next == null && newNode.getFreq() > trav.getFreq())
        {
          trav.next = newNode;
          newNode.parent = trav;
        }
        //else insert it into the list
        else
        {
          //set references to node before newNode
          trav.parent.next = newNode;
          newNode.parent = trav.parent;
               
          //set references to node after newNode
          trav.parent = newNode;
          newNode.next = trav;
        }
      }      
      return first;
    }    
    
    /*
     * Builds a Huffman tree from the linked list of ordered Ascii character frequencies.
     * Returns a CharNode that is a reference to the root of the Huffman tree.
     */ 
    public static CharNode buildHuffTree(CharNode first)
    {
      if (first == null) return null;
      
      //Handle case where there is only one node in the link list
      if (first.next == null)
      {
        CharNode newNode = new CharNode(255, first.getFreq());
        first.parent = newNode;
        newNode.left = first;
        first = newNode;        
      }
      //Use Huffman algorithm to loop through the linked list of char freqs. Combine
      //the first two nodes until there is only 1 node left (root of Huffman tree).
      while (first.next != null)
      {
        CharNode newNode = new CharNode(255, first.getFreq() + first.next.getFreq());
        
        newNode.left = first;
        newNode.right = first.next;
        
        if (first.next.next == null) first = newNode;
        else 
        {
          first = first.next.next;
          first.parent = null;        
        
          //insert newNode into proper place
          first = insertCharNodeSorted(first, newNode);
        }
        
        newNode.left.next = null;
        newNode.right.next = null;
        newNode.left.parent = newNode;
        newNode.right.parent = newNode;     
      }
      return first;
    }
    
    /*
     * Accepts the Huffman tree and builds an array that contains the 
     * encoding for each Ascii character.
     */ 
    public static Code[] buildHuffTable(CharNode root)
    {
      Code[] codes = new Code[128];
      String encoding = "";
      encodeCharNodes(codes, root, encoding);
      return codes;
    }
    
    /*
     * Recursively encodes each Ascii character in the Huffman tree
     */ 
    private static void encodeCharNodes(Code[] codes, CharNode root, String encoding)
    {
      if (root == null) return;
      if (root.left != null)
      {
        encodeCharNodes(codes, root.left, encoding + "0");        
      }
      if (root.right != null)
      {
        encodeCharNodes(codes, root.right, encoding + "1");
      }
      
      //If a leaf node is reached (representing a char), add the encoding to the array
      if (root.left == null && root.right == null)
      {
        Code newCode = new Code();
        
        //Parse the encoding string and create a new Code object
        for (int i = 0; i < encoding.length(); i++)
        {
          String stringBit = "";
          stringBit = stringBit + encoding.charAt(i);
          int theBit = Integer.parseInt(stringBit);
          newCode.addBit(theBit);
        }               
        codes[root.getCharVal()] = newCode;
      }
    }
    
    
    /*
     * Writes a header to the output file. The header contains one integer per Ascii character (128 total)
     * representing the char's frequency in the original file.
     */ 
    private static void writeOutputHeader(ObjectOutputStream out, int[] asciiFreqs) throws IOException
    {
      if (out == null || asciiFreqs == null) throw new IllegalArgumentException();
      
      for(int i = 0; i < asciiFreqs.length; i++)
      {     
        out.writeInt(asciiFreqs[i]);
      }
    }
    
    /*
     * Reads through input file a second time, and writes the Huffman encoding to the output
     * file for each Ascii char in the input file.
     */ 
    private static void writeOutputBits(FileReader in, BitWriter writer, Code[] huffTable)
    {
      if (in == null || writer == null) throw new IllegalArgumentException();
      
      try
      {
        int next = in.read();
        while(next != -1)
        {
          writer.writeCode(huffTable[next]);
          next = in.read();
        }
        writer.flushBits();
      }
      catch (IOException e)
      {
        System.out.println("Error reading input file. System exiting...");
        System.exit(1);
      }
    }
    
    /** 
     * main method for compression.  Takes command line arguments. 
     * To use, type: java Huff input-file-name output-file-name 
     * at the command-line prompt. 
     */ 
    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);
        FileReader in = null;               // reads in the original file
        ObjectOutputStream out = null;      // writes out the compressed file
        
        // Get the file names from the command line (if any) or from the console.
        String infilename, outfilename;
        if (args.length >= 2) {
            infilename = args[0];
            outfilename = args[1];
        } else {
            System.out.print("Enter the name of the original file: ");
            infilename = console.nextLine();
            System.out.print("Enter the name to be used for the compressed file: ");
            outfilename = console.nextLine();
        }
            
        // Open the input file.
        try {
            in = new FileReader(infilename);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + infilename);
            System.exit(1);
        }

        // Open the output file.
        try {
            out = new ObjectOutputStream(new FileOutputStream(outfilename));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + outfilename);
            System.exit(1);
        }  
        
        // Get frequency of each Ascii character in input file
        int[] asciiFreqs = getAsciiFreqs(in);
        // Build an ordered linked list of the Ascii freqs
        CharNode charFreqList = buildInputCharFreqs(asciiFreqs);
        // Build a Huffman tree from the linked list
        CharNode huffTree = buildHuffTree(charFreqList);
        // Create a table with encoding for each Ascii character
        Code[] huffTable = buildHuffTable(huffTree);
                
        in.close();
        
        // Write the encoded characters to the output file:
        // Reopen the input file
        try {
            in = new FileReader(infilename);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + infilename);
            System.exit(1);
        }
        
        // Write the header which contains the freq of each character
        writeOutputHeader(out, asciiFreqs);
        
        // Create a BitWriter that is able to write to the compressed file.
        BitWriter writer = new BitWriter(out);
        // Write the bits of each char's encoding in input file to the output file
        writeOutputBits(in, writer, huffTable);
        
        in.close();
        out.close();
    }
}
