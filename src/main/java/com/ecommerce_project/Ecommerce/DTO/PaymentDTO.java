package com.ecommerce_project.Ecommerce.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDTO {

    private Long paymentId;
    private LocalDateTime paymentDate;

    @Min(value = 1, message = "Amount must be greater than zero")
    private int amount;

    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod;
}
