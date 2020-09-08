package com.icommerce.developer.historical.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.icommerce.developer.historical.web.rest.TestUtil;

public class HistoricalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Historical.class);
        Historical historical1 = new Historical();
        historical1.setId("id1");
        Historical historical2 = new Historical();
        historical2.setId(historical1.getId());
        assertThat(historical1).isEqualTo(historical2);
        historical2.setId("id2");
        assertThat(historical1).isNotEqualTo(historical2);
        historical1.setId(null);
        assertThat(historical1).isNotEqualTo(historical2);
    }
}
