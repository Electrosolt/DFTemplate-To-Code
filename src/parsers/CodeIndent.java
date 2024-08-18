package parsers;

public enum CodeIndent {

    INCREASE(1),
    DECREASE(-1),
    NONE(0);

    public final int value;

    CodeIndent(int val){
        this.value = val;
    }

}
