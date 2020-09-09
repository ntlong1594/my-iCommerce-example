package com.icommerce.developer.historical.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.icommerce.developer.historical.web.rest.TestUtil;

public class UserActivitiesHistoricalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActivitiesHistorical.class);
        UserActivitiesHistorical userActivitiesHistorical1 = new UserActivitiesHistorical();
        userActivitiesHistorical1.setId("id1");
        UserActivitiesHistorical userActivitiesHistorical2 = new UserActivitiesHistorical();
        userActivitiesHistorical2.setId(userActivitiesHistorical1.getId());
        assertThat(userActivitiesHistorical1).isEqualTo(userActivitiesHistorical2);
        userActivitiesHistorical2.setId("id2");
        assertThat(userActivitiesHistorical1).isNotEqualTo(userActivitiesHistorical2);
        userActivitiesHistorical1.setId(null);
        assertThat(userActivitiesHistorical1).isNotEqualTo(userActivitiesHistorical2);
    }
}
