package no.nav.fo.veilarbtiltakinfo.bruker;

import no.nav.sbl.jdbc.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;

import static java.util.Optional.of;

public class BrukerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrukerDao.class);

    private Database database;

    @Inject
    public BrukerDao(Database database) {
        this.database = database;
    }

    long opprett(Bruker bruker) {
        long brukerId = database.nesteFraSekvens("BRUKER_SEQ");
        database.update("INSERT INTO BRUKER (" +
                "bruker_id, " +
                "bruker_tidspunkt, " +
                "fnr, " +
                "oppfolgingsenhet_id, " +
                "oppfolgingsenhet_navn, " +
                "under_oppfolging, " +
                "maal, " +
                "tiltak_en, " +
                "tiltak_to) " +
                "VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?)",
            brukerId,
            bruker.getFnr(),
            bruker.getOppfolgingsEnhetId(),
            bruker.getOppfolgingsEnhetNavn(),
            bruker.getUnderOppfolging(),
            bruker.getMaal(),
            bruker.getTiltakEn(),
            bruker.getTiltakTo()
        );

        LOGGER.info("lagret bruker med id={}", brukerId);

        return brukerId;
    }

}
