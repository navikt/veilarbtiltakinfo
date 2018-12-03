package no.nav.fo.veilarbtiltakinfo;

import no.nav.dialogarena.config.fasit.DbCredentials;

import static no.nav.fo.veilarbtiltakinfo.config.DataSourceConfig.*;

public class DatabaseTestContext {

    public static void setDataSourceProperties(DbCredentials dbCredentials) {
        System.setProperty(VEILARBTILTAKINFO_URL, dbCredentials.url);
        System.setProperty(VEILARBTILTAKINFO_USERNAME, dbCredentials.getUsername());
        System.setProperty(VEILARBTILTAKINFO_PASSWORD, dbCredentials.getPassword());
    }

    public static void setInMemoryDataSourceProperties() {
        setDataSourceProperties(inMemoryConfig());
    }

    public static DbCredentials inMemoryConfig() {
        return new DbCredentials()
            .setUrl("jdbc:h2:mem:veilarbtiltakinfo;DB_CLOSE_DELAY=-1;MODE=Oracle")
            .setUsername("sa")
            .setPassword("password");
    }
}
