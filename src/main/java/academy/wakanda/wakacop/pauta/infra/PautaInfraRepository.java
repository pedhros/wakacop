package academy.wakanda.wakacop.pauta.infra;

import academy.wakanda.wakacop.pauta.application.service.PautaRepository;
import academy.wakanda.wakacop.pauta.domain.Pauta;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Log4j2
@RequiredArgsConstructor

public class PautaInfraRepository implements PautaRepository {
    private final PautaSpringDataJpaRepository pautaSpringDataJpaRepository;

    @Override
    public Pauta salva(Pauta pauta) {
    log.info("[start] PautaInfraRepository - salva");
    pautaSpringDataJpaRepository.save(pauta); 
    log.info("[finish] PautaInfraRepository - salva");
    return pauta;
    }

    @Override
    public Pauta buscarPorId(UUID idPauta) {
        log.info("[start] PautaInfraRepository - buscarPorId");
        Pauta pautaPorId = pautaSpringDataJpaRepository.findById(idPauta)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada!"));
        log.info("[finish] PautaInfraRepository - buscarPorId");
        return pautaPorId;
    }
}