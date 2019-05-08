package no.nav.fo.veilarbtiltakinfo.config;

import no.finn.unleash.FeatureToggle;
import no.nav.sbl.featuretoggle.unleash.UnleashService;
import no.nav.sbl.featuretoggle.unleash.UnleashServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureToggleConfig {

    private final static String UNLEASH_API_URL = "https://unleash.nais.adeo.no/api/";

    @Bean
    public UnleashService unleashService() {
        UnleashServiceConfig config = UnleashServiceConfig.builder()
                .applicationName("veilarbtiltakinfo")
                .unleashApiUrl(UNLEASH_API_URL)
                .build();

        return new UnleashService(config);
    }
}
