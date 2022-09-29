package kbe.aw.gateway.validation;

import java.util.Objects;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Validation
{
   public boolean tokenIsValid(String token)
   {
      RestTemplate restTemplate = new RestTemplate();
      String uri = "https://6qsv0v.deta.dev/validate";

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      JSONObject object = new JSONObject(token);

      HttpEntity<String> request = new HttpEntity<String>(object.toString(), headers);

      String response = restTemplate.postForObject(uri,request, String.class);

      return Objects.equals(response, "\"valid\"");
   }
}
