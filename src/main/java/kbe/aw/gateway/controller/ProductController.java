package kbe.aw.gateway.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kbe.aw.gateway.configuration.RabbitConfiguration;
import kbe.aw.gateway.model.Product;
import kbe.aw.gateway.request.CustomMessage;

@RestController
public class ProductController
{
   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Autowired
   private ObjectMapper objectMapper = new ObjectMapper();

   @GetMapping("/products")
   public List<Product> getAllProducts()
   {
      CustomMessage message = createMessage("getAllProducts");

      return sendRequestAndReceiveResultFromProductQue(message);
   }

   @GetMapping("/products/{id}")
   public List<Product> getOneProduct(@PathVariable int id)
   {
      CustomMessage message = createMessage(Integer.toString(id));

      return sendRequestAndReceiveResultFromProductQue(message);
   }

   private List<Product> sendRequestAndReceiveResultFromProductQue(final CustomMessage message)
   {
      List<Product> productList;
      String result = (String) rabbitTemplate.convertSendAndReceive(RabbitConfiguration.REQUEST_PRODUCT_QUE, message);

      try
      {
         productList = objectMapper.readValue(result, new TypeReference<List<Product>>(){});
      }
      catch (JsonProcessingException e)
      {
         throw new RuntimeException(e);
      }
      return productList;
   }

   private CustomMessage createMessage(String message)
   {
      CustomMessage customMessage = new CustomMessage();
      customMessage.setMessageId(UUID.randomUUID().toString());
      customMessage.setMessageDate(new Date());
      customMessage.setMessage(message);

      return customMessage;
   }
}
