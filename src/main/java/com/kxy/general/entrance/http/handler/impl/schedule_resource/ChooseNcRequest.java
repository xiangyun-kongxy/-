package com.kxy.general.entrance.http.handler.impl.schedule_resource;

import com.kxy.general.entrance.http.handler.AbstractActionRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by xiangyunkong on 03/05/2017.
 */
public class ChooseNcRequest extends AbstractActionRequest {
    private static final long serialVersionUID = 4321560594688465745L;

    /**
     * how many cpu is required.
     */
    @Getter @Setter
    private Long cpu;
    /**
     * how many memory is required.
     */
    @Getter @Setter
    private Long memory;
    /**
     * how many pps is required.
     */
    @Getter @Setter
    private Long pps;
    /**
     * how many bps is required.
     */
    @Getter @Setter
    private Long bps;
    /**
     * how much nc load at most.
     */
    @Getter @Setter
    private Double ncLoad;
}
