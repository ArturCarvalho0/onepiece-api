package br.com.onepiece.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.onepiece.dto.FruitDTO;
import br.com.onepiece.model.FruitModel;
import br.com.onepiece.repository.FruitRepository;
import br.com.onepiece.mapper.CustomModelMapper;
import br.com.onepiece.exception.ResourceNotFoundException;

@Service
public class FruitService {

  @Autowired
  private FruitRepository repository;

  public FruitDTO create(FruitDTO dto) {
    FruitModel model = CustomModelMapper.parseObject(dto, FruitModel.class);
    return CustomModelMapper.parseObject(repository.save(model), FruitDTO.class);
  }

  public FruitDTO findById(int id) {
    FruitModel model = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(null));
    return CustomModelMapper.parseObject(model, FruitDTO.class);
  }

  public Page<FruitDTO> findAll(Pageable pageable) {
    var page = repository.findAll(pageable);
    return page.map(p -> CustomModelMapper.parseObject(p, FruitDTO.class));
  }

  public FruitDTO update(FruitDTO dto) {
    FruitModel model = repository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException(null));
    model = CustomModelMapper.parseObject(dto, FruitModel.class);
    return CustomModelMapper.parseObject(repository.save(model), FruitDTO.class);
  }

  public void delete(FruitDTO dto) {
    FruitModel model = repository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException(null));
    repository.delete(model);
  }

  public Page<FruitDTO> findByName(String name, Pageable pageable) {
    var page = repository.findByNameStartsWithIgnoreCase(name, pageable);
    return page.map(p -> CustomModelMapper.parseObject(p, FruitDTO.class));
  }

}
