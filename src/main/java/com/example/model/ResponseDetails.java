package com.example.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ResponseDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String messageId;
	private Integer messageCount;
	private String status;
	private String sender;
	private String recipient;
	private Date sentAt; 
	
	
	
	public ResponseDetails() {
		super();
	}

	public ResponseDetails(String messageId, Integer messageCount, String status, String sender, String to) {
		super();
		this.messageId = messageId;
		this.messageCount = messageCount;
		this.status = status;
		this.sender = sender;
		this.recipient = to;
		this.sentAt = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public Integer getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getTo() {
		return recipient;
	}
	public void setTo(String to) {
		this.recipient = to;
	}
	public Date getSentAt() {
		return sentAt;
	}
	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}

	@Override
	public String toString() {
		return "ResponseDetails [messageId=" + messageId + ", messageCount=" + messageCount + ", status=" + status
				+ ", from=" + sender + ", to=" + recipient + ", sentAt=" + sentAt + "]";
	}
	
	

}
