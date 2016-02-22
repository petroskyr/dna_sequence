/*
 *Reverse.java
 *
 *Written by Ryan Petrosky (petrosky.ryan@gmail.com)
 *November 5, 2014
 *
 *Reads in a file, creates Sequence objects, and
 *generates a formated output file
 *
 *sample input:  12345_SEQ_FINAL_6789-10-11.txt
 *sample output: 12345_SEQ_RAW_madebyryan.txt
 *
 *The user may override the output name convention by 
 *entering the output file name as the second command
 *line argument, if desired
 *
 *Dependencies:
 *  Sequence.java
 *  ReverseSequence.java
*/


import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;

public class ReverseMain {

   //constants for the user interface
   private static final Color BG1 = new Color(65, 65, 65);
   //private static final Color BG2 = new Color(100, 100, 100);
   private static final Color FG = new Color( 230, 230, 230 );
   private static final Color BAD = new Color(200, 50, 50);
   private static final Color GOOD = new Color(50, 200, 50);
   private static final Dimension MY_WELCOME_DIM = new Dimension(600, 70);
   private static final Dimension MY_FIRST_COL_DIM = new Dimension(200, 35);
   private static final Dimension MY_SECOND_COL_DIM = new Dimension(400, 35);
   //private static final Insets MENU_INS = new Insets( 5, 5, 5, 5);
   
   //enables the user to open the Help file from the menu
   private static void displayAboutText(){
      try {
         JTextArea ta = new JTextArea(50,60);
         ta.read(new FileReader("About.txt"), null);
         ta.setEditable(false);
         JOptionPane.showMessageDialog(null, new JScrollPane(ta) ,
                  "Reverse DNA Sequence - About" , JOptionPane.PLAIN_MESSAGE);
         //remove icon, change title
         
      } catch (IOException ioe) {
         ioe.printStackTrace();
      }
   }

   //starts the program
   public static void main(String[] args) throws FileNotFoundException, IOException {
      
//build user interface---------------------------------------------------------------------------------------------Build GUI 
      //create main program frame
      JFrame frame = new JFrame("Reverse DNA Sequence");
      frame.setLayout(new GridBagLayout() );//allows formatting of GUI layout
      GridBagConstraints c = new GridBagConstraints();//will be added with each child to frame (not menu though)
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets behaviour when frame is closed
      
      //set up menu
      JMenuBar myMenu = new JMenuBar();//set up menu bar
      //"About"
      JMenuItem menuItemAbout = new JMenuItem("About");
      menuItemAbout.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_A ,
                                 java.awt.event.InputEvent.ALT_DOWN_MASK) );//Keystroke ALT+A
      menuItemAbout.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                  displayAboutText();
            }
      });
      menuItemAbout.setBackground(BG1);
      menuItemAbout.setForeground(FG);//sets menu item text color
      myMenu.add(menuItemAbout);
      frame.setJMenuBar(myMenu);//places menu in frame
      
      //create title label (inner window partition)
      JLabel myLabel = formatJLabel("Welcome to Reverse DNA Sequence",MY_WELCOME_DIM);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      c.gridwidth = 2;
      c.weightx = 1;
      c.weighty = 1;
      frame.add(myLabel, c);
      //create opening label
      JLabel opening = formatJLabel("Reading:",MY_FIRST_COL_DIM);
      opening.setHorizontalAlignment(4);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 1;
      c.insets = new Insets(0,0,0,15);
      c.gridwidth = 1;
      c.weightx = 1;
      c.weighty = 0;
      frame.add(opening, c);
      //create creating label
      JLabel creating = formatJLabel("Creating:",MY_FIRST_COL_DIM);
      creating.setHorizontalAlignment(4);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 2;
      c.insets = new Insets(0,0,0,15);
      c.gridwidth = 1;
      c.weightx = 1;
      c.weighty = 0;
      frame.add(creating, c);
      //create location display label
      JLabel dipsLoc = formatJLabel("Location:",MY_FIRST_COL_DIM);
      dipsLoc.setHorizontalAlignment(4);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 3;
      c.insets = new Insets(0,0,0,15);
      c.gridwidth = 1;
      c.weightx = 1;
      c.weighty = 0;
      frame.add(dipsLoc, c);
      //create results label
      JLabel results = formatJLabel("Status:",MY_FIRST_COL_DIM);
      results.setHorizontalAlignment(4);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 4;
      c.insets = new Insets(0,0,0,15);
      c.gridwidth = 1;
      c.weightx = 1;
      c.weighty = 0;
      frame.add(results, c);
      //create input file label
      JLabel inputFileName = formatJLabel("",MY_SECOND_COL_DIM);
      inputFileName.setHorizontalAlignment(2);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = 1;
      c.insets = new Insets(0,0,0,0);
      c.gridwidth = 1;
      c.weightx = 1;
      c.weighty = 0;
      frame.add(inputFileName, c);
      //create output file  label
      JLabel outputFileName = formatJLabel("",MY_SECOND_COL_DIM);
      outputFileName.setHorizontalAlignment(2);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = 2;
      c.insets = new Insets(0,0,0,0);
      c.gridwidth = 1;
      c.weightx = 1;
      c.weighty = 0;
      frame.add(outputFileName, c);
      //create location  label
      JLabel location = formatJLabel("",MY_SECOND_COL_DIM);
      location.setHorizontalAlignment(2);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = 3;
      c.insets = new Insets(0,0,0,0);
      c.gridwidth = 1;
      c.weightx = 1;
      c.weighty = 0;
      frame.add(location, c);            
      //create status label
      JLabel status = formatJLabel("",MY_SECOND_COL_DIM);
      status.setHorizontalAlignment(2);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      c.gridy = 4;
      c.insets = new Insets(0,0,0,0);
      c.gridwidth = 1;
      c.weightx = 1;
      c.weighty = 0;
      frame.add(status, c);
      
      frame.setMinimumSize(new Dimension(700,200));
      frame.pack();//size the frame
      frame.setLocationRelativeTo(null);//center frame on screen
      frame.setVisible(true);//make it visible
      
      //make the FileChooser, initialize to Desktop for user ease
      JFileChooser fc = new JFileChooser();//"C:/Users/Ryan/Documents/Ryan's Documents/forMom/"
      FileNameExtensionFilter txtOnly = new FileNameExtensionFilter("TEXT FILES", "txt");
      fc.setFileFilter( txtOnly );//sets file type to text
      fc.setAcceptAllFileFilterUsed(false);//user cannot change file type
      fc.setSize(650, 500);//dimensions
      fc.setDialogTitle("Select the file to reverse sequence");//FileChooser Title
      fc.setApproveButtonText("Run");//FileChooser "Accept" button
//-------------------------------------------------------------------------------------------------------------Done (mostly) GUI      
      int returnVal = fc.showOpenDialog(frame);//places FileChooser in the frame
      
      if( returnVal == 0 ) {//then user has selected a file
         File inputFile = fc.getSelectedFile();//full file path
         String inputDesc = fc.getDescription(inputFile);//just file name
         inputFileName.setText( inputDesc );//update GUI with input file name
         String inputPath = inputFile.getAbsolutePath();//needed for output file
                  
         //attach scanner
         Scanner input = new Scanner( inputFile );
            
         //opens output file object
         String outputDesc = getOutputDesc( inputDesc );//build output name, for messages to user
         String outputPath = appendPath( inputPath , inputDesc );//makes proper file path
         File output = ReverseSequence.openOutputFile( outputPath );//open output file
         if ( output != null ) {
            outputFileName.setText(outputDesc);//update GUI w output file name
            location.setText(outputPath.substring(0, outputPath.indexOf(outputDesc)));
            status.setText("IN PROGRESS");
            //allows writing to the output file object
            Writer w = new FileWriter( output );      
      
            //read in-file to the out-file
            boolean isNotBusted = true;
            try {
               ReverseSequence.doWork( input , w );
            } catch (NoSuchElementException ex) {
               isNotBusted = false;
            } catch ( NullPointerException ex) {
               isNotBusted = false;
            } finally {
               if(! isNotBusted) {
                  status.setForeground(BAD);
                  status.setText("Unable to accept input format, check output for errors");
               }
            }
            //closes the writer, saving the changes to the output file.
            w.close();
            if(Sequence.getNumberOfSequences() == 0) {
               //text file not correct format
               output.delete();//delete empty output file
               outputFileName.setText("");
               
               status.setForeground(BAD);
               status.setText("Sequences not readable");
            } else if (isNotBusted) {
               //location.setText(outputPath.substring(0, outputPath.indexOf(outputDesc)));
               status.setForeground(GOOD);
               status.setText("COMPLETE");//update GUI
            }
         } else {
            //output file returned null
            results.setForeground(BAD);
            results.setText("PROGRAM TERMINATED:");//update GUI
            status.setText("The output file - " + outputDesc + " - already exists");
            //include this so user knows which directory the file already exists in
            //location.setText(outputPath.substring(0, outputPath.indexOf(outputDesc)));
         }
      } else {
         //user did not select a file
         results.setForeground(BAD);
         results.setText("PROGRAM TERMINATED:");//update GUI
         status.setText("No input file recieved");
      }
   }
   
   //creates the output file name
   private static String getOutputDesc(String s) {
      return ( ( s.contains("_") ) ? s.split("_")[0] : s.split("\\.")[0] ) + "_SEQ_RAW_madebyryan.txt";
   }
   
   //appends output path
   private static String appendPath(String fullPath, String fileName) {
      String ourName = fullPath.substring(0 , (fullPath.indexOf( fileName ) ) ) + getOutputDesc( fileName );
      //System.out.println( ourName );
      return ourName;
   }
   
   //builds the JLabels containing the text displayed to user
   private static JLabel formatJLabel(String s, Dimension d) {
      JLabel jl = new JLabel();
      jl.setOpaque(true);
      //jl.setBackground(BG2);
      jl.setForeground(BG1);
      jl.setPreferredSize(d);//defines window size
      jl.setText(s);
      jl.setVerticalAlignment(0);//top (the class constants are not working so use integers)
      jl.setHorizontalAlignment(0);//0==CENTER; 2==LEFT 4==RIGHT
      return jl;
   }
}