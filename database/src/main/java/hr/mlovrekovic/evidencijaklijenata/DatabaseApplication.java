package hr.mlovrekovic.evidencijaklijenata;

import org.flywaydb.core.Flyway;

public class DatabaseApplication {

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .envVars()
                .locations("db/migration")
                .load();

        flyway.migrate();
    }

}
