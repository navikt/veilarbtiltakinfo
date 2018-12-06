package no.nav.fo.veilarbtiltakinfo.takontaktmedmeg;

import no.nav.fo.veilarbtiltakinfo.DatabaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;

public class TaKontaktMedMegDaoDatabaseTest extends DatabaseTest {

    @Inject
    private TaKontaktMedMegDao taKontaktMedMegDao;

    @BeforeClass
    public static void startSpring() {
        setupContext(TaKontaktMedMegDao.class);
    }

    @Test
    public void skalOppretteKontaktInfo() {
        taKontaktMedMegDao.create(brukerKontakt());
    }

    private Bruker brukerKontakt() {
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