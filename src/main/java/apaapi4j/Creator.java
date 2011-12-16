package apaapi4j;

public class Creator {
	public final String role;
	public final String creator;
	public Creator(String role, String creator) {
		this.role = role;
		this.creator = creator;
	}
	
	@Override
	public String toString() {
		return "Creator [role=" + role + ", creator=" + creator + "]";
	}
	
}
