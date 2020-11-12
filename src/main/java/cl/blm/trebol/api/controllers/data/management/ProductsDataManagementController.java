package cl.blm.trebol.api.controllers.data.management;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.blm.trebol.api.GenericCrudController;
import cl.blm.trebol.api.pojo.ProductPojo;
import cl.blm.trebol.config.CustomProperties;
import cl.blm.trebol.jpa.entities.Product;
import cl.blm.trebol.services.crud.GenericCrudService;

/**
 * API point of entry for Product entities
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@RestController
@RequestMapping("/data")
public class ProductsDataManagementController
    extends GenericCrudController<ProductPojo, Product, Integer> {

  @Autowired
  public ProductsDataManagementController(CustomProperties globals,
      GenericCrudService<ProductPojo, Product, Integer> crudService) {
    super(globals, crudService);
  }

  @Override
  @PostMapping("/products")
  @PreAuthorize("hasAuthority('products:create')")
  public Integer create(@RequestBody @Valid ProductPojo input) {
    return super.create(input);
  }

  @Override
  @GetMapping("/products/{id}")
  @PreAuthorize("hasAuthority('products:read')")
  public ProductPojo readOne(@PathVariable Integer id) {
    return super.readOne(id);
  }

  @GetMapping("/products")
  @PreAuthorize("hasAuthority('products:read')")
  public Collection<ProductPojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    return super.readMany(null, null, allRequestParams);
  }

//  @GetMapping("/products/{requestPageSize}")
//  @PreAuthorize("hasAuthority('products:read')")
//  public Collection<ProductPojo> readMany(@PathVariable Integer requestPageSize,
//      @RequestParam Map<String, String> allRequestParams) {
//    return super.readMany(requestPageSize, null, allRequestParams);
//  }
//
//  @Override
//  @GetMapping("/products/{requestPageSize}/{requestPageIndex}")
//  @PreAuthorize("hasAuthority('products:read')")
//  public Collection<ProductPojo> readMany(@PathVariable Integer requestPageSize, @PathVariable Integer requestPageIndex,
//      @RequestParam Map<String, String> allRequestParams) {
//    return super.readMany(requestPageSize, requestPageIndex, allRequestParams);
//  }
//
//  @PutMapping("/products")
//  @PreAuthorize("hasAuthority('products:update')")
//  public Integer update(@RequestBody @Valid ProductPojo input) {
//    return super.update(input, input.getId());
//  }

  @Override
  @PutMapping("/products/{id}")
  @PreAuthorize("hasAuthority('products:update')")
  public Integer update(@RequestBody @Valid ProductPojo input, @PathVariable Integer id) {
    return super.update(input, id);
  }

  @Override
  @DeleteMapping("/products/{id}")
  @PreAuthorize("hasAuthority('products:delete')")
  public boolean delete(@PathVariable Integer id) {
    return super.delete(id);
  }

  @Override
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    return super.handleValidationExceptions(ex);
  }
}