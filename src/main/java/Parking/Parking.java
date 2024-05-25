package Parking;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class Parking {

    private final String[][] plazas;
    private final String nombre;

    public Parking(String nombre, int totalPlazas) {
        this.nombre = nombre;
        this.plazas = new String[totalPlazas][2]; 
    }

    public String getNombre() {
        return nombre;
    }

    public void entrada(String matricula, int plaza) throws ParkingException {
        if (plaza >= plazas.length || plaza < 0) {
            throw new ParkingException("Plaza inexistente", matricula);
        }

        if (matricula == null || matricula.length() < 4) {
            throw new ParkingException("Matrícula incorrecta", matricula);
        }

        if (plazas[plaza][0] != null) {
            throw new ParkingException("Plaza ocupada", matricula);
        }

        for (String[] plazaActual : plazas) {
            if (plazaActual[0] != null && plazaActual[0].equals(matricula)) {
                throw new ParkingException("Matrícula repetida", matricula);
            }
        }

        plazas[plaza][0] = matricula;
        plazas[plaza][1] = String.valueOf(System.currentTimeMillis());
    }

    public String salida(String matricula) throws ParkingException {
        boolean matriculaEncontrada = false;
        int plaza = -1;
        for (int i = 0; i < plazas.length; i++) {
            if (plazas[i][0] != null && plazas[i][0].equals(matricula)) {
                plaza = i;
                matriculaEncontrada = true;
                break;
            }
        }

        if (!matriculaEncontrada) {
            throw new ParkingException("Matrícula no existente", matricula);
        }

        long tiempoIngreso = Long.parseLong(plazas[plaza][1]);
        long tiempoSalida = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoSalida - tiempoIngreso;
        long minutos = (tiempoTranscurrido / 1000) / 60;
        long horas = minutos / 60;
        minutos = minutos % 60;

        double costoPorHora = 1200.0;
        double costoPorMinuto = 20.0;
        double costoTotal = (horas * costoPorHora) + (minutos * costoPorMinuto);

        plazas[plaza][0] = null;
        plazas[plaza][1] = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tiempoSalidaFormateado = sdf.format(new Date(tiempoSalida));
        JOptionPane.showMessageDialog(null, "\n Tiempo de salida: "+ tiempoSalidaFormateado+"\n"+
                                        "Tiempo total dentro: " + horas + " horas y " + minutos + " minutos"+"\n"+
                                        "Costo total: $" + String.format("%.2f", costoTotal));

  

        return "Plaza " + plaza;
    }

    public int getPlazasTotales() {
        return plazas.length;
    }

    public int getPlazasOcupadas() {
        int ocupadas = 0;
        for (String[] plaza : plazas) {
            if (plaza[0] != null) {
                ocupadas++;
            }
        }
        return ocupadas;
    }

    public int getPlazasLibres() {
        return getPlazasTotales() - getPlazasOcupadas();
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder("Parking " + nombre + "\n");
        cadena.append("-------------------\n");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int filas = 100;
        int columnas = 5;

        for (int i = 0; i <= filas; i++) {
            for (int j = 0; j <= columnas; j++) {
                int plazaIndex = i * columnas + j;
                if (plazaIndex < plazas.length) {
                    cadena.append("Plaza ").append(plazaIndex).append(": ");
                    if (plazas[plazaIndex][0] == null) {
                        cadena.append("(vacía)");
                    } else {
                        cadena.append(plazas[plazaIndex][0])
                              .append(" (Ingreso: ")
                              .append(sdf.format(new Date(Long.parseLong(plazas[plazaIndex][1]))))
                              .append(")");
                    }
                    if (j < columnas - 1) {
                        cadena.append("    |    ");
                    }
                }
            }
            cadena.append("\n\n");}
        cadena.append("-------------------\n");

        return cadena.toString();
    }
}
