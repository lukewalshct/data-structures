/*
 * CharNode.java
 * 
 * A class that represents an Ascii character as a node in either a 
 * linked list or a linked tree. 
 * 
 * Luke Walsh, lukewalshct@gmail.com
 * 12/1/2015
 *
 */

public class CharNode
{
  private int charVal; 
  private int freq; //character frequency
  public CharNode parent; 
  public CharNode next; //only used when the node is in a linked list; otherwise null
  public CharNode right;
  public CharNode left;
  
  public CharNode(int ch, int freq)
  {
    this.charVal = ch;
    this.freq = freq;
    this.parent = null;
    this.next = null;
    this.right = null;
    this.left = null;
  }
  
  public int getCharVal()
  {
    return this.charVal; 
  }
  
  public int getFreq()
  {
    return this.freq; 
  }
}