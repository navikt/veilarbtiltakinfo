package no.nav.fo.veilarbtiltakinfo.bruker;

import no.nav.fo.veilarbtiltakinfo.DatabaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class BrukerDaoTest extends DatabaseTest {

    @Inject
    private BrukerDao brukerDao;

    @BeforeClass
    public static void startSpring() {
        setupContext(BrukerDao.class, TiltakDao.class);
    }

    @Test
    public void skalOppretteBruker() {
        Bruker bruker = bruker();
        long brukerId = brukerDao.opprett(bruker);

        Bruker brukerFraDb = brukerDao.hentBruker(brukerId);

        assertThat(brukerFraDb.getFnr(), equalTo(bruker.getFnr()));
        assertThat(brukerFraDb.getMaal(), equalTo(bruker.getMaal()));
        assertThat(brukerFraDb.getUnderOppfolging(), equalTo(bruker.getUnderOppfolging()));
    }

    @Test
    public void skalOppretteBrukerUtenMaal() {
        Bruker bruker = bruker().toBuilder().maal(null).build();
        long brukerId = brukerDao.opprett(bruker);

        Bruker brukerFraDb = brukerDao.hentBruker(brukerId);

        assertThat(brukerFraDb.getMaal(), isEmptyOrNullString());
    }

    @Test
    public void skalOppretteBrukerMedTiltak() {
        Bruker bruker = bruker();
        long brukerId = brukerDao.opprett(bruker);

        Bruker brukerFraDb = brukerDao.hentBruker(brukerId);

        List<String> tiltakNokler = bruker.getTiltak().stream().map(Tiltak::getNokkel).collect(Collectors.toList());
        List<String> tiltakNoklerFraDb = brukerFraDb.getTiltak().stream().map(Tiltak::getNokkel).collect(Collectors.toList());

        assertThat(tiltakNoklerFraDb, containsInAnyOrder(tiltakNokler.toArray()));

    }

    static Bruker bruker() {
        return Bruker.builder()
            .fnr("11111111111")
            .oppfolgingsEnhetId("0219")
            .oppfolgingsEnhetNavn("NAV Bærum")
            .underOppfolging(true)
            .maal("Samme jobb")
            .tiltak(Arrays.asList(
                Tiltak.builder().nokkel("tiltak-tilrettelegging").build(),
                Tiltak.builder().nokkel("tiltak-arbeidsrettet-rehabilitering").build()
            ))
            .build();
    }
}