package no.nav.fo.veilarbtiltakinfo.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class TiltakDto {
    private String nokkel;
}
