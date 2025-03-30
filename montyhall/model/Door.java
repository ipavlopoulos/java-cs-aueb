package montyhall.model;

public class Door {
    // todo: can these fields be final?
    private boolean hasPrize;
    private boolean isOpen;

    public Door(boolean hasPrize) {
        this.hasPrize = hasPrize;
        this.isOpen = false;
    }

    public boolean hasPrize() {
        return hasPrize;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        isOpen = true;
    }
}