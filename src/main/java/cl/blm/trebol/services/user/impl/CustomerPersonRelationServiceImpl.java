package cl.blm.trebol.services.user.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import cl.blm.trebol.api.pojo.CustomerPojo;
import cl.blm.trebol.jpa.entities.Customer;
import cl.blm.trebol.jpa.entities.QCustomer;
import cl.blm.trebol.jpa.repositories.CustomersRepository;
import cl.blm.trebol.services.user.CustomerPersonRelationService;

/**
 *
 * @author Benjamin La Madrid <bg.lamadrid@gmail.com>
 */
@Service
public class CustomerPersonRelationServiceImpl
    implements CustomerPersonRelationService {

  private final CustomersRepository customerRepository;
  private final ConversionService conversionService;

  @Autowired
  public CustomerPersonRelationServiceImpl(CustomersRepository customerRepository, ConversionService conversionService) {
    this.customerRepository = customerRepository;
    this.conversionService = conversionService;
  }

  @Override
  public CustomerPojo getCustomerFromPersonId(int personId) {
    Predicate query = QCustomer.customer.person.id.eq(personId);
    Optional<Customer> match = customerRepository.findOne(query);
    if (match.isPresent()) {
      Customer entity = match.get();
      CustomerPojo target = conversionService.convert(entity, CustomerPojo.class);
      return target;
    }
    return null;
  }
}
