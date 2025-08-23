package hotel_admin.dutkercz.com.github.dtos;

import hotel_admin.dutkercz.com.github.model.Address;
import jakarta.validation.constraints.NotBlank;

public record ClientUpdate(
        @NotBlank(message = "O campo Nome não pode estar em branco")
        String firstName,

        @NotBlank(message = "O campo Sobrenome não pode estar em branco")
        String lastName,

        @NotBlank(message = "O campo Telefone não pode estar em branco")
        String phone,

        Address address
) {
}
