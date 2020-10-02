package cl.blm.trebol.store.converters.topojo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cl.blm.trebol.store.api.pojo.UserPojo;
import cl.blm.trebol.store.jpa.entities.User;

/**
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@Component
public class User2Pojo
    implements Converter<User, UserPojo> {

  @Override
  public UserPojo convert(User source) {
    UserPojo target = new UserPojo();
    target.setId(source.getId());
    target.setName(source.getName());
    return target;
  }
}
