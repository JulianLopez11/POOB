package src.domain;
import java.util.List;

/**
 * Entrenador defensivo que prioriza estrategias de protección y mejora de estadísticas defensivas
 */
public class DefensiveTrainer extends MachineTrainer { ;
    public DefensiveTrainer(String name) {
        super(name);
    }

    @Override
    public Movement decide(Pokemon target){ //FALTA
        return null;
    }
}
