Data Structures Fina Project
Luke Walsh
lukewalshct@gmail.com

Below is a list of the classes involved in this project, followed by a brief discussion
of the efficiency for each key operation in this implementation of a Huffman encoder.


BitReader.java (unchanged from starter code)
-a class for objects that read bits from a file

BitWriter.java (unchanged from starter code)
-a class for objects that write bits to a file

CharNode.java
-a class to represent an Ascii character as a node object in either
a linked list or a linked tree. These nodes will first be used as
part of a linked list after reading in the characters from the input 
file, and then will be rearranged into a tree structure using the 
appropriate Huffman algorithm. Along with references to the next and 
previous node (in linked list form) and parent and children (in linked
tree form), this class also stores the integer value of the character
along with the frequency it appears in the input file.

Code.java (unchanged from starter code)
-a class for an encoding of a character, which consists of the bits 
themselves and the number of bits in the code. NOte that the maximum
length of a character encoding is 32 bits.

Huff.java
-A program that compresses a .txt file using Huffman encoding algorithm.
This class contains methods that do the following (excluding helper methods):
	-getAsciiFreqs: builds an array containing the frequency of each 
	character in the input file.
	-buildInputCharFreqs: builds a linked list of nodes representing 
	characters, ordered by frequency. 
	-buildHuffTree: builds the linked-tree strucure that forms the
	Huffman encoding.
	-buildHuffTable: uses the Huffman tree to create an array of encoded
	Ascii characters.
	-writeOutputHeader: writes a header to the compressed output file
	that contains the frequency of each Ascii character.
	-writeOutputBits: reads through the input file a second time and 
	writes the encoded version of each character to the output file.

Puff.java
-A program that decompresses a .txt file using Huffman encoding algorithm.
This class contains methods that do the following (excluding helper methods):
	-getHeader: creates an array of frequencies of each Ascii character
	by reading through the header of the compressed file.
	-getTotalChars: calculates the total number of characters based on 
	the array of Ascii character frequencies.
	-writeDecompressedFile: reads through the bits in the compressed file
	and writes the full Ascii encoding to the decompressed output file.
	
	
	
Below are the key operations involved with Huffman encoding, follwed by a brief
discussion of the efficiecny of the implementation used in this project.

COMPRESSING A FILE:

STEP 1: Read through input file to get frequency of each character.

The process of getting the character frequencies in the input file requires looping 
through the input file once and reading in each of the characters. This process is
O(n) where n is the number of characters in the input file.

The frequency of each character needs to be stored by the program. I chose to use an 
array to store the frequencies in the getAsciiFreqs method, where each index in 
the array corresponds to the integer value of each of the 128 Ascii characters (the 
length of the array is 128). The main benefit of this approach is that once a character
(interpreted as an integer value) is read in, the program can update the frequency in
the array by incrementing the int value at the approrpriate index. So, when each character
is read in to update its frequency it accesses the array in constant time O(1).

Having constant time access to this array when reading through the input file is arguably
one of the most critical steps in terms of time efficiency of the entire program, since in 
most cases it will be the most frequently called operation (along with reading in characters
for the actual encoding). For example, if there is a file with 1 million characters, the 
array will need to be accessed 1 million times (in contrast, the Huffman tree build operation 
will still only be called once).   

STEP 2: Build a linked list of characters (nodes) ordered by frequency.

Next we need to build a linked list representation of the frequencies of the characters.
The buildInputCharFreqs method accomplishes this by looping through the array of Ascii
character frequencies and constructing a new CharNode object for each character with at least
one occurrence, and adding it to the list in sorted order. 

The process of inserting a character into the linkedlist is O(1) in the best case (i.e. the char
is the least frequent and belongs at the beginning) and O(n) in the worst case (i.e. the char is
the most frequent and belongs at the end of the linked list), where n is the number of nodes in 
the linked list at the time of insertion. This process is repeated up to 128 times (one for each
Ascii character), but possibly less if some characters do not appear in the input file.

STEP 3: Build a Huffman tree using the linked list of character frequencies.

This operation builds the Huffman tree by repeatedly combining the first two smallest items in 
the linked list of characters, and then re-inserting it into the linked list until there is
only one node left in the list with all the characters as leaf nodes in its subtrees.

The overall time efficiency of this step is O(n), since the time to combine the first two nodes
is negligible but it the new parent node needs to be inserted into the linked list which is O(n)
as mentioned above.

STEP 4: Encode each character and store it in a "lookup table" (array).

The time efficiency for this entire step is O(nlogn) in the average case, where n is the number 
of characters that have at least one occurrence (freq > 0) in the input file. This is because when 
a character is encoded the Huffman tree is traversed which itself is O(logn) efficient in the average
case, and this traversal needs to happen n times (one for each character).

STEP 5: Write a header to the output file.

This step requires access to the "lookup table" of character frequencies, which can be accessed in 
O(1) time. This access is repeated 128 times (one for each Ascii character) to write out the 
frequency of each character into the header of the output file.

STEP 6: Write the characters from the input file to the compressed file as the
	encoded version.

This step requires reading through the input file one more time, and so the process in this step
is O(n) where n is the number of characters in the input file. The encoded values for each character
are stored in the "lookup table" array, so when each time the writeOutputBits method reads in 
a character to be output it can access the array in O(1) time. 

Similar to Step 1 above, having this O(1) access to this array is critical for the overall time 
efficiency of the program, since this process will have to be iterated many times if the file contains
many characters.



DECOMPRESSING A FILE:

STEP 1: Read in the header from the compressed file and rebuild the "lookup table" of Ascii freqs.

This step is the opposite of step 5 above in the compression process, and has the same time 
efficiency.

STEP 2: Build a linked list of characters (nodes) ordered by frequency.

This step uses the exact same method as step 2 above in the compression process, and has the same
time efficiency.

STEP 3: Build a Huffman tree using the linked list of character frequencies.

This step uses the exact same method as step 3 above in the compression process, and has the same
time efficiency.

STEP 4: Read through the compressed file and write each character as the decoded/decompressed
	version in a new output file.

This step is the only one that is significantly different from the compression process. In the 
average case this step will be O(nlogn), where n is the number of characters in the compressed
file. This is because the bits are read in one by one from the compressed file, and are used
to traverse the Huffman tree until a leaf node is reached. This is similar to a search function
using a binary tree, which is O(logn) in the average case, and this process needs to be done
for each character in the compressed file (n times), giving O(nlogn).




