package hw1.util;

/**
*
* @author kdkarki
*/
public enum Errors {
   INSTANCE;
   
   public String getErrorMessage(ErrorTypes errType, String command){
       
       String errCode = "";
       switch (errType){
           
           case DUPLICATE_ENTRY:
               errCode = "DUPLICATE_ENTRY";
               break;
           case EMPTY_ARGUMENT:
               errCode = "INVALID_ARGUMENT";
               break;
           case INVALID_DATE:
               errCode = "INVALID_DATE";
               break;
           case INVALID_DATE_OR_BOOL:
               errCode = "INVALID_DATE_OR_BOOL";
               break;
           case INVALID_PARAMETER:
               errCode = "INVALID_PARAMETER";
               break;
           case FILE_NOT_FOUND:
               errCode = "FILE_NOT_FOUND";
               break;
           case FILM_NOT_FOUND:
               errCode = "FILM_NOT_FOUND";
               break;
           case NOT_BOOL:
               errCode = "NOT_BOOL";
               break;
           case UNKNOWN_COMMAND:
               errCode = "UNKNOWN_COMMAND";
               break;
           case WRONG_ARGUMENT_COUNT:
               errCode = "WRONG_ARGUMENT_COUNT";
               break;
           default:
               errCode = "UNKNOWN_ERROR";
               break;
       }
       
       return command + ": ERROR " + errCode;
   }
}