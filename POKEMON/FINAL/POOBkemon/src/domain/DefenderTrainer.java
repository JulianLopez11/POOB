package src.domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Entrenador defensivo que prioriza estrategias de protección y mejora de estadísticas defensivas
 */
public class DefenderTrainer extends MachineTrainer {

    private static final String name = "Bob";
    public DefenderTrainer(String name) {
        super(name);
    }

    public void decide(){
        List<Pokemon> pokemons = inventory.getAllPokemons();
        for(Pokemon pokemon: pokemons){
            int defense = pokemon.getDefense();
            if (defense > 130){
                team.add(pokemon);
            }
        }

        



    }
}
