package no.nav.fo.veilarbtiltakinfo.service;

import no.nav.fo.veilarbtiltakinfo.dao.Bruker;
import no.nav.fo.veilarbtiltakinfo.dao.BrukerDao;
import no.nav.fo.veilarbtiltakinfo.dao.Tiltak;
import no.nav.fo.veilarbtiltakinfo.dto.BrukerDto;
import no.nav.fo.veilarbtiltakinfo.dto.TiltakDto;
import no.nav.fo.veilarbtiltakinfo.oppfolging.OppfolgingClient;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TiltakinfoServiceTest {

    private TiltakinfoService tiltakinfoService;
    private BrukerDto testBrukerDto;
    private Bruker testBruker;
    private long testBrukerId;
    private OppfolgingClient oppfolgingClient;
    private BrukerDao brukerDao;

    private void setup(BrukerDto dto) {
        this.oppfolgingClient = mock(OppfolgingClient.class);
        this.brukerDao = mock(BrukerDao.class);

        this.testBrukerDto = dto;
        this.testBruker = bruker();
        this.testBrukerId = 1L;

        when(brukerDao.opprett(this.testBruker)).thenReturn(this.testBrukerId);
        when(brukerDao.hent(this.testBrukerId)).thenReturn(this.testBruker);

        this.tiltakinfoService = new TiltakinfoService(brukerDao, oppfolgingClient);
    }

    @Test
    public void skalOppretteOgReturnereBrukerDto() {
        setup(brukerDto());
        BrukerDto brukerDtoFraDb = tiltakinfoService.opprettBruker(this.testBrukerDto);

        assertThat(brukerDtoFraDb.getFnr()).isEqualTo(this.testBrukerDto.getFnr());
        assertThat(brukerDtoFraDb.getOppfolgingsEnhetId()).isEqualTo(this.testBrukerDto.getOppfolgingsEnhetId());
        assertThat(brukerDtoFraDb.getUnderOppfolging()).isEqualTo(this.testBrukerDto.getUnderOppfolging());
        assertThat(brukerDtoFraDb.getMaal()).isEqualTo(this.testBrukerDto.getMaal());
        assertThat(brukerDtoFraDb.getTiltak()).isEqualTo(this.testBrukerDto.getTiltak());
        assertThat(brukerDtoFraDb.getErSykmeldt()).isEqualTo(this.testBrukerDto.getErSykmeldt());
        assertThat(brukerDtoFraDb.getHarArbeidsgiver()).isEqualTo(this.testBrukerDto.getHarArbeidsgiver());
        assertThat(brukerDtoFraDb.getServicegruppeKode()).isEqualTo(this.testBrukerDto.getServicegruppeKode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerUtenFnr() {
        BrukerDto brukerDto = brukerDto().toBuilder().fnr(null).build();
        setup(brukerDto);

        tiltakinfoService.opprettBruker(brukerDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerUtenOppfolgingsEnhetId() {
        BrukerDto brukerDto = brukerDto().toBuilder().oppfolgingsEnhetId(null).build();
        setup(brukerDto);

        tiltakinfoService.opprettBruker(brukerDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerUtenUnderOppfolging() {
        BrukerDto brukerDto = brukerDto().toBuilder().underOppfolging(null).build();
        setup(brukerDto);

        tiltakinfoService.opprettBruker(brukerDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerOgTiltakErNull() {
        BrukerDto brukerDto = brukerDto().toBuilder().tiltak(null).build();
        setup(brukerDto);

        tiltakinfoService.opprettBruker(brukerDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerOgTiltakErTomListe() {
        BrukerDto brukerDto = brukerDto().toBuilder().tiltak(new ArrayList<TiltakDto>()).build();
        setup(brukerDto);

        tiltakinfoService.opprettBruker(brukerDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerMedEttTiltak() {
        BrukerDto brukerDto = brukerDto().toBuilder().tiltak(singletonList(brukerDto().getTiltak().get(0))).build();
        setup(brukerDto);

        tiltakinfoService.opprettBruker(brukerDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerMedTreTiltak() {
        BrukerDto brukerDto = brukerDto().toBuilder().tiltak(
            Stream.concat(
                brukerDto().getTiltak().stream(),
                Stream.of(TiltakDto.builder().nokkel("tiltak-opplaering-ny-arbeidsgiver").build())).collect(toList())
        ).build();
        setup(brukerDto);

        tiltakinfoService.opprettBruker(brukerDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerMedTiltakDerNokkelErTomStreng() {
        BrukerDto brukerDto = brukerDto().toBuilder().tiltak(Arrays.asList(
            TiltakDto.builder().nokkel("").build(),
            TiltakDto.builder().nokkel("").build()
        )).build();
        setup(brukerDto);

        tiltakinfoService.opprettBruker(brukerDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skalKasteExceptionHvisOppretterBrukerMedTiltakDerNokkelErNull() {
        BrukerDto brukerDto = brukerDto().toBuilder().tiltak(Arrays.asList(
            TiltakDto.builder().nokkel(null).build(),
            TiltakDto.builder().nokkel(null).build()
        )).build();
        setup(brukerDto);

        tiltakinfoService.opprettBruker(brukerDto);
    }

    private String fnr = "11111111111";
    private String oppfolgingsEnhetId = "0219";
    private boolean underOppfolging = true;
    private String maal = "Samme jobb";
    private String forsteTiltak = "tiltak-tilrettelegging";
    private String andreTiltak = "tiltak-arbeidsrettet-rehabilitering";
    private Boolean erSykmeldt = true;
    private Boolean harArbeidsgiver = false;
    private String servicegruppeKode = "BFORM";

    private BrukerDto brukerDto() {
        return BrukerDto.builder()
            .fnr(this.fnr)
            .oppfolgingsEnhetId(this.oppfolgingsEnhetId)
            .underOppfolging(this.underOppfolging)
            .maal(this.maal)
            .tiltak(Arrays.asList(
                TiltakDto.builder().nokkel(this.forsteTiltak).build(),
                TiltakDto.builder().nokkel(this.andreTiltak).build()
            ))
            .erSykmeldt(this.erSykmeldt)
            .harArbeidsgiver(this.harArbeidsgiver)
            .servicegruppeKode(this.servicegruppeKode)
            .build();
    }

    private Bruker bruker() {
        return Bruker.builder()
            .fnr(this.fnr)
            .oppfolgingsEnhetId(this.oppfolgingsEnhetId)
            .underOppfolging(this.underOppfolging)
            .maal(this.maal)
            .tiltak(Arrays.asList(
                Tiltak.builder().nokkel(this.forsteTiltak).build(),
                Tiltak.builder().nokkel(this.andreTiltak).build()
            ))
            .erSykmeldt(this.erSykmeldt)
            .harArbeidsgiver(this.harArbeidsgiver)
            .serviceGruppeKode(this.servicegruppeKode)
            .build();
    }

    @Test
    public void skalReturnereTrueHvisBrukerFinnesIDatabase() {
        BrukerDto brukerDto = brukerDto();
        setup(brukerDto);

        when(this.brukerDao.hent(brukerDto.getFnr())).thenReturn(this.testBruker);

        assertThat(tiltakinfoService.brukerHarSendtMeldingTilNavKontor(brukerDto.getFnr())).isTrue();
    }

    @Test
    public void skalReturnereFalseHvisBrukerIkkeFinnesIDatabase() {
        BrukerDto brukerDto = brukerDto();
        setup(brukerDto);

        when(this.brukerDao.hent(brukerDto.getFnr())).thenThrow(EmptyResultDataAccessException.class);

        assertThat(tiltakinfoService.brukerHarSendtMeldingTilNavKontor(brukerDto.getFnr())).isFalse();
    }
}
