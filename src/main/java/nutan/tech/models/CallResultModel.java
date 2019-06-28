package nutan.tech.models;

public class CallResultModel {

	private boolean Failed, Successful;
	private String Message;
	
	public CallResultModel() {
		
	}
	
	public CallResultModel(boolean Failed, boolean Successful) {
		
		this.Failed = Failed;
		this.Successful = Successful;		
	}
	
	public CallResultModel(boolean Failed, boolean Successful, String Message) {
		
		this.Failed = Failed;
		this.Successful = Successful;		
		this.Message = Message;
	}

	public boolean isFailed() {
		return Failed;
	}

	public void setFailed(boolean failed) {
		Failed = failed;
	}

	public boolean isSuccessful() {
		return Successful;
	}

	public void setSuccessful(boolean successful) {
		Successful = successful;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
