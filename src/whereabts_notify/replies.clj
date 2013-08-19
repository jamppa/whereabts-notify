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
			:reply-nick (get-in reply [:user-profile :nick])
			:reply-message (get-in reply [:replymessage])}))

(defn find-reply-with-user [reply-id]
	(-> reply-id
		(find-reply)
		(with-profile)))

(defn notify-message-owner-on-reply [reply user-gcm-id message-id]
	(when (every? identity [reply user-gcm-id message-id])			; there is a change that user-gcm-id is missing
			(send-gcm-message 
				(replies-gcm-message user-gcm-id reply message-id))))

(defn notify-message-owner [reply-details]
	(let [reply (find-reply-with-user (:reply-id reply-details))
		  message-user (:user-id reply-details)
		  reply-user (.toString (:user_id reply))
		  message-user-gcm (find-user-gcm-id message-user)]

		(when-not (= message-user reply-user)
			(notify-message-owner-on-reply 
				reply message-user-gcm (:message-id reply-details)))))

(defn notify-message-repliers [reply-details]
	nil)