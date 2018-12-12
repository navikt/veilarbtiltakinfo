package no.nav.fo.veilarbtiltakinfo.bruker;

import no.nav.fo.veilarbtiltakinfo.DatabaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static no.nav.fo.veilarbtiltakinfo.bruker.BrukerDaoTest.bruker;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TiltakDaoTest extends DatabaseTest {

    @Inject
    private BrukerDao brukerDao;

    @Inject
    private TiltakDao tiltakDao;

    @BeforeClass
    public static void startSpring() {
        setupContext(BrukerDao.class, TiltakDao.class);
    }

    @Test
    public void skalOppretteOgHenteTiltak() {
        long brukerId = brukerDao.opprett(bruker());
        Tiltak tiltak = tiltak("tiltak-tilrettelegging");
        long tiltakId = tiltakDao.opprett(brukerId, tiltak);

        List<Tiltak> tiltakFraDb = tiltakDao.hentTiltak(tiltakId);

        assertThat(tiltakFraDb, hasSize(1));
        assertThat(tiltakFraDb.get(0).getNokkel(), equalTo(tiltak.getNokkel()));
    }

    @Test
    public void skalOppretteOgHenteTiltakForBruker() {
        Bruker bruker = bruker();
        List<String> tiltak = bruker.getTiltak().stream().map(Tiltak::getNokkel).collect(Collectors.toList());
        long brukerId = brukerDao.opprett(bruker);

        List<String> tiltakNoklerFraDb = tiltakDao.hentTiltakForBruker(brukerId).stream().map(Tiltak::getNokkel).collect(Collectors.toList());

        assertThat(tiltakNoklerFraDb, hasSize(2));
        assertThat(tiltakNoklerFraDb, containsInAnyOrder(tiltak.toArray()));
    }

    private Tiltak tiltak(String nokkel) {
        return Tiltak.builder().nokkel(nokkel).build();
    }
}
