package academy.wakanda.wakacop.pauta.application.service;

import academy.wakanda.wakacop.pauta.application.api.NovaPautaRequest;
import academy.wakanda.wakacop.pauta.application.api.PautaCadastraResponse;

public interface PautaService{
    PautaCadastraResponse cadastraPauta(NovaPautaRequest novaPauta);
}
