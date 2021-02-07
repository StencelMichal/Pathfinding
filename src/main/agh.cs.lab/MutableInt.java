package agh.cs.lab;

public class MutableInt {

    private int value;

    public MutableInt(int value) {
        this.value = value;
    }

    public MutableInt(){
        this(0);
    }

    public void increment(){
        value++;
    }

    public void decrement(){
        value--;
    }

    public void change(int newValue){
        value = newValue;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
