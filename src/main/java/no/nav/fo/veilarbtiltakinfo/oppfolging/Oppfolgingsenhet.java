package no.nav.fo.veilarbtiltakinfo.oppfolging;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
class Oppfolgingsenhet {
    private String navn;
    private String enhetId;
}
