package org.valuereporter.activity;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.valuereporter.activity.ObservedActivity.Builder.observe;

/**
 * Created by baardl on 15.09.17.
 */
public class ObservedActivityBuilderTest {
    @BeforeMethod
    public void setUp() throws Exception {
    }

    @Test
    public void testAddContext() throws Exception {
    }

    @Test
    public void testBuild() throws Exception {
        ObservedActivity observedActivity = observe("someTest").context("name", "test").build();
        assertNotNull(observedActivity);
        assertEquals(observedActivity.getActivityName(),"someTest");

    }



}