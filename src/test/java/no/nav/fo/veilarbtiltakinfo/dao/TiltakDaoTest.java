package no.nav.fo.veilarbtiltakinfo.dao;

import no.nav.fo.veilarbtiltakinfo.DatabaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static no.nav.fo.veilarbtiltakinfo.dao.BrukerDaoTest.bruker;
import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(tiltakFraDb).hasSize(1);
        assertThat(tiltakFraDb.get(0)).isEqualTo(tiltak);
    }

    @Test
    public void skalOppretteOgHenteTiltakForBruker() {
        Bruker bruker = bruker();
        Set<Tiltak> tiltak = new HashSet<>(bruker.getTiltak());
        long brukerId = brukerDao.opprett(bruker);

        Set<Tiltak> tiltakFraDb = new HashSet<>(tiltakDao.hentTiltakForBruker(brukerId));

        assertThat(tiltakFraDb).hasSize(2);
        assertThat(tiltakFraDb).isEqualTo(tiltak);
    }

    private Tiltak tiltak(String nokkel) {
        return Tiltak.builder().nokkel(nokkel).build();
    }
}
