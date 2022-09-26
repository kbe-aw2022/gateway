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
import kbe.aw.gateway.model.HardwareComponent;
import kbe.aw.gateway.request.CustomMessage;

@RestController
public class HardwareComponentController
{
   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Autowired
   private ObjectMapper objectMapper;

   @GetMapping("/hardwarecomponents")
   public List<HardwareComponent> getAllHardwareComponents()
   {
      CustomMessage message = createMessage("getAllHardwareComponents");

      return sendRequestAndReceiveResultFromComponentQue(message);
   }

   @GetMapping("/hardwarecomponents/{id}")
   public List<HardwareComponent> getOneHardwareComponent(@PathVariable int id)
   {
      CustomMessage message = createMessage(Integer.toString(id));

      return sendRequestAndReceiveResultFromComponentQue(message);
   }


   private List<HardwareComponent> sendRequestAndReceiveResultFromComponentQue(final CustomMessage message)
   {
      List<HardwareComponent> hardwareComponentList;
      String result = (String) rabbitTemplate.convertSendAndReceive(RabbitConfiguration.REQUEST_COMPONENT_QUE, message);

      try
      {
         hardwareComponentList = objectMapper.readValue(result, new TypeReference<List<HardwareComponent>>(){});
      }
      catch (JsonProcessingException e)
      {
         throw new RuntimeException(e);
      }
      return hardwareComponentList;
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
