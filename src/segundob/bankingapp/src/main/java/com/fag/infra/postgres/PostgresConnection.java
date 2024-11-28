package com.fag.infra.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {
    private static PostgresConnection instance;

    private Connection connection = null;

    private PostgresConnection() {
        System.out.println("Realizando conexao banco PG/SUPABASE");

        String url = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?user=postgres.khbldidfqjxxbcephtmx&password=WaRGO0BpRp1nlIAn";

        try {
            connection = DriverManager.getConnection(url, null, null);

            System.out.println("Banco Conectado");
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static synchronized PostgresConnection getInstance() {
        if (instance == null) {
            instance = new PostgresConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
