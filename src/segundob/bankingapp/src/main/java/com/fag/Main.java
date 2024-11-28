package com.fag;

import com.fag.infra.celcoin.CelcoinBassRepository;
import com.fag.infra.postgres.PgSupabase;
// import com.fag.infra.console.ConsoleUI;
import com.fag.infra.postgres.PostgresConnection;
import com.fag.infra.swing.SwingUserInterface;
// import com.fag.infra.testedb.UserTestDBRepository;
import com.fag.services.Application;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();

        PostgresConnection.getInstance();

        // ConsoleUI consoleUI = new ConsoleUI();
        SwingUserInterface swingUI = new SwingUserInterface();

        // UserTestDBRepository userRepo = new UserTestDBRepository();
        PgSupabase pgSupabase = new PgSupabase();

        CelcoinBassRepository celcoin = new CelcoinBassRepository();
        app.execute(swingUI, pgSupabase, celcoin);
    }
}