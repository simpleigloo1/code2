/** SortGUI main class to handle gui components. Uses a box layout for fixed spacing.
* Contains a panel for the graphics depiction of sorted data. Boxes are used to hold a text label and a
* group of radio buttons for selection of the sort to be performed.

* @version (1.0)
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SortGUI extends JPanel implements ActionListener
{
    private static boolean sorting = true;			//whether a sort is running
    private static boolean windowOpen = true;		// whether the user has quit
    private static int sortType = 0;				//hacky type of sort being done
    private static final int PWIDTH = 500, PHEIGHT = 500;  //dimension of drawing window
    private static Sort sortObj;					//the Sort object
    private Thread sort;							//the thread for the Sort object
    private static JLabel swapLabel;				//the label for metric counts
    
    
    /**
     * Builds the GUI.
     */
    public SortGUI()
    { 
    //You must build this method
        
    }
    
    /**
     * Main loop
     * @param sortType which sort is active or selected 
     */
    public void run(int sortType)
    {
       sortObj = new Sort(sortType);			//create the Sort object
       Thread sort = new Thread(sortObj);		//put the Sort object in a thread
       sort.start(); 							//start the thread
       
       while (sorting && windowOpen){			//normal execution (user hasn't quit and sort has not been stopped)
            Utilities.pause(0.1); // so we don't overwhelm the cpu
            repaint();
            swapLabel.setText("The number of swaps = " + sortObj.getCount());
       }
       
       //if user selects a new sort before previous completes allow the sort thread to join this one, 
       //to clean up resources
       try {
            sortObj.stop();
            sort.join();
            
       }
       catch(InterruptedException ex) {
            System.out.println("Thread weirdness");
       }
       
    }    
    
     
    /** Listens to the radio buttons. */
    public void actionPerformed(ActionEvent e)
    {
        //you must build this method
    }
 
 
    /** Creates the Window and gets everything off and running */
    public static void main(String args[])
    {
       //make the Frame
        JFrame window = new JFrame("Sort Demo");
        
        //add a window listener so it responds to the window close event
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                 sorting = false;            //used for thread cleanup
                 windowOpen = false;		 //used for thread execution
                 System.exit(0);
                
            }
        });
        
        //make the content pane for the frame
        SortGUI gui = new SortGUI();
        window.setContentPane(gui);
        window.pack();
        window.setLocation(50, 50);
        window.setVisible(true);
        
        //run loop for this thread
        while(windowOpen){
          gui.run(sortType);
          Utilities.pause(0.1);
          
          //when an old sort is stopped, sorting is set to false so the sort
          //thread can be cleaned up. if the user has not quit then we
          //want to continue sorting using the new sortType. So sorting is
          //turned back on below
          sorting = true;
        }
    }
 
 
public class DrawPane extends JPanel
{
   
    /**
     * Constructor 
     */
    public DrawPane()
    {
        //drawing panel for sort data, standard setup
         //you must build this method        
    }
    
     /**
     * Helper method to draw array data.
     */
    public void paint(Graphics g)
    {
        //erases old sort data
        g.setColor(Color.white);
        g.fillRect(0, 0, PWIDTH, PHEIGHT);
        
        //if graphics repaint beats sortObj creation then a null pointer exception occurs,
        //so do nothing and return in that case.
        if(sortObj == null) return;
        
        //paint small rectangles for sort data
        int [] temp = sortObj.getData();
        for (int i = 0; i < temp.length; i++)
        {
           g.setColor(Color.black);
           g.drawRect(temp[i],i,1,1); 
        } 
    }
 }        

 }
 
 