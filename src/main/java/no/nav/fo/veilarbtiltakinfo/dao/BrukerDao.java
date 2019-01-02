package no.nav.fo.veilarbtiltakinfo.dao;

import lombok.SneakyThrows;
import no.nav.apiapp.feil.FeilType;
import no.nav.sbl.jdbc.Database;
import no.nav.validation.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.sql.ResultSet;

import static java.util.Optional.of;

public class BrukerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrukerDao.class);

    private Database database;

    private TiltakDao tiltakDao;

    @Inject
    public BrukerDao(Database database, TiltakDao tiltakDao) {
        this.database = database;
        this.tiltakDao = tiltakDao;
    }

    public long opprett(Bruker bruker) {
        long brukerId = database.nesteFraSekvens("BRUKER_SEQ");
        database.update("INSERT INTO BRUKER (" +
                "bruker_id, " +
                "bruker_tidspunkt, " +
                "fnr, " +
                "oppfolgingsenhet_id, " +
                "under_oppfolging, " +
                "maal) " +
                "VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, ?)",
            brukerId,
            bruker.getFnr(),
            bruker.getOppfolgingsEnhetId(),
            bruker.getUnderOppfolging(),
            bruker.getMaal()
        );

        bruker.getTiltak().forEach(tiltak -> tiltakDao.opprett(brukerId, tiltak));

        LOGGER.info("lagret bruker med id={}", brukerId);

        return brukerId;
    }

    public Bruker hent(long brukerId) {
        return of(brukerId)
            .map(id -> database.queryForObject("SELECT * FROM BRUKER WHERE bruker_id = ?", this::map, id))
            .map(bruker -> bruker.toBuilder().tiltak(tiltakDao.hentTiltakForBruker(bruker.getBrukerId())).build())
            .orElseThrow(() -> new WebApplicationException(FeilType.UGYLDIG_HANDLING.getStatus()));
    }

    @SneakyThrows
    private Bruker map(ResultSet resultSet) {
        return Bruker.builder()
            .brukerId(resultSet.getLong("bruker_id"))
            .brukerTidspunkt(resultSet.getTimestamp("bruker_tidspunkt"))
            .fnr(resultSet.getString("fnr"))
            .oppfolgingsEnhetId(resultSet.getString("oppfolgingsenhet_id"))
            .underOppfolging(resultSet.getBoolean("under_oppfolging"))
            .maal(resultSet.getString("maal"))
            .build();
    }

    public Bruker hent(String fnr) {
        return of(fnr)
            .map(f -> database.queryForObject("SELECT * FROM BRUKER WHERE fnr = ?", this::map, f))
            .map(bruker -> bruker.toBuilder().tiltak(tiltakDao.hentTiltakForBruker(bruker.getBrukerId())).build())
            .orElseThrow(() -> new WebApplicationException(FeilType.FINNES_IKKE.getStatus()));
    }
}
