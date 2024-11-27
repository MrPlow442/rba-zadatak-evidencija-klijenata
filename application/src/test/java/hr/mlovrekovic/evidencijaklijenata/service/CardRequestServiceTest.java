package hr.mlovrekovic.evidencijaklijenata.service;

import hr.mlovrekovic.evidencijaklijenata.persistence.util.DatabaseCleanupExtension;
import hr.mlovrekovic.evidencijaklijenata.persistence.util.factory.DbCardRequestFactory;
import hr.mlovrekovic.evidencijaklijenata.rest.v1.cardrequest.model.CardRequestDto;
import hr.mlovrekovic.evidencijaklijenata.service.exception.NotFoundException;
import hr.mlovrekovic.evidencijaklijenata.service.model.CardRequestStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(DatabaseCleanupExtension.class)
class CardRequestServiceTest {

    @Autowired
    private CardRequestService cardRequestService;

    @Autowired
    private DbCardRequestFactory dbCardRequestFactory;

    @Test
    void test_whenGetAllCalled_thenAllCardRequestsReturned() {
        // Arrange
        dbCardRequestFactory.createCardRequest(cr -> cr.setOib("12345678901"));
        dbCardRequestFactory.createCardRequest(cr -> cr.setOib("09876543210"));
        dbCardRequestFactory.createCardRequest(cr -> cr.setOib("11111111111"));
        dbCardRequestFactory.createCardRequest(cr -> cr.setOib("22222222222"));

        // Act
        var result = cardRequestService.getAll();

        // Assert
        assertEquals(4, result.size());
    }

    @Test
    void test_whenGetOneSafeCalledWithExistingOib_thenCardRequestIsReturned() {
        // Arrange
        var cardRequest = dbCardRequestFactory.createCardRequest();

        // Act
        var result = cardRequestService.getOneSafe(cardRequest.getOib());

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    void test_whenGetOneSafeCalledWithNonExistingOib_thenEmptyOptionalIsReturned() {
        // Arrange
        //.. nothing

        // Act
        var result = cardRequestService.getOneSafe("00000000000");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void test_whenGetOneCalledWithNonExistingOib_thenNotFoundExceptionIsThrown() {
        // Arrange
        //.. nothing

        // Act and Assert
        assertThrows(NotFoundException.class, () -> cardRequestService.getOne("00000000000"));
    }

    @Test
    void test_whenCreateIsCalled_thenCardRequestIsCreated() {
        // Arrange
        var dto = new CardRequestDto();
        dto.setOib("1234567890");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setStatus(CardRequestStatus.PENDING);
        // Act
        var result = cardRequestService.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getOib(), result.getOib());
    }

    @Test
    void test_whenUpdateIsCalled_thenCardRequestIsUpdatedFully() {
        // Arrange
        var cardRequest = dbCardRequestFactory.createCardRequest();

        var dto = new CardRequestDto();
        dto.setOib(cardRequest.getOib());
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setStatus(CardRequestStatus.APPROVED);

        // Act
        var result = cardRequestService.update(cardRequest.getOib(), dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getFirstName(), result.getFirstName());
        assertEquals(dto.getLastName(), result.getLastName());
        assertEquals(dto.getOib(), result.getOib());
        assertEquals(dto.getStatus(), result.getStatus());
    }

    @Test
    void test_whenPartialUpdateIsCalled_thenSegmentOfCardRequestIsUpdated() {
        // Arrange
        var cardRequest = dbCardRequestFactory.createCardRequest();

        // Act
        var result = cardRequestService.partialUpdate(cardRequest.getOib(), Map.of("firstName", "John"));

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void test_whenDeleteIsCalled_thenCardRequestIsDeleted() {
        // Arrange
        var cardRequest = dbCardRequestFactory.createCardRequest();

        // Act
        cardRequestService.delete(cardRequest.getOib());

        // Assert
        assertEquals(0, cardRequestService.getAll().size());
    }

    @Test
    void test_whenDeleteIsCalledForNonExistentOib_thenNothingIsDeleted() {
        // Arrange
        var cardRequest = dbCardRequestFactory.createCardRequest();

        // Act
        cardRequestService.delete("00000000000");

        // Assert
        assertEquals(1, cardRequestService.getAll().size());
    }
}