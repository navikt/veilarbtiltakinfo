package no.nav.fo.veilarbtiltakinfo.bruker;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Tiltak {
    private String nokkel;
}
