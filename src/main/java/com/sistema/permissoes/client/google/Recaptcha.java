package com.sistema.permissoes.client.google;

import java.time.LocalDateTime;

public record Recaptcha(
        String success,
        LocalDateTime challenge_ts,
        String hostname
) {
}
