package no.nav.fo.veilarbtiltakinfo.bruker;

import no.nav.fo.veilarbtiltakinfo.DatabaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;

public class BrukerDaoTest extends DatabaseTest {

    @Inject
    private BrukerDao brukerDao;

    @BeforeClass
    public static void startSpring() {
        setupContext(BrukerDao.class);
    }

    @Test
    public void skalOppretteBruker() {
        brukerDao.opprett(bruker());
    }

    private Bruker bruker() {
        return Bruker.builder()
            .fnr("11111111111")
            .oppfolgingsEnhetId("0219")
            .oppfolgingsEnhetNavn("NAV Bærum")
            .underOppfolging(true)
            .maal("Samme jobb")
            .tiltakEn("tiltak-tilrettelegging")
            .tiltakTo("tiltak-arbeidsrettet-rehabilitering")
            .build();
    }
}