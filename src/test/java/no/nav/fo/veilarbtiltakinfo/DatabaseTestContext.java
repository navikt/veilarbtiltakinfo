package no.nav.fo.veilarbtiltakinfo;

import no.nav.dialogarena.config.fasit.DbCredentials;

import static no.nav.fo.veilarbtiltakinfo.config.DataSourceConfig.*;

public class DatabaseTestContext {

    static void setDataSourceProperties(DbCredentials dbCredentials) {
        System.setProperty(VEILARBTILTAKINFODB_URL, dbCredentials.url);
        System.setProperty(VEILARBTILTAKINFODB_USERNAME, dbCredentials.getUsername());
        System.setProperty(VEILARBTILTAKINFODB_PASSWORD, dbCredentials.getPassword());
    }

    static void setInMemoryDataSourceProperties() {
        setDataSourceProperties(inMemoryConfig());
    }

    public static DbCredentials inMemoryConfig() {
        return new DbCredentials()
            .setUrl("jdbc:h2:mem:veilarbtiltakinfo;DB_CLOSE_DELAY=-1;MODE=Oracle")
            .setUsername("sa")
            .setPassword("password");
    }
}
