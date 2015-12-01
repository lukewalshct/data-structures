/* 
 * Puff.java
 *
 * A program that decompresses a file that was compressed using 
 * Huffman encoding.
 *
 * <your name>, <your e-mail address>
 * <current date>
 */ 

import java.io.*;
import java.util.*;

public class Puff {

    /* Put any methods that you add here. */


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
    
        // Create a BitReader that is able to read the compressed file.
        BitReader reader = new BitReader(in);


        /****** Add your code here. ******/




        /* Leave these lines at the end of the method. */
        in.close();
        out.close();
    }
}
