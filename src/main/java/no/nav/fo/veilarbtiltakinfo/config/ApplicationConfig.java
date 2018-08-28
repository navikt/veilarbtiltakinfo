package no.nav.fo.veilarbtiltakinfo.config;

import no.nav.apiapp.ApiApplication.NaisApiApplication;
import no.nav.apiapp.config.ApiAppConfigurator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;

@Configuration
@Import({
    PepConfig.class,
})
public class ApplicationConfig implements NaisApiApplication {

    public static final String VEILARBLOGIN_REDIRECT_URL_URL = "VEILARBLOGIN_REDIRECT_URL_URL";

    @Transactional
    @Override
    public void startup(ServletContext servletContext) {
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
