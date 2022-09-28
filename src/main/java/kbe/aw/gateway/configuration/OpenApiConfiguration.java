package kbe.aw.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration
{
   @Bean
   public OpenAPI openAPIConfig()
   {
      return new OpenAPI().info(apiInfo());
   }

   public Info apiInfo()
   {
      Info info = new Info();
      info.title("API Gateway")
            .description("information about API's that can be used in the Computer Online Shop")
            .version("v.1.0.0");
      return info;


   }

}
