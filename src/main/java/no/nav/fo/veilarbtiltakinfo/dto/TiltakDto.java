package no.nav.fo.veilarbtiltakinfo.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
@Builder(toBuilder = true)
public class TiltakDto {
    @NotEmpty
    private String nokkel;
}
