package no.nav.fo.veilarbtiltakinfo.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder(toBuilder = true)
public class HarSendtMeldingDto {
    @NotNull
    private Boolean brukerHarSendtMeldingTilNavKontor;
}
