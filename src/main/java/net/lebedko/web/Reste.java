package net.lebedko.web;

import net.lebedko.entity.user.UserRole;

public class Reste {
    public static void main(String[] args) {
        Object o = UserRole.CLIENT;
        UserRole client = UserRole.CLIENT;

        System.out.println(o.equals(client));
    }
}
