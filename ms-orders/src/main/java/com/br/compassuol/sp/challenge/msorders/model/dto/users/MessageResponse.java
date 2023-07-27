package com.br.compassuol.sp.challenge.msorders.model.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class MessageResponse {
    private long userId;
    private String message;
    private boolean exists;
}
