package no.nav.fo.veilarbtiltakinfo;

import no.nav.fo.veilarbtiltakinfo.dao.Bruker;

import static no.nav.metrics.MetricsFactory.createEvent;

public class Metrikker {

    public static void opprettetBruker(Bruker bruker) {
        createEvent("veilarbtiltakinfo.bruker.opprettet")
            .addFieldToReport("underOppfolging", bruker.getUnderOppfolging())
            .addTagToReport("oppfolgingsEnhetId", bruker.getOppfolgingsEnhetId())
            .report();
    }

}
