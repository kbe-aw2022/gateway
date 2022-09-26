package kbe.aw.gateway.controller;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kbe.aw.gateway.configuration.RabbitConfiguration;
import kbe.aw.gateway.model.Product;

@RestController
public class PriceController
{
   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Autowired
   private ObjectMapper objectMapper;



   @PostMapping("/price")
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
