package academy.wakanda.wakacop.pauta.application.service;

import academy.wakanda.wakacop.pauta.application.api.NovaPautaRequest;
import academy.wakanda.wakacop.pauta.application.api.PautaCadastraResponse;
import academy.wakanda.wakacop.pauta.domain.Pauta;

import java.util.UUID;

public interface PautaService{
    PautaCadastraResponse cadastraPauta(NovaPautaRequest novaPauta);
    Pauta getPautaPorId(UUID idPauta);
}
