package cl.blm.newmarketing.backend.services.data.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import cl.blm.newmarketing.backend.api.pojo.ClientPojo;
import cl.blm.newmarketing.backend.model.entities.Client;
import cl.blm.newmarketing.backend.model.entities.QClient;
import cl.blm.newmarketing.backend.model.repositories.ClientsRepository;
import cl.blm.newmarketing.backend.services.data.GenericDataService;

/**
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@Transactional
@Service
public class ClientDataServiceImpl
    extends GenericDataService<ClientPojo, Client, Integer> {
  private static final Logger LOG = LoggerFactory.getLogger(ClientDataServiceImpl.class);

  private ClientsRepository repository;
  private ConversionService conversion;

  @Autowired
  public ClientDataServiceImpl(ClientsRepository repository, ConversionService conversion) {
    super(LOG, repository);
    this.repository = repository;
    this.conversion = conversion;
  }

  @Override
  public ClientPojo entity2Pojo(Client source) {
    return conversion.convert(source, ClientPojo.class);
  }

  @Override
  public Client pojo2Entity(ClientPojo source) {
    return conversion.convert(source, Client.class);
  }

  @Override
  public Page<Client> getAllEntities(Pageable paged, Predicate filters) {
    if (filters == null) {
      return repository.deepReadAll(paged);
    } else {
      return repository.deepReadAll(filters, paged);
    }
  }

  @Override
  public Predicate queryParamsMapToPredicate(Map<String, String> queryParamsMap) {
    LOG.debug("queryParamsMapToPredicate({})", queryParamsMap);
    QClient qClient = QClient.client;
    BooleanBuilder predicate = new BooleanBuilder();
    for (String paramName : queryParamsMap.keySet()) {
      String stringValue = queryParamsMap.get(paramName);
      try {
        Integer intValue;
        switch (paramName) {
        case "id":
          intValue = Integer.valueOf(stringValue);
          return predicate.and(qClient.id.eq(intValue)); // id matching is final
        case "name":
          predicate.and(qClient.person.name.likeIgnoreCase("%" + stringValue + "%"));
          break;
        case "idnumber":
          predicate.and(qClient.person.idCard.likeIgnoreCase("%" + stringValue + "%"));
          break;
        case "email":
          predicate.and(qClient.person.email.likeIgnoreCase("%" + stringValue + "%"));
          break;
        default:
          break;
        }
      } catch (NumberFormatException exc) {
        LOG.error("Param '{}' couldn't be parsed as number (value: '{}')", paramName, stringValue, exc);
      }
    }

    return predicate;
  }
}