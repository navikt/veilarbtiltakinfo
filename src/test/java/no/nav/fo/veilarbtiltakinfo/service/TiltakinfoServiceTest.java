package no.nav.fo.veilarbtiltakinfo.service;

import no.nav.fo.veilarbtiltakinfo.Mapper;
import no.nav.fo.veilarbtiltakinfo.dao.Bruker;
import no.nav.fo.veilarbtiltakinfo.dao.BrukerDao;
import no.nav.fo.veilarbtiltakinfo.dto.BrukerDto;
import no.nav.fo.veilarbtiltakinfo.dto.TiltakDto;
import no.nav.fo.veilarbtiltakinfo.oppfolging.OppfolgingClient;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
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

        MatcherAssert.assertThat(brukerDtoFraDb.getFnr(), Matchers.equalTo(this.testBrukerDto.getFnr()));
        MatcherAssert.assertThat(brukerDtoFraDb.getOppfolgingsEnhetId(), Matchers.equalTo(this.testBrukerDto.getOppfolgingsEnhetId()));
        MatcherAssert.assertThat(brukerDtoFraDb.getOppfolgingsEnhetNavn(), Matchers.equalTo(this.testBrukerDto.getOppfolgingsEnhetNavn()));
        MatcherAssert.assertThat(brukerDtoFraDb.getUnderOppfolging(), Matchers.equalTo(this.testBrukerDto.getUnderOppfolging()));
        MatcherAssert.assertThat(brukerDtoFraDb.getMaal(), Matchers.equalTo(this.testBrukerDto.getMaal()));
        MatcherAssert.assertThat(nokler(brukerDtoFraDb.getTiltak()), Matchers.containsInAnyOrder(nokler(this.testBrukerDto.getTiltak()).toArray()));
    }

    private List<String> nokler(List<TiltakDto> tiltakDtos) {
        return tiltakDtos.stream().map(TiltakDto::getNokkel).collect(toList());
    }

    private BrukerDto brukerDto() {
        return BrukerDto.builder()
            .fnr("11111111111")
            .oppfolgingsEnhetId("0219")
            .oppfolgingsEnhetNavn("NAV Bærum")
            .underOppfolging(true)
            .maal("Samme jobb")
            .tiltak(Arrays.asList(
                TiltakDto.builder().nokkel("tiltak-tilrettelegging").build(),
                TiltakDto.builder().nokkel("tiltak-arbeidsrettet-rehabilitering").build()
            ))
            .build();
    }
}
