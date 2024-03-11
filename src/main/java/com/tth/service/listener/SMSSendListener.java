package com.tth.service.listener;

import com.tth.service.async.service.AsyncService;
import com.tth.service.listener.enums.EventTypeEnum;
import com.tth.service.listener.event.AsyncMessageSendEvent;
import com.tth.service.listener.annotation.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SMSSendListener implements ApplicationListener<AsyncMessageSendEvent> {

    @Autowired
    private AsyncService asyncService;

    @Override
    @EventType(value = EventTypeEnum.ASYNC)
    public void onApplicationEvent(AsyncMessageSendEvent event) {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateStr + ">>>>>>>> SMSSendListener >>>>>>>>>>> " + event);
        String[] filePaths = new String[]{
                "/SITE1700017813710/A071704419032284726261/DJI_20240105094433_0005_W.JPG",
                "/SITE1677049834894/photo/A071705046307055617130/DJI_20240112160106_0001_T_航点1.JPG",
                "/SITE1677049834894/photo/A071705046307055617130/DJI_20240112160107_0001_V_航点1.JPG"};
        for (int i = 0; i < filePaths.length; i++) {
            asyncService.downloadFtpPhoto(filePaths[i].substring(filePaths[i].lastIndexOf("/") + 1), filePaths[i]);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
