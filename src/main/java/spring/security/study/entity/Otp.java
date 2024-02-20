package spring.security.study.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "sys",name = "otp")
@Getter
@Setter
public class Otp {

    @Id
    private String username;
    private String code;

}
