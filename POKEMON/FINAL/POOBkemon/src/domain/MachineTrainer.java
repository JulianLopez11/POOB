package src.domain;

public abstract class MachineTrainer  extends Trainer{

    public MachineTrainer(String name) {
        super(name);

    }

    public abstract void decide();

}
