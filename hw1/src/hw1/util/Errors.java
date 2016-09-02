/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1.util;

/**
 *
 * @author kdkarki
 */
public enum Errors {
    INSTANCE;
    
    public String getErrorMessage(ErrorTypes errType, String invalidText1, String invalidText2){
        
        String errMsg = "";
        switch (errType){
            
            case DuplicateEntry:
                errMsg = "DUPLICATE_ENTRY: " + invalidText1 
                        + " by " + invalidText2 + " already exists.";
                break;
            case EmptyArgument:
                errMsg = "INVALID_ARGUMENT: " + invalidText1 
                        + " cannot be empty";
                break;
            case InvalidDate:
                errMsg = "INVALID_DATE: " + invalidText1 + " is not valid.";
                break;
            case InvalidDateOrBool:
                errMsg = "INVALID_DATE_OR_BOOL: " + invalidText1
                        + " is not valid date or bool";
                break;
            case InvalidParameter:
                errMsg = "INVALID_PARAMETER: " + invalidText1 
                        + " is not valid in " + invalidText2;
                break;
            case FileNotFound:
                errMsg = "FILE_NOT_FOUND: File \"" + invalidText1 
                        + "\" not found";
                break;
            case FilmNotFound:
                errMsg = "FILM_NOT_FOUND: " + invalidText1 + " by " 
                        + invalidText2 + " not found in database.";
                break;
            case NotBool:
                errMsg = "NOT_BOOL: " + invalidText1 
                        + " cannot be interpreted as boolean";
                break;
            case UnknownCommand:
                errMsg = "UNKNOWN_COMMAND: " + invalidText1 
                        + " is not a valid command";
                break;
            case WrongArgumentCount:
                errMsg = "WRONG_ARGUMENT_COUNT: Too few or too many arguments in the command.";
                break;
            default:
                break;
        }
        
        return errMsg;
    }
}
