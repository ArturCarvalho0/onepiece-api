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
@Table(name = "fruits")
public class FruitModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 50)
  private String type;

  @Column(nullable = false, length = 50)
  private String description;
  
  @Column(nullable = false, length = 50)
  private String img;

  @ManyToOne
  @JoinColumn(name = "character_id", referencedColumnName = "id")
  private CharacterModel characterModel;

}