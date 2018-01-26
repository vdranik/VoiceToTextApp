package com.voicetotext.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NamedQuery(name = "find_all_audiotexts", query = "select a from AudioText a")
public class AudioText {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonProperty("RecognitionStatus")
    @NotNull
    private String recognitionStatus;

    @JsonProperty("DisplayText")
    @Column(length = Integer.MAX_VALUE)
    private String displayText;

    @JsonProperty("Offset")
    @Column(name = "off_set")
    @NotNull
    private Long offset;

    @JsonProperty("Duration")
    @NotNull
    private Long duration;

    public AudioText() {
    }

    public AudioText(int id, String recognitionStatus, String displayText, Long offset, Long duration) {
        this.id = id;
        this.recognitionStatus = recognitionStatus;
        this.displayText = displayText;
        this.offset = offset;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecognitionStatus() {
        return recognitionStatus;
    }

    public void setRecognitionStatus(String recognitionStatus) {
        this.recognitionStatus = recognitionStatus;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "\n\nAudioText{" +
                "\n    id=" + id +
                ", \n   recognitionStatus='" + recognitionStatus + '\'' +
                ", \n   displayText='" + displayText + '\'' +
                ", \n   offset=" + offset +
                ", \n   duration=" + duration +
                '}';
    }
}
