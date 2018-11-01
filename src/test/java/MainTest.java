import no.nav.brukerdialog.security.Constants;
import no.nav.dialogarena.config.fasit.FasitUtils;
import no.nav.dialogarena.config.fasit.ServiceUser;
import no.nav.dialogarena.config.util.Util;
import no.nav.fo.veilarbtiltakinfo.config.ApplicationConfig;
import no.nav.sbl.dialogarena.common.abac.pep.CredentialConstants;
import no.nav.sbl.dialogarena.common.cxf.StsSecurityConstants;
import no.nav.testconfig.ApiAppTest;

import static java.lang.System.setProperty;
import static no.nav.brukerdialog.security.oidc.provider.AzureADB2CConfig.AZUREAD_B2C_DISCOVERY_URL_PROPERTY_NAME;
import static no.nav.brukerdialog.security.oidc.provider.AzureADB2CConfig.AZUREAD_B2C_EXPECTED_AUDIENCE_PROPERTY_NAME;
import static no.nav.dialogarena.config.fasit.FasitUtils.Zone.FSS;
import static no.nav.dialogarena.config.fasit.FasitUtils.getDefaultEnvironment;
import static no.nav.fo.veilarbtiltakinfo.client.OppfolgingClient.VEILARBOPPFOLGINGAPI_URL_PROPERTY_NAME;
import static no.nav.sbl.dialogarena.common.abac.pep.service.AbacServiceConfig.ABAC_ENDPOINT_URL_PROPERTY_NAME;

public class MainTest {

    private static final String PORT = "8800";
    public static final String APPLICATION_NAME = "veilarbtiltakinfo";

    public static void main(String[] args) throws Exception {
        ApiAppTest.setupTestContext(ApiAppTest.Config.builder().applicationName(APPLICATION_NAME).build());

        ServiceUser srvveilarbtiltakinfo = FasitUtils.getServiceUser("srvveilarbtiltakinfo", APPLICATION_NAME);

        String loginUrl = FasitUtils.getRestService("veilarblogin.redirect-url", getDefaultEnvironment()).getUrl();
        setProperty(ApplicationConfig.VEILARBLOGIN_REDIRECT_URL_URL, loginUrl);

        String securityTokenService = FasitUtils.getBaseUrl("securityTokenService", FSS);
        setProperty(StsSecurityConstants.STS_URL_KEY, securityTokenService);
        setProperty(StsSecurityConstants.SYSTEMUSER_USERNAME, srvveilarbtiltakinfo.getUsername());
        setProperty(StsSecurityConstants.SYSTEMUSER_PASSWORD, srvveilarbtiltakinfo.getPassword());

        ServiceUser azureADClientId = FasitUtils.getServiceUser("aad_b2c_clientid", APPLICATION_NAME);
        Util.setProperty(AZUREAD_B2C_DISCOVERY_URL_PROPERTY_NAME, FasitUtils.getBaseUrl("aad_b2c_discovery"));
        Util.setProperty(AZUREAD_B2C_EXPECTED_AUDIENCE_PROPERTY_NAME, azureADClientId.username);

        String issoJWS = FasitUtils.getBaseUrl("isso-jwks");
        String issoHost = FasitUtils.getBaseUrl("isso-host");
        ServiceUser isso_rp_user = FasitUtils.getServiceUser("isso-rp-user", APPLICATION_NAME);
        String issoIsAlive = FasitUtils.getBaseUrl("isso.isalive", FasitUtils.Zone.FSS);
        String issoIssuer = FasitUtils.getBaseUrl("isso-issuer");
        setProperty(Constants.ISSO_JWKS_URL_PROPERTY_NAME, issoJWS);
        setProperty(Constants.ISSO_HOST_URL_PROPERTY_NAME, issoHost);
        setProperty(Constants.ISSO_RP_USER_USERNAME_PROPERTY_NAME, isso_rp_user.getUsername());
        setProperty(Constants.ISSO_RP_USER_PASSWORD_PROPERTY_NAME, isso_rp_user.getPassword());
        setProperty(Constants.ISSO_ISALIVE_URL_PROPERTY_NAME, issoIsAlive);
        setProperty(Constants.ISSO_ISSUER_URL_PROPERTY_NAME, issoIssuer);

        setProperty(CredentialConstants.SYSTEMUSER_USERNAME, srvveilarbtiltakinfo.getUsername());
        setProperty(CredentialConstants.SYSTEMUSER_PASSWORD, srvveilarbtiltakinfo.getPassword());
        setProperty(ABAC_ENDPOINT_URL_PROPERTY_NAME, FasitUtils.getRestService("abac.pdp.endpoint", getDefaultEnvironment()).getUrl());

        setProperty(VEILARBOPPFOLGINGAPI_URL_PROPERTY_NAME, "https://localhost.nav.no:8443/veilarboppfolging/api");

        Main.main(PORT);
    }
}
