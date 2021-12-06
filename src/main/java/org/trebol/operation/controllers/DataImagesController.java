package org.trebol.operation.controllers;

import com.querydsl.core.types.Predicate;
import io.jsonwebtoken.lang.Maps;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.trebol.config.OperationProperties;
import org.trebol.exceptions.BadInputException;
import org.trebol.exceptions.EntityAlreadyExistsException;
import org.trebol.jpa.entities.Image;
import org.trebol.jpa.services.GenericCrudJpaService;
import org.trebol.jpa.services.IPredicateJpaService;
import org.trebol.operation.GenericDataCrudController;
import org.trebol.pojo.DataPagePojo;
import org.trebol.pojo.ImagePojo;

import javax.validation.Valid;
import java.util.Map;

/**
 * Controller that maps API resources for CRUD operations on Images
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@RestController
@RequestMapping("/data/images")
@PreAuthorize("isAuthenticated()")
public class DataImagesController
  extends GenericDataCrudController<ImagePojo, Image> {

  @Autowired
  public DataImagesController(OperationProperties globals,
                              GenericCrudJpaService<ImagePojo, Image> crudService,
                              IPredicateJpaService<Image> predicateService) {
    super(globals, crudService, predicateService);
  }

  @GetMapping({"", "/"})
  @PreAuthorize("hasAuthority('images:read')")
  public DataPagePojo<ImagePojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    return super.readMany(null, null, allRequestParams);
  }

  @Override
  @PostMapping({"", "/"})
  @PreAuthorize("hasAuthority('images:create')")
  public void create(@Valid @RequestBody ImagePojo input) throws BadInputException, EntityAlreadyExistsException {
    super.create(input);
  }

  @Override
  @PutMapping({"", "/"})
  @PreAuthorize("hasAuthority('images:update')")
  public void update(@RequestBody ImagePojo input, @RequestParam Map<String, String> requestParams)
      throws NotFoundException, BadInputException {
    super.update(input, requestParams);
  }

  @Override
  @DeleteMapping({"", "/"})
  @PreAuthorize("hasAuthority('images:delete')")
  public void delete(@RequestParam Map<String, String> requestParams) throws NotFoundException {
    super.delete(requestParams);
  }

  @Deprecated(forRemoval = true)
  @GetMapping({"/{code}", "/{code}/"})
  @PreAuthorize("hasAuthority('images:read')")
  public ImagePojo readOne(@PathVariable String code) throws NotFoundException {
    return crudService.readOne(whereCodeIs(code));
  }

  @Deprecated(forRemoval = true)
  @PutMapping({"/{code}", "/{code}/"})
  @PreAuthorize("hasAuthority('images:update')")
  public void update(@RequestBody ImagePojo input, @PathVariable String code)
    throws NotFoundException, BadInputException {
    crudService.update(input, whereCodeIs(code));
  }

  @Deprecated(forRemoval = true)
  @DeleteMapping({"/{code}", "/{code}/"})
  @PreAuthorize("hasAuthority('images:delete')")
  public void delete(@PathVariable String code) throws NotFoundException {
    crudService.delete(whereCodeIs(code));
  }

  private Predicate whereCodeIs(String code) {
    Map<String, String> codeMatcher = Maps.of("code", code).build();
    return predicateService.parseMap(codeMatcher);
  }
}