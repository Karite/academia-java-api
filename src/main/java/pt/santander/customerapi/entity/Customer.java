package pt.santander.customerapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
@SequenceGenerator(sequenceName = "seq_customers", name ="seq_customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customers")
    //@Column(name = "idCustomer") para utilizar quando o id tem um nome especifico
    private Integer id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 9)
    private String nif;

    @Email
    @Size(max = 100)
    private String email;

    @Type(type= "true_false")
    @Column(columnDefinition = "char(1)")
    private Boolean active;


}
