package src.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa un Pokémon con sus características y comportamientos
 */
public class Pokemon implements Serializable {
    private String name;
    private final int level = 100;
    private int maxPs;
    private int currentPs;
    private int attack;
    private int defense;
    private int velocity;
    private PokemonType principalType;
    private PokemonType secondaryType;
    private List<Movement> movements;
    private int criticalHitRate;
    private StatusEffect statusEffect;

    public Pokemon(String name, PokemonType principalType) {
        this.name = name;
        this.principalType = principalType;
        secondaryType = null;
        statusEffect = StatusAllEffects.getEffect("normal");
        movements = new ArrayList<>(4);
        criticalHitRate = 6;

        Random random = new Random();
        maxPs = 200 + random.nextInt(100);
        currentPs = maxPs;
        attack = 100 + random.nextInt(50);
        defense = 100 + random.nextInt(50);
        velocity = 100 + random.nextInt(50);
    }

    /**
     * Constructor para crear un Pokémon con tipo secundario
     *
     * @param name          Nombre del Pokémon
     * @param principalType Tipo principal del Pokémon
     * @param secondaryType Tipo secundario del Pokémon
     */
    public Pokemon(String name, PokemonType principalType, PokemonType secondaryType) {
        this(name, principalType);
        this.secondaryType = secondaryType;
    }

    /**
     * Calcula el daño que este Pokémon puede hacer a otro y aplica posibles efectos secundarios
     *
     * @param target        Pokémon objetivo del ataque
     * @return Cantidad de daño calculado
     */
    public int attack(Pokemon target, Movement movement){
        if (!movement.use()) {
            System.out.println("No quedan PP para este movimiento!");
            return 0;
        }
        Random random = new Random();
        if (movement.getAccuracy() > 0 && random.nextInt(100) >= movement.getAccuracy()) {
            System.out.println("¡El ataque de " + this.name + " falló!");
            return 0;
        }
        double typeEffectiveness = TypeEffectiveness.getTotalEffectiveness(
                getMovementType(movement),
                target.getPrincipalType(),
                target.getSecondaryType()
        );
        // Obtener el poder base directamente del movimiento
        int poderBase = movement.getPower();
        // ((2 * Nivel / 5 + 2) * Poder Base * (Ataque / Defensa) / 50 + 2) * Modificador
        double baseDamage = ((2.0 * level / 5.0 + 2.0) * poderBase * (attack / (double) target.getDefense()) / 50.0) + 2.0;
        // Factor de golpe crítico
        double criticalHit = 1.0;
        if (random.nextInt(16) < 1) {
            criticalHit = 1.5;
            System.out.println("¡Golpe crítico!");
        }
        // Factor aleatorio entre 0.85 y 1.0
        double randomFactor = 0.85 + (random.nextDouble() * 0.15);
        // Bono STAB (Same Type Attack Bonus)
        double stab = 1.0;
        PokemonType movementType = getMovementType(movement);
        if (movementType.equals(this.principalType) ||
                (secondaryType != null && movementType.equals(this.secondaryType))) {
            stab = 1.5;
        }
        // El modificador incluye: efectividad por tipo, crítico, STAB y factor aleatorio
        double modificador = typeEffectiveness * criticalHit * randomFactor * stab;
        int finalDamage = (int) Math.round(baseDamage * modificador);
        int damageDealt = typeEffectiveness == 0.0 ? 0 : Math.max(1, finalDamage);
        if (damageDealt > 0) {
            target.losePS(damageDealt);

            if (typeEffectiveness > 1.5) {
                System.out.println("¡Es super efectivo!");
            } else if (typeEffectiveness < 1.0 && typeEffectiveness > 0) {
                System.out.println("No es muy efectivo...");
            } else if (typeEffectiveness == 0) {
                System.out.println("No afecta a " + target.getName() + "...");
            }
            if (!target.isFainted() && movement.getEffectState() != null) {
                target.applyStatusEffect(movement.getEffectState(), movement.getEffectProbability());
            }
        }
        return damageDealt;
    }

    /**
     * Cura al Pokémon restaurando todos sus PS
     */
    public void heal() {
        currentPs = maxPs;
        statusEffect = StatusAllEffects.getEffect("normal");
    }

    /**
     * Cura parcialmente al Pokémon
     *
     * @param amount Cantidad de PS a restaurar
     */
    public void heal(int amount) {
        currentPs = Math.min(maxPs, currentPs + amount);
    }

    /**
     * Método para que el Pokémon pierda puntos de salud
     *
     * @param damage Cantidad de daño recibido
     * @return Puntos de salud restantes
     */
    public int losePS(int damage) {
        currentPs = Math.max(0, currentPs - damage);
        return currentPs;
    }

    /**
     * Añade un movimiento al Pokémon
     *
     * @param movement Objeto Movement que representa el movimiento
     * @return true si se pudo añadir, false si ya tiene 4 movimientos
     */
    public boolean addMovement(Movement movement) {
        if (movements.size() >= 4) {
            return false;
        }
        movements.add(movement);
        return true;
    }


    /**
     * Obtiene el tipo de un movimiento específico
     *
     * @param
     * @return Tipo del movimiento
     */
    private PokemonType getMovementType(Movement movement) {
        return movement.getType();
    }

    /**
     * Aplica el efecto del estado actual al final del turno
     *
     * @return true si el Pokémon puede actuar, false si está incapacitado
     */
    public boolean applyStateEffect() {
        if (statusEffect == null || "normal".equals(statusEffect.getName())) {
            return true;
        }
        return statusEffect.applyEffect(this);
    }

    /**
     * Reemplaza un movimiento existente
     *
     * @param oldMovementIndex Índice del movimiento a reemplazar
     * @param newMovement      Nuevo movimiento
     */
    public void replaceMovement(int oldMovementIndex, Movement newMovement) {
        if (oldMovementIndex >= 0 && oldMovementIndex < movements.size()) {
            movements.set(oldMovementIndex, newMovement);
        }
    }

    /**
     * Verifica si el Pokémon está debilitado
     *
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
        return (statusEffect != null && "quemado".equals(statusEffect.getName())) ?
                attack / 2 : attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getVelocity() {
        return (statusEffect != null && "paralizado".equals(statusEffect.getName())) ?
                velocity / 4 : velocity;
    }

    public PokemonType getPrincipalType() {
        return principalType;
    }

    public PokemonType getSecondaryType() {
        return secondaryType;
    }

    public String getState() {
        return statusEffect != null ? statusEffect.getName() : "normal";
    }

    public List<Movement> getMovements() {
        return new ArrayList<>(movements);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Establece las estadísticas del Pokémon directamente
     *
     * @param ps       Puntos de salud
     * @param attack   Ataque
     * @param defense  Defensa
     * @param velocity Velocidad
     */
    public void setStats(int ps, int attack, int defense, int velocity) {
        this.maxPs = ps;
        this.currentPs = ps;
        this.attack = attack;
        this.defense = defense;
        this.velocity = velocity;
    }

    /**
     * Aplica un efecto de estado al Pokémon con una probabilidad
     *
     * @param effectState El estado a aplicar
     * @param probability Probabilidad del efecto (0-100)
     * @return true si se aplicó el efecto, false en caso contrario
     */
    public boolean applyStatusEffect(String effectState, int probability) {
        Random random = new Random();
        StatusEffect newEffect = StatusAllEffects.getEffect(effectState);
        if (newEffect == null) {
            return false;
        }
        if (statusEffect != null && !"normal".equals(statusEffect.getName()) &&
                !"confuso".equals(newEffect.getName())) {
            return false;
        }
        if (random.nextInt(100) < probability) {
            if (!newEffect.isImmune(this)) {
                if ("confuso".equals(newEffect.getName())) {
                    System.out.println(name + " está confuso!");
                    this.statusEffect = newEffect;
                } else {
                    this.statusEffect = newEffect;
                    System.out.println(name + " ahora está " + newEffect.getName() + "!");
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si el Pokémon está vivo
     *
     * @return true si el Pokémon tiene PS, false si está debilitado
     */
    public boolean isAlive() {
        return currentPs > 0;
    }


    public ArrayList<Movement> movementsUsables(){
        ArrayList<Movement> temp = new ArrayList<>();
        for(Movement m: movements){
            if (m.canUse()){
                temp.add(m);
            }
        }
        return temp;
    }

    /**
     * Selecciona un movimiento aleatorio del Pokémon
     *
     * @param target Pokémon objetivo del ataque
     * @return Movimiento aleatorio
     */
    public Movement aleatoryMovement(Pokemon target){
        ArrayList<Movement> temp = movementsUsables();
        Random random = new Random();
        int ramdomNum = random.nextInt(temp.size());
        Movement aleatoryMovement = temp.get(ramdomNum);
        return aleatoryMovement;
    }

    @Override
    public String toString() {
        return name + " (Nivel " + level + ") - PS: " + currentPs + "/" + maxPs +
                " - Tipo: " + principalType +
                (secondaryType != null ? "/" + secondaryType : "");
    }

    public Movement getMovement(Movement movement){
        for (Movement m : movements) {
            if (m.getName().equals(movement.getName())) {
                return m;
            }
        }
        return null;

    }
}

