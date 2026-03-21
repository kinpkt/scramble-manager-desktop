package com.thailandcube.repositories;

import com.thailandcube.models.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoleList implements Repository<Role> {
    private List<Role> roles;

    public RoleList() {
        roles = new ArrayList<Role>();
    }

    @Override
    public void add(Role item) {
        roles.add(item);
    }

    @Override
    public void remove(Role item) {
        roles.remove(item);
    }

    @Override
    public List<Role> getAll() {
        return Collections.unmodifiableList(roles);
    }
}
