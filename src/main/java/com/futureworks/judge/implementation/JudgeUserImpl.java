package com.futureworks.judge.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.futureworks.judge.bean.UserPojo;
import com.futureworks.judge.contants.Constant;
import com.futureworks.judge.database.UserRepo;
import com.futureworks.judge.exception.JudgeException;

@Component
public class JudgeUserImpl {
    
    // @Autowired
    // private UserDao userDao;

    @Autowired
    private UserRepo userDao;

    public UserPojo getUserDetail(String id) throws JudgeException {
        UserPojo user = userDao.findById(id).get();

        if(user != null && "".equalsIgnoreCase(user.getUserName()))
            return user;
        else throw new JudgeException("No data found",Constant.ERROR_CODE.DATABASE_ERROR);
    }

    public UserPojo loginInit(String userName, String password ) throws JudgeException {
        UserPojo user = userDao.findByUserName(userName);

        //Todo inti seesion id and otp
        if(user != null && !"".equalsIgnoreCase(user.getUserName()) && password.equals(user.getDecPassword()))
            return user;
        else throw new JudgeException("No data found",Constant.ERROR_CODE.DATABASE_ERROR);
    }

    public void setUser(UserPojo user) throws JudgeException {
        try{
                user.setEncPassword(user.getPassword());
                userDao.save(user);
        }catch(Exception exception){
            throw new JudgeException(exception.getMessage());
        }
    }

}
