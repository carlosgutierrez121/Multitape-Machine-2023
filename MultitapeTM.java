import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos Gutierrez
 */

public class MultitapeTM {
    private String start;
    private String accept;
    private Set<String> states;
    private Set<String> symbols;
    private Set<Transition> transitions;
    // private Set<char[]> inputSymbols;
    private ArrayList<Tape> tapes;
    
    public MultitapeTM(){
        this.states = new HashSet<>();
        this.symbols = new HashSet<>();
        this.transitions = new HashSet<>();
        this.tapes = new ArrayList<>();
        this.start = "";
        this.accept = "";
        
        //initialize three tapes
        tapes.add(new Tape());
        tapes.add(new Tape());
        tapes.add(new Tape());
    }
    
    public void readFile() throws IOException {
        try(BufferedReader sc = new BufferedReader(new FileReader("procedure_test.txt"))){
            
            String section = "";
            String data;
            
            while((data = sc.readLine()) != null) {
                
                if(data.equals("states")){
                    section = "states";
                }
                
                if(data.equals("symbols")){
                    section = "symbols";
                }
                
                if(data.equals("transitions")){
                    section = "transitions";
                }

                
                switch(section){
                    case "states" -> {
                        //handle accept and reject states

                        if(!data.equals("states")){
                            
                            String[] parts = data.split(":");

                            switch(parts.length){
                                case 1: 
                                    states.add(data);

                                    break;
                                case 2:
                                    String type = parts[1].trim();

                                    states.add(parts[0].trim());

                                    if(type.equals("start"))
                                        this.start = parts[0].trim();
                                    else this.accept = parts[0].trim();

                                    break;
                            }
                        }
                        
                        
                        break;
                    }
                    
                    case "symbols" -> {
                        // add symbols
                        if(!data.equals("symbols")){
                            symbols.add(data);
                        }
                        
                        break;
                    }
                    
                    case "transitions" -> {
                        // add transitions
                        String[] parts = data.trim().split(" ");

                        if(!data.equals("transitions")){
                            
                            if(parts.length == 5){
                                //parsing of transitions
                                String currentState = parts[0];
                                char[] readSymbol = parts[1].toCharArray();
                                char[] writeSymbol = parts[2].toCharArray();
                                char[] moveDirection = parts[3].toCharArray();
                                String nextState = parts[4];
                                
                                //add transition to the set
                                transitions.add(new Transition(currentState, readSymbol, writeSymbol, moveDirection, nextState));
                            } else{
                                String errorMessage = "Invalid format: " + data;
                                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        
                        break;
                    }
                }
            }
        }
    }
    
    public Set<Transition> getTransitions(){
        return Collections.unmodifiableSet(transitions);
    }
    
    public Set<String> getStates(){
        return Collections.unmodifiableSet(states);
    }

    public Set<String> getSymbols(){
        return Collections.unmodifiableSet(symbols);
    }

    public ArrayList<Tape> getTapes(){
        return tapes;
    }
    
    public String getStart(){
        return start;
    }
    
    public String getAccept(){
        return accept;
    }

    public void setStart(String start){
        this.start = start;
    }

    public void setAccept(String accept){
        this.accept = accept;
    }

    public void setTransition(Set<Transition> transitions){
        this.transitions = transitions;
    }

    public void setStates(Set<String> states){
        this.states = states;
    }

    public void setSymbols(Set<String> symbols){
        this.symbols = symbols;
    }

    public void setTapes(ArrayList<Tape> tapes){
        this.tapes = tapes;
    }

    //take note that it may be a referenced type
    public Set<Transition> getTransitionAt(String state){
        Set<Transition> filter = new HashSet<>();

        for(Transition transition : transitions) {
            if(transition.getCurrent().equals(state)){
                filter.add(transition);
            }
        }

        return filter;
    }

    public void resetMachine(){
        this.states = new HashSet<>();
        this.symbols = new HashSet<>();
        this.transitions = new HashSet<>();
        this.tapes = new ArrayList<>();
        this.start = "";
        this.accept = "";
    }
    
    public void test(){
		states.forEach(System.out::println);
        symbols.forEach(System.out::println);
        transitions.forEach(System.out::println);
		
		System.out.println("start: " + start);
		System.out.println("accept: " + accept);
    }
}