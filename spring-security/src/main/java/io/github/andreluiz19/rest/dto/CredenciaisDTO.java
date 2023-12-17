package io.github.andreluiz19.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredenciaisDTO {

    private String Login;

    private String senha;
}
