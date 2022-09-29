package kbe.aw.gateway.exception;

public class RequestNotValidException extends RuntimeException
{
   public RequestNotValidException()
   {
      super("Request is not valid. Please login to see the products");
   }
}
