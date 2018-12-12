package no.nav.fo.veilarbtiltakinfo.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static no.nav.fo.veilarbtiltakinfo.config.DataSourceConfig.VEILARBTILTAKINFODB_URL;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

@Component
public class DataSourceHelsesjekk implements Helsesjekk {

    private final JdbcTemplate database;

    @Inject
    public DataSourceHelsesjekk(JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public void helsesjekk() {
        database.queryForObject("SELECT 1 FROM DUAL", Long.class);
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String databaseUri = getRequiredProperty(VEILARBTILTAKINFODB_URL);
        return new HelsesjekkMetadata(
            "db",
            "Database: " + databaseUri,
            "Database for veilarbtiltakinfo",
            true
        );
    }
}
