package no.nav.fo.veilarbtiltakinfo.config;

import no.nav.apiapp.ApiApplication;
import no.nav.apiapp.config.ApiAppConfigurator;
import no.nav.dialogarena.aktor.AktorConfig;
import no.nav.dialogarena.aktor.AktorServiceImpl;
import no.nav.fo.veilarbtiltakinfo.service.TiltakinfoService;
import no.nav.fo.veilarbtiltakinfo.TiltakinfoRS;
import no.nav.fo.veilarbtiltakinfo.dao.TiltakDao;
import no.nav.fo.veilarbtiltakinfo.oppfolging.OppfolgingClient;
import no.nav.fo.veilarbtiltakinfo.oppfolging.OppfolgingClientHelseSjekk;
import no.nav.fo.veilarbtiltakinfo.dao.BrukerDao;
import no.nav.tjeneste.virksomhet.aktoer.v2.AktoerV2;
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
