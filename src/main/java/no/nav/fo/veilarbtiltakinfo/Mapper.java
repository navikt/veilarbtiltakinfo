package no.nav.fo.veilarbtiltakinfo;

import no.nav.apiapp.feil.FeilType;
import no.nav.fo.veilarbtiltakinfo.dao.Bruker;
import no.nav.fo.veilarbtiltakinfo.dao.Tiltak;
import no.nav.fo.veilarbtiltakinfo.dto.BrukerDto;
import no.nav.fo.veilarbtiltakinfo.dto.TiltakDto;
import no.nav.validation.ValidationUtils;

import javax.ws.rs.WebApplicationException;
import java.util.List;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class Mapper {
    public static Bruker map(BrukerDto brukerDto) {
        List<TiltakDto> tiltakDtos = ofNullable(brukerDto.getTiltak()).orElseThrow(IllegalArgumentException::new);
        return ofNullable(brukerDto)
            .map(dto -> Bruker.builder()
                .fnr(dto.getFnr())
                .oppfolgingsEnhetId(dto.getOppfolgingsEnhetId())
                .underOppfolging(dto.getUnderOppfolging())
                .maal(dto.getMaal())
                .tiltak(tiltakDtos.stream().map(Mapper::map).collect(toList()))
                .build())
            .map(ValidationUtils::validate)
            .orElseThrow(() -> new WebApplicationException(FeilType.UGYLDIG_HANDLING.getStatus()));
    }

    private static Tiltak map(TiltakDto tiltakDto) {
        return ofNullable(tiltakDto)
            .map(dto -> Tiltak.builder().nokkel(tiltakDto.getNokkel()).build())
            .map(ValidationUtils::validate)
            .orElseThrow(() -> new WebApplicationException(FeilType.UGYLDIG_HANDLING.getStatus()));
    }

    public static BrukerDto map(Bruker bruker) {
        return ofNullable(bruker)
            .map(b -> BrukerDto.builder()
                .fnr(bruker.getFnr())
                .oppfolgingsEnhetId(bruker.getOppfolgingsEnhetId())
                .underOppfolging(bruker.getUnderOppfolging())
                .maal(bruker.getMaal())
                .tiltak(bruker.getTiltak().stream().map(Mapper::map).collect(toList()))
                .build())
            .map(ValidationUtils::validate)
            .orElseThrow(() -> new WebApplicationException(FeilType.UGYLDIG_HANDLING.getStatus()));
    }

    private static TiltakDto map(Tiltak tiltak) {
        return ofNullable(tiltak)
            .map(t -> TiltakDto.builder().nokkel(t.getNokkel()).build())
            .map(ValidationUtils::validate)
            .orElseThrow(() -> new WebApplicationException(FeilType.UGYLDIG_HANDLING.getStatus()));
    }
}
