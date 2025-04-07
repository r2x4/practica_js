package com.mycompany;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GenerateInfoFiles {  // Cambiado de Main a GenerateInfoFiles
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\nGENERADOR DE ARCHIVOS BASE");
        System.out.println("1. Crear archivo vendedores.csv");
        System.out.println("2. Crear archivo productos.csv");
        System.out.println("3. Crear archivo ventas.csv");
        System.out.println("4. Crear TODOS los archivos");
        System.out.println("5. Volver al menú principal");
        System.out.print("Opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        try {
            switch(opcion) {
                case 1:
                    generarCabeceraVendedores("Datos/vendedores.csv");
                    break;
                case 2:
                    generarCabeceraProductos("Datos/productos.csv");
                    break;
                case 3:
                    generarCabeceraVentas("Datos/ventas.csv");
                    break;
                case 4:
                    generarTodosLosArchivos();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void generarTodosLosArchivos() throws IOException {
        generarCabeceraVendedores("Datos/vendedores.csv");
        generarCabeceraProductos("Datos/productos.csv");
        generarCabeceraVentas("Datos/ventas.csv");
        System.out.println("\nTodos los archivos creados en carpeta Datos/");
    }

    private static void generarCabeceraVendedores(String filename) throws IOException {
        try(FileWriter writer = new FileWriter(filename)) {
            writer.write("id,nombre,idIdentificacion,ciudad,pais\n");
            System.out.println("Archivo creado: " + filename);
        }
    }

    private static void generarCabeceraProductos(String filename) throws IOException {
        try(FileWriter writer = new FileWriter(filename)) {
            writer.write("id,nombre,marca,valorCompra,stock\n");
            System.out.println("Archivo creado: " + filename);
        }
    }

    private static void generarCabeceraVentas(String filename) throws IOException {
        try(FileWriter writer = new FileWriter(filename)) {
            writer.write("id,id_vendedor,id_producto,cantidad,precio_venta\n");
            System.out.println("Archivo creado: " + filename);
        }
    }
}
