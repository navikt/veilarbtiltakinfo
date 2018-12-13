package no.nav.fo.veilarbtiltakinfo;

import no.nav.fo.veilarbtiltakinfo.client.OppfolgingClient;
import no.nav.fo.veilarbtiltakinfo.client.Oppfolgingsstatus;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class TiltakinfoService {

    @Inject
    private OppfolgingClient oppfolgingClient;

    Oppfolgingsstatus hentOppfolgingsstatus(String fnr) {
        return oppfolgingClient.oppfolgingsstatus(fnr);
    }
}
