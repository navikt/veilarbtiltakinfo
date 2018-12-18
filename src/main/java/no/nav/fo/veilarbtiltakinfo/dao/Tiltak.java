package no.nav.fo.veilarbtiltakinfo.dao;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
@Builder(toBuilder = true)
public class Tiltak {
    @NotEmpty
    private String nokkel;
}
