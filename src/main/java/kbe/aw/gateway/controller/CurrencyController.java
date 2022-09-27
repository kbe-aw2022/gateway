package kbe.aw.gateway.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kbe.aw.gateway.configuration.RabbitConfiguration;
import kbe.aw.gateway.request.CurrencyCalculationRequest;

@RestController
public class CurrencyController
{
   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Autowired
   private ObjectMapper objectMapper;

   @PostMapping("/currency")
   public Double getPriceForProduct(@RequestBody CurrencyCalculationRequest currencyCalculationRequest)
   {
      Double priceWithCurrency;

      String result = (String) rabbitTemplate.convertSendAndReceive(RabbitConfiguration.REQUEST_CURRENCY_CALCULATION_QUE, currencyCalculationRequest);

      try
      {
         priceWithCurrency = objectMapper.readValue(result, Double.class);
      }
      catch (JsonProcessingException e)
      {
         throw new RuntimeException(e);
      }

      return priceWithCurrency;
   }
}
