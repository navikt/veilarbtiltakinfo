package no.nav.fo.veilarbtiltakinfo;

import no.nav.common.auth.SubjectHandler;
import no.nav.fo.veilarbtiltakinfo.client.OppfolgingClient;
import no.nav.fo.veilarbtiltakinfo.client.OppfolgingStatus;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

import static java.util.Optional.ofNullable;

@Component
public class TiltakService {

    public static final String FNR_QUERY_PARAM = "fnr";

    @Inject
    private OppfolgingClient oppfolgingClient;

    @Inject
    private Provider<HttpServletRequest> requestProvider;

    public OppfolgingStatus hentServiceGruppe() {
        return oppfolgingClient.serviceGruppeKode(getFnr());
    }

    private String getFnr() {
        String fnr = requestProvider.get().getParameter(FNR_QUERY_PARAM);
        return ofNullable(fnr).orElseGet(() ->
            SubjectHandler.getIdent().orElseThrow(IllegalArgumentException::new)
        );

    }
}
