import com.tecit.TFORMer.Enumerations;
import com.tecit.TFORMer.Printing.Job;
import com.tecit.TFORMer.Printing.JobDataRecordSet;

/**
 * \* User: ths
 * \* Date: 2017/9/28
 * \* Time: 15:50
 * \* Description:
 * \
 */

public class Test {

    public static void main(String[] args){

        Test test = new Test();
        JobDataRecordSet jobDataRecordSet = new JobDataRecordSet();
        JobDataRecordSet.Record record = new JobDataRecordSet.Record();
        record.setData("name","ths");
        jobDataRecordSet.add(record);
        test.print(jobDataRecordSet);
    }


    public void print(JobDataRecordSet recordSet){
        if(recordSet == null ){

        }
        try{
            TECITLicence.Register();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            Job job = new Job();
            JobDataRecordSet dataRecordSet = new JobDataRecordSet();
            job.setJobData(dataRecordSet);
            // 设置模板名称
            job.setRepositoryName("d:/Label.tff");

            // 文件输出目录
            job.setOutputName("label.pdf");

            job.setPrinterType(Enumerations.EPrinterType.PSFile);

            JobDataRecordSet.Record record = new JobDataRecordSet.Record();
            record.setData("name", "ths");
            job.print();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
