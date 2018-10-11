package no.nav.fo.veilarbtiltakinfo;

import no.nav.fo.veilarbtiltakinfo.client.OppfolgingClient;
import no.nav.fo.veilarbtiltakinfo.client.OppfolgingStatus;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class TiltakService {

    @Inject
    private OppfolgingClient oppfolgingClient;

    public OppfolgingStatus hentServiceGruppe(String fnr) {
        return oppfolgingClient.serviceGruppeKode(fnr);
    }
}
