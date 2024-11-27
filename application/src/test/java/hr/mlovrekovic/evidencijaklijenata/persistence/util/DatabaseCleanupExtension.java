package hr.mlovrekovic.evidencijaklijenata.persistence.util;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

public class DatabaseCleanupExtension implements BeforeEachCallback, AfterEachCallback {

    private List<JpaRepository> jpaRepositories;

    @Override
    public void beforeEach(ExtensionContext context) {
        truncateTables(context);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        truncateTables(context);
    }

    private void truncateTables(ExtensionContext context) {
        if (jpaRepositories == null) {
            jpaRepositories = SpringExtension.getApplicationContext(context)
                    .getBeansOfType(JpaRepository.class)
                    .values()
                    .stream()
                    .toList();
        }

        for (JpaRepository jpaRepository : jpaRepositories) {
            jpaRepository.deleteAll();
        }
    }


}

