package src.domain;
import java.util.*;

public class StatusAllEffects {
    private static final Map<String, StatusEffect> effects = new HashMap<>();

    static {
        effects.put("normal", new NormalEffect());
        effects.put("quemado", new BurnedEffect());
        effects.put("envenenado", new PoisonedEffect());
        effects.put("paralizado", new ParalyzedEffect());
        effects.put("congelado", new FrozenEffect());
        effects.put("dormido", new SleepEffect());
        effects.put("confuso", new ConfusedEffect());
        effects.put("atrapado", new TrappedEffect());
    }

    /**
     * Obtiene un efecto por su nombre
     * @param effectName Nombre del efecto
     * @return Instancia del efecto o null si no existe
     */
    public static StatusEffect getEffect(String effectName) {
        return effects.get(effectName);
    }

    /**
     * Verifica si existe un efecto con el nombre especificado
     * @param effectName Nombre del efecto
     * @return true si existe, false en caso contrario
     */
    public static boolean containsEffect(String effectName) {
        return effects.containsKey(effectName);
    }
}
