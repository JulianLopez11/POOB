package src.domain;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class POOBkemon {
    private ArrayList<Trainer> trainers; // Lista de entrenadores
    private String entrenadorTurno;

    public POOBkemon(String modalidad) {
        trainers = new ArrayList<>();
        // lógica con la modalidad si es necesaria
    }

    public void turno() {
        // Lógica para los turnos
    }

    public void start() {
        // Iniciar juego
    }

    public void defaultPokemons() {
        // Pokemons por defecto
    }

    public void defaultItems() {
        // Items por defecto
    }

    public void defaultMovements() {
        // Movimientos por defecto
    }

    public void coin() throws POOBkemonException {
        if (trainers == null || trainers.isEmpty()) {
            throw new POOBkemonException(POOBkemonException.NO_TRAINERS_FOUND);
        }
        Random random = new Random();
        int resultado = random.nextInt(trainers.size());
        Trainer trainerSeleccionado = trainers.get(resultado);
        try {
            entrenadorTurno = trainerSeleccionado.getNombre();
            JOptionPane.showMessageDialog(null,
                    "El entrenador que empieza es: " + entrenadorTurno,
                    "Lanzamiento de Moneda",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al seleccionar el entrenador: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
