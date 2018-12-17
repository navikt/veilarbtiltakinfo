package no.nav.fo.veilarbtiltakinfo;

import no.nav.apiapp.feil.FeilType;
import no.nav.fo.veilarbtiltakinfo.dao.Bruker;
import no.nav.fo.veilarbtiltakinfo.dao.Tiltak;
import no.nav.fo.veilarbtiltakinfo.dto.BrukerDto;
import no.nav.fo.veilarbtiltakinfo.dto.TiltakDto;
import no.nav.validation.ValidationUtils;

import javax.ws.rs.WebApplicationException;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class Mapper {
    public static Bruker map(BrukerDto brukerDto) {
        return Optional.of(brukerDto)
            .map(dto -> Bruker.builder()
                .fnr(dto.getFnr())
                .oppfolgingsEnhetId(dto.getOppfolgingsEnhetId())
                .underOppfolging(dto.getUnderOppfolging())
                .maal(dto.getMaal())
                .tiltak(dto.getTiltak().stream().map(Mapper::map).collect(toList()))
                .build())
            .orElseThrow(() -> new WebApplicationException(FeilType.UGYLDIG_HANDLING.getStatus()));
    }

    private static Tiltak map(TiltakDto tiltakDto) {
        return Tiltak.builder().nokkel(tiltakDto.getNokkel()).build();
    }

    public static BrukerDto map(Bruker bruker) {
        return BrukerDto.builder()
            .fnr(bruker.getFnr())
            .oppfolgingsEnhetId(bruker.getOppfolgingsEnhetId())
            .underOppfolging(bruker.getUnderOppfolging())
            .maal(bruker.getMaal())
            .tiltak(bruker.getTiltak().stream().map(Mapper::map).collect(toList()))
            .build();
    }

    private static TiltakDto map(Tiltak tiltak) {
        return TiltakDto.builder()
            .nokkel(tiltak.getNokkel())
            .build();
    }
}
