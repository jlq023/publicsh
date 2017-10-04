package demo.bean;

/**
 * Manageruser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Manageruser implements java.io.Serializable {

	// Fields

	private Long mid;
	private String mloginId;
	private String mpassWord;
	private Short mrole;
	private Short mstatus;
	private Long mexhibitorId;

	// Constructors

	/** default constructor */
	public Manageruser() {
	}

	/** minimal constructor */
	public Manageruser(Long mid, String mloginId, String mpassWord) {
		this.mid = mid;
		this.mloginId = mloginId;
		this.mpassWord = mpassWord;
	}

	/** full constructor */
	public Manageruser(Long mid, String mloginId, String mpassWord,
			Short mrole, Short mstatus, Long mexhibitorId) {
		this.mid = mid;
		this.mloginId = mloginId;
		this.mpassWord = mpassWord;
		this.mrole = mrole;
		this.mstatus = mstatus;
		this.mexhibitorId = mexhibitorId;
	}

	// Property accessors

	public Long getMid() {
		return this.mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getMloginId() {
		return this.mloginId;
	}

	public void setMloginId(String mloginId) {
		this.mloginId = mloginId;
	}

	public String getMpassWord() {
		return this.mpassWord;
	}

	public void setMpassWord(String mpassWord) {
		this.mpassWord = mpassWord;
	}

	public Short getMrole() {
		return this.mrole;
	}

	public void setMrole(Short mrole) {
		this.mrole = mrole;
	}

	public Short getMstatus() {
		return this.mstatus;
	}

	public void setMstatus(Short mstatus) {
		this.mstatus = mstatus;
	}

	public Long getMexhibitorId() {
		return this.mexhibitorId;
	}

	public void setMexhibitorId(Long mexhibitorId) {
		this.mexhibitorId = mexhibitorId;
	}

}