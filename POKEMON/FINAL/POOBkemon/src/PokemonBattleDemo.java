package src;

import src.domain.*;
import java.util.Scanner;
import java.util.Random;
import java.util.*;

/**
 * Clase demostrativa para probar las batallas Pokémon y los efectos de estado
 */
public class PokemonBattleDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("=== DEMOSTRACIÓN DE BATALLA POKÉMON ===");
        System.out.println("Esta demo muestra los efectos de estado en los Pokémon\n");

        // Crear Pokémon de diferentes tipos
        Pokemon charizard = new Pokemon("Charizard", PokemonType.FUEGO, PokemonType.VOLADOR);
        Pokemon blastoise = new Pokemon("Blastoise", PokemonType.AGUA);
        Pokemon venusaur = new Pokemon("Venusaur", PokemonType.PLANTA, PokemonType.VENENO);
        Pokemon pikachu = new Pokemon("Pikachu", PokemonType.ELECTRICO);
        Pokemon gengar = new Pokemon("Gengar", PokemonType.FANTASMA, PokemonType.VENENO);
        Pokemon snorlax = new Pokemon("Snorlax", PokemonType.NORMAL);

        // Establecer estadísticas personalizadas para equilibrar
        charizard.setStats(280, 130, 110, 120);
        blastoise.setStats(290, 115, 140, 105);
        venusaur.setStats(270, 120, 120, 100);
        pikachu.setStats(230, 125, 90, 140);
        gengar.setStats(250, 135, 95, 130);
        snorlax.setStats(330, 120, 130, 70);

        // Crear movimientos con efectos secundarios
        // Formato: nombre, tipo, poder, precisión, PP, categoría, efecto, probabilidad
        
        // Movimientos de Charizard
        Movement lanzallamas = new Movement("Lanzallamas", PokemonType.FUEGO, 90, 100, 15, "Especial", "quemado", 10);
        Movement golpeAereo = new Movement("Golpe Aéreo", PokemonType.VOLADOR, 75, 95, 15, "Físico");
        Movement giroFuego = new Movement("Giro Fuego", PokemonType.FUEGO, 35, 85, 15, "Especial", "atrapado", 100);
        Movement cuchillada = new Movement("Cuchillada", PokemonType.NORMAL, 70, 100, 20, "Físico");
        charizard.addMovement(lanzallamas);
        charizard.addMovement(golpeAereo);
        charizard.addMovement(giroFuego);
        charizard.addMovement(cuchillada);

        // Movimientos de Blastoise
        Movement hidrobomba = new Movement("Hidrobomba", PokemonType.AGUA, 110, 80, 5, "Especial");
        Movement rayo_hielo = new Movement("Rayo Hielo", PokemonType.HIELO, 90, 100, 10, "Especial", "congelado", 10);
        Movement cabezazo = new Movement("Cabezazo", PokemonType.NORMAL, 70, 100, 15, "Físico", "confuso", 30);
        Movement hidropulso = new Movement("Hidropulso", PokemonType.AGUA, 60, 100, 20, "Especial", "confuso", 20);
        blastoise.addMovement(hidrobomba);
        blastoise.addMovement(rayo_hielo);
        blastoise.addMovement(cabezazo);
        blastoise.addMovement(hidropulso);

        // Movimientos de Venusaur
        Movement rayoSolar = new Movement("Rayo Solar", PokemonType.PLANTA, 120, 100, 10, "Especial");
        Movement bombaLodo = new Movement("Bomba Lodo", PokemonType.VENENO, 90, 100, 10, "Especial", "envenenado", 30);
        Movement polvoVeneno = new Movement("Polvo Veneno", PokemonType.VENENO, 0, 75, 35, "Especial", "envenenado", 100);
        Movement drenadoras = new Movement("Drenadoras", PokemonType.PLANTA, 0, 90, 10, "Especial", "atrapado", 100);
        venusaur.addMovement(rayoSolar);
        venusaur.addMovement(bombaLodo);
        venusaur.addMovement(polvoVeneno);
        venusaur.addMovement(drenadoras);

        // Movimientos de Pikachu
        Movement trueno = new Movement("Trueno", PokemonType.ELECTRICO, 110, 70, 10, "Especial", "paralizado", 30);
        Movement atizadorX = new Movement("Atizador-X", PokemonType.ELECTRICO, 80, 100, 15, "Físico");
        Movement onda_trueno = new Movement("Onda Trueno", PokemonType.ELECTRICO, 0, 90, 20, "Especial", "paralizado", 100);
        Movement cola_hierro = new Movement("Cola Hierro", PokemonType.ACERO, 100, 75, 15, "Físico");
        pikachu.addMovement(trueno);
        pikachu.addMovement(atizadorX);
        pikachu.addMovement(onda_trueno);
        pikachu.addMovement(cola_hierro);

        // Movimientos de Gengar
        Movement puno_sombra = new Movement("Puño Sombra", PokemonType.FANTASMA, 60, 100, 20, "Físico");
        Movement bola_sombra = new Movement("Bola Sombra", PokemonType.FANTASMA, 80, 100, 15, "Especial");
        Movement hipnosis = new Movement("Hipnosis", PokemonType.PSIQUICO, 0, 60, 20, "Especial", "dormido", 100);
        Movement comeSuenos = new Movement("Come Sueños", PokemonType.PSIQUICO, 100, 100, 15, "Especial");
        gengar.addMovement(puno_sombra);
        gengar.addMovement(bola_sombra);
        gengar.addMovement(hipnosis);
        gengar.addMovement(comeSuenos);

        // Movimientos de Snorlax
        Movement golpeCuerpo = new Movement("Golpe Cuerpo", PokemonType.NORMAL, 85, 100, 15, "Físico", "paralizado", 30);
        Movement descanso = new Movement("Descanso", PokemonType.PSIQUICO, 0, 100, 10, "Especial", "dormido", 100);
        Movement sonambulo = new Movement("Sonámbulo", PokemonType.NORMAL, 0, 100, 10, "Especial");
        Movement terremoto = new Movement("Terremoto", PokemonType.TIERRA, 100, 100, 10, "Físico");
        snorlax.addMovement(golpeCuerpo);
        snorlax.addMovement(descanso);
        snorlax.addMovement(sonambulo);
        snorlax.addMovement(terremoto);

        // Array con todos los Pokémon disponibles
        Pokemon[] pokemons = {charizard, blastoise, venusaur, pikachu, gengar, snorlax};

        // Menú para elegir Pokémon
        System.out.println("Elige tu Pokémon:");
        for (int i = 0; i < pokemons.length; i++) {
            System.out.println((i + 1) + ". " + pokemons[i].getName());
        }
        
        int opcionJugador = obtenerOpcionValida(scanner, 1, pokemons.length);
        Pokemon pokemonJugador = pokemons[opcionJugador - 1];
        
        // Elegir rival (aleatoriamente, diferente al del jugador)
        int indiceRival;
        do {
            indiceRival = random.nextInt(pokemons.length);
        } while (indiceRival == opcionJugador - 1);
        
        Pokemon pokemonRival = pokemons[indiceRival];
        
        System.out.println("\n¡Has elegido a " + pokemonJugador.getName() + "!");
        System.out.println("Tu rival usará a " + pokemonRival.getName() + "!");
        
        // Iniciar batalla
        boolean batallaTerminada = false;
        int turno = 1;
        
        while (!batallaTerminada) {
            System.out.println("\n--- TURNO " + turno + " ---");
            
            // Mostrar estado de los Pokémon
            mostrarEstadoPokemon(pokemonJugador);
            mostrarEstadoPokemon(pokemonRival);
            
            // Turno del jugador
            System.out.println("\nTu turno. Elige un movimiento:");
            List<Movement> movimientosJugador = pokemonJugador.getMovements();
            for (int i = 0; i < movimientosJugador.size(); i++) {
                Movement mov = movimientosJugador.get(i);
                System.out.println((i + 1) + ". " + mov.getName() + " (Tipo: " + mov.getType().getName() + 
                                  ", Poder: " + mov.getPower() + ", PP: " + mov.getPp() + "/" + mov.getMaxPp() + ")");
            }
            
            // Aplicar efectos de estado al inicio del turno
            boolean jugadorPuedeActuar = pokemonJugador.applyStateEffect();
            
            if (jugadorPuedeActuar) {
                int movimientoElegido = obtenerOpcionValida(scanner, 1, movimientosJugador.size());
                System.out.println("\n¡" + pokemonJugador.getName() + " usa " + 
                                  movimientosJugador.get(movimientoElegido - 1).getName() + "!");
                
                int danoRealizado = pokemonJugador.attack(pokemonRival, movimientoElegido - 1);
                if (danoRealizado > 0) {
                    System.out.println("¡Ha causado " + danoRealizado + " puntos de daño!");
                }
            } else {
                System.out.println(pokemonJugador.getName() + " no puede atacar debido a su estado.");
            }
            
            // Verificar si el rival ha sido derrotado
            if (pokemonRival.isFainted()) {
                System.out.println("\n¡" + pokemonRival.getName() + " ha sido derrotado!");
                System.out.println("¡Has ganado la batalla!");
                batallaTerminada = true;
                continue;
            }
            
            // Pausa para leer los resultados del turno
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            
            // Turno del rival (IA simple: movimiento aleatorio)
            System.out.println("\nTurno de " + pokemonRival.getName());
            
            // Aplicar efectos de estado al inicio del turno del rival
            boolean rivalPuedeActuar = pokemonRival.applyStateEffect();
            
            if (rivalPuedeActuar) {
                int movimientoRival = random.nextInt(pokemonRival.getMovements().size());
                Movement movElegido = pokemonRival.getMovements().get(movimientoRival);
                
                System.out.println("¡" + pokemonRival.getName() + " usa " + movElegido.getName() + "!");
                
                int danoRealizado = pokemonRival.attack(pokemonJugador, movimientoRival);
                if (danoRealizado > 0) {
                    System.out.println("¡Ha causado " + danoRealizado + " puntos de daño!");
                }
            } else {
                System.out.println(pokemonRival.getName() + " no puede atacar debido a su estado.");
            }
            
            // Verificar si el jugador ha sido derrotado
            if (pokemonJugador.isFainted()) {
                System.out.println("\n¡" + pokemonJugador.getName() + " ha sido derrotado!");
                System.out.println("Has perdido la batalla...");
                batallaTerminada = true;
                continue;
            }
            
            turno++;
            
            // Pausa para leer los resultados del turno
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
        }
        
        System.out.println("\n=== FIN DE LA BATALLA ===");
        scanner.close();
    }
    
    /**
     * Muestra el estado actual de un Pokémon (PS, estado, etc.)
     * @param pokemon Pokémon a mostrar
     */
    private static void mostrarEstadoPokemon(Pokemon pokemon) {
        int porcentajePS = (pokemon.getCurrentPs() * 100) / pokemon.getMaxPs();
        String barraPS = "";
        
        for (int i = 0; i < 20; i++) {
            if (i < porcentajePS / 5) {
                barraPS += "█";
            } else {
                barraPS += "░";
            }
        }
        
        System.out.println("\n" + pokemon.getName() + " - PS: " + pokemon.getCurrentPs() + "/" + pokemon.getMaxPs());
        System.out.println("Estado: " + pokemon.getState());
        System.out.println("Barra de vida: " + barraPS + " (" + porcentajePS + "%)");
        System.out.println("ATK: " + pokemon.getAttack() + ", DEF: " + pokemon.getDefense() + ", VEL: " + pokemon.getVelocity());
    }
    
    /**
     * Obtiene una opción válida del usuario dentro de un rango
     * @param scanner Scanner para leer la entrada
     * @param min Valor mínimo aceptable
     * @param max Valor máximo aceptable
     * @return Opción elegida por el usuario
     */
    private static int obtenerOpcionValida(Scanner scanner, int min, int max) {
        int opcion = -1;
        boolean entradaValida = false;
        
        while (!entradaValida) {
            try {
                String entrada = scanner.nextLine();
                opcion = Integer.parseInt(entrada);
                
                if (opcion >= min && opcion <= max) {
                    entradaValida = true;
                } else {
                    System.out.println("Por favor, ingresa un número entre " + min + " y " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un número válido");
            }
        }
        
        return opcion;
    }
}
