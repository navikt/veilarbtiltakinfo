package no.nav.fo.veilarbtiltakinfo.oppfolging;

import no.nav.common.auth.SubjectHandler;
import no.nav.sbl.rest.RestUtils;
import org.springframework.stereotype.Component;

import static javax.ws.rs.core.HttpHeaders.COOKIE;
import static no.nav.brukerdialog.security.oidc.provider.AzureADB2CProvider.AZUREADB2C_OIDC_COOKIE_NAME;
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

    private OppfolgingClient(String veilarboppfolgingTarget) {
        this.veilarboppfolgingTarget = veilarboppfolgingTarget;
    }

    public Oppfolgingsstatus oppfolgingsstatus(String fnr) {
        Oppfolgingsstatus oppfolgingsstatus = RestUtils.withClient(
            c -> c.target(veilarboppfolgingTarget + "/person/" + fnr + "/oppfolgingsstatus?brukArena=true"
            )
                .request()
                .header(COOKIE, AZUREADB2C_OIDC_COOKIE_NAME + "=" + SubjectHandler.getSsoToken(OIDC).orElseThrow(IllegalArgumentException::new))
                .get(Oppfolgingsstatus.class)
        );
        return oppfolgingsstatus;
    }

}
