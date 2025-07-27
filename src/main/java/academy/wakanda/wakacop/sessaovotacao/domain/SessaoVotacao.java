package academy.wakanda.wakacop.sessaovotacao.domain;

import academy.wakanda.wakacop.pauta.domain.Pauta;
import academy.wakanda.wakacop.sessaovotacao.application.api.ResultadoSessaoResponse;
import academy.wakanda.wakacop.sessaovotacao.application.api.SessaoAberturaRequest;
import academy.wakanda.wakacop.sessaovotacao.application.api.VotoRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessaoVotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, unique = true, nullable = false)
    private UUID id;
    private UUID idPauta;
    private Integer tempoDuracaoEmMinutos;
    @Enumerated(EnumType.STRING)
    private StatusSessaoVotacao status;
    private LocalDateTime momentoAberturaSessao;
    private LocalDateTime momentoEncerramentoSessao;

    @OneToMany(
            mappedBy = "sessaoVotacao",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @MapKey(name = "cpfAssociado")
    private Map<String, VotoPauta> votos;

    public SessaoVotacao(SessaoAberturaRequest sessaoAberturaRequest, Pauta pauta) {
        this.idPauta = pauta.getId();
        this.tempoDuracaoEmMinutos = sessaoAberturaRequest.getTempoDuracaoEmMinutos().orElse(1);
        this.momentoAberturaSessao = LocalDateTime.now();
        this.momentoEncerramentoSessao = momentoAberturaSessao.plusMinutes(this.tempoDuracaoEmMinutos);
        this.status = StatusSessaoVotacao.ABERTA;
        this.votos = new HashMap<>();
    }

    public VotoPauta recebeVoto(VotoRequest votoRequest) {
        validaSessaoAberta();
        validaAssociado(votoRequest.getCpfAssociado());
        VotoPauta voto = new VotoPauta(this, votoRequest);
        votos.put(votoRequest.getCpfAssociado(), voto);
        return voto;
    }

    private void validaSessaoAberta() {
        atualizaStatusSessao();
        if (this.status.equals(StatusSessaoVotacao.FECHADA)) {
            throw new RuntimeException("Sessão Encerrada!");
        }
    }

    private void atualizaStatusSessao() {
        if (this.status.equals(StatusSessaoVotacao.ABERTA)) {
            if (LocalDateTime.now().isAfter(this.momentoEncerramentoSessao)) {
                fechaSessao();
            }
        }
    }

    private void fechaSessao() {
        this.status = StatusSessaoVotacao.FECHADA;
    }

    private void validaAssociado(String cpfAssociado) {
        if (this.votos.containsKey(cpfAssociado)) {
           throw new RuntimeException("Associado já votou nesta sessão.");
        }
    }

    public ResultadoSessaoResponse obtemResultado(){
        atualizaStatusSessao();
        return new ResultadoSessaoResponse(this);
    }

    public Long getTotalVotos() { return Long.valueOf(this.votos.size()); }

    public Long getTotalSim() { return calculaVotosPorOpcao(OpcaoVoto.SIM); }

    public Long getTotalNao() { return calculaVotosPorOpcao(OpcaoVoto.NAO); }

    private Long calculaVotosPorOpcao(OpcaoVoto opcao) {
        return votos.values().stream()
                .filter(voto -> voto.opcaoIgual(opcao))
                .count();
    }
}