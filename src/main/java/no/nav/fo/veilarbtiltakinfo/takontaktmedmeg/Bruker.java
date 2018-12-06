package no.nav.fo.veilarbtiltakinfo.takontaktmedmeg;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Bruker {
    private String fnr;
    private String oppfolgingsEnhetId;
    private String oppfolgingsEnhetNavn;
    private Boolean underOppfolging;
    private String maal;
    private String tiltakEn;
    private String tiltakTo;
}
