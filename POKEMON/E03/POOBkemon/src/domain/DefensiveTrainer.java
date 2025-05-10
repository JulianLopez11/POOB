package src.domain;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
/**
 * Entrenador defensivo que prioriza estrategias de protección y mejora de estadísticas defensivas
 */
public class DefensiveTrainer extends MachineTrainer { ;
    public DefensiveTrainer(String name,Color color) {
        super(name,color);
    }

    /*
     * Su enfoque va principalmente a la defensa.
     * Utiliza movimientos que potencian las estadísticas de
     * defensa y/o defensa especial, que brindan protección contra ataques
     * rivales o que bajan las estadísticas de ataque y/o ataque especial del jugador rival.
     */
    @Override
    public Movement decide(Pokemon target){ //FALTA
        ArrayList<AttributeMovement> movementsPokemon = inventory.getPokemons().get(activePokemon.getName()).getMovementsGiveDefense();
        Movement bestMovementDefensive = null;
        int status = 0;
        for (int i = 0; i < movementsPokemon.size(); i++){
            if (movementsPokemon.get(i).getStateTo().get("Defense") > status && movementsPokemon.get(i).getPP() > 0){
                status = movementsPokemon.get(i).getStateTo().get("Defense");
                bestMovementDefensive = movementsPokemon.get(i);
            }
        }

        if (bestMovementDefensive != null){
            return bestMovementDefensive;
        }
        else{
            doOtherThen(target);
            return null;
        }
    }
}
