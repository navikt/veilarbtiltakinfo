package no.nav.fo.veilarbtiltakinfo.config;

import no.nav.apiapp.ApiApplication;
import no.nav.apiapp.config.ApiAppConfigurator;
import no.nav.dialogarena.aktor.AktorConfig;
import no.nav.fo.veilarbtiltakinfo.TiltakinfoRS;
import no.nav.fo.veilarbtiltakinfo.dao.BrukerDao;
import no.nav.fo.veilarbtiltakinfo.dao.TiltakDao;
import no.nav.fo.veilarbtiltakinfo.oppfolging.OppfolgingClient;
import no.nav.fo.veilarbtiltakinfo.oppfolging.OppfolgingClientHelseSjekk;
import no.nav.fo.veilarbtiltakinfo.service.TiltakinfoService;
import no.nav.sbl.util.EnvironmentUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

@Configuration
@Import({
        FeatureToggleConfig.class,
        PepConfig.class,
        OppfolgingClient.class,
        OppfolgingClientHelseSjekk.class,
        TiltakinfoService.class,
        TiltakinfoRS.class,
        DataSourceConfig.class,
        DataSourceHelsesjekk.class,
        BrukerDao.class,
        TiltakDao.class,
        AktorConfig.class
})
public class ApplicationConfig implements ApiApplication {

    public static final String VEILARBLOGIN_REDIRECT_URL_URL = "VEILARBLOGIN_REDIRECT_URL_URL";
    public static final String AKTOR_ENDPOINT_URL = "AKTOER_V2_ENDPOINTURL";

    @Inject
    private DataSource dataSource;

    @Transactional
    @Override
    public void startup(ServletContext servletContext) {
        MigrationUtils.createTables(dataSource);
    }

    @Override
    public void configure(ApiAppConfigurator apiAppConfigurator) {
        apiAppConfigurator
                .sts()
                .validateAzureAdExternalUserTokens()
                .issoLogin()
        ;
    }

    public static String getAktorEndpointUrl() {
        return EnvironmentUtils.getRequiredProperty(AKTOR_ENDPOINT_URL);
    }

}
