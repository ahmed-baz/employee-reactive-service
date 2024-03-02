package org.demo.app.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("EMPLOYEE")
@ToString
public class Employee {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal salary;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate joinDate;

}


