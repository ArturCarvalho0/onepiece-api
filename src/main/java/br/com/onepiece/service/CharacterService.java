package br.com.onepiece.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.onepiece.dto.CharacterDTO;
import br.com.onepiece.model.CharacterModel;
import br.com.onepiece.repository.CharacterRepository;
import br.com.onepiece.mapper.CustomModelMapper;
import br.com.onepiece.exception.ResourceNotFoundException;

@Service
public class CharacterService {

  @Autowired
  private CharacterRepository repository;

  public CharacterDTO create(CharacterDTO dto) {
    CharacterModel model = CustomModelMapper.parseObject(dto, CharacterModel.class);
    return CustomModelMapper.parseObject(repository.save(model), CharacterDTO.class);
  }

  public CharacterDTO findById(int id) {
    CharacterModel model = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(null));
    return CustomModelMapper.parseObject(model, CharacterDTO.class);
  }

  public Page<CharacterDTO> findAll(Pageable pageable) {
    var page = repository.findAll(pageable);
    return page.map(p -> CustomModelMapper.parseObject(p, CharacterDTO.class));
  }

  public CharacterDTO update(CharacterDTO dto) {
    CharacterModel model = repository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException(null));
    model = CustomModelMapper.parseObject(dto, CharacterModel.class);
    return CustomModelMapper.parseObject(repository.save(model), CharacterDTO.class);
  }

  public void delete(CharacterDTO dto) {
    CharacterModel model = repository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException(null));
    repository.delete(model);
  }

  public Page<CharacterDTO> findByName(String name, Pageable pageable) {
    var page = repository.findByNameStartsWithIgnoreCase(name, pageable);
    return page.map(p -> CustomModelMapper.parseObject(p, CharacterDTO.class));
  }

}
