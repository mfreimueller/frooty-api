package com.mfreimueller.frooty.aspect;

import com.mfreimueller.frooty.annotations.CheckPlanAccess;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.PlanRepository;
import com.mfreimueller.frooty.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Aspect
@Component
public class PlanAccessAspect {
    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserService userService;

    @Before("@annotation(checkPlanAccess)")
    public void checkAccess(JoinPoint joinPoint, CheckPlanAccess checkPlanAccess) {
        final MethodSignature signature = ((MethodSignature) joinPoint.getSignature());
        final String[] parameterNames = signature.getParameterNames();

        int indexOfPlanIdParam = -1;
        for (int idx = 0; idx < parameterNames.length; idx++) {
            if (parameterNames[idx].equals(checkPlanAccess.planIdParam())) {
                indexOfPlanIdParam = idx;
                break;
            }
        }

        Assert.isTrue(indexOfPlanIdParam != -1, "CheckPlanAccess annotation is wrongly configured! Expected parameter named: " + checkPlanAccess.planIdParam());

        final Integer planId = (Integer) joinPoint.getArgs()[indexOfPlanIdParam];
        final User currentUser = userService.getCurrentUser();

        boolean hasAccess;
        if (checkPlanAccess.asOwner()) {
            hasAccess = planRepository.existsByIdAndOwner_Id(planId, currentUser.getId());
        } else {
            hasAccess = planRepository.isUserAuthorizedForPlan(planId, currentUser.getId());
        }

        if (!hasAccess) {
            throw new EntityNotFoundException(planId);
        }
    }
}
