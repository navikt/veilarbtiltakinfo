package no.nav.fo.veilarbtiltakinfo.client;

import no.nav.common.auth.SubjectHandler;
import no.nav.sbl.rest.RestUtils;
import org.springframework.stereotype.Component;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static no.nav.common.auth.SsoToken.Type.OIDC;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

@Component
public class OppfolgingClient {

    public static final String VEILARBOPPFOLGINGAPI_URL_PROPERTY_NAME = "VEILARBOPPFOLGINGAPI_URL";

    private final String veilarboppfolgingTarget;

    @SuppressWarnings("unused")
    OppfolgingClient() {
        this(getRequiredProperty(VEILARBOPPFOLGINGAPI_URL_PROPERTY_NAME));
    }

    OppfolgingClient(String veilarboppfolgingTarget) {
        this.veilarboppfolgingTarget = veilarboppfolgingTarget;
    }

    public OppfolgingStatus serviceGruppeKode(String fnr) {
        OppfolgingStatus serviceGruppeKode = RestUtils.withClient(
            c -> c.target(veilarboppfolgingTarget + "/person/" + fnr + "/oppfolgingsstatus"
            )
                .request()
                .header(AUTHORIZATION, "Bearer " + SubjectHandler.getSsoToken(OIDC).orElseThrow(IllegalArgumentException::new))
                .get(OppfolgingStatus.class)
        );
        return serviceGruppeKode;
    }

}