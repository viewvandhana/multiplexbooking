package Exception;

import java.util.ArrayList;

import com.movieapp.beans.EmberError;
import com.movieapp.util.ObjectMapperUtil;

public class ResponseFailureException extends Exception{

	public ResponseFailureException(String message)
	{
		super(message);
	}

	public String getErrorJson()
	{
		ArrayList<EmberError> errorList=new ArrayList<EmberError>();
		EmberError err=new EmberError(1000,getMessage().toString());
		errorList.add(err);
		return ObjectMapperUtil.getCustomMappedString("errors",errorList);
	}
}
