/* 
 * Huff.java
 *
 * A program that compresses a file using Huffman encoding.
 *
 * <your name>, <your e-mail address>
 * <current date>
 */ 

import java.io.*;
import java.util.*;

public class Huff {

    /* Put any methods that you add here. */


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
    
        // Create a BitWriter that is able to write to the compressed file.
        BitWriter writer = new BitWriter(out);

        /****** Add your code below. ******/
        /* 
         * Note: After you read through the input file once, you will need
         * to reopen it in order to read through the file
         * a second time.
         */




        /* Leave these lines at the end of the method. */
        in.close();
        out.close();
    }
}
