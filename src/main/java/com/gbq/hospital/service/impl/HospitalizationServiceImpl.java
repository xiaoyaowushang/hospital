package com.gbq.hospital.service.impl;

import com.gbq.hospital.common.CommonService;
import com.gbq.hospital.dao.HospitalizationMapper;
import com.gbq.hospital.dao.PatientMapper;
import com.gbq.hospital.entity.Hospitalization;
import com.gbq.hospital.entity.Patient;
import com.gbq.hospital.uitls.PatientDoctorutils;
import com.gbq.hospital.service.HospitalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HospitalizationServiceImpl implements HospitalizationService {
    @Autowired
    HospitalizationMapper hospitalizationMapper;
    @Autowired
    PatientMapper patientMapper;
    @Override
    public List<Hospitalization> getAllHospitalizations(String patientname, String intime) {
        List<Integer> patientIds= PatientDoctorutils.getPatientIds(patientname,patientMapper);
        return hospitalizationMapper.findAll(patientIds,intime);
    }

    @Override
    public List<Hospitalization> getAllHospitalizations() {
        return hospitalizationMapper.findAll(null,null);
    }

    @Override
    public String AddHospitalization(Hospitalization hospitalization) {
        hospitalizationMapper.insert(hospitalization);
        Patient patient = patientMapper.selectByPrimaryKey(hospitalization.getPatientid());
        patient.setIsout(1);
        patient.setHospitalizationid(hospitalization.getId());
        patientMapper.updateByPrimaryKeySelective(patient);
        return CommonService.add_message_success;
    }

    @Override
    public String deleteHospitalization(Integer id) {
        return hospitalizationMapper.deleteByPrimaryKey(id)>0?CommonService.del_message_success:CommonService.add_message_error;
    }

    @Override
    public Hospitalization getHospitalization(Integer id) {
        return hospitalizationMapper.selectByPrimaryKey(id);
    }

    @Override
    public String updateHospitalization(Hospitalization hospitalization) {
        if (hospitalization.getOuttime()!= null && hospitalization.getOuttime().after(new Date())){
            Patient patient = patientMapper.selectByPrimaryKey(hospitalization.getPatientid());
            if (patient.getIsout() == 1){
                patient.setIsout(2);
                patientMapper.updateByPrimaryKeySelective(patient);
            }
        }
        return hospitalizationMapper.updateByPrimaryKey(hospitalization)>0?CommonService.upd_message_success:CommonService.upd_message_error;
    }

    @Override
    public List<Hospitalization> getPatientMessage(Integer patientId) {
        return hospitalizationMapper.selectByPatientId(patientId);
    }

    @Override
    public Hospitalization findTheLastHospitalization(Integer id) {
        return hospitalizationMapper.findTheLastHospitalization(id);
    }

    @Override
    public List<Hospitalization> findOtherHospitalization(Hospitalization hospitalization) {
        return hospitalizationMapper.findOtherHospitalization(hospitalization);
    }
}
