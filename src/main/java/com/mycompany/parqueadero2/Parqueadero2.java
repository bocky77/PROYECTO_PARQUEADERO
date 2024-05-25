package com.mycompany.parqueadero2;

import Parking.Parking;
import Parking.ParkingException;
import javax.swing.JOptionPane;

public class Parqueadero2 {

    static Parking parking;
    static int opcion;

    public static void main(String[] args) {
        configuracion();  
        do {
            menu();
            accion();
        } while (opcion != 5);  
    }

    public static void accion() {
        switch (opcion) {
            case 1 -> entradaCoche();
            case 2 -> salidaCoche();
            case 3 -> mostrarParking();
            case 4 -> configuracion(); 
            case 5 -> JOptionPane.showMessageDialog(null, "Fin del del programa\n\n");
            default -> JOptionPane.showMessageDialog(null, "Error en la opción\n\n");
        }
    }

    public static void entradaCoche() {
        boolean correcto = false;
        try {
            String m = JOptionPane.showInputDialog("Introduzca matrícula: ");
            int plaza = Integer.parseInt(JOptionPane.showInputDialog("Introduzca la plaza a aparcar: "));
           
            parking.entrada(m, plaza);
            correcto = true;
        } catch (ParkingException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMensaje() + "\nNo se realizó la entrada del coche con matrícula " + ex.getMatricula() + " en el parking");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Formato de número incorrecto");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR DESCONOCIDO.");
        } finally {
            if (!correcto)
                JOptionPane.showMessageDialog(null, "Se produjo un error.");
        }
    }

    public static void salidaCoche() {
        boolean correcto = false;
        try {
            String m = JOptionPane.showInputDialog("Introduzca la matrícula: ");
            String plaza = parking.salida(m);
            JOptionPane.showMessageDialog(null, "El coche " + m + " salió de la " + plaza + "\n\n" +
                    "Plazas totales: " + parking.getPlazasTotales() + "\n" +
                    "Plazas ocupadas: " + parking.getPlazasOcupadas() + "\n" +
                    "Plazas libres: " + parking.getPlazasLibres() + "\n\n");
            correcto = true;
        } catch (ParkingException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMensaje() + "\nNo se realizó la salida del coche con matrícula " + ex.getMatricula() + " del parking");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR DESCONOCIDO.");
        } finally {
            if (!correcto)
                JOptionPane.showMessageDialog(null, "Se produjo un error");
        }
    }

    public static void menu() {
        try {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "1) Entrada de coche\n" +
                    "2) Salida de coche\n" +
                    "3) Mostrar parking\n" +
                    "4) Configuración\n" +
                    "5) Salir del programa\n"
            ));
        } catch (Exception ex) {
            opcion = 0;
        }
    }

    public static void configuracion() {
        boolean configuradoCorrectamente = false;
        while (!configuradoCorrectamente) {
            try {
                String n= JOptionPane.showInputDialog("ingrese nombre del parqueadero ");
                int capacidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese capacidad del parqueadero:"));
                parking = new Parking(n, capacidad);
                configuradoCorrectamente = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Formato de número incorrecto. Por favor, intente nuevamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "ERROR DESCONOCIDO.");
            }
        }
    }

    public static void mostrarParking() {
        JOptionPane.showMessageDialog(null, parking + "\n\n");
    }
}
