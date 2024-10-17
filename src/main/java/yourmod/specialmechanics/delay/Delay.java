package yourmod.specialmechanics.delay;

public class Delay {
    public String description;
    public int timer;
    public Runnable execute;

    public Delay(String description, int timer, Runnable execute) {
        this.description = description;
        this.timer = timer;
        this.execute = execute;
    }
}
