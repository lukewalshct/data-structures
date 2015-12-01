/* 
 * Puff.java
 *
 * A program that decompresses a file that was compressed using 
 * Huffman encoding.
 *
 * Luke Walsh, lukewalshct@gmail.com
 * 12/1/2015
 */ 

import java.io.*;
import java.util.*;

public class Puff {

    /*
     * Reads the header from the compressed file and reconstructs the Huffman
     * table containing the encoding for each Ascii character
     */ 
    private static int[] getHeader(ObjectInputStream in) 
    {
      int[] asciiFreqs = new int[128];
      
      try
      {
        for (int i = 0; i < asciiFreqs.length; i++)
        {
          asciiFreqs[i] = in.readInt();
        }
      }
      catch (Exception e)
      {
        System.out.println("Error reading header. System exiting...");
        System.exit(1);
      }
      
      return asciiFreqs;
    }
    
    /*
     * Reads through linked list of char frequencies and returns total # of
     * characters.
     */ 
    private static int getTotalChars(int[] asciiFreqs)
    {
      int totalChars = 0;
      for (int i = 0; i < asciiFreqs.length; i++)
      {
        totalChars += asciiFreqs[i];
      }
      return totalChars;
    }  
    
    /*
     * Reads through the compressed file and writes the full Ascii encoding to the decompressed
     * file for each character.
     */ 
    private static void writeDecompressedFile(BitReader reader, FileWriter out, CharNode huffTree, int totalChars)
    {
      if (reader == null || out == null) 
        throw new IllegalArgumentException();
      if (huffTree == null) return;
      
      int charsWritten = 0;
      try
      {
        int nextBit = reader.getBit();
        
        while(nextBit != -1 && charsWritten < totalChars)
        {
          CharNode nextNode = huffTree;
          while(nextBit != -1)
          {
            //Traverse the Huffman tree until a leafnode (i.e. char) is reached
            if (nextBit == 0 && nextNode.left != null) nextNode = nextNode.left;
            else if (nextBit == 1 && nextNode.right != null) nextNode = nextNode.right;
            else break;
            nextBit = reader.getBit();
          }
          //Write the character to the decompressed file
          out.write(nextNode.getCharVal());
          charsWritten++;
        }
      }
      catch (IOException e)
      {
        System.out.println("Error reading compressed file. System exiting...");
      }
    }  
      
    /** 
     * main method for decompression.  Takes command line arguments. 
     * To use, type: java Puff input-file-name output-file-name 
     * at the command-line prompt. 
     */ 
    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);
        ObjectInputStream in = null;      // reads in the compressed file
        FileWriter out = null;            // writes out the decompressed file

        // Get the file names from the command line (if any) or from the console.
        String infilename, outfilename;
        if (args.length >= 2) {
            infilename = args[0];
            outfilename = args[1];
        } else {
            System.out.print("Enter the name of the compressed file: ");
            infilename = console.nextLine();
            System.out.print("Enter the name to be used for the decompressed file: ");
            outfilename = console.nextLine();
        }

        // Open the input file.
        try {
            in = new ObjectInputStream(new FileInputStream(infilename));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + infilename);
            System.exit(1);
        }

        // Open the output file.
        try {
            out = new FileWriter(outfilename);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + outfilename);
            System.exit(1);
        }
    
        //Read in header to recreate array of char frequencies
        int[] asciiFreqs = getHeader(in);
        
        //Rebuild the Huffman tree
        CharNode charFreqList = Huff.buildInputCharFreqs(asciiFreqs);        
        CharNode huffTree = Huff.buildHuffTree(charFreqList);
        
        //Get total # of characters in input file
        int totalChars = getTotalChars(asciiFreqs);
        
        // Create a BitReader that is able to read the compressed file.
        BitReader reader = new BitReader(in);
        writeDecompressedFile(reader, out, huffTree, totalChars);
        
        in.close();
        out.close();
    }
}
