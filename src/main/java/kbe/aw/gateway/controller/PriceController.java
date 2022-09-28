package kbe.aw.gateway.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kbe.aw.gateway.configuration.RabbitConfiguration;
import kbe.aw.gateway.model.Product;

@RestController
@Tag(name = "Price")
public class PriceController
{
   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Autowired
   private ObjectMapper objectMapper;

   @PostMapping("/price")
   @Operation(summary = "calculate and return a price for a product")
   public Double getPriceForProduct(@RequestBody Product product)
   {
      Double price;
      String result = (String) rabbitTemplate.convertSendAndReceive(RabbitConfiguration.REQUEST_PRICE_QUE, product);

      try
      {
         price = objectMapper.readValue(result, Double.class);
      }
      catch (JsonProcessingException e)
      {
         throw new RuntimeException(e);
      }

      return price;
   }
}
