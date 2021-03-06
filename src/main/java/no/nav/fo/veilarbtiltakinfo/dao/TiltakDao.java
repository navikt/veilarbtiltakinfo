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
import java.util.List;
import java.util.Optional;

public class TiltakDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TiltakDao.class);

    private Database database;

    @Inject
    public TiltakDao(Database database) {
        this.database = database;
    }

    long opprett(long brukerId, Tiltak tiltak) {
        long tiltakId = database.nesteFraSekvens("TILTAK_SEQ");
        database.update("INSERT INTO TILTAK (" +
                "tiltak_id, " +
                "bruker_id, " +
                "tiltak_nokkel) " +
                "VALUES (?, ?, ?)",
            tiltakId,
            brukerId,
            tiltak.getNokkel()
        );

        LOGGER.info("lagret tiltak med id={}", tiltakId);

        return tiltakId;
    }

    List<Tiltak> hentTiltak(long tiltakId) {
        return hent("tiltak_id", tiltakId);
    }

    List<Tiltak> hentTiltakForBruker(long brukerId) {
        return hent("bruker_id", brukerId);
    }

    private List<Tiltak> hent(String felt, long id) {
        return Optional.of(id)
            .map(idArg -> database.query("SELECT * FROM TILTAK WHERE " + felt + " = ?", this::map, idArg))
            .map(ValidationUtils::validate)
            .orElseThrow(() -> new WebApplicationException(FeilType.UGYLDIG_HANDLING.getStatus()));
    }

    @SneakyThrows
    private Tiltak map(ResultSet resultSet) {
        return Tiltak.builder()
            .nokkel(resultSet.getString("tiltak_nokkel"))
            .build();
    }
}
