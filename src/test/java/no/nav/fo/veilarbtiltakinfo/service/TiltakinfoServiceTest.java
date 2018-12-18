package no.nav.fo.veilarbtiltakinfo.service;

import no.nav.fo.veilarbtiltakinfo.Mapper;
import no.nav.fo.veilarbtiltakinfo.dao.Bruker;
import no.nav.fo.veilarbtiltakinfo.dao.BrukerDao;
import no.nav.fo.veilarbtiltakinfo.dto.BrukerDto;
import no.nav.fo.veilarbtiltakinfo.dto.TiltakDto;
import no.nav.fo.veilarbtiltakinfo.oppfolging.OppfolgingClient;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TiltakinfoServiceTest {

    private TiltakinfoService tiltakinfoService;
    private BrukerDto testBrukerDto;
    private Bruker testBruker;
    private long testBrukerId;

    @Before
    public void setup() {
        OppfolgingClient oppfolgingClient = mock(OppfolgingClient.class);
        BrukerDao brukerDao = mock(BrukerDao.class);

        this.testBrukerDto = brukerDto();
        this.testBruker = Mapper.map(this.testBrukerDto);
        this.testBrukerId = 1L;

        when(brukerDao.opprett(this.testBruker)).thenReturn(this.testBrukerId);
        when(brukerDao.hent(this.testBrukerId)).thenReturn(this.testBruker);

        this.tiltakinfoService = new TiltakinfoService(brukerDao, oppfolgingClient);
    }

    @Test
    public void skalOppretteOgReturnereBrukerDto() {
        BrukerDto brukerDtoFraDb = tiltakinfoService.opprettBruker(this.testBrukerDto);

        assertThat(brukerDtoFraDb.getFnr()).isEqualTo(this.testBrukerDto.getFnr());
        assertThat(brukerDtoFraDb.getOppfolgingsEnhetId()).isEqualTo(this.testBrukerDto.getOppfolgingsEnhetId());
        assertThat(brukerDtoFraDb.getUnderOppfolging()).isEqualTo(this.testBrukerDto.getUnderOppfolging());
        assertThat(brukerDtoFraDb.getMaal()).isEqualTo(this.testBrukerDto.getMaal());
        assertThat(brukerDtoFraDb.getTiltak()).isEqualTo(this.testBrukerDto.getTiltak());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerUtenFnr() {
        tiltakinfoService.opprettBruker(brukerDto().toBuilder().fnr(null).build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerUtenOppfolgingsEnhetId() {
        tiltakinfoService.opprettBruker(brukerDto().toBuilder().oppfolgingsEnhetId(null).build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerUtenUnderOppfolging() {
        tiltakinfoService.opprettBruker(brukerDto().toBuilder().underOppfolging(null).build());
    }

    @Test(expected = NullPointerException.class)
    public void skalKasteExceptionHvisOppretterBrukerOgTiltakErNull() {
        tiltakinfoService.opprettBruker(brukerDto().toBuilder().tiltak(null).build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerOgTiltakErTomListe() {
        tiltakinfoService.opprettBruker(brukerDto().toBuilder().tiltak(new ArrayList<TiltakDto>()).build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerMedEttTiltak() {
        tiltakinfoService.opprettBruker(brukerDto().toBuilder().tiltak(singletonList(brukerDto().getTiltak().get(0))).build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerMedTreTiltak() {
        tiltakinfoService.opprettBruker(brukerDto().toBuilder().tiltak(
            Stream.concat(
                brukerDto().getTiltak().stream(),
                Stream.of(TiltakDto.builder().nokkel("tiltak-opplaering-ny-arbeidsgiver").build())).collect(Collectors.toList())
        ).build());
    }

    private BrukerDto brukerDto() {
        return BrukerDto.builder()
            .fnr("11111111111")
            .oppfolgingsEnhetId("0219")
            .underOppfolging(true)
            .maal("Samme jobb")
            .tiltak(Arrays.asList(
                TiltakDto.builder().nokkel("tiltak-tilrettelegging").build(),
                TiltakDto.builder().nokkel("tiltak-arbeidsrettet-rehabilitering").build()
            ))
            .build();
    }
}
