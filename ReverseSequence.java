/*
 *ReverseSequence.java
 *
 *Written by Ryan Petrosky (petrosky.ryan@gmail.com)
 *November 5, 2014
 *
 *Workhorse of Reverse.java
 *
 *Dependencies:
 *  Sequence.java
 *  Reverse.java
*/

import java.util.*;
import java.io.*;
import javax.swing.*;


public class ReverseSequence {

  
   //opens the input file.
   public static Scanner openInputFile( String s ) throws FileNotFoundException {
      
      File inFile = new File( s );
      //if file cannot be opened, notify user and exit.
      if ( ! inFile.canRead() ) {
         return null;
         }
      //if file was opened, return attached scanner   
      return ( new Scanner(inFile) );
   }
   
   
   //opens the output file.
   //output can go in other directories if needed ex: "../OneDirectoryBack.txt"
   public static File openOutputFile( String s ) throws IOException {

      File outFile =  new File( s );
      if ( outFile.createNewFile() ) {
         //new file successfully opened
         return ( outFile );
      }
      //this file already exists.
      return null;
   }
   
   
   //main loop that calls other functions to do the work
   public static void doWork( Scanner input , Writer w ) throws IOException{

      //builds an array of sequences, of the proper size
      Sequence[] sequences = new Sequence[findCode160( input )];
      int i = 0;
      //runs until end of file && we haven't overrun the sequence array
      while( input.hasNextLine() && i < Sequence.getNumberOfSequences() ) {
         sequences[i] = fillSequenceObject( input );
         sequences[i].printSequence( w );
         if ( sequences[i].hasCDS() ) {
            //if this sequence is CDS, we will skip past the next sequence
            i++;
            while(! input.nextLine().startsWith( Sequence.SEQUENCE_NUMBER ) ) {
               input.nextLine();
            }
         }
         i++;
      }//end of file
   }
   
   
   //sets the number of sequences in the file
   private static int findCode160( Scanner sc ) {

      int num = 0;
      String line;
      while( Sequence.getNumberOfSequences() == 0 && sc.hasNextLine() ) { 
         line = sc.nextLine();
         if( line.startsWith(Sequence.NUMBER_OF_SEQS) ) {
            //scanner is on code <160>
            num = Integer.parseInt( line.substring(5).trim() );
            Sequence.setNumberOfSequences( num );
         }
      }
      return num;
   }
   
   
   //builds a Sequence object from the input file
   private static Sequence fillSequenceObject( Scanner sc ) {
   
      String line;
      Sequence theSequence = new Sequence();
      //skips to next line until reaching the beginning of the sequence object (code 210)
      //then enters the if statement and populates the object's fields
      while( ( sc.hasNextLine() ) && ( theSequence.getSeqNum() == 0 ) ) {
         line = sc.nextLine();
         if( line.startsWith(Sequence.SEQUENCE_NUMBER) ) {//scanner is at the end of line <210>
            theSequence.setSeqNum( Integer.parseInt( line.substring(5).trim() ) );
            theSequence.setLen( Integer.parseInt( sc.nextLine().substring(5).trim() ) );
            theSequence.setType( sc.nextLine().substring(5).trim() );
            theSequence.setDescrp( sc.nextLine().substring(5).trim() );
            //scanner is now at the end of line <213>
            //checks for CDS marker
            while(! line.startsWith(Sequence.START_DNA_SEQ) ) {
               if( ( line.startsWith(Sequence.CDS_MARKER) ) && ( line.substring(5).trim().equalsIgnoreCase("CDS") ) ) {
                  //hasCDS() now  true
                  theSequence.setCDS(true);
                  //set cdsStart
                  line = sc.nextLine();
                  theSequence.setCDSStart( Integer.parseInt( line.substring( line.indexOf("(") + 1 , line.indexOf(")") ) ) );
                  //set cdsStop
                  line = line.substring( line.indexOf(")") + 1 );
                  theSequence.setCDSStop( Integer.parseInt( line.substring( line.indexOf("(") + 1 , line.indexOf(")") ) ) );
               }
               line = sc.nextLine();
            }
            //scanner is at end of line <400>, read in the sequence...
            theSequence.fillSequenceArray(sc);
         }
      }
   return theSequence;   
   }

}