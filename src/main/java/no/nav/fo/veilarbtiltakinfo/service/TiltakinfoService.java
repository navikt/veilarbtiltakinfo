package no.nav.fo.veilarbtiltakinfo.service;

import no.nav.apiapp.feil.FeilType;
import no.nav.fo.veilarbtiltakinfo.Mapper;
import no.nav.fo.veilarbtiltakinfo.dao.BrukerDao;
import no.nav.fo.veilarbtiltakinfo.dto.BrukerDto;
import no.nav.fo.veilarbtiltakinfo.dto.HarSendtMeldingDto;
import no.nav.fo.veilarbtiltakinfo.oppfolging.OppfolgingClient;
import no.nav.fo.veilarbtiltakinfo.oppfolging.Oppfolgingsstatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Component
public class TiltakinfoService {

    private OppfolgingClient oppfolgingClient;

    private BrukerDao brukerDao;

    @Inject
    public TiltakinfoService(BrukerDao brukerDao, OppfolgingClient oppfolgingClient) {
        this.oppfolgingClient = oppfolgingClient;
        this.brukerDao = brukerDao;
    }

    public Oppfolgingsstatus hentOppfolgingsstatus(String fnr) {
        return oppfolgingClient.oppfolgingsstatus(fnr);
    }

    public BrukerDto opprettBruker(BrukerDto brukerDto) {
        return of(brukerDto)
            .map(Mapper::map)
            .map(bruker -> brukerDao.opprett(bruker))
            .map(brukerId -> brukerDao.hent(brukerId))
            .map(Mapper::map)
            .get();
    }

    public HarSendtMeldingDto brukerHarSendtMeldingTilNavKontor(String fnr) {
        return of(fnr)
            .map(f -> {
                try {
                    return ofNullable(brukerDao.hent(f))
                        .map(bruker -> HarSendtMeldingDto.builder().brukerHarSendtMeldingTilNavKontor(true).build())
                        .orElseGet(() -> HarSendtMeldingDto.builder().brukerHarSendtMeldingTilNavKontor(false).build());
                } catch (EmptyResultDataAccessException e) {
                    return HarSendtMeldingDto.builder().brukerHarSendtMeldingTilNavKontor(false).build();
                }
            })
            .orElseThrow(() -> new WebApplicationException(FeilType.UGYLDIG_REQUEST.getStatus()));
    }
}
