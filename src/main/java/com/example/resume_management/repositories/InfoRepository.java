package com.example.resume_management.repositories;

import com.example.resume_management.entities.Info;

public interface InfoRepository {

    public int save(Info info);

    public Info getInfoById(int id);

    public Info getInfoByCvId(int id_cv);

    public void updateInfo(Info info);

    public void deleteInfo(int id_info);
}
