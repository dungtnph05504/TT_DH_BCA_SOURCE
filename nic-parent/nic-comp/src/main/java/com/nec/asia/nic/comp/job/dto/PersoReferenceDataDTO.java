package com.nec.asia.nic.comp.job.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author chris_wong
 * <p>DTO for Perso Data Preparation and Perso Submission</p>
 */
/**
 * Sample Data Preparation XML:
 * 
<DataExchange source="com.nec.asia.encoding.dataexchange.datapacking" target="com.nec.asia.nic.dataexchange.datapacking" version="1.0">
  <Encoding>
    <COM>60185F0104303130375F36063034303030305C066175636B6D77</COM>
    <DG1>61105A0E4130303030303030303030303850</DG1>
    <DG2>75067F6103020100</DG2>
    <DG3>63067F6103020100</DG3>
    <DG11>6B00</DG11>
    <DG13>6D0100</DG13>
    <DG17>710E4130303030303030303030303850</DG17>
    <SOD>778207C7308207C306092A864886F70D010702A08207B4308207B0020103310F300D060960864801650304020105003081E90606678108010101A081DE0481DB3081D8020100300D060960864801650304020105003081C3302502010104207326A7D7279372EB803C535ECF1A46721EC4671FE6920DE7E043C0DFA117C57C30250201020420AF23D633BDC168C0A367AB7B98327665BD0D394335FDD008DF1AE4A140BCC8ED30250201030420E9B897987A8E9847D4CF247DE14A12161C68ABAACF6E1284CE094E0D02F07437302502010B04203038A3216FBA1E955D90ADDCE0A77D46BDEF7B51CFEB73A273989A68E093F225302502010D0420673B5AAC3CCB2CD6F565214F9ECA2173C45A924A3801A71CDA03875DF86B96F5A08204D2308204CE308203B6A003020102020A146F8D57000000000003300D06092A864886F70D01010B05003053310B300906035504061302534731193017060355040A13104E454320417369612050616369666963310C300A060355040B1303474953311B301906035504031312436F756E747279205369676E696E67204341301E170D3133303331333033343630315A170D3134303331333033353630315A306B310B3009060355040613025347311230100603550408130953696E6761706F7265311230100603550407130953696E6761706F7265310C300A060355040A13034E4543310C300A060355040B1303474953311830160603550403130F446F63756D656E74205369676E657230820122300D06092A864886F70D01010105000382010F003082010A0282010100D062FD4BA357E484AF4ABDCA80D05CCD5299440D7F79759CD0C9F211930F15882B29995CE20A04AAAB16CA32ECE580D0D6AA9B0F4B83A0FAA5DBC91CC79B0BF60AB9E94B99E9BBC58E6A8804D2840CDBAD8820967B15AB178A4DE7B1FF41BA2726B458FF4B62C70E6CF809B8CAD1A74863BF5E56FC4409E47442AA3C8BAB97E267EC82CD67125C533B4C8BDA884BE951E9641B2C61623CE68342B841544C4D7702BE40405763818A11FDCF28768E4C6654DCE5DB20AFA34079EDB9BBBAD5F170297883DEAC21B50254DF0C8A82ECE97ABAD2965FD805B906A72CE744FBD61E035C99E6601C6B752FDF19A8D46535F08A61BE9A5EC17ACCF5CC45DC57546182090203010001A382018A30820186300E0603551D0F0101FF0404030204F030130603551D25040C300A06082B06010505070301307806092A864886F70D01090F046B3069300E06082A864886F70D030202020080300E06082A864886F70D030402020080300B060960864801650304012A300B060960864801650304012D300B0609608648016503040102300B0609608648016503040105300706052B0E030207300A06082A864886F70D0307301D0603551D0E04160414ACA7AC507CA49B2B585728E5DC85D54183569466301F0603551D23041830168014F317207C6B06AA509AC778E5343C7F33CF8362C330410603551D1F043A30383036A034A032863066696C653A2F2F505453565230362F43657274456E726F6C6C2F436F756E747279205369676E696E672043412E63726C305406082B0601050507010104483046304406082B06010505073002863866696C653A2F2F505453565230362F43657274456E726F6C6C2F505453565230365F436F756E747279205369676E696E672043412E637274300C0603551D130101FF04023000300D06092A864886F70D01010B0500038201010065F028E4AC5A8D2C7B5F1F55422926108A208EB4B51FF4608C48B3B73680E303D5B726A2CB1D31D6F1F066CB387542E44B7A0EFE28893F38008D9CDB4A33AC624E55E117FF69D8C5D44C3ED1CA9ADF814F91DF058CDA4AFB59F0DD9DA550C3FF3638E2669067F37A0263B287F7791FEEBD99E786079432586909861CF676DA20712AC0B43A74DC21817FA461AE93624C4DE0C9F4BCF7761D45AB90020D29A00539C8060E7136D0C9C505F87FFBFE877355273B6BC2813AAAE6F666E248649107EDA7B4CC1C1957FAD78A79E20F0C34807458108AA9999D3218A2C44CF32F5DAF63F97D5DC018B24DC9F3D857EDEF5DD0CB08D8C0323BB8085A95073A1FF577B8318201D6308201D202010130613053311B301906035504031312434F554E545259205349474E494E47204341310C300A060355040B130347495331193017060355040A13104E454320415349412050414349464943310B3009060355040613025347020A146F8D57000000000003300D06096086480165030402010500A048301506092A864886F70D01090331080606678108010101302F06092A864886F70D01090431220420E84F7444A486D45587B191F513474DF58709D93C34498C55ECDE1CCEBFE31572300D06092A864886F70D01010B050004820100B7251C6E396E6F245E1B3DBB6DFC6482EB0B0BC772ACD9F6D57A0D0A4A25F732EEF762A68C4F8DB3EF9DA9D52984B73F906B18EC16FE92125BA080C7ABD0658CD207838669DC279D40A35DF253381D0D6E921FBD6CC72D4D95E36B0EE628991E36BB721AF3EE7A6A38C338FB4C73E4553F346D421941D9B39D58F41468A25936FBF763DC0E96924B1FA3F2F36D488317982A24260F391071802A06AA3650E26E9168AAAB2DD61367A9B0C8FDAD87DFCF599AEC0177F5F2198ACE233829F3E9B4541791D27865BE7D6A1D334F5C09DB30CA86EA140778CC5EF7A38A851C5F7E54CBE77543296A490BEDE3F786D1303A20FE2C5BE829BC69F8785644D2097BB6C0</SOD>
  </Encoding>
</DataExchange>
 *
 */
/* 
 * Modification History:
 * 
 * 04 Sep 2013 (chris): add NicTransactionStatusUpdate 
 * 21 Oct 2013 (chris): add NICRefCardStatusUpdate and NICRefTransactionStatusUpdate for CIMS
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="DataExchange")
public class PersoReferenceDataDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	/*
	 * DTO to prepare/send print card info to perso system
	 */
	@XmlElement(name = "Perso")
    protected PersoDTO perso;
	/*
	 * DTO to received data packaging info, data in chip. used to transform to PersoDTO for card info.
	 */
	@XmlElement(name = "Encoding")
    protected EncodingDTO encoding;
	/*
	 * DTO to received printed card info, such as CCN, SAMKeyVersion, PersoRefId.
	 */
	@XmlElement(name = "NicPersoInfo")
	protected NicPersoInfoDTO nicPersoInfo;
	
	@Deprecated 
	@XmlElement(name = "NicCardStatusUpdate")
	protected CcnInfoDTO cardInfoDto;
	/*
	 * DTO to received dispatch info, such as dispatch Id, packageId List
	 */
	@XmlElement(name = "NicPackageStatusUpdate")
	protected PackageInfoDTO packageInfoDto;
	/*
	 * DTO to received transaction status info update, such as ccn, transaction status of destroy card
	 */
	@XmlElement(name = "NicTransactionStatusUpdate")
	protected TransactionInfoDTO transactionInfoDto;
	
	/*
	 * DTO to send card status update to card inventory, such as suspended/terminated
	 */
	@XmlElement(name = "NICRefCardStatusUpdate")
	protected NICRefCardStatusUpdateDTO refCardStatusUpdateDto;
	
	/*
	 * DTO to send transaction status update to card inventory, such as card rejected, card expired
	 */
	@XmlElement(name = "NICRefTransactionStatusUpdate")
	protected NICRefTransactionStatusUpdateDTO refTransactionStatusUpdateDto;
	
	@XmlAttribute//(name = "source")
	protected String source;
	@XmlAttribute//(name = "target")
	protected String target;
	@XmlAttribute//(name = "version")
	protected String version;
	
	
	 /**
     * Gets the value of the perso property.
     * 
     * @return
     *     possible object is
     *     {@link PersoDTO }
     *     
     */
    public PersoDTO getPerso() {
        return perso;
    }

    /**
     * Sets the value of the perso property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersoDTO }
     *     
     */
    public void setPerso(PersoDTO value) {
        this.perso = value;
    }

	 /**
    * Gets the value of the perso property.
    * 
    * @return
    *     possible object is
    *     {@link EncodingDTO }
    *     
    */
   public EncodingDTO getEncoding() {
       return encoding;
   }

   /**
    * Sets the value of the perso property.
    * 
    * @param value
    *     allowed object is
    *     {@link EncodingDTO }
    *     
    */
   public void setEncoding(EncodingDTO value) {
       this.encoding = value;
   }
   

	public NicPersoInfoDTO getNicPersoInfo() {
		return nicPersoInfo;
	}

 	public void setNicPersoInfo(NicPersoInfoDTO value) {
 		this.nicPersoInfo = value;
 	}
   
    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

	/**
	 * @return the cardInfoDto
	 */
	public CcnInfoDTO getCardInfoDto() {
		return cardInfoDto;
	}

	/**
	 * @param cardInfoDto the cardInfoDto to set
	 */
	public void setCardInfoDto(CcnInfoDTO cardInfoDto) {
		this.cardInfoDto = cardInfoDto;
	}

	/**
	 * @return the packageInfoDto
	 */
	public PackageInfoDTO getPackageInfoDto() {
		return packageInfoDto;
	}

	/**
	 * @param packageInfoDto the packageInfoDto to set
	 */
	public void setPackageInfoDto(PackageInfoDTO packageInfoDto) {
		this.packageInfoDto = packageInfoDto;
	}

	public TransactionInfoDTO getTransactionInfoDto() {
		return transactionInfoDto;
	}

	public void setTransactionInfoDto(TransactionInfoDTO transactionInfoDto) {
		this.transactionInfoDto = transactionInfoDto;
	}

	public NICRefCardStatusUpdateDTO getRefCardStatusUpdateDto() {
		return refCardStatusUpdateDto;
	}

	public void setRefCardStatusUpdateDto(
			NICRefCardStatusUpdateDTO refCardStatusUpdateDto) {
		this.refCardStatusUpdateDto = refCardStatusUpdateDto;
	}

	public NICRefTransactionStatusUpdateDTO getRefTransactionStatusUpdateDto() {
		return refTransactionStatusUpdateDto;
	}

	public void setRefTransactionStatusUpdateDto(
			NICRefTransactionStatusUpdateDTO refTransactionStatusUpdateDto) {
		this.refTransactionStatusUpdateDto = refTransactionStatusUpdateDto;
	}
    
	
    
}
