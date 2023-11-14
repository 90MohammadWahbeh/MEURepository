package meu.edu.jo.entities;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "achievements")
@Data
public class Achievements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "achievements_file")
    private byte[] achievementsFile;

    // Getters and setters
}
