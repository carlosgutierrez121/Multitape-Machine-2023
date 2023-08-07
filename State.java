import java.util.ArrayList;

public class State {
    private String name;
    private ArrayList<char[]> acceptedSymbols;

    public State(String name, ArrayList<char[]> acceptedSymbols){
        this.name = name;
        this.acceptedSymbols = acceptedSymbols;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<char[]> getAcceptedSymbols(){
        return this.acceptedSymbols;
    }
}
