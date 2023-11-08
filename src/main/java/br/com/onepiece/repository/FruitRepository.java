package br.com.onepiece.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onepiece.model.FruitModel;

@Repository
public interface FruitRepository extends JpaRepository<FruitModel, Integer> {

  public Page<FruitModel> findAll(Pageable pageable);

  public Page<FruitModel> findByNameStartsWithIgnoreCase(String name, Pageable pageable);

}
