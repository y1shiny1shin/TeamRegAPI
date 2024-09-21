package com.jixiecompitionregistration.Util;

import com.jixiecompitionregistration.dao.DbSelect;
import com.jixiecompitionregistration.dao.admin.DbAdminSelect;
import jakarta.servlet.http.HttpSession;

public class SessionIdCheck {
    public static boolean adminCheck(HttpSession sessionId)
    {
        String adminName = (String) sessionId.getAttribute("adminName");
        String adminPassword = (String) sessionId.getAttribute("adminPassword");

        if ((adminName == null || adminPassword == null) || !new DbAdminSelect().isAdmin(adminName ,adminPassword)){
            return false;
        }
        return true;
    }
}
