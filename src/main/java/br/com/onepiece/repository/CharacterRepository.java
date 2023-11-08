package br.com.onepiece.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onepiece.model.CharacterModel;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterModel, Integer> {

  public Page<CharacterModel> findAll(Pageable pageable);

  public Page<CharacterModel> findByNameStartsWithIgnoreCase(String name, Pageable pageable);

}
