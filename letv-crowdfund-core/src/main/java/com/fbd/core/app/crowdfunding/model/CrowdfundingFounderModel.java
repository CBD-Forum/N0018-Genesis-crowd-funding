package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import java.util.List;
import com.fbd.core.base.BaseModel;

public class CrowdfundingFounderModel extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String loanNo;

    private String name;

    private String position;

    private Date createTime;
    
    /**教育经历**/
    private List<CrowdfundingFounderEducationsModel> educationList;
    
    /**工作经历**/
    private List<CrowdfundingFounderWorksModel> workList;
    
    /**创业经历**/
    private List<CrowdfundingFounderBusinessModel> businessList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    

    /**
     * @return the loanNo
     */
    public String getLoanNo() {
        return loanNo;
    }

    /**
     * @param loanNo the loanNo to set
     */
    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the educationList
     */
    public List<CrowdfundingFounderEducationsModel> getEducationList() {
        return educationList;
    }

    /**
     * @param educationList the educationList to set
     */
    public void setEducationList(
            List<CrowdfundingFounderEducationsModel> educationList) {
        this.educationList = educationList;
    }

    /**
     * @return the workList
     */
    public List<CrowdfundingFounderWorksModel> getWorkList() {
        return workList;
    }

    /**
     * @param workList the workList to set
     */
    public void setWorkList(List<CrowdfundingFounderWorksModel> workList) {
        this.workList = workList;
    }

    /**
     * @return the businessList
     */
    public List<CrowdfundingFounderBusinessModel> getBusinessList() {
        return businessList;
    }

    /**
     * @param businessList the businessList to set
     */
    public void setBusinessList(List<CrowdfundingFounderBusinessModel> businessList) {
        this.businessList = businessList;
    }
    
    
}