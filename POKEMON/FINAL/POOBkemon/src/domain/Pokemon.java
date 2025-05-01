package src.domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa un Pokémon con sus características y comportamientos
 */
public class Pokemon {
    private static String name;
    private final int level = 100;
    private int ps;
    private int maxPs;
    private int currentPs;
    private int attack;
    private int defense;
    private int velocity;
    private PokemonType principalType;
    private PokemonType secondaryType;
    private String state; // normal, paralizado, envenenado, dormido, quemado, congelado
    private List<String> movements;
    private int criticalHitRate;

    public Pokemon(String name, PokemonType principalType) {
        this.name = name;
        this.principalType = principalType;
        this.secondaryType = null;
        this.state = "normal";
        this.movements = new ArrayList<>(4);
        this.criticalHitRate = 6; // 1/16 probabilidad (valor típico en los juegos)

        // Estadísticas base ara un Pokémon nivel 100
        Random random = new Random();
        this.maxPs = 200 + random.nextInt(100); // Entre 200-300 PS
        this.currentPs = maxPs;
        this.attack = 100 + random.nextInt(50);  // Entre 100-150 de ataque
        this.defense = 100 + random.nextInt(50); // Entre 100-150 de defensa
        this.velocity = 100 + random.nextInt(50); // Entre 100-150 de velocidad
    }

    /**
     * Constructor para crear un Pokémon con tipo secundario
     * @param name Nombre del Pokémon
     * @param principalType Tipo principal del Pokémon
     * @param secondaryType Tipo secundario del Pokémon
     */
    public Pokemon(String name, PokemonType principalType, PokemonType secondaryType) {
        this(name, principalType);
        this.secondaryType = secondaryType;
    }

    /**
     * Método para que el Pokémon pierda puntos de salud
     * @param damage Cantidad de daño recibido
     * @return Puntos de salud restantes
     */
    public int losePS(int damage) {
        this.currentPs = Math.max(0, this.currentPs - damage);
        return this.currentPs;
    }

    /**
     * Calcula el daño que este Pokémon puede hacer a otro
     * @param target Pokémon objetivo del ataque
     * @param movementIndex Índice del movimiento a utilizar
     * @return Cantidad de daño calculado
     */
    public int attack(Pokemon target, int movementIndex) {
        if (movementIndex < 0 || movementIndex >= movements.size()) {
            return 0;
        }

        Random random = new Random();
        double typeEffectiveness = Type.getTotalEffectiveness(
                getMovementType(movementIndex),
                target.getPrincipalType(),
                target.getSecondaryType()
        );

        // Fórmula simplificada de daño para Pokémon nivel 100
        double baseDamage = ((42 * this.attack / target.getDefense()) / 50.0) + 2;

        // Factor de golpe crítico (1/16 de probabilidad por defecto)
        double criticalHit = 1.0;
        if (random.nextInt(16) < 1) {
            criticalHit = 1.5;
        }

        // Factor aleatorio entre 0.85 y 1.0 (como en los juegos)
        double randomFactor = 0.85 + (random.nextDouble() * 0.15);

        // Bono STAB (Same Type Attack Bonus)
        double stab = 1.0;
        PokemonType movementType = getMovementType(movementIndex);
        if (movementType.equals(this.principalType) ||
                (this.secondaryType != null && movementType.equals(this.secondaryType))) {
            stab = 1.5;
        }

        // Cálculo final del daño
        int finalDamage = (int) Math.round(baseDamage * typeEffectiveness * criticalHit * randomFactor * stab);

        // Los ataques siempre hacen al menos 1 de daño si no hay inmunidad
        return typeEffectiveness == 0.0 ? 0 : Math.max(1, finalDamage);
    }

    /**
     * Obtiene el tipo de un movimiento específico
     * @param movementIndex Índice del movimiento
     * @return Tipo del movimiento
     */
    private PokemonType getMovementType(int movementIndex) {
        // En una implementación real, cada movimiento tendría su propio tipo
        // Para simplificar, asumimos que el movimiento es del tipo principal del Pokémon
        return this.principalType;
    }

    /**
     * Cura al Pokémon restaurando todos sus PS
     */
    public void heal() {
        this.currentPs = this.maxPs;
        this.state = "normal";
    }

    /**
     * Cura parcialmente al Pokémon
     * @param amount Cantidad de PS a restaurar
     */
    public void heal(int amount) {
        this.currentPs = Math.min(this.maxPs, this.currentPs + amount);
    }

    /**
     * Cambia el estado del Pokémon
     * @param newState Nuevo estado
     * @return true si el cambio fue exitoso, false si estaba protegido
     */
    public boolean changeState(String newState) {
        // Algunos tipos son inmunes a ciertos estados
        if ((newState.equals("quemado") && (principalType.equals("Fuego") ||
                (secondaryType != null && secondaryType.equals("Fuego")))) ||
                (newState.equals("paralizado") && (principalType.equals("Eléctrico") ||
                        (secondaryType != null && secondaryType.equals("Eléctrico")))) ||
                (newState.equals("envenenado") && (principalType.equals("Veneno") ||
                        (secondaryType != null && secondaryType.equals("Veneno"))))) {
            return false;
        }

        this.state = newState;
        return true;
    }

    /**
     * Aplica el efecto del estado actual al final del turno
     */
    public void applyStateEffect() {
        switch (this.state) {
            case "envenenado":
                losePS(maxPs / 8); // Pierde 1/8 de los PS máximos
                break;
            case "quemado":
                losePS(maxPs / 16); // Pierde 1/16 de los PS máximos
                break;
            // Otros efectos según estado
        }
    }

    /**
     * Añade un movimiento al Pokémon
     * @param movement Nombre del movimiento
     * @return true si se pudo añadir, false si ya tiene 4 movimientos
     */
    public boolean addMovement(String movement) {
        if (movements.size() >= 4) {
            return false;
        }
        movements.add(movement);
        return true;
    }

    /**
     * Reemplaza un movimiento existente
     * @param oldMovementIndex Índice del movimiento a reemplazar
     * @param newMovement Nuevo movimiento
     */
    public void replaceMovement(int oldMovementIndex, String newMovement) {
        if (oldMovementIndex >= 0 && oldMovementIndex < movements.size()) {
            movements.set(oldMovementIndex, newMovement);
        }
    }

    /**
     * Verifica si el Pokémon está debilitado
     * @return true si está debilitado, false en caso contrario
     */
    public boolean isFainted() {
        return currentPs <= 0;
    }

    // Getters y setters

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentPs() {
        return currentPs;
    }

    public int getMaxPs() {
        return maxPs;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getVelocity() {
        return velocity;
    }

    public PokemonType getPrincipalType() {
        return principalType;
    }

    public PokemonType getSecondaryType() {
        return secondaryType;
    }

    public String getState() {
        return state;
    }

    public List<String> getMovements() {
        return new ArrayList<>(movements); // Devuelve una copia para evitar modificaciones no deseadas
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Establece las estadísticas del Pokémon directamente
     * @param ps Puntos de salud
     * @param attack Ataque
     * @param defense Defensa
     * @param velocity Velocidad
     */
    public void setStats(int ps, int attack, int defense, int velocity) {
        this.maxPs = ps;
        this.currentPs = ps;
        this.attack = attack;
        this.defense = defense;
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return name + " (Nivel " + level + ") - PS: " + currentPs + "/" + maxPs +
                " - Tipo: " + principalType +
                (secondaryType != null ? "/" + secondaryType : "");
    }
}

