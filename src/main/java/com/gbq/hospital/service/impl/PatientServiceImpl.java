package com.gbq.hospital.service.impl;

import com.gbq.hospital.common.CommonService;
import com.gbq.hospital.dao.LoginMapper;
import com.gbq.hospital.dao.PatientMapper;
import com.gbq.hospital.entity.Login;
import com.gbq.hospital.entity.Patient;
import com.gbq.hospital.service.LoginService;
import com.gbq.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientMapper patientMapper;
    @Autowired
    LoginMapper loginMapper;
    @Override
    public List<Patient> getAllPatients(String name,String certId) {
        return patientMapper.findAll(name,certId);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientMapper.findAll("","");
    }

    @Override
    public String delPatient(Integer id) {
        return patientMapper.deleteByPrimaryKey(id)>0? CommonService.del_message_success:CommonService.del_message_error;
    }

    @Override
    public Patient getPatient(Integer id) {
        Patient patient=patientMapper.selectByPrimaryKey(id);
        Login login=loginMapper.selectByPrimaryKey(patient.getLoginid());
        if (login!=null){
            patient.setUsername(login.getUsername());
            patient.setPassword(login.getPassword());
        }
        return patient;
    }

    @Override
    public String updatePatient(Patient patient) {
        patientMapper.updateByPrimaryKey(patient);
        Login login=loginMapper.selectByPrimaryKey(patient.getLoginid());
        login.setUsername(patient.getUsername());
        login.setPassword(patient.getPassword());
        login.setId(patient.getLoginid());
        Login byUsername = loginMapper.findByUsername(patient.getUsername());
        if (byUsername != null){
            return CommonService.upd_message_error;
        }
        loginMapper.updateByPrimaryKey(login);
        return CommonService.upd_message_success;
    }

    @Override
    public String addPatient(Patient patient) {
        String message="";
        if(patientMapper.findPatientByCertId(patient.getCertId())!=null){
            message=CommonService.add_message_error2;
        }
        else{
            patient.setIsout(0);
            Login login= new Login();
            login.setRole(3);
            loginMapper.insertSelective(login);
            patient.setIsout(0);
            patient.setLoginid(login.getId());
            message=patientMapper.insert(patient)>0?CommonService.add_message_success:CommonService.add_message_error;
        }
        return message;
    }

    @Override
    public String seek(Patient patient) {
        patientMapper.updateByPrimaryKeySelective(patient);
        return CommonService.upd_message_success;
    }

    @Override
    public Patient findPatientByLoginId(Integer loginid) {
        return patientMapper.findPatientByLoginId(loginid);
    }

    @Override
    public String updateAppointMent(Patient patient) {
        return patientMapper.updateByPrimaryKeySelective(patient)>0?CommonService.upd_message_success:CommonService.upd_message_error;
    }
}
