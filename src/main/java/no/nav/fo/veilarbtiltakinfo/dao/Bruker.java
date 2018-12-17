package no.nav.fo.veilarbtiltakinfo.dao;

import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class Bruker {
    private long brukerId;
    private Timestamp brukerTidspunkt;

    private String fnr;
    private String oppfolgingsEnhetId;
    private Boolean underOppfolging;
    private String maal;
    private List<Tiltak> tiltak;
}
