package kbe.aw.gateway.model;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HardwareComponent implements Serializable
{
   private Integer id;

   private String name;

   private String vendor;

   private double price;

   private String description;

   private String location;

   private String manufacture;

   private String productGroup;

   private int weightInGramm;

   private Status status;

   private String eanNumber;

}
