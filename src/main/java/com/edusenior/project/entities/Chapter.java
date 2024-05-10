package com.edusenior.project.entities;

import com.edusenior.project.Exceptions.FullChapterException;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Blob;

@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "chapter_id", nullable = false,updatable = false)

    private String id;



    @Column(name = "name")
    private String name;

    @Column(name = "chapter_number")
    private int number;

    @Column(name = "description")
    private String description;

    @Column(name = "material_amount")
    private int materialAmount;

    @Lob
    @Column(name = "banner")
    private Blob bannerImage;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id")
    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Blob getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(Blob bannerImage) {
        this.bannerImage = bannerImage;
    }

    public int getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(int materialAmount) {
        this.materialAmount = materialAmount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int addToMaterialAmount(int a) throws FullChapterException{
        if(this.materialAmount == Integer.MAX_VALUE){
            throw new FullChapterException("Chapter "+this.name+" reached the maximum amount of allowed material");
        }
        if(a <= 0){
            return this.materialAmount;
        }
        return this.materialAmount += a ;

    }
}
