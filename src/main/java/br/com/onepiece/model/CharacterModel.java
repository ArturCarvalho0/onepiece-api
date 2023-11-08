package br.com.onepiece.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "characters")
public class CharacterModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 50)
  private String location;

  @Column(nullable = false, length = 50)
  private String description;

  @Column(nullable = false, length = 50)
  private Double reward;
  
  @Column(nullable = false, length = 50)
  private String img;

}
