package com.kingyee.cats.export;

import com.kingyee.cats.db.CatsExpertGroupDetail;
import com.kingyee.cats.db.CatsSurveyFinishRecord;
import com.kingyee.cats.enums.YesNoEnum;
import com.kingyee.common.export.BaseXlsTemplate;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;

public class SurveyFinishRecordXlsTemplate extends BaseXlsTemplate {

    /**
     *
     * @param sheet Excel的sheet
     * @param xlsContentList bean的list
     * @return
     * @throws Exception
     */
    @Override
    protected HSSFSheet createContent(HSSFSheet sheet, List<?> xlsContentList)
            throws Exception {
        for(int i = 0; i < xlsContentList.size(); i++){
            HSSFRow row = createRow(sheet, i + 1);
            Object[] obj = (Object[])xlsContentList.get(i);
            CatsSurveyFinishRecord finishRecord = (CatsSurveyFinishRecord)obj[0];
            CatsExpertGroupDetail expert = (CatsExpertGroupDetail)obj[1];

            // "ID", "姓名", "医院", "科室", "职称", "完成时间", "奖励"

            createCell(row, finishRecord.getCsfrId());
            createCell(row, expert.getCegdRealName());
            createCell(row, expert.getCegdHospital());
            createCell(row, expert.getCegdDept());
            createCell(row, expert.getCegdProfessional());
            createCell(row, finishRecord.getCsfrFinishTimeStr());
            if(finishRecord.getCsfrHadIssueReward().equals(YesNoEnum.YES.ordinal())){
                createCell(row, "已发放");
            }else{
                createCell(row, "未发放");
            }
        }
        return sheet;
    }
}
