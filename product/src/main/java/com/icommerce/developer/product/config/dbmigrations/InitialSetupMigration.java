package com.icommerce.developer.product.config.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.icommerce.developer.product.domain.Authority;
import com.icommerce.developer.product.domain.Product;
import com.icommerce.developer.product.security.AuthoritiesConstants;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Random;

/**
 * Creates the initial database setup.
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);
        mongoTemplate.save(adminAuthority);
        mongoTemplate.save(userAuthority);
    }

    @ChangeSet(order = "02", author = "ntlong1594", id = "02-add-sample-products")
    public void addSampleProducts(MongoTemplate mongoTemplate) {
        for (int i = 1; i <= 100; i++) {
            Product product = new Product();
            product.setBrand("Brand of product " + i);
            product.setTitle("Title of product " + i);
            product.setPrice(new Random().nextInt(100000) + 5000);
            product = mongoTemplate.save(product);
        }
    }
}
