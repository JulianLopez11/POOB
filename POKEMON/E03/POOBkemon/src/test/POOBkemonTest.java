package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.domain.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class POOBkemonTest {
    private POOBkemon game;
    private Trainer trainer;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Pokemon machamp;
    private Pokemon gardevoir;
    private Physical physicalMove;
    private Special specialMove;
    private Item potion;
    
    @BeforeEach
    public void setUp() {
        game = new POOBkemon();
        trainer = new Trainer("Julian", Color.RED);
        pokemon1 = new Pokemon("Pikachu", PokemonType.ELECTRICO);
        pokemon2 = new Pokemon("Bulbasaur", PokemonType.PLANTA);
        potion = new NormalPotion();
        machamp = new Pokemon("Machamp",100,384,394,251,284,295,229,PokemonType.LUCHA,null,68);
        gardevoir = new Pokemon("Gardevoir",100,340,251,383,251,361,284,PokemonType.PSIQUICO,PokemonType.HADA,282);

        machamp.increaseStat("attack", 50);
        machamp.increaseStat("specialAttack", -50);

        gardevoir.increaseStat("defense", -87);
        gardevoir.increaseStat("specialDefense", 50);

        physicalMove = new Physical("Puño Dinámico", "Un potente puñetazo",
                20, 100, 100, PokemonType.LUCHA, 0);

        specialMove = new Special("Psíquico", "Un poderoso ataque mental",
                20, 100, 100, PokemonType.PSIQUICO, 0);

        machamp.addMovement(physicalMove);
        machamp.addMovement(specialMove);
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
        assertEquals("Julian", trainer.getNombre());
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

    //-------------------MOVIMIENTOS-------------------
    @Test
    public void testPhysicalAttackUsesFisicas() {
        int initialHP = gardevoir.getCurrentPs();
        try {
            machamp.useMovement(physicalMove, gardevoir);
            int damageDealt = initialHP - gardevoir.getCurrentPs();
            // El daño debería ser alto ya que tenemos alto ataque físico vs baja defensa física
            assertTrue(damageDealt > 100,
                    "El ataque físico debería causar daño significativo dado el alto ataque y baja defensa");

            System.out.println("Daño del ataque físico: " + damageDealt);
        } catch (POOBkemonException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    public void testSpecialAttackUsesEspeciales() {
        int initialHP = gardevoir.getCurrentPs();
        try {
            machamp.useMovement(specialMove, gardevoir);
            int damageDealt = initialHP - gardevoir.getCurrentPs();
            // El daño debería ser bajo ya que tenemos bajo ataque especial vs alta defensa especial
            assertTrue(damageDealt < 50,
                    "El ataque especial debería causar poco daño dado el bajo ataque especial y alta defensa especial");

            System.out.println("Daño del ataque especial: " + damageDealt);
        } catch (POOBkemonException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    public void testCompararDanoFisicoVsEspecial() {

        Pokemon gengar = new Pokemon("Gengar",100,324,251,394,240,273,350,PokemonType.FANTASMA,PokemonType.VENENO,94);
        Pokemon snorlax = new Pokemon("Snorlax",100,524,350,251,251,350,174,PokemonType.NORMAL,null,143);

        int initialHPforPhysical = snorlax.getCurrentPs();
        int initialHPforSpecial = initialHPforPhysical;

        Physical physicalForTest = new Physical("Golpe", "Ataque físico básico",
                30, 70, 100, PokemonType.NORMAL, 0);

        Special specialForTest = new Special("Rayo", "Ataque especial básico",
                30, 70, 100, PokemonType.NORMAL, 0);

        gengar.addMovement(physicalForTest);
        gengar.addMovement(specialForTest);

        try {
            Pokemon defenderForPhysical = snorlax.copy();
            Pokemon defenderForSpecial = snorlax.copy();

            gengar.useMovement(physicalForTest, defenderForPhysical);
            int physicalDamage = initialHPforPhysical - defenderForPhysical.getCurrentPs();

            gengar.useMovement(specialForTest, defenderForSpecial);
            int specialDamage = initialHPforSpecial - defenderForSpecial.getCurrentPs();

            assertNotEquals(physicalDamage, specialDamage,
                    "Los ataques físicos y especiales deberían causar daño diferente con las mismas condiciones");

            System.out.println("Daño físico: " + physicalDamage + ", Daño especial: " + specialDamage);
        } catch (POOBkemonException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    public void testVentajaDeTipo() {
        Pokemon charizard = new Pokemon("Charizard",90,360,293,348,280,295,328,PokemonType.FUEGO,PokemonType.VOLADOR,6);
        Pokemon blastoise = new Pokemon("Blastoise",100,362,291,295,328,339,280,PokemonType.AGUA,null,9);

        blastoise.increaseStat("specialAttack", 50);
        charizard.increaseStat("specialAttack", 50);
        blastoise.increaseStat("specialDefense", 0);
        charizard.increaseStat("specialDefense", 0);

        Special waterMove = new Special("Hidrobomba", "Potente chorro de agua",
                5, 110, 100, PokemonType.AGUA, 0);

        Special fireMove = new Special("Lanzallamas", "Potente llama",
                15, 90, 100, PokemonType.FUEGO, 0);

        blastoise.addMovement(waterMove);
        charizard.addMovement(fireMove);

        try {
            // Probar agua vs fuego (ventaja)
            int fireInitialHP = charizard.getCurrentPs();
            blastoise.useMovement(waterMove, charizard);
            int waterDamage = fireInitialHP - charizard.getCurrentPs();
            // Probar fuego vs agua (desventaja)
            int waterInitialHP = blastoise.getCurrentPs();
            charizard.useMovement(fireMove, blastoise);
            int fireDamage = waterInitialHP - blastoise.getCurrentPs();

            assertTrue(waterDamage > fireDamage,
                    "El ataque de agua debería causar significativamente más daño al Pokémon de fuego");

            System.out.println("Daño de agua a fuego: " + waterDamage);
            System.out.println("Daño de fuego a agua: " + fireDamage);
        } catch (POOBkemonException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    public void testAtributosAfectanDano() {

        Pokemon strongPhysical = machamp.copy();
        Pokemon weakPhysical = machamp.copy();

        int weak = weakPhysical.getAttack();
        System.out.println(weak);
        strongPhysical.increaseStat("attack", 100);
        weakPhysical.increaseStat("attack", -200);
        int weakAfter = weakPhysical.getAttack();
        System.out.println(weakAfter);

        try {
            Physical testMove = new Physical("Golpe", "Ataque básico",
                    30, 80, 100, PokemonType.NORMAL, 0);
            strongPhysical.addMovement(testMove);
            weakPhysical.addMovement(testMove);
            Pokemon defenderCopy1 = gardevoir.copy();
            Pokemon defenderCopy2 = gardevoir.copy();
            int initialHP1 = defenderCopy1.getCurrentPs();
            strongPhysical.useMovement(testMove, defenderCopy1);
            int strongDamage = initialHP1 - defenderCopy1.getCurrentPs();

            int initialHP2 = defenderCopy2.getCurrentPs();
            weakPhysical.useMovement(testMove, defenderCopy2);
            int weakDamage = initialHP2 - defenderCopy2.getCurrentPs();

            assertTrue(strongDamage > weakDamage * 2,
                    "El atacante con alto ataque debería causar más del doble de daño que el débil");

            System.out.println("Daño del Pokémon fuerte: " + strongDamage);
            System.out.println("Daño del Pokémon débil: " + weakDamage);
        } catch (POOBkemonException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}