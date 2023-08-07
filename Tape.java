import java.util.ArrayList;

public class Tape {
    private int head;
    private ArrayList<Character> input;

    public Tape(){
        this.head = 0;
        this.input = new ArrayList<>();
    }

    public Tape(int head, ArrayList<Character> input){
        this.head = head;
        this.input = input;
    }

    public Tape(int head) {
        this.head = head;
    }

    public int getHead(){
        return this.head;
    }

    public ArrayList<Character> getInput(){
        return this.input;
    }

    public char getInputAt(int index){
        char result;

        if(index >= 0 && index < input.size()){
            result = input.get(index);

        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        return result;
    }

    public void setHead(int head){
        this.head = head;
    }

    public void setInput(ArrayList<Character> input){
        this.input = input;
    }
}
