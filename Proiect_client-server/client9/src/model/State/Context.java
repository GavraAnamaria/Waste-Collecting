package model.State;

public class Context {
    private State state;

    public Context(){
        state = null;
    }

    public void setState(State state){
        this.state = state;
    }

    public void doAction() {
        state.doAction(this);
    }
}