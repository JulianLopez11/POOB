package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.domain.*;
import java.util.ArrayList;
import java.util.List;

public class POOBkemonTest {
    private POOBkemon juego;
    private Trainer entrenador;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Item pocion;
    
    @BeforeEach
    public void setUp() {
        juego = new POOBkemon("Normal"); // Asumiendo que existe modo "Normal"
        entrenador = new Trainer("Ash");
        
        // Crear Pokémon de prueba
        pokemon1 = new Pokemon("Pikachu",  PokemonType.ELECTRICO);
        pokemon2 = new Pokemon("Bulbasaur", PokemonType.PLANTA);
        
        // Añadir algunos movimientos a los Pokémon
        Movement tackle = new Movement("Tackle",  PokemonType.NORMAL,40, 100,15,"Especial");
        Movement thunderbolt = new Movement("Thunderbolt", PokemonType.ELECTRICO, 40,100,15,"Especial");
        pokemon1.addMovement(tackle);
        pokemon1.addMovement(thunderbolt);
        
        Movement vineWhip = new Movement("Vine Whip", PokemonType.PLANTA, 40, 100,15,"Especial");
        pokemon2.addMovement(tackle);
        pokemon2.addMovement(vineWhip);
        
        // Crear objetos de prueba
        pocion = new NormalPotion();
    }

    @AfterEach
    public void tearDown() {
        juego = null;
        entrenador = null;
        pokemon1 = null;
        pokemon2 = null;
        pocion = null;
    }
    
    @Test
    public void deberiaCrearUnEntrenador() {
        assertNotNull(entrenador);
        assertEquals("Ash", entrenador.getNombre());
        assertTrue(entrenador.getTeam().isEmpty());
    }
    
    @Test
    public void deberiaAñadirPokemonAlEquipo() {
        assertTrue(entrenador.addPokemon(pokemon1));
        assertEquals(1, entrenador.getTeam().size());
        assertEquals(pokemon1, entrenador.getActivePokemon());
        
        assertTrue(entrenador.addPokemon(pokemon2));
        assertEquals(2, entrenador.getTeam().size());
        assertEquals(pokemon1, entrenador.getActivePokemon());
    }
    
    @Test
    public void deberiaCambiarPokemonActivo() {
        entrenador.addPokemon(pokemon1);
        entrenador.addPokemon(pokemon2);
        
        assertTrue(entrenador.switchPokemon(pokemon2));
        assertEquals(pokemon2, entrenador.getActivePokemon());
    }
    
    @Test
    public void noDeberiaPermitirCambiarAUnPokemonDebilitado() {
        entrenador.addPokemon(pokemon1);
        entrenador.addPokemon(pokemon2);

        pokemon2.losePS(400);
        
        assertFalse(entrenador.switchPokemon(pokemon2));
        assertEquals(pokemon1, entrenador.getActivePokemon());
    }
    
    @Test
    public void deberiaDetectarEntrenadorDerrotado() {
        entrenador.addPokemon(pokemon1);
        
        assertFalse(entrenador.isDefeated());

        pokemon1.losePS(400);
        
        assertTrue(entrenador.isDefeated());
    }
    
    @Test
    public void deberiaAñadirYUsarItems() {
        entrenador.addPokemon(pokemon1);
        entrenador.addItem(pocion);

        int vidaInicial = pokemon1.getMaxPs();
        pokemon1.losePS(20);
        
        assertTrue(entrenador.useItem(pocion, pokemon1));

        assertTrue(pokemon1.getCurrentPs() > vidaInicial - 20);
    }
    
    @Test
    public void deberiaCalcularDañoCorrectamente() {
        Pokemon waterPokemon = new Pokemon("Squirtle", PokemonType.AGUA);
        Pokemon firePokemon = new Pokemon("Charmander",  PokemonType.FUEGO);
        
        Movement waterGun = new Movement("Water Gun",  PokemonType.AGUA,40, 100,15,"Especial");
        Movement ember = new Movement("Ember",  PokemonType.FUEGO,40, 100,15,"Especial");
        
        waterPokemon.addMovement(waterGun);
        firePokemon.addMovement(ember);

        int hpAntes = firePokemon.getCurrentPs();
        waterPokemon.attack(firePokemon,0);
        int hpDespues = firePokemon.getCurrentPs();
        

        assertTrue((hpAntes - hpDespues) > waterGun.getPower());
        
        // Fuego no es muy efectivo contra agua
        hpAntes = waterPokemon.getCurrentPs();
        firePokemon.attack( waterPokemon,0);
        hpDespues = waterPokemon.getCurrentPs();
        
        // El daño debería ser menor debido a la resistencia
        assertTrue((hpAntes - hpDespues) < ember.getPower());
    }

    @Test
    public void deberiaProcesarUnTurno() {
        
        juego.addTrainer(entrenador);
        Trainer oponente = new Trainer("Gary");
        oponente.addPokemon(pokemon2);
        juego.addTrainer(oponente);

        juego.turno();
    }
    
    @Test
    public void deberiaUsarRevive() {
        Revive revive = new Revive();
        entrenador.addPokemon(pokemon1);
        entrenador.addItem(revive);

            pokemon1.losePS(400);
        assertTrue(pokemon1.isFainted());
        

        assertTrue(entrenador.useItem(revive, pokemon1));

        assertFalse(pokemon1.isFainted());
        assertTrue(pokemon1.getCurrentPs() > 0);
    }
    
    @Test
    public void deberiaTenerLimiteEnEquipoPokemon() {
        for (int i = 0; i < 6; i++) {
            Pokemon p = new Pokemon("Pokemon" + i,  PokemonType.NORMAL);
            assertTrue(entrenador.addPokemon(p));
        }
        Pokemon extraPokemon = new Pokemon("Extra",  PokemonType.NORMAL);
        assertFalse(entrenador.addPokemon(extraPokemon));

        assertEquals(6, entrenador.getTeam().size());
    }
}
