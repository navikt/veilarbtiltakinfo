package no.nav.fo.veilarbtiltakinfo;

import no.nav.apiapp.security.PepClient;
import no.nav.common.auth.SubjectHandler;
import no.nav.fo.veilarbtiltakinfo.dto.BrukerDto;
import no.nav.fo.veilarbtiltakinfo.oppfolging.Oppfolgingsstatus;
import no.nav.fo.veilarbtiltakinfo.service.TiltakinfoService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    private TiltakinfoService tiltakinfoService;

    @Inject
    private PepClient pepClient;

    @GET
    @Path("oppfolgingsstatus")
    public Oppfolgingsstatus hentOppfolgingsstatus() {
        String fnr = getFnr();
        pepClient.sjekkLeseTilgangTilFnr(fnr);
        return tiltakinfoService.hentOppfolgingsstatus(fnr);
    }

    @POST
    @Path("dao")
    public BrukerDto opprettBruker(BrukerDto brukerDto) {
        String fnr = getFnr();
        pepClient.sjekkSkriveTilgangTilFnr(fnr);
        return tiltakinfoService.opprettBruker(brukerDto);
    }

    private String getFnr() {
        String fnr = requestProvider.get().getParameter(FNR_QUERY_PARAM);
        return ofNullable(fnr).orElseGet(() ->
            SubjectHandler.getIdent().orElseThrow(IllegalArgumentException::new)
        );
    }
}
