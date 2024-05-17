package com.edusenior.project.dataTransferObjects;

import com.edusenior.project.dataTransferObjects.DatabaseQueryObjects.ChapterDTO;

import java.util.ArrayList;

public class FetchAllStudentChaptersDTO {
    private ArrayList<ChapterDTO> allChapters;

    public ArrayList<ChapterDTO> getAllChapters() {
        return allChapters;
    }

    public void setAllChapters(ArrayList<ChapterDTO> allChapters) {
        this.allChapters = allChapters;
    }
}
