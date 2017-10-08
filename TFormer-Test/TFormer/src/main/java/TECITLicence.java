import com.tecit.TFORMer.Enumerations;
import com.tecit.TFORMer.TFORMer;
import com.tecit.TFORMer.TFormerException;

/**
 * \* User: ths
 * \* Date: 2017/9/28
 * \* Time: 16:27
 * \* Description: license 注册
 * \
 */

public class TECITLicence {
    private static  String pf_licensee = "Mem: Cai Zhuo Information & Technology Co.,Ltd";
    private static  String pf_licenseKey = "45D392D071FA5767B4D85D2EE7ED7E76";
    private static  int pf_numberOfLicense = 1;

    private static Enumerations.ELicenseKind  pf_licenseKind = Enumerations.ELicenseKind.Developer;

    public static void Register()
    {
        try {
            TFORMer.license(pf_licensee, pf_licenseKind, pf_numberOfLicense, pf_licenseKey);
        }catch (TFormerException e){
            e.printStackTrace();
        }
    }
}
