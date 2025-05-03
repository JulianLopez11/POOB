package src.domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa un Pokémon con sus características y comportamientos
 */
public class Pokemon {
    private String name;
    private final int level = 100;
    private int maxPs;
    private int currentPs;
    private int attack;
    private int defense;
    private int velocity;
    private PokemonType principalType;
    private PokemonType secondaryType;
    private String state;
    private List<Movement> movements;
    private int criticalHitRate;

    public Pokemon(String name, PokemonType principalType) {
        this.name = name;
        this.principalType = principalType;
        this.secondaryType = null;
        this.state = "normal";
        this.movements = new ArrayList<>(4);
        this.criticalHitRate = 6;

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
     * Calcula el daño que este Pokémon puede hacer a otro y aplica posibles efectos secundarios
     * @param target Pokémon objetivo del ataque
     * @param movementIndex Índice del movimiento a utilizar
     * @return Cantidad de daño calculado
     */
    public int attack(Pokemon target, int movementIndex) {
        if (movementIndex < 0 || movementIndex >= movements.size()) {
            return 0;
        }

        Movement movement = movements.get(movementIndex);

        // Verificar si hay suficientes PP
        if (!movement.use()) {
            System.out.println("No quedan PP para este movimiento!");
            return 0;
        }

        Random random = new Random();

        // Verificar la precisión del movimiento
        if (movement.getAccuracy() > 0 && random.nextInt(100) >= movement.getAccuracy()) {
            System.out.println("¡El ataque de " + this.name + " falló!");
            return 0;
        }

        double typeEffectiveness = TypeEffectiveness.getTotalEffectiveness(
                getMovementType(movementIndex),
                target.getPrincipalType(),
                target.getSecondaryType()
        );

        // Obtener el poder base directamente del movimiento
        int poderBase = movement.getPower();

        // Nueva fórmula de daño: ((2 * Nivel / 5 + 2) * Poder Base * (Ataque / Defensa) / 50 + 2) * Modificador
        double baseDamage = ((2.0 * this.level / 5.0 + 2.0) * poderBase * (this.attack / (double)target.getDefense()) / 50.0) + 2.0;

        // Factor de golpe crítico (1/16 de probabilidad por defecto)
        double criticalHit = 1.0;
        if (random.nextInt(16) < 1) {
            criticalHit = 1.5;
            System.out.println("¡Golpe crítico!");
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

        // El modificador incluye: efectividad por tipo, crítico, STAB y factor aleatorio
        double modificador = typeEffectiveness * criticalHit * randomFactor * stab;

        // Cálculo final del daño
        int finalDamage = (int) Math.round(baseDamage * modificador);

        // Aplicar daño al objetivo
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

            // Aplicar efecto secundario si existe y el Pokémon no está debilitado
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
     * Método para que el Pokémon pierda puntos de salud
     * @param damage Cantidad de daño recibido
     * @return Puntos de salud restantes
     */
    public int losePS(int damage) {
        this.currentPs = Math.max(0, this.currentPs - damage);
        return this.currentPs;
    }

    /**
     * Añade un movimiento al Pokémon
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
     * @param movementIndex Índice del movimiento
     * @return Tipo del movimiento
     */
    private PokemonType getMovementType(int movementIndex) {
        if (movementIndex >= 0 && movementIndex < movements.size()) {
            return movements.get(movementIndex).getType();
        }
        return principalType;
    }

    /**
     * Aplica el efecto del estado actual al final del turno
     * @return true si el Pokémon puede actuar, false si está incapacitado
     */
    public boolean applyStateEffect() {
        Random random = new Random();
        boolean canAct = true;
        
        switch (this.state) {
            case "envenenado":
                losePS(maxPs / 8); // Pierde 1/8 de los PS máximos
                break;
                
            case "quemado":
                losePS(maxPs / 16); // Pierde 1/16 de los PS máximos
                // El ataque se reduce a la mitad (ya contemplado en getAttack())
                break;
                
            case "congelado":
                // 20% de probabilidad de descongelarse
                if (random.nextInt(100) < 20) {
                    this.state = "normal";
                    System.out.println(name + " se ha descongelado!");
                } else {
                    canAct = false; // No puede actuar mientras está congelado
                }
                break;
                
            case "dormido":
                // 33% de probabilidad de despertar
                if (random.nextInt(100) < 33) {
                    this.state = "normal";
                    System.out.println(name + " se ha despertado!");
                } else {
                    canAct = false; // No puede actuar mientras duerme
                }
                break;
                
            case "paralizado":
                // 25% de probabilidad de no poder moverse
                if (random.nextInt(100) < 25) {
                    System.out.println(name + " está paralizado y no puede moverse!");
                    canAct = false;
                }
                // La velocidad se reduce a 1/4 (ya contemplado en getVelocity())
                break;
                
            case "gravemente envenenado":
                // El daño aumenta cada turno
                int turno = 1; // Idealmente, deberíamos rastrear los turnos
                losePS((maxPs * turno) / 16); // Aumenta progresivamente
                break;
                
            case "confuso":
                // 33% de probabilidad de golpearse a sí mismo
                if (random.nextInt(100) < 33) {
                    System.out.println(name + " está confuso y se golpeó a sí mismo!");
                    // Daño autoinfligido: fórmula simplificada
                    int damage = (int)((((2.0 * level / 5.0 + 2.0) * 40 * 
                            (attack / (double)defense)) / 50.0) + 2.0);
                    losePS(damage);
                    canAct = false;
                }
                // 20% de probabilidad de recuperarse de la confusión
                if (random.nextInt(100) < 20) {
                    this.state = "normal";
                    System.out.println(name + " ya no está confuso!");
                }
                break;
                
            case "atrapado":
                losePS(maxPs / 16); // Daño por estar atrapado
                // 20% de probabilidad de liberarse
                if (random.nextInt(100) < 20) {
                    this.state = "normal";
                    System.out.println(name + " se ha liberado!");
                }
                break;
                
            default:
                break;
        }
        
        return canAct;
    }

    /**
     * Reemplaza un movimiento existente
     * @param oldMovementIndex Índice del movimiento a reemplazar
     * @param newMovement Nuevo movimiento
     */
    public void replaceMovement(int oldMovementIndex, Movement newMovement) {
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
        return "quemado".equals(state) ? attack / 2 : attack;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public int getVelocity() {
        return "paralizado".equals(state) ? velocity / 4 : velocity;
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

    public List<Movement> getMovements() {
        return new ArrayList<>(movements);
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

    /**
     * Aplica un efecto de estado al Pokémon con una probabilidad
     * @param effectState El estado a aplicar
     * @param probability Probabilidad del efecto (0-100)
     * @return true si se aplicó el efecto, false en caso contrario
     */
    public boolean applyStatusEffect(String effectState, int probability) {
        Random random = new Random();
        
        // Si el Pokémon ya tiene un estado, normalmente no se puede aplicar otro
        if (!"normal".equals(this.state) && !"confuso".equals(effectState)) {
            return false;
        }
        
        // Verificar si el efecto se aplica según su probabilidad
        if (random.nextInt(100) < probability) {
            // Algunos tipos son inmunes a ciertos estados
            boolean isImmune = false;
            
            // Verificar inmunidades por tipo
            switch (effectState) {
                case "envenenado":
                case "gravemente envenenado":
                    if (principalType == PokemonType.ACERO || principalType == PokemonType.VENENO ||
                            (secondaryType != null && (secondaryType == PokemonType.ACERO || secondaryType == PokemonType.VENENO))) {
                        isImmune = true;
                    }
                    break;
                case "paralizado":
                    if (principalType == PokemonType.TIERRA || 
                            (secondaryType != null && secondaryType == PokemonType.TIERRA)) {
                        isImmune = true;
                    }
                    break;
                case "quemado":
                    if (principalType == PokemonType.FUEGO || 
                            (secondaryType != null && secondaryType == PokemonType.FUEGO)) {
                        isImmune = true;
                    }
                    break;
                case "congelado":
                    if (principalType == PokemonType.HIELO || 
                            (secondaryType != null && secondaryType == PokemonType.HIELO)) {
                        isImmune = true;
                    }
                    break;
            }
            
            if (!isImmune) {
                // Si el efecto es confusión y ya tiene otro estado, se pueden acumular
                if ("confuso".equals(effectState)) {
                    System.out.println(name + " está confuso!");
                    this.state = "confuso";
                } else {
                    this.state = effectState;
                    System.out.println(name + " ahora está " + effectState + "!");
                }
                return true;
            }
        }
        
        return false;
    }

    public boolean isAlive(){
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
}

