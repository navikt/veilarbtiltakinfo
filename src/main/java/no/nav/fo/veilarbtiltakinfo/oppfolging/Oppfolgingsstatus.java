package no.nav.fo.veilarbtiltakinfo.oppfolging;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Oppfolgingsstatus {
    private String servicegruppe;
    private Oppfolgingsenhet oppfolgingsenhet;
}
