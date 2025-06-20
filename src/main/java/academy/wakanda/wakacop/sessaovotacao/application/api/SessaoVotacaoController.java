package academy.wakanda.wakacop.sessaovotacao.application.api;

import academy.wakanda.wakacop.sessaovotacao.application.service.SessaoVotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class SessaoVotacaoController implements SessaoVotacaoAPI {
    private final SessaoVotacaoService sessaoVotacaoService;

    @Override
    public SessaoAberturaResponse abrirSessaoVotacao(SessaoAberturaRequest sessaoAberturaRequest) {
        log.info("[start] SessaoVotacaoController - SessaoVotacaoController");
        SessaoAberturaResponse sessaoAberturaResponse = sessaoVotacaoService.abrirSessaoVotacao(sessaoAberturaRequest);
        log.info("[finish] SessaoVotacaoController - SessaoVotacaoController");
        return sessaoAberturaResponse;
    }
}
