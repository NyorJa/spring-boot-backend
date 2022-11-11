package org.trebol.jpa.services.crud;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.trebol.exceptions.BadInputException;
import org.trebol.jpa.entities.Image;
import org.trebol.jpa.repositories.IImagesJpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.trebol.testhelpers.ImagesTestHelper.*;

@ExtendWith(MockitoExtension.class)
class ImagesJpaCrudServiceImplTest {
  @InjectMocks ImagesJpaCrudServiceImpl instance;
  @Mock IImagesJpaRepository imagesRepositoryMock;

  @Test
  void sanity_check() {
    assertNotNull(instance);
  }

  @Test
  void finds_by_filename() throws BadInputException {
    resetImages();
    when(imagesRepositoryMock.findByFilename(imagePojoForFetch().getFilename())).thenReturn(Optional.of(imageEntityAfterCreation()));

    Optional<Image> match = instance.getExisting(imagePojoForFetch());

    verify(imagesRepositoryMock).findByFilename(imagePojoForFetch().getFilename());
    assertTrue(match.isPresent());
    assertEquals(match.get().getId(), imageEntityAfterCreation().getId());
    assertEquals(match.get().getCode(), imageEntityAfterCreation().getCode());
    assertEquals(match.get().getFilename(), imageEntityAfterCreation().getFilename());
    assertEquals(match.get().getUrl(), imageEntityAfterCreation().getUrl());
  }

}