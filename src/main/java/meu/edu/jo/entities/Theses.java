package meu.edu.jo.entities;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "theses")
@Data
public class Theses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "the_type")
    private String theType;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "theses_file")
    private byte[] thesesFile;
}
