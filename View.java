import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javax.swing.Timer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * MULTITAPE TURING MACHINE SIMULATOR 
 * 
 * Description: This prototype is a 3-taped turing machine simulator implemented with Java Swing. In this simulator,
 * the user can input a string and it will give a verdict whether to reject or accept the given string. For state t-
 * ransition, it uses text file to read the contents of the transition.
 *  
 * Names of Contributors:
 * 1. Carlos Kristoffer Gutierrez
 * 2. Elijah Nicolo Rosario
 * 3. Luis Rafayel Jaime
 * 
 */

 /* 
 
    Sources:

    https://gist.github.com/NikolasTzimoulis/2846116 

    https://introcs.cs.princeton.edu/java/52turing/TuringMachine.java.html

    https://github.com/leozulfiu/multitape-turing-machine

    https://github.com/kiriloman/Multitape-Non-Deterministic-Turing-Machine

    https://github.com/HattaliAhmed/Turing-Machine-Simulator

    https://github.com/tychon/turing_simulator

    https://github.com/marcio012/turingMachine

    https://github.com/carlosdg/TuringMachineSimulator

    https://github.com/EmilianoG6/TuringMachine

    https://www.w3schools.com/java/java_files_read.asp

    https://stackoverflow.com/questions/1006611/java-swing-timer

 */

public class View {

    private static final int DELAY = 500;
    
    JFrame mainFrame;
    private JPanel tp_cnt;
    private JPanel mainPane;
    private JPanel input_cnt;
    private JPanel alg_cnt;
    private JTextField input;
    private JLabel inputLbl;
    private JPanel input_pnl;
    
    //buttons
    private JButton run;
    private JButton pause;
    private JButton step;
    private JButton rst;
    
    //tapes
    private JLabel tape_1;
    private JScrollPane tape1_pne;
    private JLabel tape_2;
    private JScrollPane tape2_pne;
    private JLabel tape_3;
    private JScrollPane tape3_pne;
    
    //procedure variables
    private JLabel procedureLbl;
    private JTextArea procedureTxt;
    private JScrollPane pcd_pne;

    private boolean isReject = false;
    private MultitapeTM tm;



    
    public View() throws FileNotFoundException, IOException{
        
        this.mainFrame = new JFrame("Multitape Turing Machine Simulator");
        this.mainPane = new JPanel();
        this.tp_cnt = new JPanel();
        this.input_cnt = new JPanel();
        this.alg_cnt = new JPanel();
        this.input = new JTextField(10);
        this.input.setText("aabbcc");
        this.inputLbl = new JLabel("Input");
        this.input_pnl = new JPanel();
        
        //declaration of tapes
        this.tape_1 = new JLabel();
        this.tape_2 = new JLabel();
        this.tape_3 = new JLabel();
        
        //declaration of procedure container
        this.procedureLbl = new JLabel("Procedure");
        this.procedureTxt = new JTextArea();
        
        //init button
        this.run = new JButton("Run");
        this.pause = new JButton("Pause");
        this.step = new JButton("Step");
        this.rst = new JButton("Reset");

        //initializing main frame
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setLayout(new BorderLayout(5,5));
        this.mainFrame.setSize(800 , 720);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setResizable(false);
        
        //initializing main pane
        this.mainPane.setLayout(null);
        this.mainPane.setBackground(Color.gray);
        this.mainPane.setBounds(0, 0, 800, 720);
        
        //initializing tape container
        this.tp_cnt.setLayout(null);
        this.tp_cnt.setBackground(Color.lightGray);
        this.tp_cnt.setBounds(10, 10, 765, 250);
        
        //initializing input container
        this.input_cnt.setLayout(null);
        this.input_cnt.setBackground(Color.lightGray);
        this.input_cnt.setBounds(398, 270, 378, 400);
        
        //initializing procedure container
        this.alg_cnt.setLayout(null);
        this.alg_cnt.setBackground(Color.lightGray);
        this.alg_cnt.setBounds(10, 270, 378, 400);
        
        //add the panel containers to main panel
        this.mainPane.add(tp_cnt);
        this.mainPane.add(input_cnt);
        this.mainPane.add(alg_cnt);
        
        //initialize input box
        this.input.setLayout(null);
        this.input.setBounds(15, 77, 348, 47);
        this.input.setFont(new Font("Courier New", Font.PLAIN, 20)); 
        this.input.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        //initialize input label
        this.inputLbl.setLayout(null);
        this.inputLbl.setBounds(0, 0, 378, 67);
        this.inputLbl.setFont(new Font("Courier New", Font.PLAIN, 30));
        this.inputLbl.setBackground(Color.lightGray);
        this.inputLbl.setHorizontalAlignment(SwingConstants.CENTER);
        
        //initialize run button
        this.run.setLayout(null);
        this.run.setBounds(15, 134, 348, 47);
        this.run.setBackground(Color.lightGray);
        this.run.setForeground(Color.black);
        this.run.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.run.setFont(new Font("Courier New", Font.PLAIN, 30));

        //add action listener
        this.run.addActionListener((ActionEvent e) -> {
            try {

                this.run.setEnabled(false);
                run();
                
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        
        //add button hover
        this.run.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                run.setBackground(Color.orange);
                run.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                run.setBackground(Color.lightGray);
                run.setForeground(Color.black);
            }
        });
        
        //initialize pause button
        this.pause.setLayout(null);
        this.pause.setBounds(15, 201, 348, 47);
        this.pause.setBackground(Color.lightGray);
        this.pause.setForeground(Color.black);
        this.pause.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.pause.setFont(new Font("Courier New", Font.PLAIN, 30));

        
        //add button hover
        this.pause.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                pause.setBackground(Color.orange);
                pause.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pause.setBackground(Color.lightGray);
                pause.setForeground(Color.black);
            }
        });
        
        //initialize step button
        this.step.setLayout(null);
        this.step.setBounds(15, 268, 348, 47);
        this.step.setBackground(Color.lightGray);
        this.step.setForeground(Color.black);
        this.step.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.step.setFont(new Font("Courier New", Font.PLAIN, 30));
        
        //add button hover
        this.step.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                step.setBackground(Color.orange);
                step.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                step.setBackground(Color.lightGray);
                step.setForeground(Color.black);
            }
        });
        
        //initialize reset button
        this.rst.setLayout(null);
        this.rst.setBounds(15, 201, 348, 47);
        this.rst.setBackground(Color.lightGray);
        this.rst.setForeground(Color.black);
        this.rst.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.rst.setFont(new Font("Courier New", Font.PLAIN, 30));
        
        //add button hover
        this.rst.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rst.setBackground(Color.orange);
                rst.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rst.setBackground(Color.lightGray);
                rst.setForeground(Color.black);
            }
        });
        
        //add event listener to reset for displaying inputs
        this.rst.addActionListener((ActionEvent e) -> {
            String txt = input.getText();
            tape_1.setText(txt);
            mainPane.setBackground(Color.GRAY);
            tape_2.setText("");
            tape_3.setText("");

        });

        //add components to input container
        this.input_cnt.add(inputLbl);
        this.input_cnt.add(input);
        this.input_cnt.add(run);
        // this.input_cnt.add(pause);
        // this.input_cnt.add(step);
        this.input_cnt.add(rst);
        
        /*-----------------------------------------------*/
        
        //initialize tapes
        this.tape_1.setBackground(Color.WHITE);
        this.tape_1.setFont(new Font("Courier New", Font.PLAIN, 20));
        this.tape_1.setHorizontalAlignment(SwingConstants.CENTER);
        this.tape1_pne = new JScrollPane(tape_1);
        this.tape1_pne.setBounds(20, 10, 725, 43);
        this.tape1_pne.setBackground(Color.LIGHT_GRAY);
        this.tape1_pne.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); 
        this.tape1_pne.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.tape_2.setBackground(Color.WHITE);
        this.tape_2.setFont(new Font("Courier New", Font.PLAIN, 20));
        this.tape_2.setHorizontalAlignment(SwingConstants.CENTER);
        this.tape2_pne = new JScrollPane(tape_2);
        this.tape2_pne.setBounds(20, 83, 725, 43);
        this.tape2_pne.setBackground(Color.LIGHT_GRAY);
        this.tape2_pne.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); 
        this.tape2_pne.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.tape_3.setBackground(Color.WHITE);
        this.tape_3.setFont(new Font("Courier New", Font.PLAIN, 20));
        this.tape_3.setHorizontalAlignment(SwingConstants.CENTER);
        this.tape3_pne = new JScrollPane(tape_3);
        this.tape3_pne.setBounds(20, 160, 725, 43);
        this.tape3_pne.setBackground(Color.LIGHT_GRAY);
        this.tape3_pne.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); 
        this.tape3_pne.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        //add tapes to tape container
        this.tp_cnt.add(tape1_pne);
        this.tp_cnt.add(tape2_pne);
        this.tp_cnt.add(tape3_pne);
        
        /*-----------------------------------------------*/
        
        //initialize procedure label
        this.procedureLbl.setLayout(null);
        this.procedureLbl.setBounds(0, 0, 378, 67);
        this.procedureLbl.setFont(new Font("Courier New", Font.PLAIN, 30));
        this.procedureLbl.setBackground(Color.lightGray);
        this.procedureLbl.setHorizontalAlignment(SwingConstants.CENTER);
        
        //initialize displaying of text for procedures
        this.procedureTxt.setFont(new Font("Courier New", Font.PLAIN, 18));
        this.procedureTxt.setLineWrap(true);
        this.procedureTxt.setWrapStyleWord(true);
        this.procedureTxt.setBounds(15, 77, 348, 305);
        this.pcd_pne = new JScrollPane(procedureTxt);
        this.pcd_pne.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
        this.pcd_pne.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.pcd_pne.setBounds(15, 77, 348, 305);

        //display contents of files to the procedures
        try(BufferedReader sc = new BufferedReader(new FileReader("procedure_test.txt"))){
            String data;
            StringBuilder contents = new StringBuilder();

            while((data = sc.readLine()) != null){
                contents.append(data).append("\n");
            }

            this.procedureTxt.setText(contents.toString());
        } 
        
        //add elements to procedure container
        this.alg_cnt.add(procedureLbl);
        this.alg_cnt.add(pcd_pne);
        
        //add the main panel to main frame
        this.mainFrame.add(mainPane);
        this.mainFrame.setVisible(true);
        
    }
    

    public void run() throws IOException{

        //initialize tm machine
        tm = new MultitapeTM();
        
        //read definition file
        tm.readFile();

        //initialize input
        char[] temp = this.input.getText().toCharArray();

        //display to the tape container
        String txt = input.getText();
        this.tape_1.setText(txt);

        System.out.println("Input: " + Arrays.toString(temp));

        ArrayList<Character> input = new ArrayList<>();

        //fill input
        for(char c : temp){
            input.add(c);
        }

        System.out.print("ArrayList Input: ");
        for(char c : input){
            System.out.print(c + " ");
        }

        System.out.println("");


        tm.getTapes().get(0).setInput(input);

        System.out.print("Tape Input: ");

        for(char c : tm.getTapes().get(0).getInput()){
            System.out.print(c + " ");
        }

        System.out.println("");

        isReject = false;

        Timer timer = new Timer(DELAY, new ActionListener() {

            //initialize the state of machine
            String currState = tm.getStart();
            String nextState = "";
            Transition currTrans = new Transition();
          
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Transition> stateTrans = tm.getTransitionAt(currState);

                for(Transition trans : stateTrans) {
                    System.out.println(trans);
                }

                //retrieve tape head
                int[] currentHead = new int[3];

                System.out.print("Current Tape Heads: ");
                for(int i = 0; i < currentHead.length; i++){
                    currentHead[i] = tm.getTapes().get(i).getHead();
                    System.out.print(currentHead[i] + " ");
                }

                System.out.println("");

                // //need to get the current symbol that is being read in each tape
                char[] currSymbol = new char[3];


                System.out.print("Current Symbol: ");
                // int tape2size = tm.getTapes().get(0).getInput().size();

                for(int i = 0; i < currSymbol.length; i++){
                    //if current head is out of bounds, add null to array list
                    System.out.println("Size of tape: " + tm.getTapes().get(i).getInput().size());
                    if(tm.getTapes().get(i).getInput().size() == currentHead[i]){
                        tm.getTapes().get(i).getInput().add(currentHead[i], '_');
                        System.out.println("Will add blank");
                    } else if(currentHead[i] == -1){
                        tm.getTapes().get(i).getInput().add(0, '_');
                    }

                    if(currentHead[i] == -1){
                        currSymbol[i] = tm.getTapes().get(i).getInputAt(currentHead[i] + 1);
                    } else{
                        currSymbol[i] = tm.getTapes().get(i).getInputAt(currentHead[i]);
                    }
                }

                System.out.println("");

                //iterate through the current state transition
                int countTrans = 0;
                for(Transition transition: stateTrans){
                    if(Arrays.equals(currSymbol, transition.getRead())){
                        currTrans = transition;
                        countTrans++;
                    } 
                }

                for(int i = 0; i < 3; i++){
                    // tm.getTapes().get(i).getInput().set(currentHead[i], currTrans.getWrite()[i]);
                    System.out.println("Tape " + (i+1) + ": " + tm.getTapes().get(i).getInput());
                }

                //determine whether it is rejected or not
                if(countTrans == 0){
                    isReject = true;
                }

                //end the while loop if it is a dead state
                if(isReject){
                    System.out.println("Rejected");
                    // break;
                }

                //set next state
                nextState = currTrans.getNext();

                System.out.println("Current Transition at State X: " + currTrans);

                // write new symbols to the tape
                for(int i = 0; i < 3; i++){
                    //convert arraylist to string
                    StringBuilder sb = new StringBuilder();

                    if(currentHead[i] == -1){
                        tm.getTapes().get(i).getInput().set(currentHead[i] + 1, currTrans.getWrite()[i]);

                        for(Character ch : tm.getTapes().get(i).getInput()){
                            sb.append(ch);
                        }

                        switch(i+1){
                            case 1:
                                tape_1.setText(setColorAt(sb.toString(), currentHead[0] + 1, "green"));
                                break;
                            case 2:
                                tape_2.setText(setColorAt(sb.toString(), currentHead[1] + 1, "green"));
                                break;
                            case 3:
                                tape_3.setText(setColorAt(sb.toString(), currentHead[2] + 1, "green"));
                                break;
                        }
                    } else{
                        tm.getTapes().get(i).getInput().set(currentHead[i], currTrans.getWrite()[i]);

                        for(Character ch : tm.getTapes().get(i).getInput()){
                            sb.append(ch);
                        }

                        switch(i+1){
                            case 1:
                                tape_1.setText(setColorAt(sb.toString(), currentHead[0], "green"));
                                break;
                            case 2:
                                tape_2.setText(setColorAt(sb.toString(), currentHead[1], "green"));
                                break;
                            case 3:
                                tape_3.setText(setColorAt(sb.toString(), currentHead[2], "green"));
                                break;
                        }
                    }
                    
                    System.out.println("Tape " + (i+1) + ": " + tm.getTapes().get(i).getInput());
                }

                for(int i = 0; i < 3; i++){
                    System.out.println("CurrTrans Move: " + currTrans.getMove()[i]);
                }

                //move tape head according to the current transition
                for(int i = 0; i < 3; i++){
                    switch(currTrans.getMove()[i]){
                        //right
                        case 'R':
                            currentHead[i] += 1;
                            break;
                        case 'L':
                            currentHead[i] -= 1;
                            break;
                        case 'S':
                            break;

                        default: 
                            System.out.println("Error Finding Move");
                            break;
                    }       
                }

                System.out.println("New Current Head: " + Arrays.toString(currentHead));

                System.out.print("From " + currState + " to " + nextState);
                System.out.println("");
                //initialize new current and next state
                currState = nextState;

                //set tape heads
                for(int i = 0; i < 3; i++){
                    tm.getTapes().get(i).setHead(currentHead[i]);
                }

                if (currState.equals(tm.getAccept()) || isReject) {

                    run.setEnabled(true);

                    if(isReject){
                        mainPane.setBackground(Color.decode("#8B0000"));
                    } else{
                        System.out.println("isRejected: " + isReject);
                        mainPane.setBackground(Color.GREEN);
                    }

                    //resets the verdict
                    isReject = false;


                    ((Timer) e.getSource()).stop(); 
                    
                }
        
            }

        });

        timer.start();

        System.out.println("Isreject: " + isReject);
    }

    private static String setColorAt(String input, int position, String color) {
        StringBuilder stringBuilder = new StringBuilder("<html>");

        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            if (i == position) {
                stringBuilder.append("<font color='").append(color).append("'><b>").append(character).append("</b></font>");
            } else {
                stringBuilder.append(character);
            }
        }

        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }
    public static void main(String[] args) throws IOException  {
        View view = new View();
        
        view.mainFrame.setVisible(true);

    }
    
}