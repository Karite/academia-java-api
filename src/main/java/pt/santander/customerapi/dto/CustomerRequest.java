package pt.santander.customerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String nif;

    @Email
    private String email;

    private List<CustomerRequest> lista;


}
