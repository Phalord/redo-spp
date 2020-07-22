package com.spp.model.domain;

public class SelfAppraisal {
    private int selfAppraisalID;
    private Practitioner author;
    private byte firstSentence;
    private byte secondSentence;
    private byte thirdSentence;
    private byte fourthSentence;
    private byte fifthSentence;

    public final void setSelfAppraisalID(int selfAppraisalID) {
        this.selfAppraisalID = selfAppraisalID;
    }

    public final int getSelfAppraisalID() {
        return selfAppraisalID;
    }

    public final void setAuthor(Practitioner author) {
        this.author = author;
    }

    public final Practitioner getAuthor() {
        return author;
    }

    public final void setFirstSentence(byte firstSentence) {
        this.firstSentence = firstSentence;
    }

    public final byte getFirstSentence() {
        return firstSentence;
    }

    public final void setSecondSentence(byte secondSentence) {
        this.secondSentence = secondSentence;
    }

    public final byte getSecondSentence() {
        return secondSentence;
    }

    public final void setThirdSentence(byte thirdSentence) {
        this.thirdSentence = thirdSentence;
    }

    public final byte getThirdSentence() {
        return thirdSentence;
    }

    public final void setFourthSentence(byte fourthSentence) {
        this.fourthSentence = fourthSentence;
    }

    public final byte getFourthSentence() {
        return fourthSentence;
    }

    public final void setFifthSentence(byte fifthSentence) {
        this.fifthSentence = fifthSentence;
    }

    public final byte getFifthSentence() {
        return fifthSentence;
    }
}
