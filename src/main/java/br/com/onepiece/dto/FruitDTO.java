package br.com.onepiece.dto;

import org.springframework.hateoas.RepresentationModel;

import br.com.onepiece.model.CharacterModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FruitDTO extends RepresentationModel{
  private int id;

  private String name;

  private String type;

  private String description;
  
  private String img;

  private CharacterModel characterModel;
}
