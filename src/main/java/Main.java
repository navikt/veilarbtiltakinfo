import no.nav.apiapp.ApiApp;
import no.nav.fo.veilarbtiltakinfo.config.ApplicationConfig;

import static java.lang.System.setProperty;
import static no.nav.brukerdialog.security.Constants.OIDC_REDIRECT_URL_PROPERTY_NAME;
import static no.nav.dialogarena.aktor.AktorConfig.AKTOER_ENDPOINT_URL;
import static no.nav.fo.veilarbtiltakinfo.config.ApplicationConfig.VEILARBLOGIN_REDIRECT_URL_URL;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

public class Main {
    public static void main(String... args) throws Exception {

        setProperty(AKTOER_ENDPOINT_URL, ApplicationConfig.getAktorEndpointUrl());

        setProperty(OIDC_REDIRECT_URL_PROPERTY_NAME, getRequiredProperty(VEILARBLOGIN_REDIRECT_URL_URL));

        ApiApp.runApp(ApplicationConfig.class, args);
    }
}
