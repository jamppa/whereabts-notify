(ns whereabts-notify.replies
	(:use 
		[whereabts-notify.gcm]
		[whereabts-notify.db.users]))

(def reply-message-type "TYPE_MESSAGE_REPLY")

(defn- replies-gcm-message [gcm-id details]
	(gcm-message [gcm-id] 
		{
			:type reply-message-type 
			:message-id (:replied-message details)
			:reply (:reply details)}))

(defn notify-user-on-reply [details]
	(if-let [user-gcm-id (find-user-gcm-id (:user-to-notify details))]
		(send-gcm-message (replies-gcm-message user-gcm-id details))
		nil))