package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.exception.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class EntityService<Entity, Dto> {
    @Autowired
    protected GroupService groupService;
    @Autowired
    protected CurrentUserService currentUserService;

    /**
     * Maps an entity of type *Entity* to the corresponding DTO.
     * This is a utility function for controllers to use.
     */
    public abstract Dto map(Entity e);

    protected void throwIfUserNotPartOfGroup(final Integer groupId) {
        final User currentUser = currentUserService.getCurrentUser();

        if (!groupService.isUserInGroup(currentUser.getId(), groupId)) {
            throw new PermissionDeniedException();
        }
    }
}
