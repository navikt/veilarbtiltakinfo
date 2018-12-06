package no.nav.fo.veilarbtiltakinfo.bruker;

import no.nav.sbl.jdbc.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void create(Bruker bruker) {
        long brukerId = database.nesteFraSekvens("BRUKER_SEQ");
        Instant brukerTidspunkt = of(Instant.now())
            .map(d -> d.atZone(ZoneId.of("Europe/Oslo")))
            .map(ChronoZonedDateTime::toInstant)
            .orElse(null);
        database.update("INSERT INTO BRUKER (" +
                "bruker_id, " +
                "bruker_tidspunkt, " +
                "fnr, " +
                "oppfolgingsEnhetId, " +
                "oppfolgingsEnhetNavn, " +
                "under_oppfolging, " +
                "maal, " +
                "tiltakEn, " +
                "tiltakTo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            brukerId,
            Timestamp.from(brukerTidspunkt),
            bruker.getFnr(),
            bruker.getOppfolgingsEnhetId(),
            bruker.getOppfolgingsEnhetNavn(),
            bruker.getUnderOppfolging(),
            bruker.getMaal(),
            bruker.getTiltakEn(),
            bruker.getTiltakTo()
        );

        LOGGER.info("lagret kartlegging med id={}", brukerId);
    }

}
