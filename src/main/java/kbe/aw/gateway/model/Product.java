package kbe.aw.gateway.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product
{
   private Integer id;

   private String name;

   private String description;

   private List<HardwareComponent> hardwareComponents;
}
