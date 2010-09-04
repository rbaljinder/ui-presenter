/**
 * 
 */


/**
 * @author Baljinder Randhawa
 * 
 */
public class SystemX {

	private Long systemId;

	private Long clientId;

	private String name;

	private String clientName;

	private String systemCode;

	private Boolean vermontCertificateOnFile;

	private String addressPreference;

	private String primaryBureauChoice;

	private String secondaryBureauChoice;

	private String tertiaryBureauChoice;

	private String commercialBureauChoice1;

	private String commercialBureauChoice2;

	private String commercialBureauChoice3;

	private Integer allowedTransactionReSubmits;

	private String dIEnableFlag;

	private String newFraudModelActive;

	private String eidVerifierFlag;

	private Integer allowedVRUAttempts;

	private Integer vRUExpirationHours;

	private Boolean velocityDetection;

	private Boolean enableCDIW;

	private Boolean enableCDIT;

	private Boolean enableBDIW;

	private Boolean enableCU;

	private Boolean enableBU;

	private Boolean IDSPBE;

	private Boolean bureau;

	private Boolean marketMax;

	private Integer timeThreshold;

	private Boolean ICLite;

	private String defaultShare;

	/**
	 * @return the systemId
	 */
	public Long getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @param clientName
	 *            the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @param systemCode
	 *            the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @return the vermontCertificateOnFile
	 */
	public Boolean getVermontCertificateOnFile() {
		return vermontCertificateOnFile;
	}

	/**
	 * @param vermontCertificateOnFile
	 *            the vermontCertificateOnFile to set
	 */
	public void setVermontCertificateOnFile(Boolean vermontCertificateOnFile) {
		this.vermontCertificateOnFile = vermontCertificateOnFile;
	}

	/**
	 * @return the addressPreference
	 */
	public String getAddressPreference() {
		return addressPreference;
	}

	/**
	 * @param addressPreference
	 *            the addressPreference to set
	 */
	public void setAddressPreference(String addressPreference) {
		this.addressPreference = addressPreference;
	}

	/**
	 * @return the primaryBureauChoice
	 */
	public String getPrimaryBureauChoice() {
		return primaryBureauChoice;
	}

	/**
	 * @param primaryBureauChoice
	 *            the primaryBureauChoice to set
	 */
	public void setPrimaryBureauChoice(String primaryBureauChoice) {
		this.primaryBureauChoice = primaryBureauChoice;
	}

	/**
	 * @return the secondaryBureauChoice
	 */
	public String getSecondaryBureauChoice() {
		return secondaryBureauChoice;
	}

	/**
	 * @param secondaryBureauChoice
	 *            the secondaryBureauChoice to set
	 */
	public void setSecondaryBureauChoice(String secondaryBureauChoice) {
		this.secondaryBureauChoice = secondaryBureauChoice;
	}

	/**
	 * @return the tertiaryBureauChoice
	 */
	public String getTertiaryBureauChoice() {
		return tertiaryBureauChoice;
	}

	/**
	 * @param tertiaryBureauChoice
	 *            the tertiaryBureauChoice to set
	 */
	public void setTertiaryBureauChoice(String tertiaryBureauChoice) {
		this.tertiaryBureauChoice = tertiaryBureauChoice;
	}

	/**
	 * @return the commercialBureauChoice1
	 */
	public String getCommercialBureauChoice1() {
		return commercialBureauChoice1;
	}

	/**
	 * @param commercialBureauChoice1
	 *            the commercialBureauChoice1 to set
	 */
	public void setCommercialBureauChoice1(String commercialBureauChoice1) {
		this.commercialBureauChoice1 = commercialBureauChoice1;
	}

	/**
	 * @return the commercialBureauChoice2
	 */
	public String getCommercialBureauChoice2() {
		return commercialBureauChoice2;
	}

	/**
	 * @param commercialBureauChoice2
	 *            the commercialBureauChoice2 to set
	 */
	public void setCommercialBureauChoice2(String commercialBureauChoice2) {
		this.commercialBureauChoice2 = commercialBureauChoice2;
	}

	/**
	 * @return the commercialBureauChoice3
	 */
	public String getCommercialBureauChoice3() {
		return commercialBureauChoice3;
	}

	/**
	 * @param commercialBureauChoice3
	 *            the commercialBureauChoice3 to set
	 */
	public void setCommercialBureauChoice3(String commercialBureauChoice3) {
		this.commercialBureauChoice3 = commercialBureauChoice3;
	}

	/**
	 * @return the allowedTransactionReSubmits
	 */
	public Integer getAllowedTransactionReSubmits() {
		return allowedTransactionReSubmits;
	}

	/**
	 * @param allowedTransactionReSubmits
	 *            the allowedTransactionReSubmits to set
	 */
	public void setAllowedTransactionReSubmits(Integer allowedTransactionReSubmits) {
		this.allowedTransactionReSubmits = allowedTransactionReSubmits;
	}

	/**
	 * @return the dIEnableFlag
	 */
	public String getdIEnableFlag() {
		return dIEnableFlag;
	}

	/**
	 * @param dIEnableFlag
	 *            the dIEnableFlag to set
	 */
	public void setdIEnableFlag(String dIEnableFlag) {
		this.dIEnableFlag = dIEnableFlag;
	}

	/**
	 * @return the newFraudModelActive
	 */
	public String getNewFraudModelActive() {
		return newFraudModelActive;
	}

	/**
	 * @param newFraudModelActive
	 *            the newFraudModelActive to set
	 */
	public void setNewFraudModelActive(String newFraudModelActive) {
		this.newFraudModelActive = newFraudModelActive;
	}

	/**
	 * @return the eidVerifierFlag
	 */
	public String getEidVerifierFlag() {
		return eidVerifierFlag;
	}

	/**
	 * @param eidVerifierFlag
	 *            the eidVerifierFlag to set
	 */
	public void setEidVerifierFlag(String eidVerifierFlag) {
		this.eidVerifierFlag = eidVerifierFlag;
	}

	/**
	 * @return the allowedVRUAttempts
	 */
	public Integer getAllowedVRUAttempts() {
		return allowedVRUAttempts;
	}

	/**
	 * @param allowedVRUAttempts
	 *            the allowedVRUAttempts to set
	 */
	public void setAllowedVRUAttempts(Integer allowedVRUAttempts) {
		this.allowedVRUAttempts = allowedVRUAttempts;
	}

	/**
	 * @return the vRUExpirationHours
	 */
	public Integer getvRUExpirationHours() {
		return vRUExpirationHours;
	}

	/**
	 * @param vRUExpirationHours
	 *            the vRUExpirationHours to set
	 */
	public void setvRUExpirationHours(Integer vRUExpirationHours) {
		this.vRUExpirationHours = vRUExpirationHours;
	}

	/**
	 * @return the velocityDetection
	 */
	public Boolean getVelocityDetection() {
		return velocityDetection;
	}

	/**
	 * @param velocityDetection
	 *            the velocityDetection to set
	 */
	public void setVelocityDetection(Boolean velocityDetection) {
		this.velocityDetection = velocityDetection;
	}

	/**
	 * @return the enableCDIW
	 */
	public Boolean getEnableCDIW() {
		return enableCDIW;
	}

	/**
	 * @param enableCDIW
	 *            the enableCDIW to set
	 */
	public void setEnableCDIW(Boolean enableCDIW) {
		this.enableCDIW = enableCDIW;
	}

	/**
	 * @return the enableCDIT
	 */
	public Boolean getEnableCDIT() {
		return enableCDIT;
	}

	/**
	 * @param enableCDIT
	 *            the enableCDIT to set
	 */
	public void setEnableCDIT(Boolean enableCDIT) {
		this.enableCDIT = enableCDIT;
	}

	/**
	 * @return the enableBDIW
	 */
	public Boolean getEnableBDIW() {
		return enableBDIW;
	}

	/**
	 * @param enableBDIW
	 *            the enableBDIW to set
	 */
	public void setEnableBDIW(Boolean enableBDIW) {
		this.enableBDIW = enableBDIW;
	}

	/**
	 * @return the enableCU
	 */
	public Boolean getEnableCU() {
		return enableCU;
	}

	/**
	 * @param enableCU
	 *            the enableCU to set
	 */
	public void setEnableCU(Boolean enableCU) {
		this.enableCU = enableCU;
	}

	/**
	 * @return the enableBU
	 */
	public Boolean getEnableBU() {
		return enableBU;
	}

	/**
	 * @param enableBU
	 *            the enableBU to set
	 */
	public void setEnableBU(Boolean enableBU) {
		this.enableBU = enableBU;
	}

	/**
	 * @return the iCLite
	 */
	public Boolean getICLite() {
		return ICLite;
	}

	/**
	 * @param iCLite
	 *            the iCLite to set
	 */
	public void setICLite(Boolean iCLite) {
		this.ICLite = iCLite;
	}

	/**
	 * @return the defaultShare
	 */
	public String getDefaultShare() {
		return defaultShare;
	}

	/**
	 * @param defaultShare
	 *            the defaultShare to set
	 */
	public void setDefaultShare(String defaultShare) {
		this.defaultShare = defaultShare;
	}

	/**
	 * @return the clientId
	 */
	public Long getClientId() {
		return clientId;
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	
	/**
	 * @return the iDSPBE
	 */
	public Boolean getIDSPBE() {
		return IDSPBE;
	}

	/**
	 * @param iDSPBE
	 *            the iDSPBE to set
	 */
	public void setIDSPBE(Boolean iDSPBE) {
		IDSPBE = iDSPBE;
	}

	/**
	 * @return the bureau
	 */
	public Boolean getBureau() {
		return bureau;
	}

	/**
	 * @param bureau
	 *            the bureau to set
	 */
	public void setBureau(Boolean bureau) {
		this.bureau = bureau;
	}

	/**
	 * @return the marketMax
	 */
	public Boolean getMarketMax() {
		return marketMax;
	}

	/**
	 * @param marketMax
	 *            the marketMax to set
	 */
	public void setMarketMax(Boolean marketMax) {
		this.marketMax = marketMax;
	}

	/**
	 * @return the timeThreshold
	 */
	public Integer getTimeThreshold() {
		return timeThreshold;
	}

	/**
	 * @param timeThreshold
	 *            the timeThreshold to set
	 */
	public void setTimeThreshold(Integer timeThreshold) {
		this.timeThreshold = timeThreshold;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "System [ICLite=" + ICLite + ", IDSPBE=" + IDSPBE + ", addressPreference=" + addressPreference + ", allowedTransactionReSubmits="
				+ allowedTransactionReSubmits + ", allowedVRUAttempts=" + allowedVRUAttempts + ", bureau=" + bureau + ",  clientId="
				+ clientId + ", clientName=" + clientName + ", commercialBureauChoice1=" + commercialBureauChoice1 + ", commercialBureauChoice2="
				+ commercialBureauChoice2 + ", commercialBureauChoice3=" + commercialBureauChoice3 + ", dIEnableFlag=" + dIEnableFlag + ", defaultShare="
				+ defaultShare + ", eidVerifierFlag=" + eidVerifierFlag + ", enableBDIW=" + enableBDIW + ", enableBU=" + enableBU + ", enableCDIT="
				+ enableCDIT + ", enableCDIW=" + enableCDIW + ", enableCU=" + enableCU + ", marketMax=" + marketMax + ", name=" + name
				+ ", newFraudModelActive=" + newFraudModelActive + ", primaryBureauChoice=" + primaryBureauChoice + ", secondaryBureauChoice="
				+ secondaryBureauChoice + ", systemCode=" + systemCode + ", systemId=" + systemId + ", tertiaryBureauChoice=" + tertiaryBureauChoice
				+ ", timeThreshold=" + timeThreshold + ", vRUExpirationHours=" + vRUExpirationHours + ", velocityDetection=" + velocityDetection
				+ ", vermontCertificateOnFile=" + vermontCertificateOnFile + "]";
	}
}
