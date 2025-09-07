/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.luisa.jdbc02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class JDBC02 {

    public static void main(String[] args) {
        String url = "jdbc:mysql://relational.fel.cvut.cz:3306/Credit"
                + "?useSSL=false&serverTimezone=UTC";
        String user = "guest";
        String password = "ctu-relational";
        
    try (Connection conn = DriverManager.getConnection(url, user, password)) {
        System.out.println(" Conexión exitosa a la BD Credit");
        
        Statement s = conn.createStatement();
        ResultSet tablas = s.executeQuery("SHOW TABLES;");
        System.out.println("Tablas encontradas:");

        int contador = 0;
        String primeraTabla = null;
        
        while (tablas.next()) {
            String tabla = tablas.getString(1);
            System.out.println("- " + tabla);
            
            if (contador == 0) {
                primeraTabla = tabla;
            }
            
            if (contador < 2) {
                try (Statement s2 = conn.createStatement();
                        ResultSet columnas = s2.executeQuery("DESC `" + tabla + "`;")) {
                    System.out.println(" Columnas:");
                    while (columnas.next()) {
                        String nombreColumna = columnas.getString("Field");
                        String tipoColumna = columnas.getString("Type");
                        System.out.println(" • " + nombreColumna + " (" + tipoColumna + ")");
                    }
                }
            }
            contador++;
        }
        System.out.println("Consulta 1: categorías cuyo código empieza por 'A'");
        String sqlTexto = "SELECT category_no, category_desc, category_code FROM category WHERE category_code LIKE ? LIMIT 10;";
        try (PreparedStatement ps = conn.prepareStatement(sqlTexto)) {
            ps.setString(1, "A%");
            try (ResultSet r1 = ps.executeQuery()) {
                while (r1.next()) {
                    System.out.println("CatID: " + r1.getInt("category_no")
                            + ", Descripción: " + r1.getString("category_desc")
                            + ", Código: " + r1.getString("category_code"));
                }
            }
        }
        System.out.println(" Consulta 2: cargos con monto mayor a 1000");
        String sqlNumero = "SELECT charge_no, member_no, charge_amt FROM charge WHERE charge_amt > ? LIMIT 10;";
        try (PreparedStatement ps = conn.prepareStatement(sqlNumero)) {
            ps.setDouble(1, 1000.0);
            try (ResultSet r2 = ps.executeQuery()) {
                while (r2.next()) {
                    System.out.println("ChargeID: " + r2.getInt("charge_no")
                            + ", MemberID: " + r2.getInt("member_no")
                            + ", Monto: " + r2.getDouble("charge_amt"));
                }
            }
        }
        System.out.println("Consulta 3: primeros 5 miembros registrados");
        String sqlMiembros = "SELECT member_no, lastname, firstname FROM member LIMIT 5;";
        try (PreparedStatement ps = conn.prepareStatement(sqlMiembros);
                ResultSet r3 = ps.executeQuery()) {
            while (r3.next()) {
                System.out.println("MemberID: " + r3.getInt("member_no")
                        + ", Nombre: " + r3.getString("firstname")
                        + ", Apellido: " + r3.getString("lastname"));
            }
        }
        System.out.println(" Consulta 4: pagos mayores a 500 en 1990");
        String sqlPagos = "SELECT payment_no, member_no, payment_amt, payment_dt FROM payment WHERE payment_amt > ? AND YEAR(payment_dt) = ? LIMIT 10;";
        try (PreparedStatement ps = conn.prepareStatement(sqlPagos)) {
            ps.setDouble(1, 500.0);
            ps.setInt(2, 1990);
            try (ResultSet r4 = ps.executeQuery()) {
                while (r4.next()) {
                    System.out.println("PaymentID: " + r4.getInt("payment_no")
                            + ", MemberID: " + r4.getInt("member_no")
                            + ", Monto: " + r4.getDouble("payment_amt")
                            + ", Fecha: " + r4.getDate("payment_dt"));
                }
            }
        }
    } catch (SQLException e) {
        System.err.println(" Error en la conexión o consulta: " + e.getMessage());
    }
    }
}

