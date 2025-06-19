package academy.wakanda.wakacop.pauta.application.service;

import academy.wakanda.wakacop.pauta.application.api.NovaPautaRequest;
import academy.wakanda.wakacop.pauta.application.api.PautaCadastraResponse;
import academy.wakanda.wakacop.pauta.domain.Pauta;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PautaApplicationService implements PautaService{
    private final PautaRepository pautaRepository;

    @Override
    public PautaCadastraResponse cadastraPauta(NovaPautaRequest novaPauta) {
        log.info("[start] PautaApplicationService - cadastraPauta");
        log.info("[NovaPauta] {}", novaPauta);
        Pauta pauta = pautaRepository.salva(new Pauta(novaPauta));
        log.info("[finish] PautaApplicationService - cadastraPauta");
        return new PautaCadastraResponse(pauta);
    }
}
