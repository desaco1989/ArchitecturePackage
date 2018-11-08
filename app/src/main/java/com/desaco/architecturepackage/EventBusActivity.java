package com.desaco.architecturepackage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by desaco on 2018/11/7.
 */

public class EventBusActivity {

    public void sendMsg(){
        EventBus.getDefault().post("msg");
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().cancelEventDelivery("");
        EventBus.getDefault().removeStickyEvent("");
    }
}
