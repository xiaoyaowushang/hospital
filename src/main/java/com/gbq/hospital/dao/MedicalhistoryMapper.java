package com.gbq.hospital.dao;

import com.gbq.hospital.entity.Medicalhistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MedicalhistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Medicalhistory record);

    int insertSelective(Medicalhistory record);

    Medicalhistory selectByPrimaryKey(Integer id);
    List<Medicalhistory> selectByPatientId(@Param("patientid")Integer patientid);

    int updateByPrimaryKeySelective(Medicalhistory record);

    int updateByPrimaryKey(Medicalhistory record);
    List<Medicalhistory> findAll(List<Integer> doctorids,List<Integer> patientids);
}