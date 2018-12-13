package no.nav.fo.veilarbtiltakinfo.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class BrukerDto {
    private String fnr;
    private String oppfolgingsEnhetId;
    private String oppfolgingsEnhetNavn;
    private Boolean underOppfolging;
    private String maal;
    private List<TiltakDto> tiltak;
}
