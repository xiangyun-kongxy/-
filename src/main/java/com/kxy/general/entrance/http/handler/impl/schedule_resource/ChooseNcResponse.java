package com.kxy.general.entrance.http.handler.impl.schedule_resource;

import com.kxy.general.entrance.http.handler.ActionResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by xiangyunkong on 03/05/2017.
 */
public class ChooseNcResponse implements ActionResponse {
    private static final long serialVersionUID = -1615881142506162322L;

    /**
     * the chosen nc.
     */
    @Getter @Setter
    private String ncId;
}
