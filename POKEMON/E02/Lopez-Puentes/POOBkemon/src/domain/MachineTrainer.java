package src.domain;
import java.util.*;

public abstract class MachineTrainer  extends Trainer{

    public MachineTrainer(String name) {
        super(name);

    }

    public abstract Movement decide(Pokemon target);

    /**
     * Decide que hacer si el pokemon activo no es el mejor para atacar al objetivo
     * @param target Pokemon objetivo
     */
    public void doOtherThen(Pokemon target){
        Random random = new Random();
        int randomIndex = random.nextInt(4);
        if (randomIndex == 0) pokemonMovementDecide(target);
        if (randomIndex == 1) useItem(null);
        else {changePokemon();}
    }


    /**
     * Cambia el pokemon activo por otro pokemon aleatorio de la lista de pokemons vivos
     */
    public void changePokemon(){
        ArrayList<Pokemon> stillAlive = inventory.getAlivePokemons(activePokemon);
        Random random = new Random();
        int choicesToPick = random.nextInt(stillAlive.size());
        activePokemon = stillAlive.get(choicesToPick);
    }

    /**
     * Usa un objeto en el pokemon activo
     * @param item Objeto a usar
     */
    public void useItem(Item item){
        item.use(activePokemon);
    }

    /**
     * Decide el movimiento a usar en base al pokemon objetivo
     * @param target Pokemon objetivo
     * @return Movimiento a usar
     */
    public Movement pokemonMovementDecide(Pokemon target){
        return activePokemon.aleatoryMovement(target);
    }
}

