package no.nav.fo.veilarbtiltakinfo;

import no.nav.apiapp.security.PepClient;
import no.nav.common.auth.SubjectHandler;
import no.nav.fo.veilarbtiltakinfo.client.Oppfolgingsstatus;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static java.util.Optional.ofNullable;

@Component
@Path("/")
@Produces("application/json")
public class TiltakinfoRS {

    public static final String FNR_QUERY_PARAM = "fnr";

    @Inject
    private Provider<HttpServletRequest> requestProvider;

    @Inject
    private TiltakService tiltakService;

    @Inject
    private PepClient pepClient;

    @GET
    @Path("servicegruppekode")
    public Oppfolgingsstatus hentOppfolgingsstatus() {
        String fnr = getFnr();
        pepClient.sjekkLeseTilgangTilFnr(fnr);
        return tiltakService.hentOppfolgingsstatus(fnr);
    }

    private String getFnr() {
        String fnr = requestProvider.get().getParameter(FNR_QUERY_PARAM);
        return ofNullable(fnr).orElseGet(() ->
            SubjectHandler.getIdent().orElseThrow(IllegalArgumentException::new)
        );
    }
}
