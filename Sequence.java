/*
 *Sequence.java
 *
 *Written by Ryan Petrosky (petrosky.ryan@gmail.com)
 *November 5, 2014
 *
 *A class for DNA Sequence Objects
 *
 *Dependencies:
 *  Sequence.java
 *  ReverseSequence.java
*/

import java.util.*;
import java.io.*;

public class Sequence {

/*
------------------------------|         FIELDS & CONSTRUCTOR         |----------------------------------
*/ 
   //class constants
   public static final String NUMBER_OF_SEQS = "<160>";
   public static final String SEQUENCE_NUMBER = "<210>";
   public static final String LENGTH_OF_SEQUENCE = "<211>";
   public static final String SEQUENCE_TYPE = "<212>";
   public static final String DESCRIPTION = "<213>";
   public static final String CDS_MARKER = "<221>";
   public static final String START_DNA_SEQ = "<400>";
   private static final int PROTEINS_PER_LINE = 16;//Protein/3 Sequences - number of proteins per line in the input file
   private static final int NUCLEOTIDES_PER_LINE = 60;//DNA & Pro/1 Sequences - number of characters per line in input file
   private static final int CDS_NUCS_PER_LINE = 48;//a DNA CDS sequence has 48 nucleotides per line
   private static final int CHARACTERS_PER_LINE = 60;//manipulates output file results

   //class field
   private static int NUM_OF_SEQS;

   //instance fields
   private int sequenceNumber = 0;//field <210>, initialized to 0 for the fillSequenceObject() loop in ReverseSequence.java
   private int lengthOfSequence;//field <211>
   private String sequenceType;//field <212>
   private String description;//field <213>
   private boolean cds = false;//field <221>
   private int cdsStart;//only populated if sequence hasCDS()
   private int cdsStop;//only populated if sequence hasCDS()  
   private String[] actualSequence;//the important lines from the code <400>
   private int sizeOfSequenceArray;//sequence array size
   private int linesAddedToSequenceArray = 0;//incremented with each string added to array


   //constructor
   public Sequence(){}


/*
------------------------------|         CLASS METHODS         |----------------------------------
*/ 
   public static void setNumberOfSequences(int n) {
      NUM_OF_SEQS = n;
   }
//                                                            < --  field <160> (number of sequences in file)
   public static int getNumberOfSequences() {
      return NUM_OF_SEQS;
   } 


/*
------------------------------|       INSTANCE METHODS        |----------------------------------
*/
   public void setSeqNum(int n) {
      sequenceNumber = n;
   }
//                                                            < --  field <210> (the sequence number)   
   public int getSeqNum() {
      return sequenceNumber;
      }
   
   public void setLen(int n) {
      lengthOfSequence = n;
   }
//                                                            < --  field <211> (the length of the sequence)   
   public int getLen() {
      return lengthOfSequence;
   }

   public void setType(String s) {
      sequenceType = s;
   }
//                                                            < --  field <212> (the sequence type)
   public String getType() {
      return sequenceType;
   }

   public void setDescrp(String s) {
      description = s;
   }
//                                                            < --  field <213> (the sequence description) 
   public boolean hasCDS() {
      return cds;
   }
   
   public void setCDS(boolean b) {
      cds = b;
   }
//                                                            < --  field <221> (CDS Flag methods)   
   public void setCDSStart( int n ) {
      cdsStart = n;
   }

   public void setCDSStop( int n ) {
      cdsStop = n;
   }
   
   private void setSizeOfSequenceArray() {//                  < --  field <400> determines size of array
      int D;
      if(sequenceType.equals("Protein/3") ) {
         D = PROTEINS_PER_LINE;
      } else if ( hasCDS() ) {
         D = CDS_NUCS_PER_LINE;
      } else {
         D = NUCLEOTIDES_PER_LINE;
      }
      //determine size of array
      sizeOfSequenceArray = lengthOfSequence / D;
      if( lengthOfSequence % D != 0) {
         sizeOfSequenceArray++;
      }
      //builds the array that will hold the sequence
      actualSequence = new String[sizeOfSequenceArray];
   }

   public void fillSequenceArray( Scanner sc ) {//             < --  field <400> reads sequence into array
      String line = sc.nextLine();
      if(! line.isEmpty() ) {//DNA or Protein/1 Sequence
         if( sequenceType.equals("PRT") ) {
            sequenceType = "Protein/1";
         }
         setSizeOfSequenceArray();
         actualSequence[0] = line.substring(0,65).replace(" ","").toUpperCase();
         for(int i = 1; i < sizeOfSequenceArray; i++) {
            if ( hasCDS() && 
               ( Integer.parseInt( line.substring(66).trim() ) > cdsStart ) &&
               ( Integer.parseInt( line.substring(66).trim() ) <= cdsStop ) ) {
               sc.nextLine();//consumes protein line
               sc.nextLine();//consumes protein index line
            }
            sc.nextLine();//consumes blank line
            line = sc.nextLine();
            actualSequence[i] = line.substring(0,65).replace(" ","").toUpperCase();
            if( hasCDS() ) {
               //CDS sequences may be less lines than actually calculated in setSizeOfSequenceArray()
               if( lengthOfSequence == Integer.parseInt( line.substring(66).trim() ) ) {
                  //this CDS sequence has start and stop values somewhere in the middle of the sequence 
                  sizeOfSequenceArray = i + 1;
                  return;
               }
            }
         }
      } else {//Protein/3 Sequence
         sequenceType = "Protein/3";
         setSizeOfSequenceArray();
         for(int i = 0; i < sizeOfSequenceArray; i++) {
            actualSequence[i] = sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
         }
      }
   }
   

//meet the output file's needs
   public String header() {
      return "<SEQ" + sequenceNumber + "; " + sequenceType + "; " + description + ">";
   }

   public void printSequence( Writer wr ) throws IOException {
      //writes header to output file
      wr.write(header());
      wr.write(System.lineSeparator());
      if (! sequenceType.equals("Protein/3") ){
         //writes sequence to output file manipulating length of characters per line
         String seq = "";
         for(int i = 0; i < sizeOfSequenceArray; i++ ) {
            seq += actualSequence[i];
         }
         int start = 0;
         int stop = CHARACTERS_PER_LINE;
         while( start < ( lengthOfSequence - CHARACTERS_PER_LINE ) ) {
            wr.write( seq.substring(start,stop) );
            wr.write( System.lineSeparator() );
            start += CHARACTERS_PER_LINE;
            stop += CHARACTERS_PER_LINE;
            wr.flush();
         }
         wr.write( seq.substring(start, lengthOfSequence) );
         wr.write(System.lineSeparator());
      } else {
         //writes Protein/3 sequence to file line by line
         for( int i = 0; i < sizeOfSequenceArray; i++ ) {
            wr.write( actualSequence[i] );
            wr.write( System.lineSeparator() );
         }
      }
      wr.write( System.lineSeparator() );
      //if an error occurrs, flushing the writer allows the file to be saved
      //up until the problematic sequence is encountered.
      wr.flush();
   }
/*
------------------------------|       END INSTANCE METHODS       |----------------------------------
*/
}