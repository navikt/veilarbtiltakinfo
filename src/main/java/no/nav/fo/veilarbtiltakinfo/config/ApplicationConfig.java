package no.nav.fo.veilarbtiltakinfo.config;

import no.nav.apiapp.ApiApplication.NaisApiApplication;
import no.nav.apiapp.config.ApiAppConfigurator;
import no.nav.fo.veilarbtiltakinfo.TiltakService;
import no.nav.fo.veilarbtiltakinfo.TiltakinfoRS;
import no.nav.fo.veilarbtiltakinfo.client.OppfolgingClient;
import no.nav.fo.veilarbtiltakinfo.client.OppfolgingClientHelseSjekk;
import no.nav.fo.veilarbtiltakinfo.takontaktmedmeg.BrukerDao;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

@Configuration
@Import({
    PepConfig.class,
    OppfolgingClient.class,
    OppfolgingClientHelseSjekk.class,
    TiltakService.class,
    TiltakinfoRS.class,
    DataSourceConfig.class,
    DataSourceHelsesjekk.class,
    BrukerDao.class
})
public class ApplicationConfig implements NaisApiApplication {

    public static final String VEILARBLOGIN_REDIRECT_URL_URL = "VEILARBLOGIN_REDIRECT_URL_URL";

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
            .azureADB2CLogin()
            .issoLogin()
        ;
    }
}
