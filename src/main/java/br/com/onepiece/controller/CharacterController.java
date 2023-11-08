package br.com.onepiece.controller;

import br.com.onepiece.dto.CharacterDTO;
import br.com.onepiece.exception.ResourceNotFoundException;
import br.com.onepiece.mapper.CustomModelMapper;
import br.com.onepiece.model.CharacterModel;
import br.com.onepiece.repository.CharacterRepository;
import br.com.onepiece.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@Tag(name = "Characters", description = "This endpoint manages Characters")
public class CharacterController {

  @Autowired
  private CharacterService service;

  @PostMapping
  @Operation(summary = "Persists a new Character in database", tags = { "Character" }, responses = {
      @ApiResponse(description = "Success!", responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class))
      }),
      @ApiResponse(description = "Bad Request!", responseCode = "400", content = { @Content }),
      @ApiResponse(description = "Unauthorized!", responseCode = "401", content = { @Content }),
      @ApiResponse(description = "Internal Error!", responseCode = "500", content = { @Content })
  })
  public CharacterDTO create(@RequestBody CharacterDTO dto) {
    return service.create(dto);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find a Character using ID", tags = { "Character" }, responses = {
      @ApiResponse(description = "Success!", responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class))
      }),
      @ApiResponse(description = "Bad Request!", responseCode = "400", content = { @Content }),
      @ApiResponse(description = "Unauthorized!", responseCode = "401", content = { @Content }),
      @ApiResponse(description = "Internal Error!", responseCode = "500", content = { @Content })
  })
  public CharacterDTO findById(@PathVariable("id") int id) {
    CharacterDTO dto = service.findById(id);
    buildEntityLink(dto);
    return dto;
  }

  @GetMapping
  @Operation(summary = "Find a all Characters", tags = { "Character" }, responses = {
      @ApiResponse(description = "Success!", responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class))
      }),
      @ApiResponse(description = "Bad Request!", responseCode = "400", content = { @Content }),
      @ApiResponse(description = "Unauthorized!", responseCode = "401", content = { @Content }),
      @ApiResponse(description = "Internal Error!", responseCode = "500", content = { @Content })
  })
  public ResponseEntity<PagedModel<CharacterDTO>> findAll(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "direction", defaultValue = "asc") String direction,
      PagedResourcesAssembler<CharacterDTO> assembler) {
    var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
    Page<CharacterDTO> Characters = service.findAll(pageable);
    for (CharacterDTO Character : Characters) {
      buildEntityLink(Character);
    }

    return new ResponseEntity(assembler.toModel(Characters), HttpStatus.OK);
  }

  @GetMapping("/find")
  public ResponseEntity<PagedModel<CharacterDTO>> findByName(
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "direction", defaultValue = "asc") String direction,
      PagedResourcesAssembler<CharacterDTO> assembler) {
    var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
    Page<CharacterDTO> Characters = service.findByName(name, pageable);
    for (CharacterDTO Character : Characters) {
      buildEntityLink(Character);
    }

    return new ResponseEntity(assembler.toModel(Characters), HttpStatus.OK);
  }

  @PutMapping
  @Operation(summary = "Update a Character using ID", tags = { "Character" }, responses = {
      @ApiResponse(description = "Success!", responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class))
      }),
      @ApiResponse(description = "Bad Request!", responseCode = "400", content = { @Content }),
      @ApiResponse(description = "Unauthorized!", responseCode = "401", content = { @Content }),
      @ApiResponse(description = "Internal Error!", responseCode = "500", content = { @Content })
  })
  public CharacterDTO update(@RequestBody CharacterDTO dto) {
    return service.update(dto);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a Character using ID", tags = { "Character" }, responses = {
      @ApiResponse(description = "Success!", responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class))
      }),
      @ApiResponse(description = "Bad Request!", responseCode = "400", content = { @Content }),
      @ApiResponse(description = "Unauthorized!", responseCode = "401", content = { @Content }),
      @ApiResponse(description = "Internal Error!", responseCode = "500", content = { @Content })
  })
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
    CharacterDTO dto = service.findById(id);
    service.delete(dto);
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public void buildEntityLink(CharacterDTO Character) {
    Character.add(
        WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(
                this.getClass()).findById(Character.getId()))
            .withSelfRel());
  }

  // public void buildCollectionLink(CollectionModel<CharacterDTO> Characters) {
  // Characters.add(
  // WebMvcLinkBuilder.linkTo(
  // WebMvcLinkBuilder.methodOn(this.getClass()).findAll()
  // ).withRel(IanaLinkRelations.COLLECTION)
  // );
  // }

}
