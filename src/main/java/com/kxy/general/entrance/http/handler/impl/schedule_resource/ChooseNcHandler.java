package com.kxy.general.entrance.http.handler.impl.schedule_resource;

import com.kxy.general.beta.relation.compare.Greater;
import com.kxy.general.beta.resource.Resource;
import com.kxy.general.beta.rule.RuleSet;
import com.kxy.general.beta.rule.impl.SimpleRule;
import com.kxy.general.beta.service.ScheduleService;
import com.kxy.general.beta.value.direct.Level;
import com.kxy.general.beta.value.mapping.ResourceAddressValue;
import com.kxy.general.common.exception.BizException;
import com.kxy.general.common.exception.error_info.ErrorInfo;
import com.kxy.general.entrance.http.handler.AbstractActionRequest;
import com.kxy.general.entrance.http.handler.ActionHandler;
import com.kxy.general.entrance.http.handler.ActionRequest;
import com.kxy.general.entrance.http.handler.ActionResponse;
import lombok.Setter;

import java.util.List;

/**
 * Created by xiangyunkong on 03/05/2017.
 */
public class ChooseNcHandler implements ActionHandler {

    /**
     * schedule service.
     */
    @Setter
    private ScheduleService scheduleService;

    /**
     * @see ActionHandler#getRequestClass()
     */
    @Override
    public Class<? extends AbstractActionRequest> getRequestClass() {
        return ChooseNcRequest.class;
    }

    /**
     * @see ActionHandler#actionName()
     */
    @Override
    public String actionName() {
        return "choose_nc";
    }

    /**
     * choose a resource of type nc by parameters.
     * @param request the request to process
     * @return the chosen nc
     */
    @Override
    public ActionResponse handle(ActionRequest request) throws BizException {
        ChooseNcRequest r = (ChooseNcRequest) request;
        RuleSet ruleSet = new RuleSet();
        ruleSet.addRule(new SimpleRule(new Greater(),
                new ResourceAddressValue("cpu"),
                new Level(r.getCpu())));

        ruleSet.addRule(new SimpleRule(new Greater(),
                new ResourceAddressValue("memory"),
                new Level(r.getMemory())));

        ruleSet.addRule(new SimpleRule(new Greater(),
                new ResourceAddressValue("pps"),
                new Level(r.getPps())));

        ruleSet.addRule(new SimpleRule(new Greater(),
                new ResourceAddressValue("bps"),
                new Level(r.getBps())));

        List<Resource> resources = scheduleService.chooseResource(ruleSet);
        if (resources != null && resources.size() > 0) {
            ChooseNcResponse response = new ChooseNcResponse();
            response.setNcId(resources.get(0).getResourceId());
            return response;
        }
        throw new BizException(ErrorInfo.E_NOT_ENOUGH_RESOURCE);
    }
}
