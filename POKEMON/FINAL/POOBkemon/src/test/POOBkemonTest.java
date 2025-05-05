package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.domain.*;
import java.util.ArrayList;
import java.util.List;

public class POOBkemonTest {
    private POOBkemon game;
    private Trainer trainer;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Item potion;
    
    @BeforeEach
    public void setUp() {
        game = new POOBkemon();
        trainer = new Trainer("Ash");
        pokemon1 = new Pokemon("Pikachu", PokemonType.ELECTRICO);
        pokemon2 = new Pokemon("Bulbasaur", PokemonType.PLANTA);

        Movement tackle = new Movement("Tackle", PokemonType.NORMAL, 40, 100, 15, "Especial");
        Movement thunderbolt = new Movement("Thunderbolt", PokemonType.ELECTRICO, 40, 100, 15, "Especial");
        pokemon1.addMovement(tackle);
        pokemon1.addMovement(thunderbolt);
        
        Movement vineWhip = new Movement("Vine Whip", PokemonType.PLANTA, 40, 100, 15, "Especial");
        pokemon2.addMovement(tackle);
        pokemon2.addMovement(vineWhip);

        potion = new NormalPotion();
    }

    @AfterEach
    public void tearDown() {
        game = null;
        trainer = null;
        pokemon1 = null;
        pokemon2 = null;
        potion = null;
    }
    
    @Test
    public void shouldCreateTrainer() {
        assertNotNull(trainer);
        assertEquals("Ash", trainer.getNombre());
        assertTrue(trainer.getTeam().isEmpty());
    }
    
    @Test
    public void shouldAddPokemonToTeam() {
        assertTrue(trainer.addPokemon(pokemon1));
        assertEquals(1, trainer.getTeam().size());
        assertEquals(pokemon1, trainer.getActivePokemon());
        
        assertTrue(trainer.addPokemon(pokemon2));
        assertEquals(2, trainer.getTeam().size());
        assertEquals(pokemon1, trainer.getActivePokemon());
    }
    
    @Test
    public void shouldSwitchActivePokemon() {
        trainer.addPokemon(pokemon1);
        trainer.addPokemon(pokemon2);
        
        assertTrue(trainer.switchPokemon(pokemon2));
        assertEquals(pokemon2, trainer.getActivePokemon());
    }
    
    @Test
    public void shouldNotAllowSwitchingToFaintedPokemon() {
        trainer.addPokemon(pokemon1);
        trainer.addPokemon(pokemon2);

        pokemon2.losePS(400);
        
        assertFalse(trainer.switchPokemon(pokemon2));
        assertEquals(pokemon1, trainer.getActivePokemon());
    }
    
    @Test
    public void shouldDetectDefeatedTrainer() {
        trainer.addPokemon(pokemon1);
        
        assertFalse(trainer.isDefeated());

        pokemon1.losePS(400);
        
        assertTrue(trainer.isDefeated());
    }
    
    @Test
    public void shouldAddAndUseItems() {
        trainer.addPokemon(pokemon1);
        trainer.addItem(potion);

        int initialHealth = pokemon1.getMaxPs();
        pokemon1.losePS(20);
        
        assertTrue(trainer.useItem(potion, pokemon1));

        assertTrue(pokemon1.getCurrentPs() > initialHealth - 20);
    }
    
    @Test
    public void shouldCalculateDamageCorrectly() {
        Pokemon waterPokemon = new Pokemon("Squirtle", PokemonType.AGUA);
        Pokemon firePokemon = new Pokemon("Charmander", PokemonType.FUEGO);
        
        Movement waterGun = new Movement("Water Gun", PokemonType.AGUA, 40, 100, 15, "Especial");
        Movement ember = new Movement("Ember", PokemonType.FUEGO, 40, 100, 15, "Especial");
        
        waterPokemon.addMovement(waterGun);
        firePokemon.addMovement(ember);

        int hpBefore = firePokemon.getCurrentPs();
        waterPokemon.attack(firePokemon, waterGun);
        int hpAfter = firePokemon.getCurrentPs();
        
        assertTrue((hpBefore - hpAfter) > waterGun.getPower());

        hpBefore = waterPokemon.getCurrentPs();
        firePokemon.attack(waterPokemon, ember);
        hpAfter = waterPokemon.getCurrentPs();

        assertTrue((hpBefore - hpAfter) < ember.getPower());
    }

    @Test
    public void shouldUseRevive() {
        Revive revive = new Revive();
        trainer.addPokemon(pokemon1);
        trainer.addItem(revive);

        pokemon1.losePS(400);
        assertTrue(pokemon1.isFainted());
        
        assertTrue(trainer.useItem(revive, pokemon1));

        assertFalse(pokemon1.isFainted());
        assertTrue(pokemon1.getCurrentPs() > 0);
    }
    
    @Test
    public void shouldHaveLimitOnPokemonTeam() {
        for (int i = 0; i < 6; i++) {
            Pokemon p = new Pokemon("Pokemon" + i, PokemonType.NORMAL);
            assertTrue(trainer.addPokemon(p));
        }
        Pokemon extraPokemon = new Pokemon("Extra", PokemonType.NORMAL);
        assertFalse(trainer.addPokemon(extraPokemon));

        assertEquals(6, trainer.getTeam().size());
    }
}