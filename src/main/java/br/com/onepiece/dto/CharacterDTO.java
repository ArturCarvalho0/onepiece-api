package br.com.onepiece.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CharacterDTO extends RepresentationModel{
  private int id;

  private String name;

  private String location;

  private String description;

  private Double reward;
  
  private String img;
}
