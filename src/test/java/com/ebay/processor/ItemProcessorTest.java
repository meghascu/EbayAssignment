package com.ebay.processor;

import com.ebay.dao.DataStore;
import io.swagger.model.EligibilityRequest;
import io.swagger.model.EligibilityResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest({DataStore.class})
@PowerMockIgnore("jdk.internal.reflect.*")
public class ItemProcessorTest {

    @Spy
    @InjectMocks
    private ItemProcessor itemProcessor=new ItemProcessor();

    @Mock
    DataStore dataStore;

    @Before
    public void setup(){
        PowerMockito.mockStatic(DataStore.class);
        PowerMockito.when(DataStore.getInstance()).thenReturn(dataStore);
        PowerMockito.when(dataStore.isCategoryAllowed(Mockito.anyInt())).thenReturn(true);
        PowerMockito.when(dataStore.isSellerAllowed(Mockito.anyString())).thenReturn(true);
        PowerMockito.when(dataStore.isPriceValid(Mockito.anyDouble())).thenReturn(true);
    }

    @Test
    public void getEligibilityInvalidCategoryTest(){
        EligibilityRequest eligibilityRequest = new EligibilityRequest();
        eligibilityRequest.setTitle("abx");
        eligibilityRequest.setSeller("aaa");
        eligibilityRequest.setCategory(1);
        eligibilityRequest.setPrice(0.65);
        PowerMockito.when(dataStore.isCategoryAllowed(Mockito.anyInt())).thenReturn(false);
        EligibilityResponse actual = itemProcessor.getEligibility(eligibilityRequest);
        Assert.assertEquals(false,actual.isEligible());
    }

    @Test
    public void getEligibilityInvalidSellerIdTest(){
        EligibilityRequest eligibilityRequest = new EligibilityRequest();
        eligibilityRequest.setTitle("abx");
        eligibilityRequest.setSeller("aaa");
        eligibilityRequest.setCategory(1);
        eligibilityRequest.setPrice(0.65);
        PowerMockito.when(dataStore.isSellerAllowed(Mockito.anyString())).thenReturn(false);
        EligibilityResponse actual = itemProcessor.getEligibility(eligibilityRequest);
        Assert.assertEquals(false,actual.isEligible());
    }

    @Test
    public void getEligibilityInvalidPriceTest(){
        EligibilityRequest eligibilityRequest = new EligibilityRequest();
        eligibilityRequest.setTitle("abx");
        eligibilityRequest.setSeller("aaa");
        eligibilityRequest.setCategory(1);
        eligibilityRequest.setPrice(0.65);
        PowerMockito.when(dataStore.isPriceValid(Mockito.anyDouble())).thenReturn(false);
        EligibilityResponse actual = itemProcessor.getEligibility(eligibilityRequest);
        Assert.assertEquals(false,actual.isEligible());
    }

    @Test
    public void getEligibilityValidItem(){
        EligibilityRequest eligibilityRequest = new EligibilityRequest();
        eligibilityRequest.setTitle("abx");
        eligibilityRequest.setSeller("aaa");
        eligibilityRequest.setCategory(1);
        eligibilityRequest.setPrice(0.65);
        EligibilityResponse actual = itemProcessor.getEligibility(eligibilityRequest);
        Assert.assertEquals(true,actual.isEligible());
    }


}
