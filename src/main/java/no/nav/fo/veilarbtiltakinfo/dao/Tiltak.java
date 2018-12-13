package no.nav.fo.veilarbtiltakinfo.dao;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Tiltak {
    private String nokkel;
}
