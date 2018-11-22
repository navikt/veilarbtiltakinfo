package no.nav.fo.veilarbtiltakinfo.client;

import lombok.Data;
import lombok.experimental.Accessors;
import no.nav.fo.veilarboppfolging.domain.Oppfolgingsenhet;

@Data
@Accessors(chain = true)
public class Oppfolgingsstatus {
    private String servicegruppe;
    private Oppfolgingsenhet oppfolgingsenhet;
}
