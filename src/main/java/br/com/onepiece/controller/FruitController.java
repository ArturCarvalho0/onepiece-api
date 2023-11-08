package br.com.onepiece.controller;

import br.com.onepiece.dto.FruitDTO;
import br.com.onepiece.exception.ResourceNotFoundException;
import br.com.onepiece.mapper.CustomModelMapper;
import br.com.onepiece.model.FruitModel;
import br.com.onepiece.repository.FruitRepository;
import br.com.onepiece.service.FruitService;
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
@RequestMapping("/api/fruits")
@Tag(name = "Fruits", description = "This endpoint manages Fruits")
public class FruitController {

  @Autowired
  private FruitService service;

  @PostMapping
  @Operation(summary = "Persists a new Fruit in database", tags = { "Fruit" }, responses = {
      @ApiResponse(description = "Success!", responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = FruitDTO.class))
      }),
      @ApiResponse(description = "Bad Request!", responseCode = "400", content = { @Content }),
      @ApiResponse(description = "Unauthorized!", responseCode = "401", content = { @Content }),
      @ApiResponse(description = "Internal Error!", responseCode = "500", content = { @Content })
  })
  public FruitDTO create(@RequestBody FruitDTO dto) {
    return service.create(dto);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find a Fruit using ID", tags = { "Fruit" }, responses = {
      @ApiResponse(description = "Success!", responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = FruitDTO.class))
      }),
      @ApiResponse(description = "Bad Request!", responseCode = "400", content = { @Content }),
      @ApiResponse(description = "Unauthorized!", responseCode = "401", content = { @Content }),
      @ApiResponse(description = "Internal Error!", responseCode = "500", content = { @Content })
  })
  public FruitDTO findById(@PathVariable("id") int id) {
    FruitDTO dto = service.findById(id);
    buildEntityLink(dto);
    return dto;
  }

  @GetMapping
  @Operation(summary = "Find a all Fruits", tags = { "Fruit" }, responses = {
      @ApiResponse(description = "Success!", responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = FruitDTO.class))
      }),
      @ApiResponse(description = "Bad Request!", responseCode = "400", content = { @Content }),
      @ApiResponse(description = "Unauthorized!", responseCode = "401", content = { @Content }),
      @ApiResponse(description = "Internal Error!", responseCode = "500", content = { @Content })
  })
  public ResponseEntity<PagedModel<FruitDTO>> findAll(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "direction", defaultValue = "asc") String direction,
      PagedResourcesAssembler<FruitDTO> assembler) {
    var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
    Page<FruitDTO> Fruits = service.findAll(pageable);
    for (FruitDTO Fruit : Fruits) {
      buildEntityLink(Fruit);
    }

    return new ResponseEntity(assembler.toModel(Fruits), HttpStatus.OK);
  }

  @GetMapping("/find")
  public ResponseEntity<PagedModel<FruitDTO>> findByName(
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "direction", defaultValue = "asc") String direction,
      PagedResourcesAssembler<FruitDTO> assembler) {
    var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
    Page<FruitDTO> Fruits = service.findByName(name, pageable);
    for (FruitDTO Fruit : Fruits) {
      buildEntityLink(Fruit);
    }

    return new ResponseEntity(assembler.toModel(Fruits), HttpStatus.OK);
  }

  @PutMapping
  @Operation(summary = "Update a Fruit using ID", tags = { "Fruit" }, responses = {
      @ApiResponse(description = "Success!", responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = FruitDTO.class))
      }),
      @ApiResponse(description = "Bad Request!", responseCode = "400", content = { @Content }),
      @ApiResponse(description = "Unauthorized!", responseCode = "401", content = { @Content }),
      @ApiResponse(description = "Internal Error!", responseCode = "500", content = { @Content })
  })
  public FruitDTO update(@RequestBody FruitDTO dto) {
    return service.update(dto);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a Fruit using ID", tags = { "Fruit" }, responses = {
      @ApiResponse(description = "Success!", responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = FruitDTO.class))
      }),
      @ApiResponse(description = "Bad Request!", responseCode = "400", content = { @Content }),
      @ApiResponse(description = "Unauthorized!", responseCode = "401", content = { @Content }),
      @ApiResponse(description = "Internal Error!", responseCode = "500", content = { @Content })
  })
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
    FruitDTO dto = service.findById(id);
    service.delete(dto);
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public void buildEntityLink(FruitDTO Fruit) {
    Fruit.add(
        WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(
                this.getClass()).findById(Fruit.getId()))
            .withSelfRel());
  }

  // public void buildCollectionLink(CollectionModel<FruitDTO> Fruits) {
  // Fruits.add(
  // WebMvcLinkBuilder.linkTo(
  // WebMvcLinkBuilder.methodOn(this.getClass()).findAll()
  // ).withRel(IanaLinkRelations.COLLECTION)
  // );
  // }

}
