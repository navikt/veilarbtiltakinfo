package no.nav.fo.veilarbtiltakinfo.service;

import no.nav.fo.veilarbtiltakinfo.Mapper;
import no.nav.fo.veilarbtiltakinfo.bruker.BrukerDao;
import no.nav.fo.veilarbtiltakinfo.dto.BrukerDto;
import no.nav.fo.veilarbtiltakinfo.oppfolging.OppfolgingClient;
import no.nav.fo.veilarbtiltakinfo.oppfolging.Oppfolgingsstatus;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static java.util.Optional.of;

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
        long brukerId = of(brukerDto)
            .map(Mapper::map)
            .map(bruker -> brukerDao.opprett(bruker))
            .get();

        return of(brukerDao.hent(brukerId)).map(Mapper::map).get();
    }
}
