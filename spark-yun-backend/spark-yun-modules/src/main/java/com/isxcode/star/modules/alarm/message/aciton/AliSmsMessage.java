package com.isxcode.star.modules.alarm.message.aciton;

import com.isxcode.star.api.alarm.constants.MessageType;
import com.isxcode.star.modules.alarm.message.MessageContext;
import com.isxcode.star.modules.alarm.message.MessageRunner;
import com.isxcode.star.modules.alarm.repository.AlarmInstanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AliSmsMessage extends MessageRunner {

	protected AliSmsMessage(AlarmInstanceRepository alarmInstanceRepository) {
		super(alarmInstanceRepository);
	}

	@Override
	public String getActionName() {
		return MessageType.ALI_SMS;
	}

	@Override
	public void sendMessage(MessageContext messageContext) {
		System.out.println("发送阿里云短信");
	}
}
