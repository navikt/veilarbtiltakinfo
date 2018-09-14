package no.nav.fo.veilarbtiltakinfo.config;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("/")
@Produces("application/json")
public class TiltakinfoRS {

    @GET
    @Path("hentServiceGruppeKode")
    public ServiceGruppeKodeDto hentServiceGruppeKode() {
        return new ServiceGruppeKodeDto().setServiceGruppeKode(ServiceGruppeKode.BATT);
    }

}
