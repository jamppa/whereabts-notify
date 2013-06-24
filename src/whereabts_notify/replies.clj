(ns whereabts-notify.replies
	(:use 
		[whereabts-notify.gcm]
		[whereabts-notify.db.users]
		[whereabts-notify.db.replies]))

(def reply-message-type "TYPE_MESSAGE_REPLY")

(defn- replies-gcm-message [gcm-id reply message-id]
	(gcm-message [gcm-id] 
		{
			:type reply-message-type 
			:message-id message-id
			:reply-nick (:nick reply)
			:reply-message (:replymessage reply)}))

(defn notify-user-on-reply [details]
	(if-let [user-gcm-id (find-user-gcm-id (:user-to-notify details))]
		(if-let [reply (find-reply (:reply details))]
			(send-gcm-message 
				(replies-gcm-message user-gcm-id reply (:replied-message details)))
		nil)))