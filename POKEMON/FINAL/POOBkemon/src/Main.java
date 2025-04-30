package src;

import src.domain.*;
import java.util.List;

/**
 * Clase principal para probar el funcionamiento del sistema POOBkemon
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Bienvenido al Sistema POOBkemon ===");
        
        // Probar la enumeración PokemonType
        System.out.println("\n=== Tipos de Pokémon ===");
        for (PokemonType tipo : PokemonType.values()) {
            System.out.println(tipo.getName() + " - Categoría: " + tipo.getTypeMov());
        }
        
        // Crear algunos Pokémon
        System.out.println("\n=== Creación de Pokémon ===");
        Pokemon pikachu = new Pokemon("Pikachu", PokemonType.ELECTRICO);
        Pokemon charizard = new Pokemon("Charizard", PokemonType.FUEGO, PokemonType.VOLADOR);
        Pokemon blastoise = new Pokemon("Blastoise", PokemonType.AGUA);
        
        System.out.println(pikachu);
        System.out.println(charizard);
        System.out.println(blastoise);
        
        // Crear algunos movimientos
        System.out.println("\n=== Creación de Movimientos ===");
        Movement impactrueno = new Movement("Impactrueno", PokemonType.ELECTRICO, 40, 100, 30, "Especial", "paralizado", 10);
        Movement lanzallamas = new Movement("Lanzallamas", PokemonType.FUEGO, 90, 100, 15, "Especial", "quemado", 10);
        Movement hidrobomba = new Movement("Hidrobomba", PokemonType.AGUA, 110, 80, 5, "Especial");
        
        System.out.println(impactrueno);
        System.out.println(lanzallamas);
        System.out.println(hidrobomba);
        
        // Añadir movimientos a los Pokémon
        pikachu.addMovement("Impactrueno");
        charizard.addMovement("Lanzallamas");
        blastoise.addMovement("Hidrobomba");
        
        // Probar cálculos de efectividad
        System.out.println("\n=== Cálculos de Efectividad ===");
        probarEfectividad(PokemonType.ELECTRICO, PokemonType.AGUA);
        probarEfectividad(PokemonType.ELECTRICO, PokemonType.TIERRA);
        probarEfectividad(PokemonType.AGUA, PokemonType.FUEGO);
        probarEfectividad(PokemonType.AGUA, PokemonType.PLANTA);
        
        // Efectividad con tipos dobles
        System.out.println("\nEfectividad contra tipos dobles:");
        double efectividad = Type.getTotalEffectiveness(
                PokemonType.ELECTRICO, 
                PokemonType.FUEGO, 
                PokemonType.VOLADOR
        );
        System.out.println("Eléctrico vs Fuego/Volador: " + efectividad + " - " + 
                Type.getEffectivenessMessage(efectividad));
        
        // Simulación de batalla simple
        System.out.println("\n=== Simulación de Batalla ===");
        simularBatalla(pikachu, blastoise);
    }
    
    /**
     * Prueba la efectividad entre dos tipos y muestra el resultado
     */
    private static void probarEfectividad(PokemonType tipoAtaque, PokemonType tipoDefensa) {
        double efectividad = Type.getEffectiveness(tipoAtaque, tipoDefensa);
        System.out.println(tipoAtaque.getName() + " vs " + tipoDefensa.getName() + 
                ": " + efectividad + " - " + Type.getEffectivenessMessage(efectividad));
    }
    
    /**
     * Simula una batalla completa entre dos Pokémon hasta que uno se debilite
     */
    private static void simularBatalla(Pokemon pokemon1, Pokemon pokemon2) {
        System.out.println("¡Comienza la batalla entre " + pokemon1.getName() + " y " + pokemon2.getName() + "!");
        System.out.println(pokemon1.getName() + ": " + pokemon1.getCurrentPs() + "/" + pokemon1.getMaxPs() + " PS");
        System.out.println(pokemon2.getName() + ": " + pokemon2.getCurrentPs() + "/" + pokemon2.getMaxPs() + " PS");
        System.out.println("-----------------------------------------");
        
        // Determinar quién ataca primero basado en la velocidad
        Pokemon pokemonRapido, pokemonLento;
        if (pokemon1.getVelocity() >= pokemon2.getVelocity()) {
            pokemonRapido = pokemon1;
            pokemonLento = pokemon2;
        } else {
            pokemonRapido = pokemon2;
            pokemonLento = pokemon1;
        }
        
        System.out.println(pokemonRapido.getName() + " es más rápido y atacará primero.");
        System.out.println("-----------------------------------------");
        
        int turno = 1;
        
        // Bucle principal de la batalla
        while (!pokemon1.isFainted() && !pokemon2.isFainted()) {
            System.out.println("=== TURNO " + turno + " ===");
            
            // Ataque del Pokémon más rápido
            realizarAtaque(pokemonRapido, pokemonLento);
            
            // Verificar si ya hay un ganador
            if (pokemonLento.isFainted()) {
                break;
            }
            
            // Ataque del Pokémon más lento
            realizarAtaque(pokemonLento, pokemonRapido);
            
            // Aplicar efectos de estado al final del turno
            if (!pokemon1.isFainted()) {
                aplicarEfectosEstado(pokemon1);
            }
            
            if (!pokemon2.isFainted()) {
                aplicarEfectosEstado(pokemon2);
            }
            
            turno++;
            System.out.println("-----------------------------------------");
        }
        
        // Determinar el ganador
        Pokemon ganador = pokemon1.isFainted() ? pokemon2 : pokemon1;
        Pokemon perdedor = pokemon1.isFainted() ? pokemon1 : pokemon2;
        
        System.out.println("¡" + perdedor.getName() + " se ha debilitado!");
        System.out.println("¡" + ganador.getName() + " es el ganador de la batalla!");
        System.out.println("PS restantes: " + ganador.getCurrentPs() + "/" + ganador.getMaxPs());
    }
    
    /**
     * Realiza un ataque de un Pokémon a otro
     */
    private static void realizarAtaque(Pokemon atacante, Pokemon defensor) {
        // El Pokémon atacante usa su primer movimiento
        int daño = atacante.attack(defensor, 0);
        String mensaje = atacante.getName() + " usa " + atacante.getMovements().get(0);
        
        // Calcular efectividad
        double efectividad = Type.getTotalEffectiveness(
                atacante.getPrincipalType(),
                defensor.getPrincipalType(),
                defensor.getSecondaryType() != null ? defensor.getSecondaryType() : null
        );
        
        // Mostrar mensaje de efectividad
        String mensajeEfecto = Type.getEffectivenessMessage(efectividad);
        if (!mensajeEfecto.isEmpty()) {
            mensaje += "\n" + mensajeEfecto;
        }
        
        // Aplicar el daño
        defensor.losePS(daño);
        mensaje += "\nInflige " + daño + " puntos de daño.";
        
        if (!defensor.isFainted()) {
            mensaje += "\n" + defensor.getName() + " tiene " + defensor.getCurrentPs() + 
                    "/" + defensor.getMaxPs() + " PS restantes.";
        }
        
        System.out.println(mensaje);
    }
    
    /**
     * Aplica los efectos de estado al final del turno
     */
    private static void aplicarEfectosEstado(Pokemon pokemon) {
        if (!"normal".equals(pokemon.getState())) {
            System.out.println(pokemon.getName() + " sufre los efectos de estar " + pokemon.getState() + ".");
            pokemon.applyStateEffect();
            System.out.println(pokemon.getName() + " tiene ahora " + pokemon.getCurrentPs() + 
                    "/" + pokemon.getMaxPs() + " PS.");
            
            if (pokemon.isFainted()) {
                System.out.println("¡" + pokemon.getName() + " se ha debilitado por los efectos de " + 
                        pokemon.getState() + "!");
            }
        }
    }
}
