import java.util.Arrays;

public class Transition {
    private String current;
    private char[] read;
    private char[] write;
    private char[] move;
    private String next;

    public Transition(){
        
    }
    
    public Transition(String current, char[] read, char[] write, char[] move, String next){
        this.current = current;
        this.read = read;
        this.write = write;
        this.move = move;
        this.next = next;
    }
    
    public String getCurrent(){
        return current;
    }
    
    public char[] getRead(){
        return read;
    }
    
    public char[] getWrite(){
        return write;
    }

    public char[] getMove(){
        return move;
    }
    
    public String getNext(){
        return next;
    }

    @Override
    public String toString() {
        return "Transition: " + current + " " + Arrays.toString(read) + " " + Arrays.toString(write) + " " + Arrays.toString(move) + " " + next;
    }
}   