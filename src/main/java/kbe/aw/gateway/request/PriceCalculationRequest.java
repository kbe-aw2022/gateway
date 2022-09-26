package kbe.aw.gateway.request;

import kbe.aw.gateway.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriceCalculationRequest
{
   private Product product;
}
