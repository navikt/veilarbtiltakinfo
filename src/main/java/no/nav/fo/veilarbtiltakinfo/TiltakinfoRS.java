package no.nav.fo.veilarbtiltakinfo;

import no.nav.apiapp.feil.Feil;
import no.nav.apiapp.security.veilarbabac.Bruker;
import no.nav.apiapp.security.veilarbabac.VeilarbAbacPepClient;
import no.nav.common.auth.SubjectHandler;
import no.nav.dialogarena.aktor.AktorService;
import no.nav.fo.veilarbtiltakinfo.dto.BrukerDto;
import no.nav.fo.veilarbtiltakinfo.dto.HarSendtMeldingDto;
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
import static no.nav.apiapp.feil.FeilType.FINNES_IKKE;

@Component
@Path("/")
@Produces("application/json")
public class TiltakinfoRS {

    public static final String FNR_QUERY_PARAM = "fnr";

    @Inject
    private AktorService aktorService;

    @Inject
    private Provider<HttpServletRequest> requestProvider;

    @Inject
    private TiltakinfoService tiltakinfoService;

    @Inject
    private VeilarbAbacPepClient pepClient;

    @GET
    @Path("oppfolgingsstatus")
    public Oppfolgingsstatus hentOppfolgingsstatus() {
        Bruker bruker = getBruker();
        pepClient.sjekkLesetilgangTilBruker(bruker);
        return tiltakinfoService.hentOppfolgingsstatus(bruker.getFoedselsnummer());
    }

    @POST
    @Path("bruker")
    public BrukerDto opprettBruker(BrukerDto brukerDto) {
        Bruker bruker = getBruker();
        pepClient.sjekkSkrivetilgangTilBruker(bruker);
        return tiltakinfoService.opprettBruker(brukerDto.toBuilder().fnr(bruker.getFoedselsnummer()).build());
    }

    @GET
    @Path("bruker/harsendtmeldingtilnavkontor")
    public HarSendtMeldingDto brukerHarSendtMeldingTilNavKontor() {
        Bruker bruker = getBruker();
        pepClient.sjekkLesetilgangTilBruker(bruker);
        return tiltakinfoService.brukerHarSendtMeldingTilNavKontor(bruker.getFoedselsnummer());
    }

    private Bruker getBruker() {
        String fnr = ofNullable(requestProvider.get().getParameter(FNR_QUERY_PARAM))
                .orElseGet(() -> SubjectHandler.getIdent().orElseThrow(IllegalArgumentException::new));

        return Bruker.fraFnr(fnr)
                .medAktoerIdSupplier(()->aktorService.getAktorId(fnr)
                        .orElseThrow(() -> new Feil(FINNES_IKKE, "Finner ikke aktørId for gitt Fnr")));
    }

}
