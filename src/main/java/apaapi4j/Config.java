package apaapi4j;

import java.util.ResourceBundle;

public class Config {
	final String aws_access_key_id;
	final String aws_secret_key;
	final String endpoint;
	final String associate_tag;
	
	public Config() {
		this("appapi4j");
	}

	public Config(String resourceName) {
		ResourceBundle rb = ResourceBundle.getBundle("appapi4j");
		this.aws_access_key_id = rb.getString("aws_access_key_id");
		this.aws_secret_key = rb.getString("aws_secret_key");
		this.endpoint = rb.getString("endpoint");
		this.associate_tag = rb.getString("associate_tag");
	}

}
