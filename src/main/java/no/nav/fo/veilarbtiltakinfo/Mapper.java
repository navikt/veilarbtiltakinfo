package no.nav.fo.veilarbtiltakinfo;

import no.nav.fo.veilarbtiltakinfo.dao.Bruker;
import no.nav.fo.veilarbtiltakinfo.dao.Tiltak;
import no.nav.fo.veilarbtiltakinfo.dto.BrukerDto;
import no.nav.fo.veilarbtiltakinfo.dto.TiltakDto;

import static java.util.stream.Collectors.toList;

public class Mapper {
    public static Bruker map(BrukerDto brukerDto) {
        return Bruker.builder()
            .fnr(brukerDto.getFnr())
            .oppfolgingsEnhetId(brukerDto.getOppfolgingsEnhetId())
            .oppfolgingsEnhetNavn(brukerDto.getOppfolgingsEnhetNavn())
            .underOppfolging(brukerDto.getUnderOppfolging())
            .maal(brukerDto.getMaal())
            .tiltak(brukerDto.getTiltak().stream().map(Mapper::map).collect(toList()))
            .build();
    }

    private static Tiltak map(TiltakDto tiltakDto) {
        return Tiltak.builder().nokkel(tiltakDto.getNokkel()).build();
    }

    public static BrukerDto map(Bruker bruker) {
        return BrukerDto.builder()
            .fnr(bruker.getFnr())
            .oppfolgingsEnhetId(bruker.getOppfolgingsEnhetId())
            .oppfolgingsEnhetNavn(bruker.getOppfolgingsEnhetNavn())
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
