package com.greedygame.moviebagnow;

import com.greedygame.moviebagnow.activities.home.view.HomeScreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class HomeScreenTest {

    @Mock
    HomeScreen mockNow;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }



    @Test
    public void onContinueShown() {
        mockNow = mock(HomeScreen.class);
        boolean continueShown = mockNow.isContinuedShown;
        assertTrue(continueShown);
    }

    @Test
    public void isPhoneNumberEmpty() {
        mockNow = mock(HomeScreen.class);
        //etMobileNo.getText().toString().trim().replaceAll("\\D+", Constants.BLANK_STRING);
        if(mockNow.mNowPlayingMovie.isEmpty()){
            assertNull(mockNow);
        }
        else{
            assertNotNull(mockNow);
        }
    }


}