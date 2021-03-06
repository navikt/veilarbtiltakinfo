package no.nav.fo.veilarbtiltakinfo.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class BrukerDto {

    @NotEmpty
    private String fnr;
    @NotNull
    private Boolean erSykmeldt;
    @NotNull
    private Boolean harArbeidsgiver;
    @NotEmpty
    private String servicegruppeKode;
    @NotEmpty
    private String oppfolgingsEnhetId;
    @NotNull
    private Boolean underOppfolging;
    private String maal;
    @NotEmpty
    private List<TiltakDto> tiltak;

    @AssertTrue
    public boolean isHarToTiltak() {
        return tiltak != null && tiltak.size() == 2;
    }
}
