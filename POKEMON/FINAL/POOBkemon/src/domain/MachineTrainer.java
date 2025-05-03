package src.domain;
import java.util.*;

public abstract class MachineTrainer  extends Trainer{

    public MachineTrainer(String name) {
        super(name);

    }

    public abstract Movement decide(Pokemon target);

    public void doOtherThen(Pokemon target){
        Random random = new Random();
        int randomIndex = random.nextInt(4);
        if (randomIndex == 0) pokemonMovementDecide(target);
        if (randomIndex == 1) useItem(null);
        else {changePokemon();}
    }

    public void changePokemon(){
        ArrayList<Pokemon> stillAlive = inventory.getAlivePokemons(activePokemon);
        Random random = new Random();
        int choicesToPick = random.nextInt(stillAlive.size());
        activePokemon = stillAlive.get(choicesToPick);
    }

    public void useItem(Item item){
        item.use(activePokemon);
    }

    public Movement pokemonMovementDecide(Pokemon target){
        return activePokemon.aleatoryMovement(target);
    }
}

