package com.isxcode.star.modules.alarm.message;

import com.isxcode.star.api.alarm.constants.AlarmSendStatus;
import com.isxcode.star.backend.api.base.exceptions.IsxAppException;
import com.isxcode.star.modules.alarm.entity.AlarmInstanceEntity;
import com.isxcode.star.modules.alarm.repository.AlarmInstanceRepository;

import static com.isxcode.star.common.config.CommonConfig.TENANT_ID;

public abstract class MessageRunner implements MessageAction {

	private final AlarmInstanceRepository alarmInstanceRepository;

	protected MessageRunner(AlarmInstanceRepository alarmInstanceRepository) {
		this.alarmInstanceRepository = alarmInstanceRepository;
	}

	public void send(MessageContext messageContext) {

		TENANT_ID.set(messageContext.getTenantId());

		try {
			sendMessage(messageContext);
			// 发送成功，写入实例
			AlarmInstanceEntity alarmInstanceEntity = messageContextToAlarmInstanceEntity(messageContext);
			alarmInstanceEntity.setSendStatus(AlarmSendStatus.SUCCESS);
			alarmInstanceRepository.save(alarmInstanceEntity);
		} catch (Exception e) {

			// 发送失败，写入实例
			AlarmInstanceEntity alarmInstanceEntity = messageContextToAlarmInstanceEntity(messageContext);
			alarmInstanceEntity.setSendStatus(AlarmSendStatus.FAIL);
			alarmInstanceRepository.save(alarmInstanceEntity);
			throw new IsxAppException("运行异常，" + e.getMessage());
		}
	}

	protected AlarmInstanceEntity messageContextToAlarmInstanceEntity(MessageContext messageContext) {

		return AlarmInstanceEntity.builder().alarmId(messageContext.getAlarmId())
				.alarmType(messageContext.getAlarmType()).alarmEvent(messageContext.getAlarmEvent())
				.msgType(messageContext.getMsgType()).content(messageContext.getContent())
				.receiver(messageContext.getReceiver()).sendDateTime(messageContext.getSendDateTime()).build();
	}
}
