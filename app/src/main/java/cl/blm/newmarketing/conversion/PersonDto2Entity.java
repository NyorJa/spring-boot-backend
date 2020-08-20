package cl.blm.newmarketing.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cl.blm.newmarketing.rest.dtos.PersonDto;
import cl.blm.newmarketing.model.entities.Person;

/**
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@Component
public class PersonDto2Entity
        implements Converter<PersonDto, Person> {
  @Override
  public Person convert(PersonDto source) {
    Person target = new Person();
    target.setId(source.getPersonId());
    target.setFullName(source.getPersonFullName());
    target.setIdNumber(source.getPersonIdNumber());
    
    String address = source.getPersonAddress();
    if (address != null && !address.isEmpty()) {
      target.setAddress(address);
    }
    
    String email = source.getPersonEmail();
    if (email != null && !email.isEmpty()) {
      target.setEmail(email);
    }
    return target;
  }
}
