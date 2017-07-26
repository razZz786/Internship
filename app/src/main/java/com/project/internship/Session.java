package com.project.internship;

import android.app.Application;

/**
 * Created by Rahul Bhardwaj on 7/7/2017.
 */

public class Session extends Application {
    String searchtype,applytype="",studentid="";

    String socialemail="",socialid="",Socialactype="",loginresult="",companyname,email,studentlistdata,companymessageemail,inboxoutput,inboxtype;
    int applyposition,inboxpos;
    String outboxtype="";
    String inboxtypeemp="";
    String companysearchtype;

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getCompanysearchtype() {
        return companysearchtype;
    }

    public void setCompanysearchtype(String companysearchtype) {
        this.companysearchtype = companysearchtype;
    }

    public String getApplytype() {
        return applytype;
    }

    public void setApplytype(String applytype) {
        this.applytype = applytype;
    }

    public String getInboxtypeemp() {
        return inboxtypeemp;
    }

    public void setInboxtypeemp(String inboxtypeemp) {
        this.inboxtypeemp = inboxtypeemp;
    }

    public String getOutboxtype() {
        return outboxtype;
    }

    public void setOutboxtype(String outboxtype) {
        this.outboxtype = outboxtype;
    }

    public String getSearchtype() {
        return searchtype;
    }

    public void setSearchtype(String searchtype) {
        this.searchtype = searchtype;
    }

    public String getInboxtype() {
        return inboxtype;
    }

    public void setInboxtype(String inboxtype) {
        this.inboxtype = inboxtype;
    }

    public int getInboxpos() {
        return inboxpos;
    }

    public void setInboxpos(int inboxpos) {
        this.inboxpos = inboxpos;
    }

    public String getInboxoutput() {
        return inboxoutput;
    }

    public void setInboxoutput(String inboxoutput) {
        this.inboxoutput = inboxoutput;
    }

    public String getCompanymessageemail() {
        return companymessageemail;
    }

    public void setCompanymessageemail(String companymessageemail) {
        this.companymessageemail = companymessageemail;
    }

    public int getApplyposition() {
        return applyposition;
    }

    public void setApplyposition(int applyposition) {
        this.applyposition = applyposition;
    }

    public String getStudentlistdata() {
        return studentlistdata;
    }

    public void setStudentlistdata(String studentlistdata) {
        this.studentlistdata = studentlistdata;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginresult() {
        return loginresult;
    }

    public void setLoginresult(String loginresult) {
        this.loginresult = loginresult;
    }

    public String getSocialactype() {
        return Socialactype;
    }

    public void setSocialactype(String socialactype) {
        Socialactype = socialactype;
    }

    public String getSocialemail() {
        return socialemail;
    }

    public void setSocialemail(String socialemail) {
        this.socialemail = socialemail;
    }

    public String getSocialid() {
        return socialid;
    }

    public void setSocialid(String socialid) {
        this.socialid = socialid;
    }
}
