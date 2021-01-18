package com.ebay.processor;

import com.ebay.dao.DataStore;
import io.swagger.model.Operation;
import io.swagger.model.RuleRequest;
import io.swagger.model.RuleType;
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
public class RuleProcessorTest {

    @Spy
    @InjectMocks
    private RuleProcessor ruleProcessor = new RuleProcessor();

    @Mock
    DataStore dataStore;

    @Before
    public void setup(){
        PowerMockito.mockStatic(DataStore.class);
        PowerMockito.when(DataStore.getInstance()).thenReturn(dataStore);
    }


    @Test
    public void addRuleItemCategoryTest(){
        RuleRequest ruleRequest = new RuleRequest();
        ruleRequest.setOperation(Operation.ADD);
        ruleRequest.setRuleType(RuleType.CATEGORY);
        ruleRequest.setValue("1");

        PowerMockito.when(dataStore.addCategoryId(Mockito.anyInt())).thenReturn(true);
        boolean actualResp =  ruleProcessor.updateRule(ruleRequest);
        Assert.assertEquals(true,actualResp);
    }

   @Test
    public void updateRuleItemCategoryTest(){
       RuleRequest ruleRequest = new RuleRequest();
       ruleRequest.setOperation(Operation.UPDATE);
       ruleRequest.setRuleType(RuleType.CATEGORY);
       ruleRequest.setValue("1");

       boolean actualResp =  ruleProcessor.updateRule(ruleRequest);
       Assert.assertEquals(false,actualResp);
   }

   @Test
    public void deleteRuleItemCategoryTest(){
       RuleRequest ruleRequest = new RuleRequest();
       ruleRequest.setOperation(Operation.DELETE);
       ruleRequest.setRuleType(RuleType.CATEGORY);
       ruleRequest.setValue("1");
       PowerMockito.when(dataStore.removeCategory(Mockito.anyInt())).thenReturn(true);
       boolean actualResp =  ruleProcessor.updateRule(ruleRequest);
       Assert.assertEquals(true,actualResp);
   }

    @Test
    public void addRuleItemSellerTest(){
        RuleRequest ruleRequest = new RuleRequest();
        ruleRequest.setOperation(Operation.ADD);
        ruleRequest.setRuleType(RuleType.SELLER);
        ruleRequest.setValue("ans");
        PowerMockito.when(dataStore.addSeller(Mockito.anyString())).thenReturn(true);
        boolean actualResp =  ruleProcessor.updateRule(ruleRequest);
        Assert.assertEquals(true,actualResp);
    }

    @Test
    public void updateRuleItemSellerTest(){
        RuleRequest ruleRequest = new RuleRequest();
        ruleRequest.setOperation(Operation.UPDATE);
        ruleRequest.setRuleType(RuleType.SELLER);
        ruleRequest.setValue("ans");

        boolean actualResp =  ruleProcessor.updateRule(ruleRequest);
        Assert.assertEquals(false,actualResp);
    }

    @Test
    public void deleteRuleItemSellerTest(){
        RuleRequest ruleRequest = new RuleRequest();
        ruleRequest.setOperation(Operation.DELETE);
        ruleRequest.setRuleType(RuleType.SELLER);
        ruleRequest.setValue("ans");
        PowerMockito.when(dataStore.removeSeller(Mockito.anyString())).thenReturn(true);

        boolean actualResp =  ruleProcessor.updateRule(ruleRequest);
        Assert.assertEquals(true,actualResp);
    }

    @Test
    public void updateRuleItemPriceTest(){
        RuleRequest ruleRequest = new RuleRequest();
        ruleRequest.setOperation(Operation.UPDATE);
        ruleRequest.setRuleType(RuleType.PRICE);
        ruleRequest.setValue("6.6");
        PowerMockito.when(dataStore.updateMinPrice(Mockito.anyDouble())).thenReturn(true);
        boolean actualResp =  ruleProcessor.updateRule(ruleRequest);
        Assert.assertEquals(true,actualResp);
    }

    @Test
    public void addRuleItemPriceTest(){
        RuleRequest ruleRequest = new RuleRequest();
        ruleRequest.setOperation(Operation.ADD);
        ruleRequest.setRuleType(RuleType.PRICE);
        ruleRequest.setValue("6.6");

        boolean actualResp =  ruleProcessor.updateRule(ruleRequest);
        Assert.assertEquals(false,actualResp);
    }

    @Test
    public void deleteRuleItemPriceTest(){
        RuleRequest ruleRequest = new RuleRequest();
        ruleRequest.setOperation(Operation.DELETE);
        ruleRequest.setRuleType(RuleType.PRICE);
        ruleRequest.setValue("6.6");

        boolean actualResp =  ruleProcessor.updateRule(ruleRequest);
        Assert.assertEquals(false,actualResp);
    }


    @Test
    public void invalidItemPriceTest(){
        RuleRequest ruleRequest = new RuleRequest();
        ruleRequest.setOperation(Operation.UPDATE);
        ruleRequest.setRuleType(RuleType.PRICE);
        ruleRequest.setValue("bvc");
        try {
            boolean actualResp = ruleProcessor.updateRule(ruleRequest);
        }
        catch (NumberFormatException ex) {
            Assert.fail();
        }
    }



}
