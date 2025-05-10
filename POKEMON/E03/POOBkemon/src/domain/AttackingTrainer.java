package src.domain;
import java.awt.*;
import java.util.*;
public class AttackingTrainer extends MachineTrainer {
    public AttackingTrainer(String name,Color color) {
        super(name,color);
    }

    public Movement decide(Pokemon target){
        ArrayList<AttributeMovement> movementsPokemon = inventory.getPokemons().get(activePokemon.getName()).getMovementsGiveAttack();
        Movement bestAttackMovement = null;
        double possibleAttackMovement = 0;
        for (int i = 0; i < movementsPokemon.size(); i++){
            double attackMovement = movementsPokemon.get(i).getMultiplicator(target.getPrincipalType());
            if (possibleAttackMovement < attackMovement && movementsPokemon.get(i).getPP() > 0){
                possibleAttackMovement = attackMovement;
                bestAttackMovement = movementsPokemon.get(i);
            }
        }
        if (bestAttackMovement != null){
            return bestAttackMovement;
        }

        return activePokemon.aleatoryMovement(target);
    }
}
