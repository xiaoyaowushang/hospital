package com.gbq.hospital.service.impl;

import com.gbq.hospital.common.CommonService;
import com.gbq.hospital.dao.DoctorMapper;
import com.gbq.hospital.dao.MedicalhistoryMapper;
import com.gbq.hospital.dao.PatientMapper;
import com.gbq.hospital.entity.Medicalhistory;
import com.gbq.hospital.uitls.PatientDoctorutils;
import com.gbq.hospital.service.MedicalhistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MedicalhistoryServiceImpl implements MedicalhistoryService {
    @Autowired
    MedicalhistoryMapper medicalhistoryMapper;
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    PatientMapper patientMapper;
    @Override
    public List<Medicalhistory> getAllMedicalhistorys(String doctorname, String patientname) {
        Map map= PatientDoctorutils.getDoctorIdsAndPatientIds(doctorname,doctorMapper,patientname,patientMapper);
        return medicalhistoryMapper.findAll((List)map.get("doctorids"),(List)map.get("patientids"));
    }

    @Override
    public String delMedicalhistory(Integer id) {
        return medicalhistoryMapper.deleteByPrimaryKey(id)>0? CommonService.del_message_success:CommonService.del_message_error;
    }

    @Override
    public Medicalhistory getMedicalhistory(Integer id) {
        return medicalhistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public String UpdateMedicalhistory(Medicalhistory medicalhistory) {
        return medicalhistoryMapper.updateByPrimaryKey(medicalhistory)>0?CommonService.upd_message_success:CommonService.upd_message_error;
    }

    @Override
    public String addMedicalhistory(Medicalhistory medicalhistory) {
        return medicalhistoryMapper.insert(medicalhistory)>0?CommonService.add_message_success:CommonService.add_message_error;
    }

    @Override
    public List<Medicalhistory> getMedicalhistoryByPatientId(Integer id) {
        return medicalhistoryMapper.selectByPatientId(id);
    }
}
