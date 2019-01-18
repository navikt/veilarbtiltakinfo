package no.nav.fo.veilarbtiltakinfo.dao;

import no.nav.fo.veilarbtiltakinfo.DatabaseTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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

        Bruker brukerFraDb = brukerDao.hent(brukerId);

        assertThat(brukerFraDb.getFnr()).isEqualTo(bruker.getFnr());
        assertThat(brukerFraDb.getMaal()).isEqualTo(bruker.getMaal());
        assertThat(brukerFraDb.getUnderOppfolging()).isEqualTo(bruker.getUnderOppfolging());
        assertThat(brukerFraDb.getOppfolgingsEnhetId()).isEqualTo(bruker.getOppfolgingsEnhetId());
        assertThat(brukerFraDb.getErSykmeldt()).isEqualTo(bruker.getErSykmeldt());
        assertThat(brukerFraDb.getHarArbeidsgiver()).isEqualTo(bruker.getHarArbeidsgiver());
        assertThat(brukerFraDb.getServiceGruppeKode()).isEqualTo(bruker.getServiceGruppeKode());
    }

    @Test
    public void skalOppretteBrukerUtenMaal() {
        Bruker bruker = bruker().toBuilder().maal(null).build();
        long brukerId = brukerDao.opprett(bruker);

        Bruker brukerFraDb = brukerDao.hent(brukerId);

        assertThat(brukerFraDb.getMaal()).isNull();
    }

    @Test
    public void skalOppretteBrukerMedTiltak() {
        Bruker bruker = bruker();
        long brukerId = brukerDao.opprett(bruker);

        Bruker brukerFraDb = brukerDao.hent(brukerId);

        Set<Tiltak> tiltak = new HashSet<>(bruker.getTiltak());
        Set<Tiltak> tiltakFraDb = new HashSet<>(brukerFraDb.getTiltak());

        assertThat(tiltakFraDb).isEqualTo(tiltak);

    }

    static Bruker bruker() {
        return Bruker.builder()
            .fnr("11111111111")
            .erSykmeldt(true)
            .harArbeidsgiver(true)
            .serviceGruppeKode("BATT")
            .oppfolgingsEnhetId("0219")
            .underOppfolging(true)
            .maal("Samme jobb")
            .tiltak(Arrays.asList(
                Tiltak.builder().nokkel("tiltak-tilrettelegging").build(),
                Tiltak.builder().nokkel("tiltak-arbeidsrettet-rehabilitering").build()
            ))
            .build();
    }

    @Test
    public void skalReturnereBrukerHvisFinnesIDatabase() {
        brukerDao.opprett(bruker());
        Bruker bruker = brukerDao.hent(bruker().getFnr());

        assertThat(bruker).isNotNull();
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void skalKasteExceptionHvisBrukerIkkeFinnesIDatabase() {
        brukerDao.hent(bruker().getFnr());
    }

    @Test(expected = DuplicateKeyException.class)
    public void skalKasteExceptionHvisBrukerFinnesFraFor() {
        Bruker bruker = bruker();
        brukerDao.opprett(bruker);
        brukerDao.opprett(bruker.toBuilder().serviceGruppeKode("BFORM").build());
    }

    @Test
    public void skalOppretteToBrukereMedUlikFnr() {
        Bruker bruker = bruker();
        brukerDao.opprett(bruker);
        brukerDao.opprett(bruker.toBuilder().fnr("2222222222").build());
    }
}