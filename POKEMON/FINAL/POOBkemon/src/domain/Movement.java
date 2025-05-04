package src.domain;

import java.io.Serializable;

/**
 * Clase que representa un movimiento o ataque de Pokémon
 */
public class Movement implements Serializable {
    private String name;
    private PokemonType type;
    private int power;
    private int accuracy;
    private int pp;
    private int maxPp;
    private String category;
    private String effectState;
    private int effectProbability;

    /**
     * Constructor para un movimiento sin efecto secundario
     * @param name Nombre del movimiento
     * @param type Tipo del movimiento
     * @param power Poder base
     * @param accuracy Precisión (de 0 a 100)
     * @param pp Puntos de poder (usos)
     * @param category Categoría ("Físico" o "Especial")
     */
    public Movement(String name, PokemonType type, int power, int accuracy, int pp, String category) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.maxPp = pp;
        this.category = category;
        this.effectState = null;
        this.effectProbability = 0;
    }

    /**
     * Constructor para un movimiento con efecto secundario
     * @param name Nombre del movimiento
     * @param type Tipo del movimiento
     * @param power Poder base
     * @param accuracy Precisión (de 0 a 100)
     * @param pp Puntos de poder (usos)
     * @param category Categoría ("Físico" o "Especial")
     * @param effectState Estado que puede provocar
     * @param effectProbability Probabilidad del efecto (de 0 a 100)
     */
    public Movement(String name, PokemonType type, int power, int accuracy, int pp, String category, String effectState, int effectProbability) {
        this(name, type, power, accuracy, pp, category);
        this.effectState = effectState;
        this.effectProbability = effectProbability;
    }

    /**
     * Reduce los PP del movimiento al usarlo
     * @return true si quedan PP, false si están agotados
     */
    public boolean use() {
        if (pp > 0) {
            pp--;
            return true;
        }
        return false;
    }

    public boolean canUse(){
        return pp > 0;
    }

    /**
     * Obtiene el nombre del movimiento
     * @return Nombre del movimiento
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el tipo del movimiento
     * @return Tipo del movimiento
     */
    public PokemonType getType() {
        return type;
    }

    /**
     * Obtiene el poder base del movimiento
     * @return Poder base
     */
    public int getPower() {
        return power;
    }

    /**
     * Obtiene la precisión del movimiento
     * @return Precisión (de 0 a 100)
     */
    public int getAccuracy() {
        return accuracy;
    }

    /**
     * Obtiene los PP actuales
     * @return PP actuales
     */
    public int getPp() {
        return pp;
    }

    /**
     * Obtiene los PP máximos
     * @return PP máximos
     */
    public int getMaxPp() {
        return maxPp;
    }

    /**
     * Obtiene la categoría del movimiento
     * @return Categoría ("Físico" o "Especial")
     */
    public String getCategory() {
        return category;
    }

    /**
     * Obtiene el efecto de estado que puede causar
     * @return Efecto de estado, o null si no tiene
     */
    public String getEffectState() {
        return effectState;
    }

    /**
     * Obtiene la probabilidad del efecto
     * @return Probabilidad (de 0 a 100), o 0 si no tiene efecto
     */
    public int getEffectProbability() {
        return effectProbability;
    }

    public double getMultiplicator(PokemonType attackerType, PokemonType defenderType) {
        double multiplicator = TypeEffectiveness.getEffectiveness(attackerType, defenderType);
        return multiplicator;
    }

}

