package com.mycompany;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Main {

    private static final String CARPETA_DATOS = "Datos/";

    public static void main(String[] args) {
        String vendedoresFile = "vendedores.csv";
        String productosFile = "productos.csv";
        String ventasFile = "ventas.csv";

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMENÚ PRINCIPAL");
            System.out.println("1. Ingresar vendedores");
            System.out.println("2. Ingresar productos");
            System.out.println("3. Ingresar ventas");
            System.out.println("4. Generar archivos base (CSV)");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        ingresarVendedores(vendedoresFile, scanner);
                        break;
                    case "2":
                        ingresarProductos(productosFile, scanner);
                        break;
                    case "3":
                        ingresarVentas(ventasFile, scanner);
                        break;
                    case "4":
                        menuGenerarArchivosBase(scanner);
                        break;
                    case "5":
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (IOException e) {
                System.out.println("Error procesando archivos: " + e.getMessage());
            }
        }
    }

    // Métodos para ingresar datos
    public static void ingresarVendedores(String filename, Scanner scanner) throws IOException {
        List<Vendedor> vendedores = new ArrayList<>();

        System.out.print("\nIngrese la cantidad de vendedores a registrar: ");
        int cantidad = leerEnteroPositivo(scanner);

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nRegistrando vendedor #" + (i + 1));
            Vendedor vendedor = Vendedor.crearVendedor(scanner);
            vendedores.add(vendedor);
        }

        try (FileWriter writer = new FileWriter(CARPETA_DATOS + filename, true)) {
            for (Vendedor v : vendedores) {
                writer.write(String.format("%d,%s,%s,%s,%s%n",
                    v.getId(), v.getNombre(), v.getIdIdentificacion(),
                    v.getCiudad(), v.getPais()));
            }
        }

        System.out.println("\n✓ " + cantidad + " vendedores registrados exitosamente");
    }

    public static void ingresarProductos(String filename, Scanner scanner) throws IOException {
        List<Producto> productos = new ArrayList<>();

        System.out.print("\nIngrese la cantidad de productos a registrar: ");
        int cantidad = leerEnteroPositivo(scanner);

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nRegistrando producto #" + (i + 1));
            Producto producto = Producto.crearProducto(scanner);
            productos.add(producto);
        }

        try (FileWriter writer = new FileWriter(CARPETA_DATOS + filename, true)) {
            for (Producto p : productos) {
                writer.write(String.format("%d,%s,%s,%.2f,%d%n",
                    p.getId(), p.getNombre(), p.getMarca(),
                    p.getValorCompra(), p.getStock()));
            }
        }

        System.out.println("\n✓ " + cantidad + " productos registrados exitosamente");
    }

    public static void ingresarVentas(String filename, Scanner scanner) throws IOException {
        List<Venta> ventas = new ArrayList<>();

        System.out.print("\nIngrese la cantidad de ventas a registrar: ");
        int cantidad = leerEnteroPositivo(scanner);

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nRegistrando venta #" + (i + 1));
            Venta venta = Venta.crearVenta(scanner);
            ventas.add(venta);
        }

        try (FileWriter writer = new FileWriter(CARPETA_DATOS + filename, true)) {
            for (Venta v : ventas) {
                writer.write(String.format("%d,%d,%d,%d,%.2f%n",
                    v.getId(), v.getIdVendedor(), v.getIdProducto(),
                    v.getCantidad(), v.getPrecioVenta()));
            }
        }

        System.out.println("\n✓ " + cantidad + " ventas registradas exitosamente");
    }

    // Métodos para generación de archivos base
    private static void menuGenerarArchivosBase(Scanner scanner) throws IOException {
        while (true) {
            System.out.println("\nGENERAR ARCHIVOS BASE");
            System.out.println("1. Generar todos los archivos");
            System.out.println("2. Generar solo vendedores.csv");
            System.out.println("3. Generar solo productos.csv");
            System.out.println("4. Generar solo ventas.csv");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione opción: ");
            String subOpcion = scanner.nextLine();

            switch (subOpcion) {
                case "1":
                    generarTodosArchivos(scanner);
                    return;
                case "2":
                    generarArchivoIndividual("vendedores.csv", 
                        "id,nombre,idIdentificacion,ciudad,pais\n", scanner);
                    return;
                case "3":
                    generarArchivoIndividual("productos.csv", 
                        "id,nombre,marca,valorCompra,stock\n", scanner);
                    return;
                case "4":
                    generarArchivoIndividual("ventas.csv", 
                        "id,id_vendedor,id_producto,cantidad,precio_venta\n", scanner);
                    return;
                case "5":
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void generarTodosArchivos(Scanner scanner) throws IOException {
        if (confirmarSobrescritura("todos los archivos", scanner)) {
            generarArchivo("vendedores.csv", "id,nombre,idIdentificacion,ciudad,pais\n");
            generarArchivo("productos.csv", "id,nombre,marca,valorCompra,stock\n");
            generarArchivo("ventas.csv", "id,id_vendedor,id_producto,cantidad,precio_venta\n");
            System.out.println("\n✓ Todos los archivos generados exitosamente");
        }
    }

    private static void generarArchivoIndividual(String filename, String cabecera, Scanner scanner) 
            throws IOException {
        if (confirmarSobrescritura(filename, scanner)) {
            generarArchivo(filename, cabecera);
            System.out.println("\n✓ " + filename + " generado exitosamente");
        }
    }

    private static boolean confirmarSobrescritura(String archivo, Scanner scanner) {
        File file = new File(CARPETA_DATOS + archivo);
        if (file.exists()) {
            System.out.print("\nEl archivo " + archivo + " ya existe. ¿Desea sobrescribirlo? (S/N): ");
            return scanner.nextLine().equalsIgnoreCase("S");
        }
        return true;
    }

    private static void generarArchivo(String filename, String cabecera) throws IOException {
        crearDirectorioSiNoExiste();
        try (FileWriter writer = new FileWriter(CARPETA_DATOS + filename)) {
            writer.write(cabecera);
        }
    }

    private static void crearDirectorioSiNoExiste() {
        File directorio = new File(CARPETA_DATOS);
        if (!directorio.exists()) {
            if (directorio.mkdir()) {
                System.out.println("Directorio " + CARPETA_DATOS + " creado");
            } else {
                System.out.println("No se pudo crear el directorio " + CARPETA_DATOS);
            }
        }
    }

    private static int leerEnteroPositivo(Scanner scanner) {
        while (true) {
            try {
                int numero = Integer.parseInt(scanner.nextLine());
                if (numero > 0) {
                    return numero;
                } else {
                    System.out.println("Debe ingresar un número mayor que cero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
            }
            System.out.print("Por favor ingrese un valor válido: ");
        }
    }
}