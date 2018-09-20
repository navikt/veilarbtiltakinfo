package no.nav.fo.veilarbtiltakinfo;

import no.nav.fo.veilarbtiltakinfo.client.OppfolgingStatus;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("/")
@Produces("application/json")
public class TiltakinfoRS {

    @Inject
    private TiltakService tiltakService;

    @GET
    @Path("servicegruppekode")
    public OppfolgingStatus hentServiceGruppeKode() {
        return tiltakService.hentServiceGruppe();
    }

}
