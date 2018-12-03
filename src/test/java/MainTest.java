import no.nav.dialogarena.config.fasit.FasitUtils;
import no.nav.fo.veilarbtiltakinfo.DatabaseTestContext;
import no.nav.fo.veilarbtiltakinfo.SystemTestConfigurator;
import no.nav.testconfig.ApiAppTest;

import static no.nav.fo.veilarbtiltakinfo.SystemTestConfigurator.APPLICATION_NAME;
import static no.nav.sbl.util.EnvironmentUtils.getOptionalProperty;

public class MainTest {

    private static final String PORT = "8800";

    public static void main(String[] args) throws Exception {
        ApiAppTest.setupTestContext(ApiAppTest.Config.builder().applicationName(APPLICATION_NAME).build());
        SystemTestConfigurator.setup(getOptionalProperty("real_database").isPresent() ? FasitUtils.getDbCredentials(APPLICATION_NAME) : DatabaseTestContext.inMemoryConfig());

        Main.main(PORT);
    }
}
