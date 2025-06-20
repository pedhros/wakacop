package academy.wakanda.wakacop.sessaovotacao.application.api;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;
import java.util.UUID;

@ToString
public class SessaoAberturaRequest {
    @Getter
    @NotNull
    private UUID idPauta;
    private Integer tempoDuracaoEmMinutos;

    public Optional<Integer> getTempoDuracaoEmMinutos() {
        return Optional.ofNullable(tempoDuracaoEmMinutos);
    }
}