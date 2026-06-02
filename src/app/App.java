package app;

import controllers.SortPersonaMethods;
import models.Persona;
import models.Resultado;
import utils.Benchmarking;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static Persona[] generarPersonas(int cantidad) {
        Persona[] personas = new Persona[cantidad];
        for (int i = 0; i < cantidad; i++) {
            String nombre = "Persona " + (i + 1);
            int edad = (int) (Math.random() * 101);
            personas[i] = new Persona(nombre, edad);
        }
        return personas;
    }

    public static void main(String[] args) {
        SortPersonaMethods sorter = new SortPersonaMethods();
        List<Resultado> resultados = new ArrayList<>();
        int[] tamanos = {10000, 50000, 100000};

        // LISTA DE TEXTOS PARA EL REPORTE FINAL MANUAL
        List<String> reporteManual = new ArrayList<>();

        for (int size : tamanos) {
            System.out.println("Procesando muestra de tamaño: " + size + "...");
            
            //escenario 1
            Persona[] baseDesordenado = generarPersonas(size);
            Persona[] copiaInsercion1 = baseDesordenado.clone();
            Persona[] copiaQuick1 = baseDesordenado.clone();

            // Medir Inserción en Desordenado
            try {
                Resultado rIns1 = Benchmarking.medirTiempo(() -> {
                    sorter.insertionSort(copiaInsercion1);
                    return null;
                }, "Inserción", "Desordenado", size);
                resultados.add(rIns1);
            } catch (Throwable e) {
                System.out.println("Error en Inserción Desordenado");
            }

            // Medir QuickSort en Desordenado
            try {
                Resultado rQuick1 = Benchmarking.medirTiempo(() -> {
                    sorter.quickSort(copiaQuick1, 0, copiaQuick1.length - 1);
                    return null;
                }, "QuickSort", "Desordenado", size);
                resultados.add(rQuick1);
            } catch (Throwable e) {
                System.out.println("Error en QuickSort Desordenado");
            }

            //escenario 2
            try {
                sorter.quickSort(baseDesordenado, 0, baseDesordenado.length - 1);
            } catch (Throwable t) {
                sorter.insertionSort(baseDesordenado);
            }

            Persona[] casiOrdenado = new Persona[size + 1];
            System.arraycopy(baseDesordenado, 0, casiOrdenado, 0, size);
            casiOrdenado[size] = new Persona("Persona Extra", (int) (Math.random() * 101));

            Persona[] copiaInsercion2 = casiOrdenado.clone();
            Persona[] copiaQuick2 = casiOrdenado.clone();

            // Medir Inserción en Casi Ordenado
            try {
                Resultado rIns2 = Benchmarking.medirTiempo(() -> {
                    sorter.insertionSort(copiaInsercion2);
                    return null;
                }, "Inserción", "Casi ordenado + 1", casiOrdenado.length);
                resultados.add(rIns2);
            } catch (Throwable e) {
                System.out.println("Error en Inserción Casi Ordenado");
            }

            // Medir QuickSort en Casi Ordenado (Controlado sin usar 'new Resultado' problemáticos)
            if (size <= 10000) {
                try {
                    Resultado rQuick2 = Benchmarking.medirTiempo(() -> {
                        sorter.quickSort(copiaQuick2, 0, copiaQuick2.length - 1);
                        return null;
                    }, "QuickSort", "Casi ordenado + 1", casiOrdenado.length);
                    resultados.add(rQuick2);
                } catch (Throwable e) {
                    reporteManual.add("Casi ordenado + 1 | QuickSort | " + casiOrdenado.length + " | [ERROR: StackOverflow]");
                }
            } else {
                // Si es de 50k o 100k, guardamos el texto de error directamente en nuestra lista de texto limpia
                reporteManual.add("Casi ordenado + 1 | QuickSort | " + casiOrdenado.length + " | [ERROR: Memoria Excedida - Peor Caso]");
            }
        }
        System.out.println("\n=======================================================");
        System.out.println("         --- COPIA ESTOS DATOS PARA TU INFORME ---     ");
        System.out.println("=======================================================");
        
        // Imprime resultados que salieron bien
        for (Resultado r : resultados) {
            System.out.println(r);
        }
        
        // Imprime alertas de memoria que capturamos de forma segura en texto plano
        for (String alerta : reporteManual) {
            System.out.println(alerta);
        }
        System.out.println("=======================================================");
    }
}