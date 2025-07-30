package academy.wakanda.wakacop.associado.application.service;

import academy.wakanda.wakacop.associado.infra.client.ConsultaCPFResponse;
import academy.wakanda.wakacop.associado.infra.client.SerproClienteFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AssociadoApplicationService implements AssociadoService {
    private final SerproClienteFeign serproClienteFeign;

    @Override
    public void validaAssociadoAptoVoto(String cpfAssociado) {
        log.debug("[start] AssociadoApplicationService - validaAssociadoAptoVoto");
        ConsultaCPFResponse consultaCPFResponse = serproClienteFeign.consultaCPF(TOKEN, cpfAssociado);
        validaCPF(consultaCPFResponse);
        log.debug("[finish] AssociadoApplicationService - validaAssociadoAptoVoto");
    }

    private void validaCPF(ConsultaCPFResponse consultaCPFResponse) {
        if (consultaCPFResponse.isInvalido()) {
            throw new RuntimeException("CPF inválido ou não encontrado");
        }
    }

    private static final String TOKEN = "Bearer 06aef429-a981-3ec5-a1f8-71d38d86481e";
}