package spring.security.study.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(schema = "sys",name = "token")
@Entity
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guid")
    private int guid;
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "token")
    private String token;

}
